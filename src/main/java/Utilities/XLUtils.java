package Utilities;

import com.google.gson.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class XLUtils {

    public static FileInputStream fi;
    public static XSSFWorkbook workbook;
    private static File file;
    public static XSSFSheet sheet;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static JsonObject MyObject;

    public static int lastRowNum;

    public static void LoadExcel() throws IOException {
        System.out.println("Loading Excel data...");

        file = new File(Constants.Excelpath);
        fi = new FileInputStream(file);
        workbook = new XSSFWorkbook(fi);
        fi.close();
    }

    public static Map<String, List<Map<String, String>>> getSheetDataMap() throws Exception {
        if (workbook == null) {
            LoadExcel();
        }

        Map<String, List<Map<String, String>>> sheetDataMap = new LinkedHashMap<>();
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();

            List<Map<String, String>> dataList = new ArrayList<>();
            int lastRowNum = sheet.getLastRowNum();
            int lastColNum = sheet.getRow(0).getLastCellNum();
            List<String> keys = new ArrayList<>();

            // Get the keys from the first row
            row = sheet.getRow(0);
            for (int j = 0; j < lastColNum; j++) {
                String key = row.getCell(j).getStringCellValue();
                keys.add(key);
            }

            // Process the data rows
            for (int k = 1; k <= lastRowNum; k++) {
                row = sheet.getRow(k);
                Map<String, String> rowData = new LinkedHashMap<>();

                // Iterate over each column and map it with the corresponding key
                for (int j = 0; j < lastColNum; j++) {
                    String value = row.getCell(j).getStringCellValue();
                    rowData.put(keys.get(j), value);
                }

                dataList.add(rowData);
            }

            sheetDataMap.put(sheetName, dataList);
        }

        return sheetDataMap;
    }

    public static String getJsonData() throws Exception {
        Map<String, List<Map<String, String>>> sheetDataMap = getSheetDataMap();

        // Create a Gson instance with pretty printing enabled
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Convert data to JSON format
        JsonObject mainJsonObject = new JsonObject();
        for (Map.Entry<String, List<Map<String, String>>> entry : sheetDataMap.entrySet()) {
            String sheetName = entry.getKey();
            List<Map<String, String>> dataMap = entry.getValue();

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(gson.toJson(dataMap)).getAsJsonArray();

            mainJsonObject.add(sheetName, jsonArray);
        }

        // Convert the mainJsonObject to a pretty formatted string
        return gson.toJson(mainJsonObject);
    }

}



