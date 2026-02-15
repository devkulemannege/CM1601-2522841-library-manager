# global variables
from langchain_core.language_models import FakeStreamingListLLM

br = '---------------------------------'

def check_validator(isbn):
    """checks if given isbn is valid through check digit (last number)
    :param isbn - isbn number"""
    check_digit = 0

    for pos, val in enumerate(isbn[:-1]):
        if pos % 2 == 1:
            check_digit += int(val) * 3
        else:
            check_digit += int(val) * 1

    check_digit = check_digit % 10
    check_digit = 10 - check_digit

    return str(check_digit)

def book_search(data, book_id):
    """access dictionary contents to view data on a specific book
    :param data - "books" dictionary
    :param book_id - book ID of required book"""
    index = 0

    for i in data['id'].values():
        index += 1 # find index location (row) of required info accross all keys
        if i == book_id:
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

def add_book(data):
    """add new information to last index positions of each dictionary keys
    :param data - "books" dictionary"""
    last_index = len(data['id'].values()) - 1  # get index location of final data row - 1
    update_option_loop = True

    print('Enter book information. Press ENTER to go to previous menu.')

    while update_option_loop:
        id_input = input('Enter book ID: ')
        if id_input in data['id'].values():  # evaluate if id already exists
            print('Book with matching ID already exists.\n')
            continue
        elif id_input == '':
            return

        isbn_input = str(input('Enter ISBN: '))
        if not ( # input validation
                isbn_input.isnumeric() and
                len(isbn_input) == 13 and
                isbn_input[-1] == check_validator(isbn_input)
        ):
            print('>> Entered ISBN number is invalid <<\n')
            continue
        if isbn_input in str(data['isbn'].values()):  # evaluate if ISBN already exists
            print('Book with matching ISBN already exists.\n')
            continue
        if isbn_input == '':
            return

        title_input = input('Enter book title: ')
        if len(title_input) > 20:
            print('>> Title should have a maximum of 20 characters <<\n') # input validation
            continue
        elif title_input == '':
            return

        copies_input = input('Enter number of copies: ')
        if not copies_input.isnumeric():
            print('>> Entered value is invalid <<\n') # input validation
            continue
        elif int(copies_input) > 2:
            print('>> A maximum value of 2 should be entered <<\n') # input validation
            continue
        elif copies_input == '':
            return

        available_input = input('Enter book available: ')
        if not available_input.isnumeric():
            print('>> Entered value is invalid <<\n') # input validation
            continue
        elif int(available_input) > int(copies_input):
            print('>> Available book count cannot be greater than No. of copies <<\n') # input validation
            continue
        elif available_input == '':
            return

        price_input = ''
        try:
            price_input = float(input('Enter book price (00.00): '))
        except ValueError:
            print('>> Entered value is invalid <<\n') # validate if entered value is float
            continue
        if price_input == '':
            return

        update_option_loop = False
        break
    try:
        data['id'][last_index] = id_input
        data['isbn'][last_index] = int(isbn_input)
        data['title'][last_index] = title_input
        data['copies'][last_index] = int(copies_input)
        data['available'][last_index] = int(available_input)
        data['price'][last_index] = float(price_input)
    except Exception as e:
        print(f'An error occurred while accessing dictionary: {e}')

    print(
        f'\nData Entered successfully:\n'
        f'\tTitle: {data['title'][last_index]}\n'
        f'\tISBN: {data['isbn'][last_index]}\n'
        f'\tBook ID: {data['id'][last_index]}\n'
        f'\tNumber of Copies: {data['copies'][last_index]}\n'
        f'\tAvailable Copies: {data['available'][last_index]}\n'
        f'\tPrice: {data['price'][last_index]}\n'
    )

def update_info(data, book_id):
    """update information of a specified book
    :param data - "books" dictionary
    :param book_id - book ID of required book"""
    update_option_loop = True # looping flag for this function only
    display_flag = True
    index = 0

    for i in data['id'].values(): # find index location (row) of required info across all keys
        index += 1
        if i == book_id:
            index = index - 1
            break

    while update_option_loop:
        print(
            '\nSelect an option. Press ENTER to go to previous menu.\n'
            '1. Book Id\n'
            '2. Book ISBN\n'
            '3. Book Title\n'
            '5. Number of Copies\n'
            '4. Available Copies\n'
            '5. Price'
        )
        usr_choice = input('\n-> Select an option (1,2,3,4,5): ')

        try:
            match usr_choice:
                case '1':
                    id_input = input('\n-> New Book ID: ')
                    if id_input == '':
                        print('>> Entered value cannot be empty <<\n') # check if empty
                        continue
                    if id_input in data['id'].values(): # evaluate if id already exists
                        print('Book with matching ID already exists.\n')
                        continue

                    data['id'][index] = book_id
                    update_option_loop = False
                case '2':
                    isbn_input = str(input('\n-> New ISBN: '))
                    if isbn_input == '':
                        print('>> Entered value cannot be empty <<\n') # check if empty
                        continue
                    if not ( # input validation
                            isbn_input.isnumeric() and
                            len(isbn_input) == 13 and
                            isbn_input[-1] == check_validator(isbn_input)
                    ):
                        print('>> Entered ISBN number is invalid <<\n')
                        continue
                    if isbn_input in str(data['isbn'].values()):  # evaluate if ISBN already exists
                        print('Book with matching ISBN already exists.\n')
                        continue

                    data['isbn'][index] = int(isbn_input)
                    update_option_loop = False
                case '3':
                    title_input = input('\n-> New Book Title: ')
                    if title_input == '':
                        print('>> Entered value cannot be empty <<\n') # check if empty
                        continue
                    if len(title_input) > 20:
                        print('>> Title should have a maximum of 20 characters <<\n')  # input validation
                        continue

                    data['title'][index] = title_input
                    update_option_loop = False
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
                    update_option_loop = False
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
                    update_option_loop = False
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
                    update_option_loop = False
                case '':
                    display_flag = False
                    update_option_loop = False
                    break
                case _:
                    print('>> Please select an option from 1 to 5 <<')
                    continue
        except Exception as e:
            print(f'An error occurred while accessing dictionary: {e}')

    if display_flag:
        print(
            f'\nData updated successfully:\n'
            f'\tTitle: {data['title'][index]}\n'
            f'\tISBN: {data['isbn'][index]}\n'
            f'\tBook ID: {data['id'][index]}\n'
            f'\tNumber of Copies: {data['copies'][index]}\n'
            f'\tAvailable Copies: {data['available'][index]}\n'
            f'\tPrice: {data['price'][index]}\n'
        )
    return

def delete_info(data, book_id):
    """delete information of a specified book
    :param data - "books" dictionary
    :param book_id - book ID of required book"""
    update_option_loop = True  # looping flag for this function only
    index = 0

    for i in data['id'].values(): # find index location (row) of required info across all keys
        index += 1
        if i == book_id:
            index = index - 1
            break

    while update_option_loop:
        print(
            '\nAre you sure you want to delete the following?\n'
            f'\tTitle: {data['title'][index]}\n'
            f'\tISBN: {data['isbn'][index]}\n'
            f'\tBook ID: {data['id'][index]}\n'
            f'\tNumber of Copies: {data['copies'][index]}\n'
            f'\tAvailable Copies: {data['available'][index]}\n'
            f'\tPrice: {data['price'][index]}\n'
        )
        usr_option = input('-> Select an option (y/n): ')

        if usr_option not in ['y','n']: # input validation
            print('>> Please select "y" or "n" <<')
            continue
        elif usr_option == 'y':
            print('Book Information Deleted.')
            info_list = ['title', 'isbn', 'id', 'available', 'copies', 'price']
            for info in info_list: # for loop to reduce code redundancy
                del data[info][index] # delete row of specified book

        # re-adjust the order of indexes since deleting values will change the index order (ex: 0,1,2,4 -> 0,1,2,3)
        for key in data.keys():
            data[key] = dict(enumerate(data[key].values()))
        break
    return

def access_manager(data):
    """menu which controls the flow of book managing component of LMS
    :param data - "books" dictionary """
    option_loop = True

    while option_loop:
        print(
            'Book Manager\n'
            '1. View Details of a Book.\n'
            '2. Add a New Book.\n'
            '3. Edit Information of a Book.\n'
            '4. Delete Information of a Book\n'
            '5. Go to Main Menu.'
        )

        usr_choice = input('\n-> Please select an option (1,2,3,4): ')
        print(br) # line break

        match usr_choice:
            case '1':
                book_id = input('-> Enter book ID: ')
                if book_id not in data['id'].values(): # evaluate for the existence of book ID
                    print(f'Given Book ID does not exist.\n{br}')
                    continue

                book_search(data, book_id)
                print(br)
            case '2':
                add_book(data)
                print(br)
            case '3':
                book_id = input('-> Enter book ID: ')
                if book_id not in data['id'].values():  # evaluate for the existence of book ID
                    print(f'Given Book ID does not exist.\n{br}')
                    continue

                update_info(data, book_id)
                print(br)
            case '4':
                book_id = input('-> Enter book ID: ')
                if book_id not in data['id'].values():  # evaluate for the existence of book ID
                    print(f'Given Book ID does not exist.\n{br}')
                    continue

                delete_info(data, book_id)
                print(br)
            case '5':
                option_loop = False
                break
            case _:
                print('>> Please select an option from 1 to 4 <<\n')
    return

