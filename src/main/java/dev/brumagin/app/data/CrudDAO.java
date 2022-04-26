package dev.brumagin.app.data;

import dev.brumagin.app.entities.Column;
import dev.brumagin.app.entities.ForeignKey;
import dev.brumagin.app.entities.PrimaryKey;
import dev.brumagin.app.utilities.ConnectionUtility;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public interface CrudDAO <T> {

    default T createEntity(T entity) {

        try {
            Connection connection = ConnectionUtility.createConnection();
            List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields()).collect(Collectors.toList());
            List<Field> fields2 = fields.stream().filter(n -> n.isAnnotationPresent(PrimaryKey.class)).collect(Collectors.toList());
            fields = fields.stream().filter(n -> n.isAnnotationPresent(Column.class) || n.isAnnotationPresent(ForeignKey.class)).collect(Collectors.toList());
            StringBuilder statement = new StringBuilder("insert into ");
            statement.append(entity.getClass().getName().substring(entity.getClass().getPackage().getName().length() + 1).toLowerCase());
            statement.append(" (");
            StringBuilder conversionString;

            for (int i = 0; i < fields.size(); i++) {

                conversionString = new StringBuilder();
                char[] convert = fields.get(i).getName().toCharArray();

                for (char c : convert) {
                    if (Character.isUpperCase(c)) {
                        conversionString.append("_");
                        conversionString.append(Character.toLowerCase(c));
                    } else {
                        conversionString.append(c);
                    }
                }

                if (i + 1 < fields.size()) {
                    statement.append(conversionString);
                    statement.append(", ");
                } else {
                    statement.append(conversionString);
                    statement.append(") values (");
                }
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
            conversionString = new StringBuilder();

            char[] convert = fields2.get(0).getName().toCharArray();
            for (char c : convert) {
                if (Character.isUpperCase(c)) {
                    conversionString.append("_");
                    conversionString.append(Character.toLowerCase(c));
                } else {
                    conversionString.append(c);
                }
            }

            Object generatedKey;
            generatedKey =  rs.getObject(String.valueOf(conversionString));
            String converted = Character.toUpperCase(fields2.get(0).getName().charAt(0)) + fields2.get(0).getName().substring(1);
            Optional<Method> setPrimaryKey = methods.stream().filter(n -> n.getName().equals("set" + converted)).findFirst();

            if (setPrimaryKey.isPresent())
                setPrimaryKey.get().invoke(entity, generatedKey);

        } catch (SQLException | InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }
}
