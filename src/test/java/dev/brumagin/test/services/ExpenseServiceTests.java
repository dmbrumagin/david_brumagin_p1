package dev.brumagin.test.services;

import dev.brumagin.app.entities.CannotEditException;
import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import dev.brumagin.app.services.EmployeeService;
import dev.brumagin.app.services.EmployeeServiceImpl;
import dev.brumagin.app.services.ExpenseService;
import dev.brumagin.app.services.ExpenseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ExpenseServiceTests {

    EmployeeService employeeService = new EmployeeServiceImpl();
    ExpenseService expenseService = new ExpenseServiceImpl();

    @Test
    void create_expense() throws CannotEditException {
        Employee employee = new Employee(0,"Bob","Dylan");
        employeeService.createEmployee(employee);
        Expense expense = new Expense(0,"Ferrari",64333.34, ExpenseStatus.PENDING,employee.getEmployeeId());
        expenseService.createExpense(expense);
        Assertions.assertNotEquals(0,expense.getExpenseId());
        expenseService.deleteExpense(expense.getExpenseId());
        employeeService.deleteEmployee(employee.getEmployeeId());
    }

    @Test
    void get_expense_by_id() throws CannotEditException {
        Employee employee = new Employee(0,"Circle","Lover");
        employee = employeeService.createEmployee(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        Expense compareExpense = expenseService.createExpense(expense);
        Assertions.assertEquals(compareExpense.getExpenseId(), expenseService.getExpenseById(expense.getExpenseId()).getExpenseId());
        expenseService.deleteExpense(expense.getExpenseId());
        employeeService.deleteEmployee(employee.getEmployeeId());
    }

    @Test
    void get_all_expenses() throws CannotEditException {
        Employee employee = new Employee(0,"Bob","Dylan");
        employeeService.createEmployee(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        expenseService.createExpense(expense);
        List<Expense> expenses = expenseService.getAllExpenses();
        Assertions.assertNotEquals(0,expenses.size());
        expenseService.deleteExpense(expense.getExpenseId());
        employeeService.deleteEmployee(employee.getEmployeeId());
    }

    @Test
    void update_expense() throws CannotEditException {
        Employee employee = new Employee(0,"Bob","Dylan");
        employee =   employeeService.createEmployee(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        expense = expenseService.createExpense(expense);
        expense.setDescription("test");
        expenseService.updateExpense(expense,ExpenseStatus.PENDING);
        Assertions.assertEquals("test", expenseService.getExpenseById(expense.getExpenseId()).getDescription());
        expenseService.deleteExpense(expense.getExpenseId());
        employeeService.deleteEmployee(employee.getEmployeeId());
    }

    @Test
    void delete_expense() throws CannotEditException {
        Employee employee = new Employee(0,"Bob","Dylan");
        employee =   employeeService.createEmployee(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        expenseService.createExpense(expense);
        System.out.println(expense);
        Assertions.assertTrue( expenseService.deleteExpense(expense.getExpenseId()));
        employeeService.deleteEmployee(employee.getEmployeeId());
    }
}
