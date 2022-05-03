package dev.brumagin.test.data;

import dev.brumagin.app.data.EmployeeORM;
import dev.brumagin.app.data.ExpenseORM;
import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ORMTest {

    EmployeeORM<Employee> employeeORM = new EmployeeORM();
    ExpenseORM<Expense> expenseORM = new ExpenseORM();

    @Test
    void create_entity(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee= employeeORM.createEntity(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        expense = expenseORM.createEntity(expense);
        System.out.println(employee.getEmployeeId() +" em");
        System.out.println(expense.getExpenseId() + " ex");
        Assertions.assertNotEquals(0,employee.getEmployeeId());
        Assertions.assertNotEquals(0,expense.getExpenseId());
    }

    @Test
    void get_entity(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee= employeeORM.createEntity(employee);
        employee = employeeORM.getEntityById(employee.getEmployeeId());
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        expense = expenseORM.createEntity(expense);
        expenseORM.getEntityById(expense.getExpenseId());
    }

    @Test
    void get_all_entities(){
        employeeORM.getAllEntities();
        expenseORM.getAllEntities();
    }

    @Test
    void update_entity(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee= employeeORM.createEntity(employee);
        employee.setLastName("Test");
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        expense = expenseORM.createEntity(expense);
        expense.setDescription("test");
        expenseORM.updateEntity(expense);

    }

}
