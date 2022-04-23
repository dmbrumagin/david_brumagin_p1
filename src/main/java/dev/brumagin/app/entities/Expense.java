package dev.brumagin.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @PrimaryKey
    private int expenseId;
    @Column
    private String description;
    @Column
    private double cost;
    @Column
    private ExpenseStatus status;
    @ForeignKey
    private int employeeId;
}
