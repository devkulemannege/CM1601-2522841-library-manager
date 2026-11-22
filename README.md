# CM1601 Coursework - Library Management System

## Purpose

This project is a CSV-based Library Management System. It is designed to help a
library maintain book and student records, record borrowing activity, validate
the data, and produce useful reports from the stored information.

The project is split into two components that use the same CSV data:

- **PythonComponent** provides the main command-line library management
  workflow.
- **JavaComponent** provides a JavaFX desktop interface for checking,
  correcting, viewing, and reporting on the CSV data.

## Functionality

### Python component

The Python application creates and manages a `C:\LibraryData` directory
containing:

- `book.csv` - book IDs, ISBNs, titles, copy counts, availability, and prices.
- `student.csv` - student IDs and first names.
- `transactions.csv` - issue and return dates, book IDs, student IDs, and
  transaction types.

From its main menu, users can:

1. Add and manage books, including searching for a book and updating its
   details.
2. Register students with validated student IDs and names.
3. Issue books to registered students while checking availability and existing
   loans.
4. Record book returns.
5. Generate a trend graph showing the number of books issued over time.
6. Export the current records back to CSV files before exiting.

Input validation helps prevent duplicate IDs, invalid ISBN-13 values, invalid
dates, and inconsistent copy or availability values.

### JavaFX component

The Java application allows a user to select the directory containing the
library CSV files. It then analyzes the files and identifies issues such as
invalid formats, duplicate student IDs, invalid dates, and invalid book data.
When errors are found, the correction screen guides the user through fixing
each affected value.

After the data has been checked, the application provides:

- A summary of the rows and columns in each CSV file.
- A report of books issued on a specified date.
- A book availability search, including title wildcard searches and current
  borrowers.
- An average book-cost calculation.
- Export of the availability report to `AvailabilityReport.txt`.

## Technologies

- Python with Pandas and Matplotlib.
- Java with JavaFX, Maven, and OpenCSV.
- CSV files for persistent storage.

## Project structure

```text
PythonComponent/   Command-line management and CSV export
JavaComponent/     JavaFX validation, correction, and reporting interface
```

This is a university project.
