package dev.brumagin.app.services;

import dev.brumagin.app.data.ExpenseDAO;
import dev.brumagin.app.data.ExpenseDAOPostgresImpl;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;

import java.util.List;
import java.util.stream.Stream;

public class ExpenseServiceImpl implements ExpenseService{

    private ExpenseDAO expenseDAO;

    public ExpenseServiceImpl(){
        expenseDAO = new ExpenseDAOPostgresImpl();
    }

    @Override
    public boolean createExpenses(List<Expense> expenses) {
        return false;
    }

    @Override
    public boolean createExpense(Expense expense) {
        System.out.println(expense);
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
    public List<Expense> getAllExpenses(ExpenseStatus status, int employeeId) {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        expenses.forEach(d->expenses.removeIf(f->f.getStatus()!=status));
        return expenses;
    }

    @Override
    public boolean updateAllExpenseStatus( ExpenseStatus status,int employeeId) {
        List<Expense> expenses = getAllExpenses(ExpenseStatus.PENDING,employeeId);
        for(Expense expense :expenses){
            expense.setStatus(status);
            expenseDAO.updateExpense(expense);
        }
        return false;
    }

    @Override
    public boolean updateExpenseStatus( ExpenseStatus status,Expense expense) {
        expense.setStatus(status);
        expenseDAO.updateExpense(expense);

        return false;
    }

    @Override
    public List<Expense> deleteExpenses(List<Expense> expenses) {
        List<Expense> compare = getAllExpenses(ExpenseStatus.PENDING);
        for(Expense expense : expenses){
            if(compare.contains(expense)) {
                if (expenseDAO.deleteExpense(expense))
                    expenses.remove(expense);
            }
            else{
                Logger.log("Failed to remove expense: \n"+ expense +"\n Status was not PENDING", LogLevel.INFO);
            }

        }
        return expenses;  //failed to remove these expenses
    }

    @Override
    public boolean deleteExpense(Expense expense) {
        if(expenseDAO.getExpenseById(expense.getExpenseId()).getStatus().name()=="PENDING")
            return expenseDAO.deleteExpense(expense);
        else{
            Logger.log("Failed to remove expense: \n"+ expense +"\n Status was not PENDING", LogLevel.INFO);
            return false;
        }
    }


}
