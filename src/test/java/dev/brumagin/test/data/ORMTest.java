package dev.brumagin.test.data;

import dev.brumagin.app.data.EmployeeD;
import dev.brumagin.app.entities.Employee;
import org.junit.jupiter.api.Test;

public class ORMTest {

    //EmployeeD employeeD = new EmployeeD();

    @Test
    void create_employee(){
        Employee employee = new Employee(0,"Bob","Dylan");
      //  employeeD.createEntity(employee);
    }

}
