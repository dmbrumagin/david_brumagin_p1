package dev.brumagin.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @PrimaryKey
    private int employeeId;
    @Column
    private String firstName;
    @Column
    private String lastName;

}
