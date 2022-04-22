package dev.brumagin.app.data;

import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.utilities.ConnectionUtility;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that acts as the Data Access Object Implementation for Employee Entities
 */
public class EmployeeDAOPostgresImpl implements EmployeeDAO{

    /**
     * A method to create employees in the database
     * @param employee the contents of the employee object to add the database
     * @return the added employee with ID
     */
    @Override
    public Employee createEmployee(Employee employee){
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "insert into employee (first_name,last_name) values (?,?);";
            PreparedStatement ps = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedKey = rs.getInt("employee_id");
            employee.setId(generatedKey);

            return employee;
        }
        catch (SQLException e){
            Logger.log("**The employee was not created; please check database access and parameters.**\n"+employee,LogLevel.WARNING);
            return null;
        }
    }

    /**
     * A method to get an employee from the database by its ID(PK)
     * @param employeeId the primary key in the database of the employee record
     * @return the returned employee record
     */
    @Override
    public Employee getEmployeeById(int employeeId) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "select * from employee where employee_id=?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Employee employee = new Employee();
            employee.setId(employeeId);
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            return employee;
        }
        catch (SQLException e){
            Logger.log("**The employee was not found; please check database access and parameters.**\nemployee id: " +employeeId,LogLevel.WARNING);
            return null;
        }
    }

    /**
     * A method to get all employee records from the database
     * @return a List of all employee records in the database
     */
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "select * from employee;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("employee_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employees.add(employee);
            }
            return employees;
        }
        catch (SQLException e){
            Logger.log("**There are no employees; please check database access.**",LogLevel.WARNING);
            return employees;
        }
    }

    /**
     * A method to update Employee records in the database
     * @param employee the contents of the record's updates
     * @return if the employee was updated in the database
     */
    @Override
    public boolean updateEmployee(Employee employee) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "update employee set first_name = ?, last_name = ? where employee_id = ?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setInt(3, employee.getId());
            return ps.executeUpdate() != 0;
        }
        catch (SQLException e){
            Logger.log("**The employee was not found; please check database access and parameters.**\n" + employee,LogLevel.WARNING);
            return false;
        }
    }

    /**
     * A method to delete employees from the database
     * @param employeeId the primary key ID of the employee to delete
     * @return if the employee was deleted from the database
     */
    @Override
    public boolean deleteEmployee(int employeeId) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "delete from employee where employee_id = ?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, employeeId);
            return  ps.executeUpdate()!=0;
        }
        catch (SQLException e){
            Logger.log("**The employee was not found; please check database access and parameters.**\n" + employeeId,LogLevel.WARNING);
            return false;
        }
    }
}
