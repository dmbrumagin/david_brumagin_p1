package dev.brumagin.app.services;

import dev.brumagin.app.data.*;
import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.CannotEditException;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeDAO employeeDAO;
    private final ExpenseDAO expenseDAO;

    public EmployeeServiceImpl(){
        employeeDAO = new EmployeeDAOPostgresImpl();
        expenseDAO = new ExpenseDAOPostgresImpl();
    }

    @Override
    public boolean createEmployee(Employee employee) {
        return employeeDAO.createEmployee(employee) != null;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeDAO.getEmployeeById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public boolean updateEmployee(Employee employee) throws CannotEditException {
        if(canEdit(employee.getId())){
            return employeeDAO.updateEmployee(employee);}
        return false;
    }

    @Override
    public boolean deleteEmployee(int employeeId) throws CannotEditException {
        if(canEdit(employeeId))
            return employeeDAO.deleteEmployee(employeeId);
        return false;
    }

    public boolean canEdit(int employeeId) throws CannotEditException {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        for(Expense e : expenses){
            if(e.getEmployeeId()==employeeId)
                throw new CannotEditException();
        }
        return true;
    }
}
