package dev.brumagin.app.data;

import dev.brumagin.app.entities.Employee;
import java.util.List;

public interface EmployeeDAO {

    Employee createEmployee(Employee employee);
    Employee getEmployeeById(int employeeId);
    List<Employee> getAllEmployees();
    boolean updateEmployee(Employee employee);
    boolean deleteEmployee(int employeeId);

}
