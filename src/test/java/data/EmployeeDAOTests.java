package data;

import dev.brumagin.app.data.EmployeeDAO;
import dev.brumagin.app.data.EmployeeDAOPostgresImpl;
import dev.brumagin.app.entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class EmployeeDAOTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();
    @Test
    void create_employee() {
        Employee employee = new Employee(0,"Bob","Dylan");
        employee = employeeDAO.createEmployee(employee);
        Assertions.assertNotEquals(0,employee.getId());
        employeeDAO.deleteEmployee(employee.getId());
    }

    @Test
    void get_employee_by_id(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee = employeeDAO.createEmployee(employee);
        Employee compare = employeeDAO.getEmployeeById(employee.getId());
        Assertions.assertEquals(compare.getFirstName(),employee.getFirstName());
        employeeDAO.deleteEmployee(employee.getId());
        employeeDAO.deleteEmployee(compare.getId());
    }

    @Test
    void get_all_employees(){
        Employee employee = new Employee(0,"Bob","Dylan");
        Employee employee2 = new Employee(0,"Bob2","Dylan2");
        Employee employee3 = new Employee(0,"Bob3","Dylan3");
        employeeDAO.createEmployee(employee);
        employeeDAO.createEmployee(employee2);
        employeeDAO.createEmployee(employee3);
        List<Employee> employees = employeeDAO.getAllEmployees();

        Assertions.assertNotEquals(employees.size(),0);
        employeeDAO.deleteEmployee(employee.getId());
        employeeDAO.deleteEmployee(employee2.getId());
        employeeDAO.deleteEmployee(employee3.getId());
    }

    @Test
    void update_employee(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee =   employeeDAO.createEmployee(employee);
        employee.setFirstName("test");
        employee.setLastName("update");
        employeeDAO.updateEmployee(employee);
        Assertions.assertEquals("test",employeeDAO.getEmployeeById(employee.getId()).getFirstName());
        employeeDAO.deleteEmployee(employee.getId());
    }

    @Test
    void delete_employee(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employeeDAO.createEmployee(employee);
        Assertions.assertTrue(employeeDAO.deleteEmployee(employee.getId()));
    }


}
