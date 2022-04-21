package dev.brumagin.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    private int expenseId;
    private String description;
    private double cost;
    private ExpenseStatus status;
    private int employeeId;
}
