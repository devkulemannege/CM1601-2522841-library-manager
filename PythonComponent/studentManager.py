# global variables
br = '---------------------------------'

def add_info(data, id_num, name):
    """add the acquired information to the last position of dictionary
    :param data - "students" dictionary
    :param id_num - acquired student id
    :param name - acquired student's first name"""
    try:
        last_index = len(data['id'].values())  # get index location of final data row
        data['id'][last_index] = id_num
        data['firstName'][last_index] = name

        print('\nData entered successfully.')
    except Exception as e:
        print(f'Error occurred while accessing dictionary: {e} ')

def access_manager(data):
    """menu which controls the flow of student managing component of LMS
    :param data - "students" dictionary """
    update_option_loop = True # function looping flag
    inner_option_loop = True # flag for inner loop inside while loop

    print('Student Manager\n\nPress ENTER to cancel.')

    while update_option_loop:
        id_input = str(input('-> Enter Student ID: '))
        if id_input == '': break # go to main menu
        if not (id_input.isnumeric() == True and len(id_input) == 8): # input validation
            print('>> Entered ID is not valid <<\n')
            continue
        if int(id_input) in data['id'].values(): # validate if id is duplicate
            print('Entered ID already exists.\n')
            continue

        name_input = input('-> Enter First Name: ')
        if name_input == '': break # go to main menu
        if not name_input.isalpha(): # input validation
            print('>> Entered name is not valid <<\n')
            continue
        elif not len(name_input) <= 10: # input validation
            print('>> Name must have a maximum of 10 characters <<\n')
            continue

        print(
            '\nAre the following details correct?\n'
            f'Student ID: {id_input}\n'
            f'First Name: {name_input}\n'
        )

        while inner_option_loop:
            usr_option = input('-> Select an option (y/n): ')
            if usr_option not in ['y', 'n']:  # input validation
                print('>> Please select "y" or "n" <<\n')
                continue
            elif usr_option == 'y':
                add_info(data, id_input, name_input) # add data to dictionary through function
                # disable flags
                inner_option_loop = False
                update_option_loop = False
                break
            else:
                break
    print(br) # line break
    return





