package dev.brumagin.app.api;

import com.google.gson.Gson;
import dev.brumagin.app.entities.*;
import dev.brumagin.app.services.EmployeeService;
import dev.brumagin.app.services.EmployeeServiceImpl;
import dev.brumagin.app.services.ExpenseService;
import dev.brumagin.app.services.ExpenseServiceImpl;
import dev.brumagin.app.utilities.ConnectionUtility;
import io.javalin.Javalin;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

/**
 * An application that keeps track of employee expenses and reimbursements
 */
public class ReimbursementApp {

    static final EmployeeService employeeService = new EmployeeServiceImpl(); // API -> DAO
    static final ExpenseService expenseService = new ExpenseServiceImpl(); // API -> DAO
    static final Gson gson = new Gson(); //JSON formatter
    static Javalin app = Javalin.create(); //Create web application

    public static void main(String[] args) {

        databaseConnectionRoute();

        //-----------------------Employee Routes-------------------------//

        createEmployeeRoute(); // "/"

        getAllEmployeesRoute(); // "/employees"

        getEmployeeByIdRoute(); // "/employees"

        updateEmployeeByIdRoute(); // "/employees/{id}"

        deleteEmployeeByIdRoute(); // "/employees/{id}"

        //-----------------------Expense Routes-------------------------//

        createExpenseRoute(); // "/expenses"

        getAllExpensesRoute(); // "/expenses"

        getExpenseByIdRoute(); // "/expenses/{id}"

        updateExpenseByIdRoute(); // "/expenses/{id}"

        updateExpenseStatusRoute("approve"); // "/expenses/{id}/approve"

        updateExpenseStatusRoute("deny"); // "/expenses/{id}/deny"

        deleteExpenseByIdRoute(); // "/expenses/{id}"

        //-----------------------Compound Routes-------------------------//

        createExpenseByEmployeeIdRoute(); // "/employees/{id}/expenses"

        getExpensesByEmployeeIdRoute(); // "/employees/{id}/expenses"


        app.start(5000); //Start configured application
    }


    public static void databaseConnectionRoute() {
        app.get("/", context -> {
            Connection connection = ConnectionUtility.createConnection();
            if (connection != null) {
                context.status(200);
                context.result("Application connects to database.");
            } else {
                context.status(400);
                context.result("Application cannot connect to database.");
            }
        });
    }

    public static void createEmployeeRoute() {
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
    }

    public static void getAllEmployeesRoute() {
        app.get("/employees", context -> {
            String json = gson.toJson(employeeService.getAllEmployees());
            context.result(json);
        });
    }

    public static void getEmployeeByIdRoute(){
        app.get("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Employee employee = employeeService.getEmployeeById(id);

            if (employee != null) {
                String json = gson.toJson(employee);
                context.status(200);
                context.result(json);

            } else {
                context.status(404);
                context.result("Did not find employee: " + id);
            }

        });
    }

    public static void updateEmployeeByIdRoute(){
        app.put("/employees/{id}", context -> {
            String body = context.body();
            Employee employee = gson.fromJson(body, Employee.class);
            int id = Integer.parseInt(context.pathParam("id"));
            employee.setId(id);

            try {
                if (employeeService.updateEmployee(employee)) {
                    String json = gson.toJson(employeeService.getEmployeeById(id));
                    context.status(200);
                    context.result(json);
                } else {
                    context.status(404);
                    context.result("Did not find employee to update: " + id);
                }
            } catch (CannotEditException e) {
                context.status(409);
                context.result("Employee could not be changed because they have recorded expenses: " + id);
            }
        });
    }

    public static void deleteEmployeeByIdRoute(){
        app.delete("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try {
                if (employeeService.deleteEmployee(id)) {
                    context.status(200);
                    context.result("Employee was deleted: " + id);
                } else {
                    context.status(404);
                    context.result("Did not find employee to delete: " + id);
                }
            } catch (CannotEditException e) {
                context.status(409);
                context.result("Employee could not be deleted because they have recorded expenses: " + id);
            }
        });
    }

    public static void createExpenseRoute(){
        app.post("/expenses", context -> {
            String body = context.body();
            Expense expense = gson.fromJson(body, Expense.class);
            if (expense.getCost() < 0) {
                context.status(400);
                context.result("Expense was negative. Did not create a new expense: " + expense);
            } else if (expenseService.createExpense(expense)) {
                context.status(201);
                context.result("Created a new expense: " + expense);
            } else {
                context.status(400);
                context.result("Did not create a new expense: " + expense);
            }
        });
    }

    public static void getAllExpensesRoute(){
        app.get("/expenses", context -> {
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
    }

    public static void getExpenseByIdRoute(){
        app.get("/expenses/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Expense expense = expenseService.getExpenseById(id);

            if (expense != null) {
                context.status(200);
                context.result(gson.toJson(expense));
            } else {
                context.status(404);
                context.result("Did not find an expense: " + id + ".");
            }
        });
    }

    public static void updateExpenseByIdRoute(){
        app.put("/expenses/{id}", context -> {
            String body = context.body();
            int id = Integer.parseInt(context.pathParam("id"));
            Expense expense = gson.fromJson(body, Expense.class);
            expense.setExpenseId(id);
            try {
                if (expense.getCost() < 0) {
                    context.status(400);
                    context.result("Expense was negative. Did not create a new expense: " + expense);
                } else if (expenseService.updateExpense(expense, ExpenseStatus.PENDING)) {
                    context.status(200);
                    context.result("Updated expense: " + expense);
                } else {
                    context.status(404);
                    context.result("Did not find expense: " + id);
                }
            } catch (CannotEditException e) {
                context.status(409);
                context.result("Failed to update expense: \n" + expense.getExpenseId() + "\nStatus was not PENDING");
            }
        });
    }

    public static void updateExpenseStatusRoute(String pathParam){
        app.patch("/expenses/{id}/"+pathParam, context -> {
            String body = context.body();
            int id = Integer.parseInt(context.pathParam("id"));
            Expense expense = gson.fromJson(body, Expense.class);
            expense.setExpenseId(id);
            try {
                ExpenseStatus status = pathParam.equals("approve") ? ExpenseStatus.APPROVED : ExpenseStatus.DENIED;
                if (expenseService.updateExpense(expense, status)) {
                    context.status(200);
                    String result =  pathParam.equals("approve") ? "Approved" : "Denied";
                    context.result( result +" PENDING expense:" + expense);
                } else {
                    context.status(404);
                    context.result("Did not find the expense: " + expense);
                }
            } catch (CannotEditException e) {
                context.status(409);
                context.result("Failed to update expense: \n" + expense.getExpenseId() + "\nStatus was not PENDING");
            }
        });
    }

    public static void deleteExpenseByIdRoute(){
        app.delete("/expenses/{id}", context -> {
            String body = context.body();
            Expense expense = gson.fromJson(body, Expense.class);
            int id = Integer.parseInt(context.pathParam("id"));
            expense.setExpenseId(id);
            try {
                if (expenseService.deleteExpense(id)) {
                    context.status(200);
                    context.result("Expense deleted: " + expense);
                } else {
                    context.status(404);
                    context.result("Did not find expense: " + expense);
                }
            } catch (CannotEditException e) {
                context.status(409);
                context.result("Failed to remove expense: \n" + expense + "\nStatus was not PENDING");
            }
        });
    }

    public static void createExpenseByEmployeeIdRoute(){
        app.post("/employees/{id}/expenses", context -> {
            String body = context.body();
            int id = Integer.parseInt(context.pathParam("id"));
            Expense expense = gson.fromJson(body, Expense.class);
            expense.setEmployeeId(id);
            if (expense.getCost() < 0) {
                context.status(400);
                context.result("Expense was negative. Did not create a new expense: " + expense);
            } else if (expenseService.createExpense(expense)) {
                context.status(201);
                context.result("Created a new expense for employee: " + expense.getEmployeeId());
            } else {
                context.status(400);
                context.result("Did not create a new expense for employee: " + expense.getEmployeeId());
            }
        });
    }

    public static void getExpensesByEmployeeIdRoute(){
        app.get("/employees/{id}/expenses", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            List<Expense> expenses = expenseService.getAllExpenses(id);

            if (!expenses.isEmpty()) {
                context.status(200);
                context.result(gson.toJson(expenses));
            } else {
                context.status(404);
                context.result("Did not find expenses for employee: " + id);
            }
        });
    }
}
