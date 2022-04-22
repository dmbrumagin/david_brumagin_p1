package dev.brumagin.app.services;

import dev.brumagin.app.data.ExpenseDAO;
import dev.brumagin.app.data.ExpenseDAOPostgresImpl;
import dev.brumagin.app.entities.CannotEditException;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;
import java.util.List;

/**
 * A class that implements services related to the Expense Entity
 */
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseDAO expenseDAO; //the expense Data Access Object

    public ExpenseServiceImpl(){
        expenseDAO = new ExpenseDAOPostgresImpl();
    }

    /**
     * Create an expense
     * If the expense returned by the DAO is not null the expense was created
     * @param expense the expense to create in the database
     * @return if the expense was successfully created
     */
    @Override
    public boolean createExpense(Expense expense){
        return expenseDAO.createExpense(expense) !=null;
    }

    /**
     * A method to get an expense by its primary key ID #
     * @param expenseId the primary key of the database entry
     * @return the expense in the database at the given ID
     */
    @Override
    public Expense getExpenseById(int expenseId){
        return expenseDAO.getExpenseById(expenseId);
    }

    /**
     * A method to get all expenses in the database
     * @return A list of all expenses in the database
     */
    @Override
    public List<Expense> getAllExpenses() {
        return expenseDAO.getAllExpenses();
    }

    /**
     * A method to get all expenses of a given status in the database
     * @param status the status of the expenses to return
     * @return A list of every expense in the database with the given status
     */
    @Override
    public List<Expense> getAllExpenses(ExpenseStatus status) {
        List<Expense> expenses = getAllExpenses();
        expenses.removeIf(e->e.getStatus()!=status);
        return expenses;
    }

    /**
     * A method to get all expenses by a given employee primary key ID
     * @param employeeId the primary key ID of the employee
     * @return A list of every expense in the database by the given employee
     */
    @Override
    public List<Expense> getAllExpenses( int employeeId) {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        expenses.removeIf(f-> f.getEmployeeId() !=employeeId);
        return expenses;
    }

    /**
     * A method to update Expenses AND - APPROVE or DENY expenses
     * @param expense the expense record
     * @param status the status to change to upon completion
     * @return if the expense was updated successfully
     * @throws CannotEditException the update cannot be completed because the record is not PENDING
     */
    @Override
    public boolean updateExpense(Expense expense,ExpenseStatus status) throws CannotEditException {
        if(expenseDAO.getExpenseById(expense.getExpenseId()) != null){
            if(expenseDAO.getExpenseById(expense.getExpenseId()).getStatus().name().equals("PENDING")) {
                expense.setStatus(status);
                return expenseDAO.updateExpense(expense);
            }
            else {
                throw new CannotEditException();
            }
        }
        else {
            Logger.log("Failed to update expense: \n"+ expense +"\nStatus was not PENDING", LogLevel.INFO);
            return false;
        }
    }

    /**
     * A method to delete expenses
     * @param expenseId the primary key ID of the expense
     * @return if the expense was deleted successfully
     * @throws CannotEditException the deletion cannot be completed because the record is not PENDING
     */
    @Override
    public boolean deleteExpense(int expenseId) throws CannotEditException {
        if(expenseDAO.getExpenseById(expenseId) != null){
            if(expenseDAO.getExpenseById(expenseId).getStatus().name().equals("PENDING"))
                return expenseDAO.deleteExpense(expenseId);
            else {
                throw new CannotEditException();
            }
        }
        else{
            Logger.log("Failed to remove expense: \n"+ expenseId +"\nStatus was not PENDING", LogLevel.INFO);
            return false;
        }
    }
}
