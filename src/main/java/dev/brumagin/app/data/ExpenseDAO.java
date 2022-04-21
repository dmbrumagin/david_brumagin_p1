package dev.brumagin.app.data;

import dev.brumagin.app.entities.Expense;

import java.util.List;

public interface ExpenseDAO {
    Expense createExpense(Expense expense);
    Expense getExpenseById(int expenseId);
    List<Expense> getAllExpenses();
    boolean updateExpense(Expense expense);
    boolean deleteExpense(int expenseId);
}
