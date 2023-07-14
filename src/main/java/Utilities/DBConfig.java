package Utilities;

import java.sql.*;
import java.util.*;

import static Base.Setup.log;
import static Base.Setup.nameofCurrMethod;
import static Utilities.Constants.*;


public class DBConfig {
    private static ResultSet result;

    public static List<Map<String, String>> Db(String Query) throws Exception {
        Connection con;

        if (nameofCurrMethod.equals("DataBinder")) {
            con = DriverManager.getConnection(LiveDatabaseurl, LiveDbusername, LiveDbpassword);
        } else {
            con = DriverManager.getConnection(LocalDatabaseurl, LocalDbusername, LocalDbpassword);
        }

        try (
                Connection connection = con;
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(Query)) {

            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Map<String, String>> dataList = new ArrayList<>();

            while (result.next()) {
                Map<String, String> rowData = new LinkedHashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = result.getString(i);
                    rowData.put(columnName, columnValue);
                }

                dataList.add(rowData);
            }
            return dataList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.debug(e.getMessage());
        }
        return null;
    }


    public static List<String> GetDatas(String query, String columnname) throws Exception {

        List<String> stringMapValues = new ArrayList<>();
        Db(query).forEach(StringMap -> {String value = StringMap.get(columnname);stringMapValues.add(value);
        });

        return stringMapValues;
    }

    public static List<Object> GetDataObject(String query) throws Exception {

        List<Object> stringMapValues = new ArrayList<>();
        Db(query).forEach(StringMap -> {stringMapValues.add(StringMap);
        });
        return stringMapValues;
    }


    public static List<Map<String, String>> Logindata(String Query) throws Exception {
        Connection con;

            con = DriverManager.getConnection(LocalDatabaseurl, LocalDbusername, LocalDbpassword);

        try (
                Connection connection = con;
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(Query)) {

            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Map<String, String>> dataList = new ArrayList<>();

            while (result.next()) {
                Map<String, String> rowData = new LinkedHashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = result.getString(i);
                    rowData.put(columnName, columnValue);
                }

                dataList.add(rowData);
            }
            return dataList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.debug(e.getMessage());
        }
        return null;
    }

}




