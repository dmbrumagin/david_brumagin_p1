package dev.brumagin.app.services;

import dev.brumagin.app.entities.CannotEditException;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import java.util.List;

public interface ExpenseService {

    Expense createExpense(Expense expense);
    Expense getExpenseById(int expenseId);
    List<Expense> getAllExpenses();
    List<Expense> getAllExpenses(ExpenseStatus status);
    List<Expense> getAllExpenses(int employeeId);
    boolean updateExpense(Expense expense,ExpenseStatus status) throws CannotEditException;
    boolean deleteExpense(int expenseId) throws CannotEditException;
}
