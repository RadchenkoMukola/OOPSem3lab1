package com.example.oppsem2labjavae1.Core.Models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Book {
    public static final String TABLE_NAME = "books_description";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";

    private int id;
    private String title;
    private String description;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static String getCreationTableCreationSql() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ID +" SERIAL PRIMARY KEY, " + TITLE + " varchar(255), " + DESCRIPTION + " varchar(1024))";
    }
}
