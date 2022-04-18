package dev.brumagin.app.entities;

public class Expense {

    private String expenseId;
    private int employeeId;
    private String description;
    private double cost;
    private boolean approved;

    public Expense() {
    }

    public Expense(String expenseId, int employeeId, String description, double cost, boolean approved) {
        this.expenseId = expenseId;
        this.employeeId = employeeId;
        this.description = description;
        this.cost = cost;
        this.approved = approved;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId='" + expenseId + '\'' +
                ", employeeId=" + employeeId +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", approved=" + approved +
                '}';
    }
}
