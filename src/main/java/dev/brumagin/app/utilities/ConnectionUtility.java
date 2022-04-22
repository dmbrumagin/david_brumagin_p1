package dev.brumagin.app.utilities;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A Class that contains helpful functions in order to establish and mantain SQL database connections.
 */
public class ConnectionUtility {

    private ConnectionUtility(){

    }

    /**
     * The method that allows a database connection to be established
     * @return the database connection
     */
    public static java.sql.Connection createConnection() {
        java.sql.Connection connection = null;
        try {
            String url = System.getenv("EXPENSEDB");
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            Logger.log("Cannot access database. Please verify database path and credentials.",LogLevel.ERROR);
        }

        return connection;
    }
}
