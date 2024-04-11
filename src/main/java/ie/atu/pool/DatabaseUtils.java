package ie.atu.pool;

import java.sql.*;
import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

public class DatabaseUtils {
    // Later we can store this type of data in a better location like a properties file
    private static final String URL = "jdbc:mysql://localhost/SubwayDatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final DataSource dataSource;

    // Static block to initialize the DataSource
    static {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        mysqlDataSource.setUser(USERNAME);
        mysqlDataSource.setPassword(PASSWORD);
        dataSource = mysqlDataSource;
    }

    // Method to get a connection from the DataSource
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
