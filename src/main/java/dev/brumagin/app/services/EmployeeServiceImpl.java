package dev.brumagin.app.services;

import dev.brumagin.app.data.EmployeeDAO;
import dev.brumagin.app.data.EmployeeDAOPostgresImpl;

public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeDAO employeeDAO;

    EmployeeServiceImpl(){
        employeeDAO = new EmployeeDAOPostgresImpl();
    }

}
