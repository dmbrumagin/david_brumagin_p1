package dev.brumagin.test.services;

import dev.brumagin.app.entities.CannotEditException;
import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.services.EmployeeService;
import dev.brumagin.app.services.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class EmployeeServiceTests {

    static EmployeeService employeeService = new EmployeeServiceImpl();

    @Test
    void create_employee() throws CannotEditException {
        Employee employee = new Employee(0,"Bob","Dylan");
        Assertions.assertNotNull(employeeService.createEmployee(employee));
        employeeService.deleteEmployee(employee.getEmployeeId());
    }

    @Test
    void get_employee_by_id() throws CannotEditException {
        Employee employee = new Employee(0,"Bob","Dylan");
        employeeService.createEmployee(employee);
        Employee compare = employeeService.getEmployeeById(employee.getEmployeeId());
        Assertions.assertEquals(compare.getFirstName(),employee.getFirstName());
        employeeService.deleteEmployee(employee.getEmployeeId());
    }

    @Test
    void get_all_employees() throws CannotEditException {
        Employee employee = new Employee(0,"Bob","Dylan");
        Employee employee2 = new Employee(0,"Bob2","Dylan2");
        Employee employee3 = new Employee(0,"Bob3","Dylan3");
        employeeService.createEmployee(employee);
        employeeService.createEmployee(employee2);
        employeeService.createEmployee(employee3);
        List<Employee> employees = employeeService.getAllEmployees();

        Assertions.assertNotEquals(0,employees.size());
        employeeService.deleteEmployee(employee.getEmployeeId());
        employeeService.deleteEmployee(employee2.getEmployeeId());
        employeeService.deleteEmployee(employee3.getEmployeeId());
    }

    @Test
    void update_employee() throws CannotEditException {
        Employee employee = new Employee(0,"Bob","Dylan");
        employeeService.createEmployee(employee);
        employee.setFirstName("test");
        employee.setLastName("update");
        employeeService.updateEmployee(employee);
        Assertions.assertEquals("test", employeeService.getEmployeeById(employee.getEmployeeId()).getFirstName());
        employeeService.deleteEmployee(employee.getEmployeeId());
    }

    @Test
    void delete_employee() throws CannotEditException {
        Employee employee = new Employee(0,"Bob","Dylan");
        employeeService.createEmployee(employee);
        Assertions.assertTrue(employeeService.deleteEmployee(employee.getEmployeeId()));
    }
}
