package dev.brumagin.test.services;

import dev.brumagin.app.data.EmployeeDAO;
import dev.brumagin.app.data.EmployeeDAOPostgresImpl;
import org.junit.jupiter.api.Test;

class EmployeeServiceTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();

    @Test
    void create_employee(){
    }
}
