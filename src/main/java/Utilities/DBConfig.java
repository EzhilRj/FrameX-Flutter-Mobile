package Utilities;

import java.sql.*;
import java.util.*;

import static Base.Setup.*;
import static Utilities.Constants.*;


public class DBConfig {

    public static List<Map<String, String>> executeQuery(String query) throws Exception {
        try (
                Connection connection = DriverManager.getConnection(LocalDatabaseurl, LocalDbusername, LocalDbpassword);
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query)
        ) {
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<Map<String, String>> dataList = new ArrayList<>();

            while (result.next()) {
                String category = result.getString("Category");
                Map<String, String> rowData = new LinkedHashMap<>();

                if (shouldIncludeData(category)) {
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        String columnValue = result.getString(i);
                        rowData.put(columnName, columnValue);
                    }
                    dataList.add(rowData);
                }
            }

            return dataList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.debug(e.getMessage());
        }

        return null;
    }


    //GetDatas
    public static List<String> getColumnValues(String query, String columnname) throws Exception {

        List<String> columnValues = new ArrayList<>();
        List<Map<String, String>> queryResult = executeQuery(query);
        for (Map<String, String> row : queryResult) {
            String value = row.get(columnname);
            columnValues.add(value);
        }

        return columnValues;
    }


    //GetDataObject
    public static List<Object> getDataObject(String query) throws Exception {

        List<Object> dataObjects = new ArrayList<>();
        List<Map<String, String>> queryResult = executeQuery(query);

        for (Map<String, String> row : queryResult) {
            dataObjects.add(row);
        }
        return dataObjects;
    }


    public static List<Map<String, String>> testDatas(String query) throws Exception {
        return executeQuery(query);
    }




    private static boolean shouldIncludeData(String category) {
        if (testSuiteName.contains("Regression") && category.contains("Regression")) {
            return true;
        }
        if (testSuiteName.contains("Smoke") && category.contains("Smoke")) {
            return true;
        }
        if (testSuiteName.contains("Negative") && category.contains("Negative")) {
            return true;
        }
        return false;
    }
}




