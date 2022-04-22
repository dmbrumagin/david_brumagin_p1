package dev.brumagin.app.services;

import dev.brumagin.app.data.*;
import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.CannotEditException;
import java.util.List;

/**
 * A class the implements services for the Employee entity
 */
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeDAO employeeDAO; //the employee Data Access Object
    private final ExpenseDAO expenseDAO; //the expense Data AccessObject

    public EmployeeServiceImpl(){
        employeeDAO = new EmployeeDAOPostgresImpl();
        expenseDAO = new ExpenseDAOPostgresImpl();
    }

    /**
     * A method to create employees
     * @param employee the employee to create in the database
     * @return if the employee was successfully created
     */
    @Override
    public boolean createEmployee(Employee employee) {
        return employeeDAO.createEmployee(employee) != null;
    }

    /**
     * A method to get information relating to an employee based on its ID
     * @param id the primary key ID of the employee entry in the database
     * @return the employee relating to the ID in the database
     */
    @Override
    public Employee getEmployeeById(int id) {
        return employeeDAO.getEmployeeById(id);
    }

    /**
     * A method to get a list of all employees in the database
     * @return a List of all employees in the database
     */
    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    /**
     * A method to update an employee with a given record
     * @param employee the contents of the updated record
     * @return if the record was succesfully updated
     * @throws CannotEditException if the employee cannot be updated because it has expenses in the expense table.
     */
    @Override
    public boolean updateEmployee(Employee employee) throws CannotEditException {
        if(canEdit(employee.getId())){
            return employeeDAO.updateEmployee(employee);}
        return false;
    }

    /**
     * A method to delete an employee from the database with a given primary key ID
     * @param employeeId the primary key ID of the employee to delete
     * @return if the employee was successfully deleted
     * @throws CannotEditException if the employee cannot be deleted because it has expenses in the expense table
     */
    @Override
    public boolean deleteEmployee(int employeeId) throws CannotEditException {
        if(canEdit(employeeId))
            return employeeDAO.deleteEmployee(employeeId);
        return false;
    }

    /**
     * A method to determine if an employee is able to be edited
     * @param employeeId the primary key ID of the employee
     * @return if the employee is able to be edited
     * @throws CannotEditException the employee is not able to be edited
     */
    public boolean canEdit(int employeeId) throws CannotEditException {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        for(Expense e : expenses){
            if(e.getEmployeeId()==employeeId)
                throw new CannotEditException();
        }
        return true;
    }
}
