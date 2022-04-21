package dev.brumagin.app.services;

import dev.brumagin.app.data.ExpenseDAO;
import dev.brumagin.app.data.ExpenseDAOPostgresImpl;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import dev.brumagin.app.entities.NegativeExpenseException;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;

import java.util.List;
import java.util.stream.Stream;

public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseDAO expenseDAO;

    public ExpenseServiceImpl(){
        expenseDAO = new ExpenseDAOPostgresImpl();
    }

    @Override
    public boolean createExpense(Expense expense) throws NegativeExpenseException {
        if (expense.getCost()<0)
            throw new NegativeExpenseException();
        return expenseDAO.createExpense(expense) !=null;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseDAO.getAllExpenses();
    }

    @Override
    public List<Expense> getAllExpenses(ExpenseStatus status) {
        List<Expense> expenses = getAllExpenses();
        expenses.removeIf(e->e.getStatus()!=status);
        return expenses;
    }

    @Override
    public List<Expense> getAllExpenses( int employeeId) {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        expenses.removeIf(f-> f.getEmployeeId() !=employeeId);
        return expenses;
    }

    @Override
    public boolean updateExpense(Expense expense,ExpenseStatus status) throws NegativeExpenseException {
        if(expense.getCost()<0)
            throw new NegativeExpenseException();
        if(expenseDAO.getExpenseById(expense.getExpenseId()).getStatus().name().equals("PENDING")) {
            expense.setStatus(status);
            return expenseDAO.updateExpense(expense);
        }
        else {
            Logger.log("Failed to update expense: \n"+ expense +"\n Status was not PENDING", LogLevel.INFO);
            return false;
        }
    }

    @Override
    public boolean deleteExpense(Expense expense) {
        System.out.println(expense);
        if(expenseDAO.getExpenseById(expense.getExpenseId()).getStatus().name().equals("PENDING"))
            return expenseDAO.deleteExpense(expense);
        else{
            Logger.log("Failed to remove expense: \n"+ expense +"\n Status was not PENDING", LogLevel.INFO);
            return false;
        }
    }


}
