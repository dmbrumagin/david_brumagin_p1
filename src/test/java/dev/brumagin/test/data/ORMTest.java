package dev.brumagin.test.data;

import dev.brumagin.app.data.EmployeeORM;
import dev.brumagin.app.entities.Employee;
import org.junit.jupiter.api.Test;

public class ORMTest {

    EmployeeORM employeeD = new EmployeeORM();

    @Test
    void create_employee(){
        Employee employee = new Employee(0,"Bob","Dylan");
        employeeD.createEntity(employee);
    }

}
