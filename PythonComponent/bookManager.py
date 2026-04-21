# global variables
br = '---------------------------------'

def checkValidator(isbn):
    """checks if given isbn is valid through check digit (last number)
    :param isbn - isbn number"""
    checkDigit = 0

    for pos, val in enumerate(isbn[:-1]):
        if pos % 2 == 1:
            checkDigit += int(val) * 3
        else:
            checkDigit += int(val) * 1

    checkDigit = checkDigit % 10
    checkDigit = 10 - checkDigit

    return str(checkDigit)

def bookSearch(data, bookId):
    """access dictionary contents to view data on a specific book
    :param data - "books" dictionary
    :param bookId - book ID of required book"""
    index = 0

    for i in data['id'].values():
        index += 1 # find index location (row) of required info accross all keys
        if i == bookId:
            print(
                '\nBook details:\n'
                f'\tTitle: {data['title'][index-1]}\n'
                f'\tISBN: {data['isbn'][index-1]}\n'
                f'\tBook ID: {data['id'][index-1]}\n'
                f'\tNumber of Copies: {data['copies'][index-1]}\n'
                f'\tAvailable Copies: {data['available'][index-1]}\n'
                f'\tPrice: {data['price'][index-1]}'
            )
            break
    return

def addBook(data):
    """add new information to last index positions of each dictionary keys
    :param data - "books" dictionary"""
    lastIndex = len(data['id'].values())  # get index location of final data row
    optionLoop = True

    print('Enter book information. Press ENTER to go to previous menu.')

    while True:
        idInput = input('Enter book ID: ')
        if idInput in data['id'].values():  # evaluate if id already exists
            print('Book with matching ID already exists.\n')
            continue
        elif idInput == '':
            return data

        isbnInput = str(input('Enter ISBN: '))
        if not ( # input validation
                isbnInput.isnumeric() and
                len(isbnInput) == 13 and
                (int(isbnInput[0:3]) == 978 or int(isbnInput[0:3]) == 979) and
                isbnInput[-1] == checkValidator(isbnInput)
        ):
            print('>> Entered ISBN number is invalid <<\n')
            continue
        if isbnInput in str(data['isbn'].values()):  # evaluate if ISBN already exists
            print('Book with matching ISBN already exists.\n')
            continue
        if isbnInput == '':
            return data

        titleInput = input('Enter book title: ')
        if len(titleInput) > 20:
            print('>> Title should have a maximum of 20 characters <<\n') # input validation
            continue
        elif titleInput == '':
            return data

        copiesInput = input('Enter number of copies: ')
        if not copiesInput.isnumeric():
            print('>> Entered value is invalid <<\n') # input validation
            continue
        elif int(copiesInput) > 2:
            print('>> A maximum value of 2 should be entered <<\n') # input validation
            continue
        elif copiesInput == '':
            return data

        availableInput = input('Enter books available: ')
        if not availableInput.isnumeric():
            print('>> Entered value is invalid <<\n') # input validation
            continue
        elif int(availableInput) > int(copiesInput):
            print('>> Available book count cannot be greater than No. of copies <<\n') # input validation
            continue
        elif availableInput == '':
            return data

        priceInput = ''
        try:
            priceInput = float(input('Enter book price (00.00): '))
        except ValueError:
            print('>> Entered value is invalid <<\n') # validate if entered value is float
            continue
        if priceInput == '':
            return data

        break
    try:
        data['id'][lastIndex] = idInput
        data['isbn'][lastIndex] = int(isbnInput)
        data['title'][lastIndex] = titleInput
        data['copies'][lastIndex] = int(copiesInput)
        data['available'][lastIndex] = int(availableInput)
        data['price'][lastIndex] = float(priceInput)
    except Exception as e:
        print(f'An error occurred while accessing dictionary: {e}')

    print(
        f'\nData Entered successfully:\n'
        f'\tTitle: {data['title'][lastIndex]}\n'
        f'\tISBN: {data['isbn'][lastIndex]}\n'
        f'\tBook ID: {data['id'][lastIndex]}\n'
        f'\tNumber of Copies: {data['copies'][lastIndex]}\n'
        f'\tAvailable Copies: {data['available'][lastIndex]}\n'
        f'\tPrice: {data['price'][lastIndex]}\n'
    )
    return data

def updateInfo(data, bookId):
    """update information of a specified book
    :param data - "books" dictionary
    :param bookId - book ID of required book"""
    displayFlag = True
    index = 0

    for i in data['id'].values(): # find index location (row) of required info across all keys
        index += 1
        if i == bookId:
            index = index - 1
            break

    while True:
        print(
            '\nSelect an option. Press ENTER to go to previous menu.\n'
            '1. Book Id\n'
            '2. Book ISBN\n'
            '3. Book Title\n'
            '4. Number of Copies\n'
            '5. Available Copies\n'
            '6. Price'
        )
        usrChoice = input('\n-> Select an option (1,2,3,4,5): ')

        try:
            match usrChoice:
                case '1':
                    id_input = input('\n-> New Book ID: ')
                    if id_input == '':
                        print('>> Entered value cannot be empty <<\n') # check if empty
                        continue
                    if id_input in data['id'].values(): # evaluate if id already exists
                        print('Book with matching ID already exists.\n')
                        continue

                    data['id'][index] = id_input
                    break
                case '2':
                    isbn_input = str(input('\n-> New ISBN: '))
                    if isbn_input == '':
                        print('>> Entered value cannot be empty <<\n') # check if empty
                        continue
                    if not ( # input validation
                            isbn_input.isnumeric() and
                            len(isbn_input) == 13 and
                            (int(isbn_input[0:3]) == 978 or int(isbn_input[0:3]) == 979) and
                            isbn_input[-1] == checkValidator(isbn_input)
                    ):
                        print('>> Entered ISBN number is invalid <<\n')
                        continue
                    if isbn_input in str(data['isbn'].values()):  # evaluate if ISBN already exists
                        print('Book with matching ISBN already exists.\n')
                        continue

                    data['isbn'][index] = int(isbn_input)
                    break
                case '3':
                    title_input = input('\n-> New Book Title: ')
                    if title_input == '':
                        print('>> Entered value cannot be empty <<\n') # check if empty
                        continue
                    if len(title_input) > 20:
                        print('>> Title should have a maximum of 20 characters <<\n')  # input validation
                        continue

                    data['title'][index] = title_input
                    break
                case '4':
                    copies_input = input('\n-> New Number of Copies: ')
                    if copies_input == '':
                        print('>> Entered value cannot be empty <<\n') # check if empty
                        continue
                    if not copies_input.isnumeric():
                        print('>> Entered value is invalid <<\n') # input validation
                        continue
                    elif int(copies_input) > 2:
                        print('>> A maximum value of 2 should be entered <<\n')

                    data['copies'][index] = int(copies_input)
                    break
                case '5':
                    available_input = input('\n-> New Available Copies: ')
                    if available_input == '':
                        print('>> Entered value cannot be empty <<\n') # check if empty
                        continue
                    if not available_input.isnumeric():
                        print('>> Entered value is invalid <<\n') # input validation
                        continue
                    elif int(available_input) > int(data['copies'][index]):
                        print('>> Available book count cannot be greater than No. of copies <<\n')

                    data['available'][index] = int(available_input)
                    break
                case '6':
                    price_input = '' # initialize
                    if price_input == '':
                        print('>> Entered value cannot be empty <<\n') # check if empty
                        continue
                    try:
                        price_input = float(input('\n-> New Book Price (00.00): '))
                    except ValueError:
                        print('>> Entered value is invalid <<\n')  # validate if entered value is float
                        continue

                    data['price'][index] = float(price_input)
                    break
                case '':
                    display_flag = False
                    break
                case _:
                    print('>> Please select an option from 1 to 5 <<')
                    continue
        except Exception as e:
            print(f'An error occurred while accessing dictionary: {e}')

    if displayFlag:
        print(
            f'\nData updated successfully:\n'
            f'\tTitle: {data['title'][index]}\n'
            f'\tISBN: {data['isbn'][index]}\n'
            f'\tBook ID: {data['id'][index]}\n'
            f'\tNumber of Copies: {data['copies'][index]}\n'
            f'\tAvailable Copies: {data['available'][index]}\n'
            f'\tPrice: {data['price'][index]}\n'
        )
    return data

def accessManager(data):
    """menu which controls the flow of book managing component of LMS
    :param data - "books" dictionary """

    while True:
        print(
            'Book Manager\n'
            '1. View Details of a Book.\n'
            '2. Add a New Book.\n'
            '3. Edit Information of a Book.\n'
            '4. Go to Main Menu.'
        )

        usrChoice = input('\n-> Please select an option (1,2,3,4): ')
        print(br) # line break

        match usrChoice:
            case '1':
                bookId = input('-> Enter book ID: ')
                if bookId not in data['id'].values(): # evaluate for the existence of book ID
                    print(f'Given Book ID does not exist.\n{br}')
                    continue

                bookSearch(data, bookId)
                print(br)
            case '2':
                data = addBook(data)
                print(br)
            case '3':
                bookId = input('-> Enter book ID: ')
                if bookId not in data['id'].values():  # evaluate for the existence of book ID
                    print(f'Given Book ID does not exist.\n{br}')
                    continue

                data = updateInfo(data, bookId)
                print(br)
            case '4':
                break
            case _:
                print('>> Please select an option from 1 to 4 <<\n')
    return data

