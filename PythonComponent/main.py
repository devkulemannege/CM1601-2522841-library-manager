"""
Author: R.D.kulemannege
Description: Library Management System with CSV exporting
"""

# modular imports
import transactionManager
import bookManager
import studentManager

import matplotlib.pyplot as plt
import pandas as pd
import pandas.errors
import datetime
import os

# initialization of variables
fileNames = ['book','student','transactions'] # files names of the .csv files
br = '------------------------------------------------------------------'  # line break for clarity

def create_file(name):
    """Function to create missing csv files to reduce code redundancy"""
    file = open(f'C:/LibraryData/{name}.csv', 'w')
    file.close()

def strip_values(keys, df):
    """Function to strip values from a dataframe
    :param keys: list of keys
    :param df: pandas dataframe"""
    for key in keys: df[key] = df[key].str.strip()
    return df

try:
    if not os.path.exists("C:/LibraryData"):
        os.mkdir("C:/LibraryData") # create directory to store csv data if it doesn't exist

    for name in fileNames:
        if f'{name}.csv' not in os.listdir("C:/LibraryData"):
            create_file(name) # create csv files needed for LMS operations
except Exception as e:
    print(f'An error occurred while preparing directory and data for system: {e}')

# get csv data as dictionaries. if files empty, use empty dictionaries
try:
    booksDf = pd.read_csv("C:/LibraryData/book.csv")
    booksDf = strip_values(['id', 'title'], booksDf) # strip values in dictionary
    books = booksDf.to_dict()
except pandas.errors.EmptyDataError:
    books = {'id': {}, 'isbn': {}, 'title': {}, 'copies': {}, 'available': {}, 'price': {}}

try:
    studentsDf = pd.read_csv("C:/LibraryData/student.csv")
    studentsDf = strip_values(['firstName'], studentsDf) # strip values in dictionary
    students = studentsDf.to_dict()
except pandas.errors.EmptyDataError:
    students = {'id': {}, 'firstName': {}}

try:
    transactionsDf = pd.read_csv("C:/LibraryData/transactions.csv")
    transactionsDf = strip_values(['date'], transactionsDf)
    transactions = transactionsDf.to_dict()
except pandas.errors.EmptyDataError:
    transactions = {'date': {}, 'bookId': {}, 'studentId': {}, 'type':{}}

# starting point of program
print(f'{br}\nWelcome to the Library Management System.\n')

while True:
    print(
        'Main Menu\n'
        '1. Manage books\n'
        '2. Add Student\n'
        '3. Issue Book\n'
        '4. Return Book\n'
        '5. Display Trend Graph\n'
        '6. Export to CSV & Exit'
    )
    usrChoice = input('\n-> Please select an option (1,2,3,4,5,6,7): ')
    print(br) # line break

    match usrChoice:
        case '1':
            books = bookManager.accessManager(books)
        case '2':
            students = studentManager.accessManager(students)
        case '3':
            transactions = transactionManager.issueBook(books, students, transactions)
        case '4':
            transactions = transactionManager.returnBook(books, students, transactions)
        case '5':
            # initialization of temporary dictionary
            plotDict = {
                'date': [],
                'type': [],
            }

            plotDf = pd.DataFrame(transactions, columns=['date','type']) # get specific columns for dataframe
            plotDf = plotDf[plotDf.type != 2] # remove rows with 2 (return) type

            fullDict = plotDf.to_dict()
            trimDict = plotDf.drop_duplicates(subset=['date']).to_dict()

            # find no. of issue's per specified date
            for date in trimDict['date'].values():
                issueCount = 0
                for subDate in fullDict['date'].values():
                    if subDate == date: issueCount += 1

                plotDict['date'].append(date)
                plotDict['type'].append(issueCount)

            plotDf = pd.DataFrame(plotDict, columns=['date','type']) # create dataframe from newly made dictionary
            plotDf.plot(x='date', y='type', kind='line')

            # export graph with filename with date & time
            dateInfo = datetime.datetime.now()
            year = dateInfo.strftime('%Y')
            month = dateInfo.strftime('%m')
            date = dateInfo.strftime('%d')
            hour = dateInfo.strftime('%H')
            minute = dateInfo.strftime('%M')
            second = dateInfo.strftime('%S')

            fileName = f'{year}{month}{date}-{hour}{minute}{second}.jpg'
            plt.savefig(f'C:/LibraryData/{fileName}') # save graph in directory
            plt.show()  # display graph

            print(f'Trend Graph has been Generated and Saved as {fileName} at C:/LibraryData.')
            print(br)  # line break

        case '6':
            for name in fileNames:
                os.remove(f'C:/LibraryData/{name}.csv')

            # export dictionaries to csv files and exit program
            try:
                booksDf = pd.DataFrame(books)
                booksDf.to_csv('C:/LibraryData/book.csv', index=False)
                studentsDf = pandas.DataFrame(students)
                studentsDf.to_csv('C:/LibraryData/student.csv', index=False)
                transactionsDf = pandas.DataFrame(transactions)
                transactionsDf.to_csv('C:/LibraryData/transactions.csv', index=False)

                print('CSV files have been exported to C:/LibraryData.')
            except Exception as e:
                print(f'An error occurred while exporting CSV files: {e}')
            exit()
        case _ :
            print('>> Please select an option from 1 to 4 <<\n')
