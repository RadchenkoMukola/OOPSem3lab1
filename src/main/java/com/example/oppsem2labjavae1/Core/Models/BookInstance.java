package com.example.oppsem2labjavae1.Core.Models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookInstance {
    private long isbn;
    private Book book;

    private BookInstanceStatus status;
    private User borrower;

    @Override
    public String toString() {
        return "BookInstance{" +
                "isbn='" + isbn + '\'' +
                ", book=" + book +
                ", status=" + status +
                ", borrower=" + borrower +
                '}';
    }

    public static String getCreationTableCreationSql() {
        String bookInstanceCreationQuery = "CREATE TABLE IF NOT EXISTS books_instances (ISBN bigint PRIMARY KEY, id integer, status integer);";
        String bookBorrowersCreationQuery = "CREATE TABLE IF NOT EXISTS books_borrowers (ISBN bigint, username varchar(36), CONSTRAINT fk_uid FOREIGN KEY(username) REFERENCES user_entity(username));";
        return bookInstanceCreationQuery + bookBorrowersCreationQuery;
    }
}
