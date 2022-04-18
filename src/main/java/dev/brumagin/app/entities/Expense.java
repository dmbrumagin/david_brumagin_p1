package dev.brumagin.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    private String expenseId;
    private int employeeId;
    private String description;
    private double cost;
    private ExpenseStatus status;

}
