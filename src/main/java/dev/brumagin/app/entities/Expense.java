package dev.brumagin.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @PrimaryKey(name = "expense_id")
    private int expenseId;
    @Column(name = "description")
    private String description;
    @Column(name = "cost")
    private double cost;
    @Column(name = "status")
    private ExpenseStatus status;
    @ForeignKey
    @Column(name = "employee_id")
    private int employeeId;
}
