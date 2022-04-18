package dev.brumagin.app.data;


import dev.brumagin.app.entities.Expense;

public interface ExpenseDAO {
    public Expense createExpense(Expense expense);
    public Expense getExpenseById(String Id);
    public boolean updateExpense(Expense expense);
    public boolean deleteExpense(Expense expense);
}
