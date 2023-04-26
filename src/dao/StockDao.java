package dao;

import entity.Book;
import entity.Stock;
import lombok.SneakyThrows;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class StockDao implements Dao<Long, Stock> {

    private static final StockDao INSTANCE = new StockDao();

    private static final String FIND_BY_ID = """
            SELECT id,book_id,count,order_id
            FROM stock
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO stock(book_id, count, order_id) 
            VALUES (?,?,?)
            """;
    private static final String FIND_BY_ORDER_ID = """
            SELECT id,book_id,count
            FROM stock
            WHERE order_id = ?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM stock
            WHERE order_id = ?;
            """;
    private static final String FIND_ALL = """
            SELECT id,book_id,count,order_id
            FROM stock;
            """;

    @SneakyThrows
    public List<Stock> findByOrderId(Long id) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(FIND_BY_ORDER_ID);
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();
            List<Stock> stocks = new ArrayList<>();
            while (resultSet.next()) {
                stocks.add(buildStock(resultSet));
            }

            return stocks;
        }
    }


    @SneakyThrows
    public Stock create(Stock entity) {
        try (Connection connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, entity.getBookId());
            preparedStatement.setObject(2, entity.getCount());
            preparedStatement.setObject(3, entity.getOrderId());

            preparedStatement.executeUpdate();
            var keys = preparedStatement.getGeneratedKeys();
            keys.next();

            entity.setId(keys.getObject("id", Long.class));
            return entity;
        }
    }

    @Override
    @SneakyThrows
    public List<Stock> findAll() {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(FIND_ALL);
            var resultSet = preparedStatement.executeQuery();
            List<Stock> stocks = new ArrayList<>();
            while (resultSet.next()){
                stocks.add(buildStock(resultSet));
            }
            return stocks;
        }
    }

    @Override
    @SneakyThrows
    public Optional<Stock> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setObject(1,id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Optional.ofNullable(buildStock(resultSet));
            }
            return Optional.empty();
        }
    }

    @SneakyThrows
    public void delete(Long orderId) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setObject(1,orderId);
            preparedStatement.executeUpdate();
        }
    }

    private static Stock buildStock(ResultSet resultSet) throws SQLException {
        return Stock.builder()
                .id(resultSet.getObject("id", Long.class))
                .bookId(resultSet.getObject("book_id", Long.class))
                .count(resultSet.getObject("count", Integer.class))
                .build();
    }

    public static StockDao getInstance() {
        return INSTANCE;
    }
}
