package dev.brumagin.app.data;

import dev.brumagin.app.entities.Column;
import dev.brumagin.app.entities.Employee;
import dev.brumagin.app.entities.ForeignKey;
import dev.brumagin.app.entities.PrimaryKey;
import dev.brumagin.app.utilities.ConnectionUtility;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public interface CrudDAO <T> {


    default T createEntity(T entity) {
        try {
            System.out.println(entity.getClass());
            Connection connection = ConnectionUtility.createConnection();
            List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields()).collect(Collectors.toList());
            List<Field> fields2 = fields.stream().filter(n -> n.isAnnotationPresent(PrimaryKey.class)).collect(Collectors.toList());
            fields = fields.stream().filter(n -> n.isAnnotationPresent(Column.class) || n.isAnnotationPresent(ForeignKey.class)).collect(Collectors.toList());
            StringBuilder statement = new StringBuilder("insert into ");
            statement.append(entity.getClass().getName().substring(entity.getClass().getPackage().getName().length() + 1).toLowerCase());
            statement.append(" (");

            for (int i = 0; i < fields.size(); i++) {
                statement.append(fields.get(i).getAnnotation(Column.class).name());
                String append = i+1 <fields.size()? ", ":") values (";
                statement.append(append);
            }

            List<Method> methods = Arrays.stream(entity.getClass().getMethods()).collect(Collectors.toList());
            Optional<Method> method;

            for (int i = 0; i < fields.size(); i++) {
                String convert = Character.toUpperCase(fields.get(i).getName().charAt(0)) + fields.get(i).getName().substring(1);
                method = methods.stream().filter(n -> n.getName().equals("get" + convert)).findFirst();

                if (method.isPresent()) {
                    if (!method.get().getReturnType().isPrimitive()) {
                        statement.append("'");
                        statement.append(method.get().invoke(entity));
                        statement.append("'");
                    } else {
                        statement.append(method.get().invoke(entity));
                    }

                    if (i + 1 < fields.size())
                        statement.append(", ");
                    else {
                        statement.append(");");
                    }
                }
            }

            PreparedStatement ps = connection.prepareStatement(statement.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            String append = fields2.get(0).getAnnotation(PrimaryKey.class).name();
            Object generatedKey =  rs.getObject(String.valueOf(append));
            String converted = Character.toUpperCase(fields2.get(0).getName().charAt(0)) + fields2.get(0).getName().substring(1);
            Optional<Method> setPrimaryKey = methods.stream().filter(n -> n.getName().equals("set" + converted)).findFirst();

            if (setPrimaryKey.isPresent())
                setPrimaryKey.get().invoke(entity, generatedKey);

        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }
/*
    default T getEntityById(int id){

        T persistentClass = null;
        T parameterizedType = (T) persistentClass;
            //System.out.println("Return type: " + parameterizedType.getTypeName());
           // persistentClass = (T) parameterizedType.getActualTypeArguments()[0];
            System.out.println("Parameter type: " + ((Type) persistentClass).getTypeName());
        


        System.out.println(getClass().getDeclaringClass());
        System.out.println(persistentClass);
        try {

            Connection connection = ConnectionUtility.createConnection();
            String statement = "select * from employee where employee_id=?;";
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            T type = null;
            System.out.println(type.getClass().getName());
            List<Field> fields = Arrays.stream(type.getClass().getFields()).collect(Collectors.toList());
            for(Field f : fields){
                System.out.println(f);
            }
            List<Method> methods = Arrays.stream(type.getClass().getMethods()).filter(n->n.getName().contains("get")).collect(Collectors.toList());
            for(Method m : methods){
                System.out.println(m);
            }
           // employee.setEmployeeId(employeeId);
          //  type.setFirstName(rs.getString("first_name"));
           // type.setLastName(rs.getString("last_name"));
            return null;
        }
        catch (SQLException e){
          //  Logger.log("**The employee was not found; please check database access and parameters.**\nemployee id: " +employeeId, LogLevel.WARNING);
            return null;
        }

    };*/ // Get an entity by ID

  /*  default List<T> getAllEntities(){

    };// get all instances of the entity

    default T updateEntity(T entity){

    };// update an instance*/
}
