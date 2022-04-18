package dev.brumagin.app.data;

import dev.brumagin.app.entities.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {

    public Employee createEmployee(Employee employee) throws SQLException;
    public Employee getEmployeeById(int Id);
    public List<Employee> getAllEmployees();
    public boolean updateEmployee(Employee employee);
    public boolean deleteEmployee(Employee employee);

}
