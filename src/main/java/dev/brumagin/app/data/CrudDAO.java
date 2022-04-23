package dev.brumagin.app.data;

import dev.brumagin.app.entities.Column;
import dev.brumagin.app.utilities.ConnectionUtility;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public interface CrudDAO <T> {
    default T createEntity(T entity) {
      /*  //try {
        Connection connection = ConnectionUtility.createConnection();
        Field[] fields = entity.getClass().getDeclaredFields();
        List<String> editedSQLColumns= new ArrayList<>();
        String conversionString ="";
        for (Field f : fields){
            char[] convert = f.getName().toCharArray();
            for(char c : convert){
                if(Character.isUpperCase(c)){
                    conversionString+= "_"+Character.toLowerCase(c);
                }
                else {
                    conversionString +=c;
                }
            }
            editedSQLColumns.add(conversionString);
            conversionString="";

        }

        //System.out.println(entity.getClass().getName().substring(26).toLowerCase());
        String statement = "insert into ";
        statement+= entity.getClass().getName().substring(26).toLowerCase() +" (";
        for (int i = 0; i < fields.length; i++) {
           // Optional<Annotation> optionalAnnotation = Arrays.stream(fields[i].getAnnotations()).findFirst();
            //System.out.println(optionalAnnotation.get().annotationType().getName().substring(26));
            if(i+1< fields.length)
            statement += editedSQLColumns.get(i)+", "; //(fields[i].getName()+",");
            else {
                statement += editedSQLColumns.get(i)+") ";//(fields[i].getName()+") ");
            }
        }
        statement+= "values (";
        System.out.println(statement);*/

        return entity;
    }
}
            /*String statement = "insert into employee (first_name,last_name) values (?,?);";

            PreparedStatement ps = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedKey = rs.getInt("employee_id");
            employee.setId(generatedKey);

            return employee;*/
       // }
        //catch (SQLException e){
           // Logger.log("**The employee was not created; please check database access and parameters.**\n"+employee,LogLevel.WARNING);
        //    return null;
       // columns.forEach(System.out::println);

        /*
            String statement = "update employee set first_name = ?, last_name = ? where employee_id = ?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setInt(3, employee.getId());
            return ps.executeUpdate() != 0;
        }
        catch (SQLException e){
            Logger.log("**The employee was not found; please check database access and parameters.**\n" + employee, LogLevel.WARNING);
            return false;
        }*/
   // } // creates an entity
    /*default T getEntityById(int id){

    }; // Get an entity by ID

    default List<T> getAllEntities(){

    };// get all instances of the entity

    default T updateEntity(T entity){

    };// update an instance*/

