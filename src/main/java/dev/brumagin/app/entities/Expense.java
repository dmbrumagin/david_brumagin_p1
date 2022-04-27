package dev.brumagin.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expense {

    @PrimaryKey(name = "expense_id")
    private int expenseId;
    @Column(name = "description")
    private String description;
    @Column(name = "cost")
    private double cost;
    @Column(name = "status")
    private ExpenseStatus status;
    @Column(name = "employee_id")
    private int employeeId;
}
