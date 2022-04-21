package dev.brumagin.app.data;

import dev.brumagin.app.data.EmployeeDAO;
import dev.brumagin.app.data.EmployeeDAOPostgresImpl;
import dev.brumagin.app.data.ExpenseDAO;
import dev.brumagin.app.data.ExpenseDAOPostgresImpl;
import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ExpenseDAOTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();
    static ExpenseDAO expenseDAO  = new ExpenseDAOPostgresImpl();

    @Test
    public void create_expense() {
        Employee employee = new Employee(0,"Bob","Dylan");
        employee = employeeDAO.createEmployee(employee);
        Expense expense = new Expense(0,"Ferrari",64333.34, ExpenseStatus.PENDING,employee.getId());
        expense = expenseDAO.createExpense(expense);
        Assertions.assertNotEquals(0,expense.getExpenseId());
        expenseDAO.deleteExpense(expense.getExpenseId());
        employeeDAO.deleteEmployee(employee.getId());
    }

    @Test
    void get_expense_by_id(){
        Employee employee = new Employee(0,"Circle","Lover");
        employee = employeeDAO.createEmployee(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getId());
        Expense compareExpense = expenseDAO.createExpense(expense);
        Assertions.assertEquals(compareExpense.getExpenseId(),expenseDAO.getExpenseById(expense.getExpenseId()).getExpenseId());
        expenseDAO.deleteExpense(expense.getExpenseId());
        employeeDAO.deleteEmployee(employee.getId());
    }

    @Test
    void get_all_expenses(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employeeDAO.createEmployee(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getId());
        expenseDAO.createExpense(expense);
        List<Expense> expenses = expenseDAO.getAllExpenses();
        Assertions.assertNotEquals(0,expenses.size());
        expenseDAO.deleteExpense(expense.getExpenseId());
        employeeDAO.deleteEmployee(employee.getId());
    }

    @Test
    void update_expense(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee =   employeeDAO.createEmployee(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getId());
        expenseDAO.createExpense(expense);
        expense.setDescription("test");
        expenseDAO.updateExpense(expense);
        Assertions.assertEquals("test",expenseDAO.getExpenseById(expense.getExpenseId()).getDescription());
        expenseDAO.deleteExpense(expense.getExpenseId());
        employeeDAO.deleteEmployee(employee.getId());
    }

    @Test
    void delete_expense(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee =   employeeDAO.createEmployee(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getId());
        expenseDAO.createExpense(expense);
        System.out.println(expense);
        Assertions.assertTrue( expenseDAO.deleteExpense(expense.getExpenseId()));
        employeeDAO.deleteEmployee(employee.getId());
    }

}
