package dev.brumagin.app.data;

import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.Expense;

import java.util.HashMap;
import java.util.Map;

public class EntityMapper {

    public Map<String,Class> entities= new HashMap<>();
    public Map<String,String> primaryKeys = new HashMap<>();

    EntityMapper(){
        entities.put("Employee",Employee.class);
        entities.put("Expense",Expense.class);
        primaryKeys.put("Employee","employee_id");
        primaryKeys.put("Expense","expense_id");
    }

}
