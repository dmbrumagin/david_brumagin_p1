package dev.brumagin.app.services;

import dev.brumagin.app.entities.CannotEditException;
import dev.brumagin.app.entities.Employee;
import java.util.List;

public interface EmployeeService {

    boolean createEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    boolean updateEmployee(Employee employee) throws CannotEditException;
    boolean deleteEmployee(int employeeId) throws CannotEditException;

}
