package dev.brumagin.app.data;

import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.Expense;

import java.util.HashMap;
import java.util.Map;

public class EntityMapper {

    private Map<String,Class> entities= new HashMap<>();
    private Map<String,String> primaryKeys = new HashMap<>();

    EntityMapper(){
        entities.put("Employee",Employee.class);
        entities.put("Expense",Expense.class);
        primaryKeys.put("Employee","employee_id");
        primaryKeys.put("Expense","expense_id");
    }

    public Map<String, Class> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, Class> entities) {
        this.entities = entities;
    }

    public Map<String, String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(Map<String, String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }
}
