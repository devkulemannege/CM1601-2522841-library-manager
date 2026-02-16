# global variables
br = '---------------------------------'

def issue_logic(book_data, transact_data, book_input, student_input, full_date):
    """docstring for issue_logic. Control how issuing is handled
    :param book_input: Book ID
    :param student_input: student ID
    :param full_date: combined date
    :param book_data: "books" dictionary
    :param transact_data: "transactions" dictionary"""
    issue_count = student_issue_count = book_index = last_index = 0

    for index, transact_type in enumerate(transact_data['type'].values()): # find no. of times a book has been issued
        if transact_data['bookId'][index] == book_input and transact_type == 1:
            issue_count += 1
        elif transact_data['bookId'][index] == book_input and transact_type == 2:
            issue_count -= 1
            if issue_count < 0: issue_count = 0  # reset issue counting to 0 if it goes to -1
        if (
            transact_data['studentId'][index] == int(student_input) and # find if the student already has a copy of specified book
            transact_data['bookId'][index] == book_input and
            transact_type == 1
        ):
            student_issue_count += 1
        elif (
            transact_data['studentId'][index] == int(student_input) and
            transact_data['bookId'][index] == book_input and
            transact_type == 2
        ):
            student_issue_count -= 1
            if student_issue_count < 0: student_issue_count = 0  # reset student issue counting to 0 if it goes to -1

    for i in book_data['id'].values(): # find index location (row) of required info across all keys
        book_index += 1
        if i == book_input:
            book_index = book_index - 1
            break

    if issue_count >= book_data['available'][book_index]:
        print('\nNo copies are available to issue.')
    elif issue_count < book_data['available'][book_index]:
        if student_issue_count >= 1:
            print('\nA copy of specified book has already been issued to the student.')
        else:
            last_index = len(transact_data['date'].values())  # get index location of final data row

            transact_data['date'][last_index] = full_date
            transact_data['bookId'][last_index] = book_input
            transact_data['studentId'][last_index] = int(student_input)
            transact_data['type'][last_index] = 1
            print('\nTransaction Recorded.')

    return transact_data

def return_logic(transact_data, book_input, student_input, full_date):
    """docstring for issue_logic. Control how issuing is handled
    :param book_input: Book ID
    :param student_input: student ID
    :param full_date: combined date
    :param book_data: "books" dictionary
    :param transact_data: "transactions" dictionary"""
    return_count = 0

    for index, transact_type in enumerate(transact_data['type'].values()):
        if (
            transact_data['studentId'][index] == int(student_input) and
            transact_data['bookId'][index] == book_input and
            transact_type == 1
        ):
            return_count-=1
            if return_count < 0: return_count = 0  # reset return counting to 0 if it goes to -1
        elif (
            transact_data['studentId'][index] == int(student_input) and
            transact_data['bookId'][index] == book_input and
            transact_type == 2
        ):
            return_count+=1

    if return_count == 0:
        last_index = len(transact_data['date'].values())  # get index location of final data row

        transact_data['date'][last_index] = full_date
        transact_data['bookId'][last_index] = book_input
        transact_data['studentId'][last_index] = int(student_input)
        transact_data['type'][last_index] = 2
        print('\nTransaction Recorded.')
    else:
        print('Specified book has already been returned.')

    print(br) # line break
    return transact_data

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

        year_input = str(input('\tEnter year: '))
        if year_input == '':
            print(br)
            return transact_data # go to main menu
        if not year_input.isnumeric(): # input validation
            print('>> Entered value is invalid <<\n')
            continue
        break

    full_date = ( # adjust date format, add trailing zero
        f'{'{:02d}'.format(int(day_input))}/'
        f'{'{:02d}'.format(int(month_input))}/'
        f'{year_input}'
    )

    try:
        transact_data = issue_logic(
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
        if not year_input.isnumeric(): # input validation
            print('>> Entered value is invalid <<\n')
            continue
        break

    full_date = (  # adjust date format, add trailing zero
        f'{'{:02d}'.format(int(day_input))}/'
        f'{'{:02d}'.format(int(month_input))}/'
        f'{year_input}'
    )

    try:
        transact_data = return_logic(
            transact_data,
            book_input,
            student_input,
            full_date
        )
    except Exception as e:
        print(f'Error occurred while accessing dictionary: {e} ')

    return transact_data