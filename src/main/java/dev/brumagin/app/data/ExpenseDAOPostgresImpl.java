package dev.brumagin.app.data;

import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import dev.brumagin.app.utilities.ConnectionUtility;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that acts as the Data Access Object Implementation for Expenses Entities
 */
public class ExpenseDAOPostgresImpl implements ExpenseDAO{

    /**
     * A method to create expenses in the database
     * @param expense the expense to add to the database
     * @return the expense that was added to the database with an ID
     */
    @Override
    public Expense createExpense(Expense expense) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "insert into expense (description, cost, status, employee_id) values (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,expense.getDescription());
            ps.setDouble(2,expense.getCost());
            ps.setString(3,expense.getStatus().name());
            ps.setInt(4,expense.getEmployeeId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt("expense_id");
            expense.setExpenseId(id);
            return expense;
        }
        catch (SQLException e){
            Logger.log("**The expense was not created; please check database access and parameters.**\n"+expense, LogLevel.WARNING);
            return null;
        }
    }

    /**
     * A method to get an expense from the database by its ID
     * @param expenseId the ID(PK) of the expense to retrieve from the database
     * @return the retrieved expense from the database
     */
    @Override
    public Expense getExpenseById(int expenseId) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "select * from expense where expense_id = ?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, expenseId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Expense expense = new Expense();
            expense.setExpenseId(expenseId);
            expense.setStatus(ExpenseStatus.valueOf(rs.getString("status")));
            expense.setCost(rs.getDouble("cost"));
            expense.setDescription(rs.getString("description"));
            expense.setEmployeeId(rs.getInt("employee_id"));

            return expense;
        }
        catch (SQLException e){
            Logger.log("**The expense was not found; please check database access and parameters.**\n"+expenseId, LogLevel.WARNING);
            return null;
        }
    }

    /**
     * A method to get all expense entries in the database
     * @return a list of all expenses from the database
     */
    @Override
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "select * from expense;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setStatus(ExpenseStatus.valueOf(rs.getString("status")));
                expense.setCost(rs.getDouble("cost"));
                expense.setDescription(rs.getString("description"));
                expense.setEmployeeId(rs.getInt("employee_id"));
                expenses.add(expense);
            }
            return expenses;
        }
        catch(SQLException e){
            Logger.log("Expenses were not found; please check database access and that an expense exists.**\n", LogLevel.WARNING);
            return null;
        }
    }

    /**
     * A method to update expenses in the database
     * @param expense the contents of the expense to add to the database
     * @return whether the expense was updated in the database
     */
    @Override
    public boolean updateExpense(Expense expense) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "update expense set description = ?, cost = ?, status = ?, employee_id = ? where expense_id = ?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1,expense.getDescription());
            ps.setDouble(2,expense.getCost());
            ps.setString(3,expense.getStatus().name());
            ps.setInt(4,expense.getEmployeeId());
            ps.setInt(5,expense.getExpenseId());
            ps.execute();
            return true;
        }
        catch (SQLException e){
            Logger.log("Expense was not updated; please check database access, that your expense currently exists, and that your updated expense is valid.**\n"+expense, LogLevel.WARNING);
            return false;
        }
    }

    /**
     * A method to delete expenses in the database
     * @param expenseId the ID(PK) of the expense to delete in the database
     * @return if the expense was deleted in the database
     */
    @Override
    public boolean deleteExpense(int expenseId) {
        try{
            Connection connection = ConnectionUtility.createConnection();
            String statement = "delete from expense where expense_id = ?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1,expenseId);
            ps.execute();
            return true;
        }
        catch (SQLException e){
            Logger.log("Expense was not deleted; please check database access and that your expense currently exists.**\n"+expenseId, LogLevel.WARNING);
            return false;
        }
    }
}
