package dev.brumagin.app.services;

import dev.brumagin.app.entities.Employee;

import java.util.List;

public interface EmployeeService {

    boolean createEmployees(List<Employee> employees);
    boolean createEmployee(String firstName, String lastName);
    Employee getEmployeeById(int id);
    Employee getAllEmployees();
    // not required for routes boolean updateEmployee(Employee employee);
    boolean deleteEmployee(Employee employee);

}
