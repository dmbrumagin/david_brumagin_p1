package dev.brumagin.app.data;

import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.utilities.ConnectionUtility;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;

import java.sql.*;
import java.util.List;

public class EmployeeDAOPostgresImpl implements EmployeeDAO{
    @Override
    public Employee createEmployee(Employee employee){

        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "insert into employee (employee_id,first_name,last_name) values (?,?,?);";
            PreparedStatement ps = null;
            ps = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, employee.getId());
            ps.setString(2, employee.getFirstName());
            ps.setString(3, employee.getLastName());

            ResultSet rs = ps.executeQuery();
            rs.next();
            int generatedKey = rs.getInt("employee_id");
            employee.setId(generatedKey);
            return employee;
        } catch (SQLException e) {
        Logger.log("# Employee was not created. Either the employee table was not found or a value to be inserted was invalid.\n" + employee.toString(), LogLevel.WARNING);
        return null;
    }


    }

    @Override
    public Employee getEmployeeById(int Id) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return false;
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        return false;
    }
}
