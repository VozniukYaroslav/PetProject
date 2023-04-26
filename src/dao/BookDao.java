package dao;

import entity.Book;
import entity.Genre;
import lombok.SneakyThrows;
import util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao implements Dao<Long, Book> {

    private static final BookDao INSTANCE = new BookDao();

    private static final String CREATE_BOOK_ORDER = """
            INSERT INTO books (name, author, year, genre, cost) 
            VALUES (?,?,?,?,?)
            """;
    private static final String FIND_ALL = """
            SELECT id, name, author, year, genre, cost
            FROM books
            ORDER BY id
            """;

    private static final String FIND_BY_ID = """
            SELECT id, name, author, year, genre, cost
            FROM books
            WHERE id = ?
            """;

    private static final String FIND_BY_AUTHOR = """
            SELECT id, name, author, year, genre, cost
            FROM books
            WHERE author = ?
            """;

    @SneakyThrows
    public Optional<Book> findByAuthor(String stock) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(FIND_BY_AUTHOR);
            preparedStatement.setObject(1, stock);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(buildBook(resultSet));
            }
            return Optional.empty();
        }
    }

    @SneakyThrows
    public Book createBook(Book book) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(CREATE_BOOK_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, book.getName());
            preparedStatement.setObject(2, book.getAuthor());
            preparedStatement.setObject(3, book.getYear());
            preparedStatement.setObject(4, book.getGenre().name());
            preparedStatement.setObject(5, book.getCost());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            book.setId(generatedKeys.getObject(1, Long.class));
            return book;
        }
    }

    @SneakyThrows
    @Override
    public List<Book> findAll() {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(FIND_ALL);
            List<Book> bookList = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookList.add(buildBook(resultSet));

            }
            return bookList;
        }
    }

    @Override
    @SneakyThrows
    public Optional<Book> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return Optional.ofNullable(buildBook(resultSet));
            }
            return Optional.empty();
        }
    }

    private static Book buildBook(ResultSet resultSet) throws SQLException {
        return Book.builder()
                .id(resultSet.getObject("id", Long.class))
                .name(resultSet.getObject("name", String.class))
                .author(resultSet.getObject("author", String.class))
                .year(LocalDate.from(resultSet.getTimestamp("year").toLocalDateTime()))
                .genre(Genre.find(resultSet.getObject("genre", String.class)).orElse(null))
                .cost(resultSet.getObject("cost", Integer.class))
                .build();
    }

    public static BookDao getInstance() {
        return INSTANCE;
    }
}
