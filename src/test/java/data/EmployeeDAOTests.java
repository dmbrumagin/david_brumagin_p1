package data;

import dev.brumagin.app.data.EmployeeDAO;
import dev.brumagin.app.data.EmployeeDAOPostgresImpl;
import dev.brumagin.app.entities.Employee;
import org.junit.jupiter.api.Test;

public class EmployeeDAOTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();

    @Test
    void create_employee(){

        Employee employee = new Employee(0,"Bob","Dylan");

    }


}
