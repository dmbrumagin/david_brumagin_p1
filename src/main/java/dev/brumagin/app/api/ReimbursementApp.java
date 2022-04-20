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
import java.lang.*;
import java.util.Arrays;
import java.util.List;

public class ReimbursementApp {

    static EmployeeService employeeService = new EmployeeServiceImpl();
    static ExpenseService expenseService = new ExpenseServiceImpl();
    static Gson gson = new Gson();

    public static void main (String args[]){

        Javalin app = Javalin.create();

        app.post("/employees", context -> {
            String body = context.body();
            Employee employee = gson.fromJson(body, Employee.class);

            if (employeeService.createEmployee(employee)) {
                context.status(201);
                context.result("Created a new employee: " + employee);
            } else {
                context.status(400);
                context.result("Did not create a new employee: " + employee);
            }
        });

        app.get("/employees", context -> {
            String json = gson.toJson(employeeService.getAllEmployees());
            context.result(json);
        });

        app.get("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Employee employee = employeeService.getEmployeeById(id);

            if (employee == null) {
                context.status(404);
                context.result("Did not find employee: " + id);
            } else {
                String json = gson.toJson(employee);
                context.result(json);
            }
        });

        app.put("/employees/{id}",context -> {
            String body = context.body();
            Employee employee = gson.fromJson(body, Employee.class);
            int id = Integer.parseInt(context.pathParam("id"));

            if (employeeService.updateEmployee(employee)) {
                String json = gson.toJson(employeeService.getEmployeeById(id));
                context.result(json);
            } else {
                context.status(404);
                context.result("Did not find employee to update: " + id);
            }
        });

        app.delete("/employees/{id}",context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            if (employeeService.deleteEmployee(id)) {
                context.status(200);
                context.result("Employee was deleted: " + id);
            } else {
                context.status(404);
                context.result("Did not find employee to delete: " + id);
            }
        });

        app.post("/expenses/", context -> {
            String body = context.body();
            Expense expense = gson.fromJson(body, Expense.class);

            if (expenseService.createExpense(expense)) {
                context.status(201);
                context.result("Created a new expense for employee: " + expense.getEmployeeId() + ".");
            } else {
                context.status(400);
                context.result("Did not create a new expense for employee: " + expense.getEmployeeId() + ".");
            }
        });

        app.get("/expenses",context -> {
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

        app.get("/expenses/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            List<Expense> expenses = expenseService.getAllExpenses(id);

            if (expenses.size() != 0) {
                context.status(200);
                context.result(gson.toJson(expenses));
            } else {
                context.status(404);
                context.result("Did not find an expense for employee: " + id + ".");
            }
        });

        app.put("/expenses/{id}",context -> {
            String body = context.body();
            int id = Integer.parseInt(context.pathParam("id"));
            Expense expense = gson.fromJson(body, Expense.class);
            expense.setEmployeeId(id);

            if (expenseService.updateExpense(expense, ExpenseStatus.PENDING)) {
                context.status(200);
                context.result("Updated expense for " + id + ".\n" + expense);
            } else {
                context.status(404);
                context.result("Did not find an expense: " + expense + " for employee: " + id + ".");
            }
        });

        app.patch("/expenses/{id}/approve",context -> {
            String body = context.body();
            int id = Integer.parseInt(context.pathParam("id"));
            Expense expense = gson.fromJson(body, Expense.class);
            expense.setEmployeeId(id);

            if (expenseService.updateExpense(expense, ExpenseStatus.APPROVED)) {
                context.status(200);
                context.result("Approved PENDING expense:" + expense + " for employee: " + id);
            } else {
                context.status(404);
                context.result("Did not find the PENDING expense for employee: " + id + ".+\n expense: " + expense);
            }
        });

        app.patch("/expenses/{id}/deny",context -> {
            String body = context.body();
            int id = Integer.parseInt(context.pathParam("id"));
            Expense expense = gson.fromJson(body, Expense.class);
            expense.setEmployeeId(id);

            if (expenseService.updateExpense(expense, ExpenseStatus.DENIED)) {
                context.status(200);
                context.result("Denied PENDING expense:" + expense + " for employee: " + id);
            } else {
                context.status(404);
                context.result("Did not find the PENDING expense for employee: " + id + ".+\n expense: " + expense);
            }
        });

        app.delete("/expenses/{id}",context -> {
            String body = context.body();
            Expense expense = gson.fromJson(body, Expense.class);
            int id = Integer.parseInt(context.pathParam("id"));
            expense.setEmployeeId(id);

            if (expenseService.deleteExpense(expense)) {
                context.status(200);
                context.result("Expense deleted." + expense);
            } else {
                context.status(404);
                context.result("Did not find PENDING expense for employee: " + id + " to delete.\n" + expense);

            }
        });

        app.start(5000);
    }
}