package dev.brumagin.app.data;

import dev.brumagin.app.entities.Employee;

import java.util.List;

public interface EmployeeDAO {

    public Employee createEmployee(Employee employee);
    public Employee getEmployeeById(int Id);
    public List<Employee> getAllEmployees();
    public boolean updateEmployee(Employee employee);
    public boolean deleteEmployee(Employee employee);

}
