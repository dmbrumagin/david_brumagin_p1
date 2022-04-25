package dev.brumagin.app.data;

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
          List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields())
                  .collect(Collectors.toList());
          List<Field> fields2 = fields.stream()
                  .filter(n -> Arrays.stream(n.getAnnotations()).findFirst().get().annotationType().getName().equals(PrimaryKey.class.getName()))
                  .collect(Collectors.toList());
          fields = fields.stream()
                  .filter(n -> !Arrays.stream(n.getAnnotations()).findFirst().get().annotationType().getName().equals(PrimaryKey.class.getName()))
                  .collect(Collectors.toList());
          List<String> editedSQLColumns = new ArrayList<>();
          StringBuilder conversionString;

          for (Field f : fields) {
              conversionString = new StringBuilder();
              char[] convert = f.getName().toCharArray();
              for (char c : convert) {
                  if (Character.isUpperCase(c)) {
                      conversionString.append("_");
                      conversionString.append(Character.toLowerCase(c));
                  } else {
                      conversionString.append(c);
                  }
              }
              editedSQLColumns.add(String.valueOf(conversionString));
          }

          StringBuilder statement = new StringBuilder("insert into ");
          statement.append(entity.getClass().getName().substring(entity.getClass().getPackage().getName().length() + 1).toLowerCase());
          statement.append(" (");
          for (int i = 0; i < fields.size(); i++) {
              if (i + 1 < fields.size()) {
                  statement.append(editedSQLColumns.get(i));
                  statement.append(", ");
              } else {
                  statement.append(editedSQLColumns.get(i));
                  statement.append(") ");
              }
          }
          statement.append("values (");
          List<Method> methods = Arrays.stream(entity.getClass().getMethods()).collect(Collectors.toList());
          List<Method> method;
          List<Method> methods2 = new ArrayList<>();

          for (int i = 0; i < fields.size(); i++) {
              String convert = Character.toUpperCase(fields.get(i).getName().charAt(0)) + fields.get(i).getName().substring(1);
              method = methods.stream().filter(n -> n.getName().equals("get" + convert)).collect(Collectors.toList());
              methods2.add(method.get(0));
              if (i + 1 < fields.size())
                  statement.append(" ?,");
              else {
                  statement.append(" ?);");
              }
          }

          PreparedStatement ps = connection.prepareStatement(statement.toString(), Statement.RETURN_GENERATED_KEYS);
          for (int i = 0; i < fields.size(); i++) {
              Type type = methods2.get(i).getReturnType();
              System.out.println(type.getTypeName());
              if (type.getTypeName().equals("java.lang.String"))
                  ps.setString(i + 1, (String) methods2.get(i).invoke(entity));
              else {
                  System.out.println("other type");
              }
          }
          ps.execute();
          ResultSet rs = ps.getGeneratedKeys();
          rs.next();
          conversionString = new StringBuilder();
          char[] convert2 = fields2.get(0).getName().toCharArray();
          for (char c : convert2) {
              if (Character.isUpperCase(c)) {
                  conversionString.append("_");
                  conversionString.append(Character.toLowerCase(c));
              } else {
                  conversionString.append(c);
              }
          }
          int generatedKey = rs.getInt(String.valueOf(conversionString));
      }

      catch (SQLException e){
          System.out.println("Thrown SQL");
      } catch (InvocationTargetException e) {
          e.printStackTrace();
      } catch (IllegalAccessException e) {
          e.printStackTrace();
      }
        return entity;
    }
}
