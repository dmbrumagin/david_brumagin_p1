package dev.brumagin.app.utilities;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    private ConnectionUtility(){

    }
    public static java.sql.Connection createConnection() {
        java.sql.Connection connection = null;
        try {
            String url = System.getenv("EXPENSEDB");
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            Logger.log("Cannot access database. Please verify database path and credentials.",LogLevel.WARNING);
        }

        return  connection;
    }
}
