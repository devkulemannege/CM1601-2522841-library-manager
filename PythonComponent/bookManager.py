# global variables
option_loop = True

def book_search(data, id):
    print('Welcome to book search')
    exit()

def access_manager(data):
    """menu which controls the flow of book managing component of LMS
    :param data - "books" dictionary """
    global option_loop

    print(
        '\n1. View details of a book.\n'
        '2. Add a new book.\n'
        '3. Edit information of a book.'
    )

    while option_loop:
        usr_choice = input('\nPlease select an option (1,2,3): ')
        match usr_choice:
            case '1':
                book_id = input('\nEnter book id: ')
                book_search(data=data, id=book_id)
            case _:
                print('Please select an option from 1 to 5.')