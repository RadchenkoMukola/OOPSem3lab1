package com.example.oppsem2labjavae1.Core.Repository;

import com.example.oppsem2labjavae1.Core.Models.Book;
import com.example.oppsem2labjavae1.Core.Models.BookInstance;
import com.example.oppsem2labjavae1.Core.Models.User;

import java.util.List;

public interface Repository {
    BookInstance findBookInstanceByISBN(long isbn);

    List<BookInstance> findBookInstancesByPrompt(String prompt);

    void borrowBook(String username, long isbn, int status);

    void returnBook(long isbn);

    int addBook(String title, String description);

    void addBookInstance(long isbn, int id);

    List<Book> findBookByPrompt(String prompt);
}
