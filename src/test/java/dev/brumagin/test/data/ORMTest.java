package dev.brumagin.test.data;

import dev.brumagin.app.data.EmployeeORM;
import dev.brumagin.app.data.ExpenseORM;
import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ORMTest {

    EmployeeORM<Employee> employeeD = new EmployeeORM();
    ExpenseORM<Expense> expenseORM = new ExpenseORM();

    @Test
    @Disabled
    void create_entity(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee= employeeD.createEntity(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        expense = expenseORM.createEntity(expense);
        System.out.println(employee.getEmployeeId() +" em");
        System.out.println(expense.getExpenseId() + " ex");
        Assertions.assertNotEquals(0,employee.getEmployeeId());
        Assertions.assertNotEquals(0,expense.getExpenseId());
    }

    @Test
    @Disabled
    void get_entity(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee= employeeD.createEntity(employee);
        System.out.println("employee id : "+employee.getEmployeeId());
        employee = employeeD.getEntityById(employee.getEmployeeId());
        System.out.println("employee id 2: "+employee.getEmployeeId());
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        expense = expenseORM.createEntity(expense);
        System.out.println(expense.getExpenseId() + "  :expense id");
        expenseORM.getEntityById(expense.getExpenseId());
    }

    @Test
    @Disabled
    void get_all_entities(){
        employeeD.getAllEntities();
        expenseORM.getAllEntities();
    }

    @Test
    @Disabled
    void update_entity(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee=employeeD.createEntity(employee);
        System.out.println(employee);
        employee.setLastName("Test");
        employee = employeeD.updateEntity(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        expense = expenseORM.createEntity(expense);
        expense.setDescription("test");
        expenseORM.updateEntity(expense);

    }

}
