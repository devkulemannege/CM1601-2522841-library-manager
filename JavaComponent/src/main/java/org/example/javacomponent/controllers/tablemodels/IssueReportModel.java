package org.example.javacomponent.controllers.tablemodels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IssueReportModel {
    public IssueReportModel(String bookId, String studentId) {
        bookIdProperty().set(bookId);
        studentIdProperty().set(studentId);
    }

    private StringProperty bookId;
    public StringProperty bookIdProperty() {
        if (bookId == null) bookId = new SimpleStringProperty(this, "bookId");
        return bookId;
    }

    private StringProperty studentId;
    public StringProperty studentIdProperty() {
        if (studentId == null) studentId = new SimpleStringProperty(this, "studentId");
        return studentId;
    }
}
