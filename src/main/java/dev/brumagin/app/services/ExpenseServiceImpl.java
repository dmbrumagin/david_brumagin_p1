package dev.brumagin.app.services;

import dev.brumagin.app.data.ExpenseDAO;
import dev.brumagin.app.data.ExpenseDAOPostgresImpl;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService{

    private ExpenseDAO expenseDAO;

    ExpenseServiceImpl(){
        expenseDAO = new ExpenseDAOPostgresImpl();
    }

    @Override
    public boolean createExpenses(List<Expense> expenses) {
        return false;
    }

    @Override
    public boolean createExpense(int employeeId, String description, double cost) {
        return false;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return null;
    }

    @Override
    public List<Expense> getAllExpenses(ExpenseStatus status) {
        return null;
    }

    @Override
    public List<Expense> getAllExpenses(ExpenseStatus status, int employeeId) {
        return null;
    }

    @Override
    public boolean updateAllExpenseStatus(int employeeId, ExpenseStatus status) {
        return false;
    }

    @Override
    public boolean updateExpenseStatus(Expense expense, int employeeId, ExpenseStatus status) {
        return false;
    }

    @Override
    public boolean deleteExpenses(List<Expense> expenses) {
        return false;
    }

    @Override
    public boolean deleteExpense(Expense expense) {
        return false;
    }


}
