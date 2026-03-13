import datetime

# global variables
current = datetime.datetime.now()
br = '---------------------------------'

def issueLogic(bookData, transactData, bookInput, studentInput, fullDate):
    """docstring for issueLogic. Control how issuing is handled
    :param bookInput: Book ID
    :param studentInput: student ID
    :param fullDate: combined date
    :param bookData: "books" dictionary
    :param transactData: "transactions" dictionary"""
    issueCount = studentIssueCount = bookIndex = lastIndex = 0

    for index, transactType in enumerate(transactData['type'].values()): # find no. of times a book has been issued
        if transactData['bookId'][index] == bookInput and transactType == 1:
            issueCount += 1
        elif transactData['bookId'][index] == bookInput and transactType == 2:
            issueCount -= 1
            if issueCount < 0: issueCount = 0  # reset issue counting to 0 if it goes to -1
        if (
            transactData['studentId'][index] == int(studentInput) and # find if the student already has a copy of specified book
            transactData['bookId'][index] == bookInput and
            transactType == 1
        ):
            studentIssueCount += 1
        elif (
            transactData['studentId'][index] == int(studentInput) and
            transactData['bookId'][index] == bookInput and
            transactType == 2
        ):
            studentIssueCount -= 1
            if studentIssueCount < 0: studentIssueCount = 0  # reset student issue counting to 0 if it goes to -1

    for i in bookData['id'].values(): # find index location (row) of required info across all keys
        bookIndex += 1
        if i == bookInput:
            bookIndex = bookIndex - 1
            break

    if issueCount >= bookData['available'][bookIndex]:
        print('\nNo copies are available to issue.')
    elif issueCount < bookData['available'][bookIndex]:
        if studentIssueCount >= 1:
            print('\nA copy of specified book has already been issued to the student.')
        else:
            lastIndex = len(transactData['date'].values())  # get index location of final data row

            transactData['date'][lastIndex] = fullDate
            transactData['bookId'][lastIndex] = bookInput
            transactData['studentId'][lastIndex] = int(studentInput)
            transactData['type'][lastIndex] = 1
            print('\nTransaction Recorded.')

    return transactData

def returnLogic(transactData, bookInput, studentInput, fullDate):
    """docstring for issueLogic. Control how issuing is handled
    :param bookInput: Book ID
    :param studentInput: student ID
    :param fullDate: combined date
    :param bookData: "books" dictionary
    :param transactData: "transactions" dictionary"""
    returnCount = 0

    for index, transactType in enumerate(transactData['type'].values()): # find if book has been issued to student 
        if (
            transactData['studentId'][index] == int(studentInput) and
            transactData['bookId'][index] == bookInput and
            transactType == 1
        ):
            returnCount-=1
            if returnCount < 0: returnCount = 0  # reset return counting to 0 if it goes to -1
        elif (
            transactData['studentId'][index] == int(studentInput) and # find if book has been returned by student
            transactData['bookId'][index] == bookInput and
            transactType == 2
        ):
            returnCount+=1

    if returnCount == 0:
        lastIndex = len(transactData['date'].values())  # get index location of final data row

        transactData['date'][lastIndex] = fullDate
        transactData['bookId'][lastIndex] = bookInput
        transactData['studentId'][lastIndex] = int(studentInput)
        transactData['type'][lastIndex] = 2
        print('\nTransaction Recorded.')
    else:
        print('Specified book has already been returned.')

    print(br) # line break
    return transactData

def issue_book(book_data, student_data, transact_data):
    """docstring for issue_book
    :param book_data: "books" dictionary
    :param student_data: "students" dictionary
    :param transact_data: "transactions" dictionary"""
    full_date = day_input = month_input = year_input = inner_val = book_input = student_input = ''

    print('Issue Book\n\nPress ENTER to cancel.')

    while True:
        book_input = input('Enter Book ID: ')
        if book_input == '':
            print(br)
            return transact_data # go to main menu
        if book_input not in book_data['id'].values():  # evaluate if id already exists
            print('>> Entered Book ID does not exist <<\n')
            continue

        student_input = str(input('Enter Student ID: '))
        if student_input == '':
            print(br)
            return transact_data # go to main menu
        if not (student_input.isnumeric() == True and len(student_input) == 8): # input validation
            print('>> Entered ID is not valid <<\n')
            continue
        if int(student_input) not in student_data['id'].values():  # evaluate if id already exists
            print('>> Entered Student ID does not exist <<\n')
            continue

        print('\nIssue Date...')
        day_input = str(input('\tEnter day (1-31): '))
        if day_input == '':
            print(br)
            return transact_data # go to main menu
        if not (day_input.isnumeric() and 31 >= int(day_input) >= 1): # input validation
            print('>> Entered value is invalid <<\n')
            continue

        month_input = str(input('\tEnter month (1-12): '))
        if month_input == '':
            print(br)
            return transact_data # go to main menu
        if not (month_input.isnumeric() and 12 >= int(month_input) >= 1): # input validation
            print('>> Entered value is invalid <<\n')
            continue

        year_input = str(input('\tEnter year (minimum: 2000): '))
        if year_input == '':
            print(br)
            return transact_data # go to main menu
        if not (year_input.isnumeric() and 2000 <= int(year_input) <= int(current.strftime('%Y'))): # input validation
            print('>> Entered value is invalid <<\n')
            continue
        break

    full_date = ( # adjust date format, add trailing zero
        f'{'{:02d}'.format(int(day_input))}/'
        f'{'{:02d}'.format(int(month_input))}/'
        f'{year_input}'
    )

    try:
        transact_data = issueLogic(
            book_data,
            transact_data,
            book_input,
            student_input,
            full_date
        )
    except Exception as e:
        print(f'Error occurred while accessing dictionary: {e} ')

    print(br) # line break
    return transact_data

def return_book(book_data, student_data, transact_data):
    """docstring for return_book
    :param book_data: "books" dictionary
    :param student_data: "students" dictionary
    :param transact_data: "transactions" dictionary"""

    print('Return Book\n\nPress ENTER to cancel.')

    while True:
        book_input = input('Enter Book ID: ')
        if book_input == '':
            print(br)
            return transact_data # go to main menu
        if book_input not in book_data['id'].values():  # evaluate if id already exists
            print('>> Entered Book ID does not exist <<\n')
            continue

        student_input = str(input('Enter Student ID: '))
        if student_input == '':
            print(br)
            return transact_data # go to main menu
        if not (student_input.isnumeric() == True and len(student_input) == 8): # input validation
            print('>> Entered ID is not valid <<\n')
            continue
        if int(student_input) not in student_data['id'].values():  # evaluate if id already exists
            print('>> Entered Student ID does not exist <<\n')
            continue

        print('\nReturn Date...')
        day_input = str(input('\tEnter day (1-31): '))
        if day_input == '':
            print(br)
            return transact_data # go to main menu
        if not (day_input.isnumeric() and 31 >= int(day_input) >= 1): # input validation
            print('>> Entered value is invalid <<\n')
            continue

        month_input = str(input('\tEnter month (1-12): '))
        if month_input == '':
            print(br)
            return transact_data # go to main menu
        if not (month_input.isnumeric() and 12 >= int(month_input) >= 1): # input validation
            print('>> Entered value is invalid <<\n')
            continue

        year_input = str(input('\tEnter year: '))
        if year_input == '':
            print(br)
            return transact_data # go to main menu
        if not (year_input.isnumeric() and 2000 <= int(year_input) <= int(current.strftime('%Y'))): # input validation
            print('>> Entered value is invalid <<\n')
            continue
        break

    full_date = (  # adjust date format, add trailing zero
        f'{'{:02d}'.format(int(day_input))}/'
        f'{'{:02d}'.format(int(month_input))}/'
        f'{year_input}'
    )

    try:
        transact_data = returnLogic(
            transact_data,
            book_input,
            student_input,
            full_date
        )
    except Exception as e:
        print(f'Error occurred while accessing dictionary: {e} ')

    return transact_data