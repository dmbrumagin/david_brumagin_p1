package dev.brumagin.app.data;

import dev.brumagin.app.entities.Column;
import dev.brumagin.app.entities.PrimaryKey;
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
      //try {
        Connection connection = ConnectionUtility.createConnection();
        List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields()).collect(Collectors.toList());
        System.out.println(Arrays.stream(fields.get(0).getAnnotations()).findFirst().get().annotationType().getName());
        System.out.println(PrimaryKey.class.getName());
        for(Field f : fields){
            System.out.println(f);
        }
        fields = fields.stream().filter(n -> Arrays.stream(n.getAnnotations()).findFirst().get().annotationType().getName().equals(PrimaryKey.class.getName())).collect(Collectors.toList());
        for(Field f : fields){
            System.out.println(f);
        }
        List<String> editedSQLColumns= new ArrayList<>();
        StringBuilder conversionString;
        for (Field f : fields){
            conversionString = new StringBuilder();
            char[] convert = f.getName().toCharArray();
            for(char c : convert){
                if(Character.isUpperCase(c)){
                    conversionString.append("_");
                    conversionString.append(Character.toLowerCase(c));
                }
                else {
                    conversionString.append(c);
                }
            }
            editedSQLColumns.add(String.valueOf(conversionString));
        }

        //System.out.println(entity.getClass().getName().substring(26).toLowerCase());
        StringBuilder statement = new StringBuilder("insert into ");
        statement.append(entity.getClass().getName().substring(entity.getClass().getPackage().getName().length()+1).toLowerCase());
        statement.append(" (");
        for (int i = 0; i < fields.size(); i++) {
           // Optional<Annotation> optionalAnnotation = Arrays.stream(fields[i].getAnnotations()).findFirst();
            //System.out.println(optionalAnnotation.get().annotationType().getName().substring(26));
            if(i+1< fields.size()){
            statement.append(editedSQLColumns.get(i)); //(fields[i].getName()+",");
            statement.append(", ");}
            else {
                statement.append( editedSQLColumns.get(i));//(fields[i].getName()+") ");
                statement.append(") ");
            }
        }
        statement.append("values (");
        System.out.println(statement);

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

