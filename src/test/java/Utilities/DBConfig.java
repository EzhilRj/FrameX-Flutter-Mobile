package Utilities;

import Base.TestSetup;

import java.sql.*;
import java.util.*;

import static Utilities.Constants.*;


public class DBConfig extends TestSetup {

    public static List<Map<String, String>> executeQuery(String query) throws Exception {
        try (
                Connection con = DriverManager.getConnection(gettestserverurl(), LiveDbusername, LiveDbpassword);
                Statement statement = con.createStatement();
                ResultSet result = statement.executeQuery(query)) {

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
        }
        return null;
    }


    public static List<String> getColumnNamesFromDatabase(String query, String columnName) throws Exception {
        List<String> stringMapValues = new ArrayList<>();
        List<Map<String, String>> result = executeQuery(query);
        for (Map<String, String> stringMap : result) {
            String value = stringMap.get(columnName);
            stringMapValues.add(value);
        }

        return stringMapValues;
    }




    public static List<Object> getDataObject(String query) throws Exception {
        List<Object> stringMapValues = new ArrayList<>();
        List<Map<String, String>> result = executeQuery(query);
        stringMapValues.addAll(result);

        return stringMapValues;
    }

    public static String getdatafromdatabase(String query, String columnName) throws Exception {

        List<Map<String, String>> result = executeQuery(query);
        if (!result.isEmpty()) {
            Map<String, String> firstRow = result.get(0); // Assuming there's only one row
            return firstRow.get(columnName);
        }
        return null;
    }


}




