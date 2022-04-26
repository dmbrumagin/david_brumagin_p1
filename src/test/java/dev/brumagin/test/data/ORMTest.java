package dev.brumagin.test.data;

import dev.brumagin.app.data.EmployeeORM;
import dev.brumagin.app.data.ExpenseORM;
import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ORMTest {

    EmployeeORM employeeD = new EmployeeORM();
    ExpenseORM expenseORM = new ExpenseORM();

    @Test
    void create_entity(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee= employeeD.createEntity(employee);
        Expense expense = new Expense(0,"pie",3.14, ExpenseStatus.PENDING,employee.getEmployeeId());
        expense = expenseORM.createEntity(expense);
        Assertions.assertNotEquals(0,employee.getEmployeeId());
        Assertions.assertNotEquals(0,expense.getExpenseId());
    }

    @Test
    void get_entity(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employee= employeeD.createEntity(employee);
       // employeeD.getEntityById(employee.getEmployeeId());
    }

}
