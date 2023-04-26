package dao;

import entity.Order;
import lombok.SneakyThrows;
import util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao implements Dao<Long, Order> {

    private static final OrderDao INSTANCE = new OrderDao();

    private static final String FIND_BY_ID = """
            SELECT id,customer_name,date_of_order,sum
            FROM orders
            WHERE id=?
            """;

    private static final String FIND_ALL = """
            SELECT id,customer_name,date_of_order,sum
            FROM orders
            """;

    private static final String FIND_BY_DATE = """
            SELECT id,customer_name,date_of_order,sum
            FROM orders
            WHERE date_of_order BETWEEN ? AND ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE orders
            SET customer_name = ?
            WHERE id = ?        
            """;
    private static final String DELETE_SQL = """
            DELETE FROM orders
            WHERE id = ?
            """;
    private static final String CREATE_NEW_ORDER = """
            INSERT INTO orders (customer_name, date_of_order, sum)
            VALUES (?,(SELECT now()),?)
            """;
    private static final String UPDATE_ORDER = """
            UPDATE orders 
            SET customer_name = ?,
            sum = ?
            WHERE id = ?
            """;

    @SneakyThrows
    public void update(Long id, String name) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setObject(1, name);
            preparedStatement.setObject(2, id);
            preparedStatement.executeUpdate();
        }
    }


    @SneakyThrows
    public void deleteOrder(Long id) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        }
    }



    @SneakyThrows
    public Order createNewOrder(Order entity) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(CREATE_NEW_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, entity.getCustomerName());
            preparedStatement.setObject(2, entity.getSumOfOrder());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject(1, Long.class));
            return entity;
        }
    }
    @SneakyThrows
    public Order updateOrder(Order orderEntity,Long id) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(UPDATE_ORDER);
            preparedStatement.setObject(1,orderEntity.getCustomerName());
            preparedStatement.setObject(2,orderEntity.getSumOfOrder());
            preparedStatement.setObject(3,id);
            preparedStatement.executeUpdate();

            orderEntity.setId(id);
            return orderEntity;
        }
    }
    @SneakyThrows
    public List<Order> findByDateInterval(LocalDate first,LocalDate second){
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(FIND_BY_DATE);
            preparedStatement.setObject(1,first);
            preparedStatement.setObject(2,second);
            List<Order> orderList = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                orderList.add(buildOrder(resultSet));
            }
            return orderList;
        }
    }


    @SneakyThrows
    @Override
    public List<Order> findAll() {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(FIND_ALL);
            List<Order> orderList = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderList.add(buildOrder(resultSet));
            }
            return orderList;
        }
    }

    @SneakyThrows
    @Override
    public Optional<Order> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            var preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(buildOrder(resultSet));
            }
            return Optional.empty();
        }
    }

    private static Order buildOrder(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .id(resultSet.getObject("id", Long.class))
                .customerName(resultSet.getObject("customer_name", String.class))
                .dateOfOrder(LocalDate.from(resultSet.getTimestamp("date_of_order").toLocalDateTime()))
                .sumOfOrder(resultSet.getObject("sum", BigDecimal.class))
                .build();
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
