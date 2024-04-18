package ie.atu.app;

import java.sql.*;

public interface CRUD {
    void create(Connection conn) throws SQLException;
    void read(Connection conn) throws SQLException;
    void update(Connection conn) throws SQLException;
    void delete(Connection conn) throws SQLException;
}

