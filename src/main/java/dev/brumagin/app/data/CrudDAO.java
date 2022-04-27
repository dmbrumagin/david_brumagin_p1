package dev.brumagin.app.data;

import dev.brumagin.app.entities.*;
import dev.brumagin.app.utilities.ConnectionUtility;
import dev.brumagin.app.utilities.LogLevel;
import dev.brumagin.app.utilities.Logger;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    default T getEntityById(int id){
        try {
            EntityMapper mapEntity = new EntityMapper();
            Class type = mapEntity.entities.get(Arrays.stream(getClass().getTypeParameters()).findFirst().get().getName());
            List<Method> methods = Arrays.stream(type.getMethods()).filter(n -> n.getName().contains("set")).collect(Collectors.toList());
            Connection connection = ConnectionUtility.createConnection();
            StringBuilder statement = new StringBuilder("select * from ");
            statement.append(Arrays.stream(getClass().getTypeParameters()).findFirst().get().getName().toLowerCase());
            statement.append(" where ");
            statement.append(mapEntity.primaryKeys.get(Arrays.stream(getClass().getTypeParameters()).findFirst().get().getName()));
            statement.append(" = ?;");
            System.out.println(statement);
            PreparedStatement ps = connection.prepareStatement(statement.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            T entity = (T) type.newInstance();


            for (int i = 0; i < methods.size(); i++) {
                StringBuilder methodName = new StringBuilder(methods.get(i).getName().substring(3));
                methodName.replace(0, 1, String.valueOf(Character.toLowerCase(methodName.charAt(0))));
                for (int j = 0; j < methodName.length(); j++) {
                    if (Character.isUpperCase(methodName.charAt(j))) {
                        methodName.replace(j, j + 1, String.valueOf(Character.toLowerCase(methodName.charAt(j))));
                        methodName.insert(j, "_");

                        break;
                    }
                }
                if (Stream.of(ExpenseStatus.values()).anyMatch(n -> {
                    try {
                        return n.name().equals(rs.getObject(String.valueOf(methodName)));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return false;
                })) {
                    methods.get(i).invoke(entity, ExpenseStatus.valueOf(String.valueOf(rs.getObject(String.valueOf(methodName)))));
                } else {
                    methods.get(i).invoke(entity, rs.getObject(String.valueOf(methodName)));
                }
            }
            return entity;
        }
        catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            //  Logger.log("**The employee was not found; please check database access and parameters.**\nemployee id: " +employeeId, LogLevel.WARNING);
            return null;
        }
    }

   default List<T> getAllEntities(){
       try {
           EntityMapper mapEntity = new EntityMapper();
           Class type = mapEntity.entities.get(Arrays.stream(getClass().getTypeParameters()).findFirst().get().getName());
           List<Method> methods = Arrays.stream(type.getMethods()).filter(n -> n.getName().contains("set")).collect(Collectors.toList());
           Connection connection = ConnectionUtility.createConnection();
           StringBuilder statement = new StringBuilder("select * from ");
           statement.append(Arrays.stream(getClass().getTypeParameters()).findFirst().get().getName().toLowerCase());
           statement.append(";");
           PreparedStatement ps = connection.prepareStatement(statement.toString());
           ResultSet rs = ps.executeQuery();
           List<T> entities = new ArrayList<>();
           while (rs.next()) {
               T entity = (T) type.newInstance();


               for (int i = 0; i < methods.size(); i++) {
                   StringBuilder methodName = new StringBuilder(methods.get(i).getName().substring(3));
                   methodName.replace(0, 1, String.valueOf(Character.toLowerCase(methodName.charAt(0))));
                   for (int j = 0; j < methodName.length(); j++) {
                       if (Character.isUpperCase(methodName.charAt(j))) {
                           methodName.replace(j, j + 1, String.valueOf(Character.toLowerCase(methodName.charAt(j))));
                           methodName.insert(j, "_");

                           break;
                       }
                   }
                   if (Stream.of(ExpenseStatus.values()).anyMatch(n -> {
                       try {
                           return n.name().equals(rs.getObject(String.valueOf(methodName)));
                       } catch (SQLException e) {
                           e.printStackTrace();
                       }
                       return false;
                   })) {
                       methods.get(i).invoke(entity, ExpenseStatus.valueOf(String.valueOf(rs.getObject(String.valueOf(methodName)))));
                   } else {
                       methods.get(i).invoke(entity, rs.getObject(String.valueOf(methodName)));
                   }
               }
               entities.add(entity);
           }
           entities.forEach(System.out::println);
           return entities;
       }
       catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
           //  Logger.log("**The employee was not found; please check database access and parameters.**\nemployee id: " +employeeId, LogLevel.WARNING);
           return null;
       }

    }

    default T updateEntity(T entity){
        try {
            List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields()).filter(n->n.isAnnotationPresent(Column.class)).collect(Collectors.toList());
            List<Field> primary =  Arrays.stream(entity.getClass().getDeclaredFields()).filter(n->n.isAnnotationPresent(PrimaryKey.class)).collect(Collectors.toList());
            Connection connection = ConnectionUtility.createConnection();
            StringBuilder statement = new StringBuilder("update ");
            statement.append(entity.getClass().getName().substring(entity.getClass().getPackage().getName().length() + 1).toLowerCase());
            statement.append(" set ");
            List<Method> methods = Arrays.asList(entity.getClass().getMethods());
            for(Field f : fields){
                System.out.println(f.getAnnotation(Column.class).name());
            }
            Method method;
            for (int i = 0; i < fields.size(); i++) {
                statement.append(fields.get(i).getAnnotation(Column.class).name());
                statement.append(" = ");
                int finalI = i;

                method = methods.stream().filter(n->n.getName().contains("get")).filter(n->n.getName().contains(fields.get(finalI).getName().substring(1))).collect(Collectors.toList()).get(0);
                if (!method.getReturnType().isPrimitive()) {
                    statement.append("'");
                    statement.append(method.invoke(entity));
                    statement.append("'");
                } else {
                    statement.append(method.invoke(entity));
                }

                String append = i+1 <fields.size()? ", ":" where ";
                statement.append(append);
            }
            statement.append(primary.get(0).getAnnotation(PrimaryKey.class).name());
            statement.append(" = ");
            method = methods.stream().filter(n->n.getName().contains("get")).filter(n->n.getName().contains(primary.get(0).getName().substring(1))).collect(Collectors.toList()).get(0);
            if (!method.getReturnType().isPrimitive()) {
                statement.append("'");
                statement.append(method.invoke(entity));
                statement.append("'");
            } else {
                statement.append(method.invoke(entity));
            }
            statement.append(";");
            PreparedStatement ps = connection.prepareStatement(String.valueOf(statement));
            ps.executeUpdate();
            return entity;
        }
        catch (InvocationTargetException|IllegalAccessException|SQLException e){
         //   Logger.log("**The employee was not found; please check database access and parameters.**\n" + employee, LogLevel.WARNING);
            return null;
        }
    }
}
