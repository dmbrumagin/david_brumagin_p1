package dev.brumagin.app.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A Class that contains helpful functions in order to establish and mantain SQL database connections.
 */
public class ConnectionUtility {

    /**
     * The method that allows a database connection to be established
     * @return the database connection
     */
    public static Connection createConnection() {
        Connection connection;
        try {
            String url = System.getenv("EXPENSEDB");
            connection = DriverManager.getConnection(url);
            return connection;
        } catch (SQLException e) {
            Logger.log("Cannot access database. Please verify database path and credentials.",LogLevel.ERROR);
        }
        return null;
    }
}
