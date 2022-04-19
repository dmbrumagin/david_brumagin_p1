package services;

import dev.brumagin.app.data.EmployeeDAO;
import dev.brumagin.app.data.EmployeeDAOPostgresImpl;
import dev.brumagin.app.entities.Employee;
import org.junit.jupiter.api.Test;

public class EmployeeServiceTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();

    @Test
    void create_employee(){
        //Employee employee = new Employee(0,"Bob","Dylan");
       // employeeDAO.createEmployee(employee);
    }
}
