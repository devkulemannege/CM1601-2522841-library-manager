# global variables
br = '---------------------------------'

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

    print('\nEnter book information. Press ENTER to go to previous menu.')
    id_input = input('Enter book ID: ')
    if id_input == '': return
    isbn_input = input('Enter ISBN: ')
    if isbn_input == '': return
    title_input = input('Enter book title: ')
    if title_input == '': return
    copies_input = input('Enter number of copies: ')
    if copies_input == '': return
    available_input = input('Enter book available: ')
    if available_input == '': return
    price_input = input('Enter book price: ')
    if price_input == '': return

    try:
        last_index = len(data['id'].values())-1 # get index location of final data row - 1
        data['id'][last_index] = id_input
        data['isbn'][last_index] = isbn_input
        data['title'][last_index] = title_input
        data['copies'][last_index] = copies_input
        data['available'][last_index] = available_input
        data['price'][last_index] = price_input
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
    index = 0

    for i in data['id'].values(): # find index location (row) of required info across all keys
        index += 1
        if i == book_id:
            index = index - 1
            break

    while update_option_loop:
        print(
            '\n1. Book Id\n'
            '2. Book ISBN\n'
            '3. Book Title\n'
            '5. Number of Copies\n'
            '4. Available Copies\n'
            '5. Price'
        )
        usr_choice = input('\n-> Select an option to update (1,2,3,4,5): ')

        try:
            match usr_choice:
                case '1':
                    id_input = input('\n-> New Book ID: ')
                    data['id'][index] = book_id
                    update_option_loop = False
                case '2':
                    isbn_input = input('\n-> New ISBN: ')
                    data['isbn'][index] = isbn_input
                    update_option_loop = False
                case '3':
                    title_input = input('\n-> New Book Title: ')
                    data['title'][index] = title_input
                    update_option_loop = False
                case '4':
                    copies_input = input('\n-> New Number of Copies: ')
                    data['copies'][index] = copies_input
                    update_option_loop = False
                case '5':
                    available_input = input('\n-> New Available Copies: ')
                    data['available'][index] = available_input
                    update_option_loop = False
                case '6':
                    price_input = input('\n-> New Book Price: ')
                    data['price'][index] = price_input
                    update_option_loop = False
                case _:
                    print('>> Please select an option from 1 to 5 <<')
                    continue
        except Exception as e:
            print(f'An error occurred while accessing dictionary: {e}')
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
            '4. Go to Main Menu.'
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
                option_loop = False
                break
            case _:
                print('>> Please select an option from 1 to 4 <<\n')
    return

