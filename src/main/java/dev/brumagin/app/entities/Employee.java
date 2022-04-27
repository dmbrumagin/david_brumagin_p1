package dev.brumagin.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {


    @PrimaryKey(name ="employee_id")
    private int employeeId;
    @Column(name ="first_name")
    private String firstName;
    @Column(name ="last_name")
    private String lastName;

}
