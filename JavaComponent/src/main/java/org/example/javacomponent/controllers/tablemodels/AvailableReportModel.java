package org.example.javacomponent.controllers.tablemodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AvailableReportModel {
    public AvailableReportModel(String bookId, String bookTitle, String copiesAvailable, String currentBurrower)  {
        bookIdProperty().set(bookId);
        bookTitleProperty().set(bookTitle);
        copiesAvailableProperty().set(copiesAvailable);
        currentBurrowerProperty().set(currentBurrower);
    }

    private StringProperty bookId;
    public StringProperty bookIdProperty() {
        if (bookId == null) bookId = new SimpleStringProperty(this, "bookId");
        return bookId;
    }
    public String getBookId() { return bookIdProperty().get(); }

    private StringProperty bookTitle;
    public StringProperty bookTitleProperty() {
        if (bookTitle == null) bookTitle = new SimpleStringProperty(this, "bookTitle");
        return bookTitle;
    }
    public String getBookTitle() { return bookTitleProperty().get(); }


    private StringProperty copiesAvailable;
    public StringProperty copiesAvailableProperty() {
        if (copiesAvailable == null) copiesAvailable = new SimpleStringProperty(this, "copiesAvailable");
        return copiesAvailable;
    }
    public String getCopiesAvailable() { return copiesAvailableProperty().get(); }


    private StringProperty currentBurrower;
    public StringProperty currentBurrowerProperty() {
        if (currentBurrower == null) currentBurrower = new SimpleStringProperty(this, "currentBurrower");
        return currentBurrower;
    }
    public String getCurrentBurrower() { return currentBurrowerProperty().get(); }

}
