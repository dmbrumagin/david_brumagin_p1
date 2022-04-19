package dev.brumagin.app.api;

import com.google.gson.Gson;
import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.Expense;
import dev.brumagin.app.entities.ExpenseStatus;
import dev.brumagin.app.services.EmployeeService;
import dev.brumagin.app.services.EmployeeServiceImpl;
import dev.brumagin.app.services.ExpenseService;
import dev.brumagin.app.services.ExpenseServiceImpl;
import io.javalin.Javalin;

import javax.xml.ws.Service;
import java.lang.*;
import java.util.Arrays;
import java.util.List;

public class ReimbursementApp {

    static EmployeeService employeeService = new EmployeeServiceImpl();
    static ExpenseService expenseService = new ExpenseServiceImpl();

    public static void main (String args[]){

        Javalin app = Javalin.create();

        app.post("/employees", context -> {
            String body = context.body();
            Gson gson = new Gson();
            Employee employee = gson.fromJson(body, Employee.class);
            if(employeeService.createEmployee(employee)) {
                context.status(201);
                context.result("Created a new employee.");
            }
            else{
                context.status(400);
                context.result("Did not create a new employee.");
            }
        });

        app.get("/employees", context -> {
            Gson gson = new Gson();
            String json = gson.toJson(employeeService.getAllEmployees());
            context.result(json);
        });

        app.get("/employees/{id}", context -> {
            Gson gson = new Gson();
            int id = Integer.parseInt(context.pathParam("id"));
            Employee employee = employeeService.getEmployeeById(id);
            if(employee == null) {
                context.status(404);
                context.result("Did not find employee: "+id);
            }
            else {
                String json = gson.toJson(employee);
                context.result(json);
            }
        });

        app.put("/employees/{id}",context -> {
            Gson gson = new Gson();
            String body = context.body();
            Employee employee = gson.fromJson(body, Employee.class);
            int id = Integer.parseInt(context.pathParam("id"));

            if(employeeService.updateEmployee(employee)){
                String json = gson.toJson(employeeService.getEmployeeById(id));
                context.result(json);
            }
            else{
                context.status(404);
                context.result("Did not find employee to update: "+id);
            }
        });

        app.delete("/employees/{id}",context -> {
            int id = Integer.parseInt(context.pathParam("id"));
           if(employeeService.deleteEmployee(id)){
               context.status(200);
               context.result("Employee was deleted.");
           }
           else{
               context.status(404);
               context.result("Did not find employee to delete: "+id);
           }
        });

        app.post("expenses/", context -> {
            Gson gson = new Gson();
            String body = context.body();
            Expense expense = gson.fromJson(body,Expense.class);
            if(expenseService.createExpense(expense)){
                context.status(201);
                context.result("Created a new expense for employee " + expense.getEmployeeId() + ".");
            }
            else{
                context.status(400);
                context.result("Did not create a new expense for employee "+ expense.getEmployeeId() +".");
            }
        });

        app.get("expenses/",context -> {
            Gson gson = new Gson();
            String json;
            List<Expense> expenses;
            String queryParam = context.queryParam("status");
            if (Arrays.stream(ExpenseStatus.values()).anyMatch(s -> s.name().equalsIgnoreCase(queryParam))) {
                expenses = expenseService.getAllExpenses(ExpenseStatus.valueOf(queryParam.toUpperCase()));
            } else {
                expenses = expenseService.getAllExpenses();
            }
            json = gson.toJson(expenses);
            context.result(json);
        });

        /*TODO
        GET /expenses/12
        returns a 404 if expense not found
        PUT /expenses/15
        returns a 404 if expense not found
        PATCH /expenses/20/approve
        returns a 404 if expense not found
        PATCH /expenses/20/deny
        returns a 404 if expense not found
        DELETE /expenses/19
        returns a 404 if car not found
         */

        app.start(1235);

    }

}