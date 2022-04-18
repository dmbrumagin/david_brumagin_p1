package dev.brumagin.app.data;

import dev.brumagin.app.entities.Expense;

import java.util.List;

public interface ExpenseDAO {
    public Expense createExpense(Expense expense);
    public Expense getExpenseById(String Id);
    public List<Expense> getAllExpenses();
    public boolean updateExpense(Expense expense);
    public boolean deleteExpense(Expense expense);
}
