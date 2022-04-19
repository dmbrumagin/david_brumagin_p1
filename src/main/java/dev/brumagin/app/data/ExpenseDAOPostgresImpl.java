package dev.brumagin.app.data;

import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import dev.brumagin.app.utilities.ConnectionUtility;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOPostgresImpl implements ExpenseDAO{

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
            String id = rs.getString("expense_id");
            expense.setExpenseId(id);
            return expense;
        }
        catch (SQLException e){
            Logger.log("**The expense was not created; please check database access and parameters.**\n"+expense, LogLevel.WARNING);
            return null;
        }
    }

    @Override
    public Expense getExpenseById(String Id) {
        System.out.println(Id);
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "select * from expense where expense_id = ?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1,Id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Expense expense = new Expense();
            expense.setExpenseId(Id);
            expense.setStatus(ExpenseStatus.valueOf(rs.getString("status")));
            expense.setCost(rs.getDouble("cost"));
            expense.setDescription(rs.getString("description"));
            expense.setEmployeeId(rs.getInt("employee_id"));
            return expense;
        }
        catch (SQLException e){
            Logger.log("**The expense was not found; please check database access and parameters.**\n"+Id, LogLevel.WARNING);
            return null;
        }
    }

    @Override
    public List<Expense> getAllExpenses() {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String statement = "select * from expense;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            List<Expense> expenses = new ArrayList();
            while(rs.next()){
                Expense expense = new Expense();
                expense.setExpenseId(rs.getString("expense_id"));
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
            ps.setString(5,expense.getExpenseId());
            ps.execute();
            return true;
        }
        catch (SQLException e){
            Logger.log("Expense was not updated; please check database access, that your expense currently exists, and that your updated expense is valid.**\n"+expense, LogLevel.WARNING);
            return false;
        }
    }

    @Override
    public boolean deleteExpense(Expense expense) {
        try{
            Connection connection = ConnectionUtility.createConnection();
            String statement = "delete from expense where expense_id = ?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1,expense.getExpenseId());
            ps.execute();
            return true;
        }
        catch (SQLException e){
            Logger.log("Expense was not deleted; please check database access and that your expense currently exists.**\n"+expense, LogLevel.WARNING);
            return false;
        }
    }
}
