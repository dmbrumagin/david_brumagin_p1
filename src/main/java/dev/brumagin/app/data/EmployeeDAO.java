package dev.brumagin.app.data;

import dev.brumagin.app.entities.Employee;

public interface EmployeeDAO {

    public Employee createEmployee(Employee employee);
    public Employee getEmployeeById(int Id);
    public boolean updateEmployee(Employee employee);
    public boolean deleteEmployee(Employee employee);

}
