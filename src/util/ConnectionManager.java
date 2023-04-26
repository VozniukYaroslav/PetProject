package util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;

@UtilityClass
public class ConnectionManager {

    private static final String name = "db.name";
    private static final String password = "db.password";
    private static final String url = "db.url";

    static {
        loadDriver();
    }

    @SneakyThrows
    private static void loadDriver() {
        Class.forName("org.postgresql.Driver");
    }

    @SneakyThrows
    public Connection get() {
        return DriverManager.getConnection(
                PropertiesUtil.getKey(url),
                PropertiesUtil.getKey(name),
                PropertiesUtil.getKey(password));
    }


}
