package dev.drumagin.test.utlilities;

import dev.brumagin.app.utilities.ConnectionUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTests {

    @Test

    void create_connection(){
        Connection connection = ConnectionUtility.createConnection();
        System.out.println(connection);
        Assertions.assertNotNull(connection);
    }
}
