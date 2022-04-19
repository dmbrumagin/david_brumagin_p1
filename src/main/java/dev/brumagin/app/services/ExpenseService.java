package dev.brumagin.app.services;

import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;

import java.util.List;

public interface ExpenseService {

    boolean createExpenses (List<Expense> expenses);
    boolean createExpense(Expense expense);
    List<Expense> getAllExpenses();
    List<Expense> getAllExpenses(ExpenseStatus status);
    List<Expense> getAllExpenses(ExpenseStatus status, int employeeId);
    boolean updateAllExpenseStatus(ExpenseStatus status, int employeeId);
    boolean updateExpenseStatus(ExpenseStatus status, Expense expense );
    List<Expense> deleteExpenses(List<Expense> expenses);
    boolean deleteExpense(Expense expense);
}
