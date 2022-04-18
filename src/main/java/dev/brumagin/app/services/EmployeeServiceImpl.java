package dev.brumagin.app.services;

import dev.brumagin.app.data.EmployeeDAO;
import dev.brumagin.app.data.EmployeeDAOPostgresImpl;
import dev.brumagin.app.entities.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeDAO employeeDAO;

    EmployeeServiceImpl(){
        employeeDAO = new EmployeeDAOPostgresImpl();
    }

    @Override
    public boolean createEmployees(List<Employee> employees) {
        return false;
    }

    @Override
    public boolean createEmployee(String firstName, String lastName) {
        return false;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return null;
    }

    @Override
    public Employee getAllEmployees() {
        return null;
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        return false;
    }
}
