# modular imports
import bookManager

import pandas as pd
import os

# initialization of variables
books = {'id': [], 'isbn': [], 'title': [], 'copies': [], 'available': [], 'price': []}
students = {'id': [], 'firstName': []}
transactions = {'date': [], 'bookId': [], 'studentId': [], 'type': []}
file_names = ['book','student','transactions'] # files names of the .csv files
br = '------------------------------------------------------------------'  # line break for clarity
option_loop = True

def create_file(name):
    """Function to create missing csv files to reduce code redundency"""
    file = open(f'C:/LibraryData/{name}.csv', 'w')
    file.close()

try:
    if not os.path.exists("C:/LibraryData"):
        os.mkdir("C:/LibraryData") # create directory to store csv data if it doesn't exist

    for name in file_names:
        if f'{name}.csv' not in os.listdir("C:/LibraryData"):
            create_file(name) # create csv files needed for LMS operations
except Exception as e:
    print(f'An error occurred while preparing directory and data for system: {e}')

# get csv data as dictionaries. if files empty, user empty dictionaries
try:
    books_df = pd.read_csv("C:/LibraryData/book.csv")
    books = books_df.to_dict()

    students_df = pd.read_csv("C:/LibraryData/student.csv")
    students = students_df.to_dict()

    transactions_df = pd.read_csv("C:/LibraryData/transactions.csv")
    transactions = transactions_df.to_dict()

except Exception as e:
    print(f'An error occurred while accessing data of .csv files: {e}')

# starting point of program
print(f'{br}\nWelcome to the Library Management System.\n')

while option_loop:
    print(
        '1. Manage books\n'
        '2. Add Student\n'
        '3. Issue Book\n'
        '4. Return Book\n'
        '5. Exit'
    )
    usr_choice = input('\n-> Please select an option (1,2,3,4,5): ')
    print(br) # line break

    match usr_choice:
        case '1':
            bookManager.access_manager(books)
        case '5':
            option_loop = False # disable menu-loop flag
            exit()
        case _ :
            print('>> Please select an option from 1 to 4 <<\n')
