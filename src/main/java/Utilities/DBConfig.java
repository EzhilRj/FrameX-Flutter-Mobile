package Utilities;

import java.sql.*;
import java.util.*;

import static Base.Setup.log;
import static UiObjects.CallPlan_Objects.SetCategoryAttribute;
import static Utilities.Actions.*;
import static Utilities.Constants.*;


public class DBConfig {
    private static ResultSet result;

    public static List<Map<String, String>> Db(String Query) throws Exception {

        try (Connection con = DriverManager.getConnection(Databaseurl, Dbusername, Dbpassword);
             Statement statement = con.createStatement();
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
        Db(query).forEach(StringMap -> {
            String value = StringMap.get(columnname);
            stringMapValues.add(value);
        });

        return stringMapValues;
    }

    public static List<Object> GetDataObject(String query) throws Exception {

        List<Object> stringMapValues = new ArrayList<>();
        Db(query).forEach(StringMap -> {stringMapValues.add(StringMap);
        });
        return stringMapValues;
    }



    public static void main(String[] args) throws Exception {
        // Getting Category names
        List<String> categoryNames = GetDatas(Categorymasterquery, "Name");
        for (String category : categoryNames) {
            if (category.equals("100 JUICE 1 LTR")) {
                // Getting Form names
                List<String> formNames = GetDatas(FormFieldsquery, "Form Name");
                for (String form : formNames) {
                    if (form.equals("Stk_Availability")) {
                        // Getting Fieldnames
                        List<Object> fieldnames = GetDataObject("select FieldName,DataType,ControlType from FormFieldsDetail where [Form Name] = 'Stk_Availability'");
                        for (Object field : fieldnames) {
                            if (field instanceof LinkedHashMap<?, ?> fieldData) {
                                if(fieldData.get("FieldName").equals("Quantity")){
                                    if(fieldData.get("ControlType").equals("TextBox")){

                                        System.out.println("YES BRO");
                                    }

                                }
                            } else {
                                System.out.println("Invalid object type. Expected LinkedHashMap.");
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

}