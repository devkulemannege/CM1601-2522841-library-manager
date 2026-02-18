# global variables
br = '---------------------------------'

def addInfo(data, idNum, name):
    """add the acquired information to the last position of dictionary
    :param data - "students" dictionary
    :param idNum - acquired student id
    :param name - acquired student's first name"""
    try:
        lastIndex = len(data['id'].values())  # get index location of final data row
        data['id'][lastIndex] = int(idNum)
        data['firstName'][lastIndex] = name

        print('\nData entered successfully.')
    except Exception as e:
        print(f'Error occurred while accessing dictionary: {e} ')
    return data

def accessManager(data):
    """menu which controls the flow of student managing component of LMS
    :param data - "students" dictionary """
    optionLoop = True # function looping flag
    innerOptionLoop = True # flag for inner loop inside while loop

    print('Student Manager\n\nPress ENTER to cancel.')

    while optionLoop:
        idInput = str(input('-> Enter Student ID: '))
        if idInput == '': break # go to main menu
        if not (idInput.isnumeric() == True and len(idInput) == 8): # input validation
            print('>> Entered ID is not valid <<\n')
            continue
        if int(idInput) in data['id'].values(): # validate if id is duplicate
            print('Entered ID already exists.\n')
            continue

        nameInput = input('-> Enter First Name: ')
        if nameInput == '': break # go to main menu
        if not nameInput.isalpha(): # input validation
            print('>> Entered name is not valid <<\n')
            continue
        elif not len(nameInput) <= 10: # input validation
            print('>> Name must have a maximum of 10 characters <<\n')
            continue

        print(
            '\nAre the following details correct?\n'
            f'Student ID: {idInput}\n'
            f'First Name: {nameInput}\n'
        )

        while innerOptionLoop:
            usrOption = input('-> Select an option (y/n): ')
            if usrOption not in ['y', 'n']:  # input validation
                print('>> Please select "y" or "n" <<\n')
                continue
            elif usrOption == 'y':
                data = addInfo(data, idInput, nameInput) # add data to dictionary through function
                # disable flags
                innerOptionLoop = False
                optionLoop = False
                break
            else:
                break
    print(br) # line break
    return data
