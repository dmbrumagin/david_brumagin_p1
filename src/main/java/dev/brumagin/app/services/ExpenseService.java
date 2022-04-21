package dev.brumagin.app.services;

import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import dev.brumagin.app.entities.NegativeExpenseException;

import java.util.List;

public interface ExpenseService {

    boolean createExpense(Expense expense) throws NegativeExpenseException;
    Expense getExpenseById(int expenseId);
    List<Expense> getAllExpenses();
    List<Expense> getAllExpenses(ExpenseStatus status);
    List<Expense> getAllExpenses(int employeeId);
    boolean updateExpense(Expense expense,ExpenseStatus status) throws NegativeExpenseException;
    boolean deleteExpense(Expense expense);
}
