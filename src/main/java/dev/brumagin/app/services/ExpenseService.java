package dev.brumagin.app.services;

import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;

import java.util.List;

public interface ExpenseService {

    //boolean createExpenses (List<Expense> expenses);
    boolean createExpense(Expense expense);
    List<Expense> getAllExpenses();
    List<Expense> getAllExpenses(ExpenseStatus status);
    List<Expense> getAllExpenses(int employeeId);
    //List<Expense> getAllExpenses(ExpenseStatus status, int employeeId);
    //boolean updateAllExpenseStatus(ExpenseStatus status, int employeeId);
    public boolean updateExpense(Expense expense,ExpenseStatus status);
    //List<Expense> deleteExpenses(List<Expense> expenses);
    boolean deleteExpense(Expense expense);
}
