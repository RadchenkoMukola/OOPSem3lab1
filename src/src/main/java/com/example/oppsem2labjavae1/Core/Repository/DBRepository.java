package com.example.oppsem2labjavae1.Core.Repository;

import com.example.oppsem2labjavae1.Core.Models.Book;
import com.example.oppsem2labjavae1.Core.Models.BookInstance;
import com.example.oppsem2labjavae1.Core.Models.BookInstanceStatus;
import com.example.oppsem2labjavae1.Core.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBRepository implements Repository {
    private Connection connection;

    public DBRepository(String url, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);

            List<String> tableCreationTablesQueries = new ArrayList<>();
            tableCreationTablesQueries.add(BookInstance.getCreationTableCreationSql());
            tableCreationTablesQueries.add(Book.getCreationTableCreationSql());

            Statement statement = connection.createStatement();

            for (String tableCreationQuery : tableCreationTablesQueries) {
                statement.execute(tableCreationQuery);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BookInstance findBookInstanceByISBN(long isbn) {
        try {
            Statement statement = connection.createStatement();

            String booksDescriptionSelect = "select id, title, description from books_description";

            String booksInstancesSelect = "select ISBN, id, status from books_instances where isbn = " + isbn;

            String bookBorrowersSelect = "select ISBN, username from books_borrowers";

            String userSelect = "select username, CONCAT(first_name, ' ', last_name) as name from user_entity";

            String bookInstancesWithDescription = "((" + booksDescriptionSelect + ") JOIN (" +  booksInstancesSelect + ") USING(" + Book.ID + "))";

            String query = "select id, title, description, ISBN, status, username, name from (" + bookInstancesWithDescription + " LEFT JOIN (" + bookBorrowersSelect + ") USING(ISBN)) LEFT JOIN (" + userSelect + ") USING(username)";

            ResultSet resultSet = statement.executeQuery(query);

            BookInstance instance = null;

            if (resultSet.next()) {
                instance = new BookInstance();
                parseBookInstance(resultSet, instance);
            }

            return instance;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseBookInstance(ResultSet resultSet, BookInstance instance) throws SQLException {
        instance.setIsbn(resultSet.getLong("ISBN"));

        Book book = new Book();
        parseBook(resultSet, book);

        instance.setBook(book);

        String username = resultSet.getString("username");
        if (!resultSet.wasNull()) {
            User user = new User();
            user.setName(username);

            String name = resultSet.getString("name");
            user.setName(name);

            instance.setBorrower(user);
        }

        BookInstanceStatus status = BookInstanceStatus.parse(resultSet.getInt("status"));

        instance.setStatus(status);
    }


    private void parseBook(ResultSet resultSet, Book book) throws SQLException {
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setDescription(resultSet.getString("description"));
    }

    @Override
    public List<BookInstance> findBookInstancesByPrompt(String prompt) {
        try {
            Statement statement = connection.createStatement();

            String lowerPrompt = prompt.toLowerCase();

            String booksDescriptionSelect;
            if (prompt.isEmpty()) {
                booksDescriptionSelect = "select id, title, description from books_description";
            } else {
                booksDescriptionSelect = "select id, title, description from books_description where strpos(lower(title), '" + lowerPrompt + "') > 0 or strpos(lower(description), '" + lowerPrompt + "') > 0";
            }

            String booksInstancesSelect = "select ISBN, id, status from books_instances";

            String bookBorrowersSelect = "select ISBN, username from books_borrowers";

            String userSelect = "select username, CONCAT(first_name, ' ', last_name) as name from user_entity";

            String bookInstancesWithDescription = "((" + booksDescriptionSelect + ") JOIN (" +  booksInstancesSelect + ") USING(id))";

            String query = "select id, title, description, ISBN, status, username, name from (" + bookInstancesWithDescription + " LEFT JOIN (" + bookBorrowersSelect + ") USING(ISBN)) LEFT JOIN (" + userSelect + ") USING(username)";

            ResultSet resultSet = statement.executeQuery(query);

            List<BookInstance> instances = new ArrayList<>();

            while (resultSet.next()) {
                BookInstance instance = new BookInstance();

                parseBookInstance(resultSet, instance);

                instances.add(instance);
            }

            return instances;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void borrowBook(String username, long isbn, int status) {
        try {
            Statement statement = connection.createStatement();

            String statusUpdate = "update books_instances set status=" + status + " where ISBN=" + isbn;

            String addBorrower = "insert into books_borrowers VALUES(" + isbn + ", '" + username + "')";

            statement.execute(addBorrower);
            statement.execute(statusUpdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void returnBook(long isbn) {
        try {
            Statement statement = connection.createStatement();

            String statusUpdate = "update books_instances set status=" + BookInstanceStatus.Available.getValue() +
                    " where ISBN=" + isbn;

            String removeBorrower = "delete from books_borrowers where ISBN=" + isbn;

            statement.execute(removeBorrower);
            statement.execute(statusUpdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addBook(String title, String description) {
        try {
            Statement statement = connection.createStatement();

            String addBook = "insert into books_description(title, description) values('" + title + "', '" + description + "')";
            statement.execute(addBook);

            String usernameSelect = "select id from books_description";
            ResultSet resultSet = statement.executeQuery(usernameSelect);
            resultSet.next();

            return resultSet.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addBookInstance(long isbn, int id) {
        try {
            Statement statement = connection.createStatement();

            String addBookInstance = "insert into books_instances(ISBN, id) values(" + isbn + ", " + id + ")";

            statement.execute(addBookInstance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findBookByPrompt(String prompt) {
        try {
            Statement statement = connection.createStatement();

            String lowerPrompt = prompt.toLowerCase();

            String booksDescriptionSelect;
            if (prompt.isEmpty()) {
                booksDescriptionSelect = "select id, title, description from books_description";
            } else {
                booksDescriptionSelect = "select id, title, description from books_description where strpos(lower(title), '" + lowerPrompt + "') > 0 or strpos(lower(description), '" + lowerPrompt + "') > 0";
            }

            ResultSet resultSet = statement.executeQuery(booksDescriptionSelect);

            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                Book book = new Book();

                parseBook(resultSet, book);

                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
