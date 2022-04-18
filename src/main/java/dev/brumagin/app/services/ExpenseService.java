package dev.brumagin.app.services;

import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;

import java.util.List;

public interface ExpenseService {

    boolean createExpenses (List<Expense> expenses);
    boolean createExpense(int employeeId,String description, double cost);
    List<Expense> getAllExpenses();
    List<Expense> getAllExpenses(ExpenseStatus status);
    List<Expense> getAllExpenses(ExpenseStatus status,int employeeId);
    boolean updateAllExpenseStatus(int employeeId, ExpenseStatus status);
    boolean updateExpenseStatus(Expense expense, int employeeId, ExpenseStatus status);
    boolean deleteExpenses(List<Expense> expenses);
    boolean deleteExpense(Expense expense);
}
