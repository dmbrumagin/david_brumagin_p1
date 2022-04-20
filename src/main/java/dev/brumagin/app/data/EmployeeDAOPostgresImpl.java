package dev.brumagin.app.data;

import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.utilities.ConnectionUtility;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOPostgresImpl implements EmployeeDAO{

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

    @Override
    public Employee getEmployeeById(int Id) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "select * from employee where employee_id=?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, Id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Employee employee = new Employee();
            employee.setId(Id);
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            return employee;
        }
        catch (SQLException e){
            Logger.log("**The employee was not found; please check database access and parameters.**\nemployee id: " +Id,LogLevel.WARNING);
            return null;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "select * from employee;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            List<Employee> employees = new ArrayList();
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
            return null;
        }
    }

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
