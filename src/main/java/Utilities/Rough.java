package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import static Pages.Attendance_page.*;
import static Pages.Attendance_page.attendancemessages;
import static Utilities.Actions.isElementDisplayed;
import static Utilities.Utils.propertyloader;

public class Rough {

    public static HashMap<String,String> propertyloader() throws FileNotFoundException {

        String configfilepath  =System.getProperty("user.dir") + "\\src\\test\\resources\\Properties\\Config.properties";
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;

        Map<String, String> propertiesMap = null;
        try {
            fileInputStream = new FileInputStream(configfilepath);
            properties.load(fileInputStream);

            String value  = "";

            // Convert properties to HashMap
            propertiesMap = new HashMap<>();
            for (String key : properties.stringPropertyNames()) {
                value = properties.getProperty(key);
                if(key.contains("path")){
                    value = System.getProperty("user.dir") +properties.getProperty(key);
                }
                propertiesMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return (HashMap<String, String>) propertiesMap;
    }
    public static void main(String[] args) throws FileNotFoundException {

        propertyloader();

    }


}
