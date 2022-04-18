package dev.brumagin.app.services;

import dev.brumagin.app.data.ExpenseDAO;
import dev.brumagin.app.data.ExpenseDAOPostgresImpl;

public class ExpenseServiceImpl implements ExpenseService{

    private ExpenseDAO expenseDAO;

    ExpenseServiceImpl(){
        expenseDAO = new ExpenseDAOPostgresImpl();
    }

}
