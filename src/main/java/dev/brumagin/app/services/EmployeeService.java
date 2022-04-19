package dev.brumagin.app.services;

import dev.brumagin.app.entities.Employee;

import java.util.List;

public interface EmployeeService {

    boolean createEmployees(List<Employee> employees);
    boolean createEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    boolean updateEmployee(Employee employee);
    boolean deleteEmployee(int employeeId);

}
