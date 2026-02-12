import pandas as pd

# modular imports
import bookManager

# initialization of variables
option_loop = True

print('Welcome to the Library Management System.\n')
print(
    '1. Manage books\n'
    '2. Add Student\n'
    '3. Issue Book\n'
    '4. Return Book\n'
    '5. Exit'
)

while option_loop:
    usr_choice = input('\nPlease select an option (1,2,3,4,5): ')
    match usr_choice:
        case '1':
            bookManager.access_manager()
        case '5':
            option_loop = False # disable menu-loop flag
            exit()
        case _ :
            print('Please select an option from 1 to 5.')







'''
books = {
    'id': ['wj49'],
    'isbn': ['9780143127741'],
    'title': ['The Pragmatic Programmer'],
    'copies': [10],
    'available': [8],
    'price': [4500]
}
students = {
    'id':[],
    'firstName':[]
}
transactions = {
    'date':[],
    'bookId':[],
    'studentId':[],
    'type':[]
}

bookDataFrame = pd.DataFrame(books)
print(bookDataFrame.iloc[0,4]) # [row location (x), value location of the row (y)]
'''