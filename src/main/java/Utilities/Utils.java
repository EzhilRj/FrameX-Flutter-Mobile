package Utilities;


import java.io.*;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import Pages.Login_Page;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import static Base.AppiumTestSetup.driver;
import static Base.AppiumTestSetup.excel;
import static Modules.Callplan_Module.fieldName;
import static Pages.CallPlan_page.*;
import static Utilities.Actions.*;
import static Utilities.Constants.queryfilepath;
import static Utilities.DBConfig.getColumnNamesFromDatabase;
import static Utilities.ValidationManager.Source;

public class Utils {

    public static String screenshotPath;
    public static String screenshotName;

    public static String Datasetter(String type, String facingtype) {

        if (type.equals("Int")) {
            if (facingtype.equalsIgnoreCase("Industry Facing *")) {
                return Randomint().get(1).toString();
            } else if (facingtype.equalsIgnoreCase("Our Brand Facing *")) {
                return Randomint().get(0).toString();
            }
            return Randomint().get(0).toString();
        } else if (type.contains("Varchar")||type.contains("string")) {
            return "Testdata";
        }
        return null;
    }

    public static List<Integer> Randomint() {

        Random random = new Random();
        List<Integer> integerList = new ArrayList<>();
        // Generate a random integer between 0 and 9
        int randomInt = random.nextInt(10);
        integerList.add(randomInt);
        // Generate a random integer between 10 and 20
        int randomIntInRange = random.nextInt(11) + 10;
        integerList.add(randomIntInRange);

        return integerList;
    }

    public static void Dropdownsetter(String formName, String productName) throws Exception {

        List<String> dropList = getColumnNamesFromDatabase(MessageFormat.format(queries.get("EnumFieldquery"),formName ,"'" + productName + "'","'" + formName + "'"), "FieldOption");
        Collections.shuffle(dropList);
        int size = dropList.size();
        Random random = new Random();
        int count = 0;
        for (String drop : dropList) {
            if (count >= size) {
                break;
            }
            /*String attribute = SetTextFieldAttribute(fieldName);*/
            if (Source(fieldName)) {
                click("ACCESSIBILITYID", fieldName);
                click("ACCESSIBILITYID", drop);
                break;
            } else {
                Scroll("up");
                click("ACCESSIBILITYID", fieldName);
                click("ACCESSIBILITYID", drop);
                break;
            }

        }

    }

    public static void ImageCapture() throws InterruptedException {

        /*String attribute = SetTextFieldAttribute(fieldName);
        if (isElementDisplayed("xpath", attribute)) {*/
        if(fieldName.contains("Photo *")){
            click("Xpath", Camerabutton_M);
        }else{
            click("Xpath", Camerabutton_NM);
        }
        WebdriverWait("Xpath", Shutterbutton, 4);
        click("Xpath", Shutterbutton);
        WebdriverWait("ACCESSIBILITYID", "Done", 3);
        click("ACCESSIBILITYID", "Done");

    }


    public static String getDeviceName() {
        try {
            Process process = Runtime.getRuntime().exec("adb devices");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith("device")) {
                    String[] parts = line.split("\\t");
                    if (parts.length > 1) {
                        return parts[0].trim();
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void Scrollto(String text) throws InterruptedException {
        String scrollCommand = "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));";
        driver.findElement(AppiumBy.androidUIAutomator(scrollCommand));
    }

    public static void captureScreenshot() throws IOException {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Date d = new Date();
        screenshotName ="Screenshot_"+ d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
        FileUtils.copyFile(scrFile, new File(props.get("Screenshotpath") + screenshotName));
    }

    /**
     * Data provider method for test cases, retrieving test data from an Excel sheet.
     *
     * @param m A reflection method object representing the test method.
     * @return Two-dimensional array of test data in the form of Hashtable objects.
     */
    @DataProvider(name = "Testdatas")
    public Object[][] getData(Method m) {
        // Get the name of the Excel sheet based on the test method name
        String sheetName = m.getName();

        // Get the number of rows and columns in the Excel sheet
        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        // Create a two-dimensional array to hold the test data
        Object[][] data = new Object[rows - 1][1];

        // Create a Hashtable to store test data for each row
        Hashtable<String, String> table = null;

        // Loop through each row in the Excel sheet
        for (int rowNum = 2; rowNum <= rows; rowNum++) {
            table = new Hashtable<String, String>();

            // Loop through each column in the Excel sheet and populate the Hashtable
            for (int colNum = 0; colNum < cols; colNum++) {
                table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
                data[rowNum - 2][0] = table;
            }
        }

        return data;
    }

    /**
     * Checks if a test case should be executed based on its run mode and data set run mode.
     *
     * @param testcasename The name of the test case.
     * @param data         A Hashtable containing test data, including the run mode.
     * @throws SkipException If the test case or its data set has the run mode set to "NO," the test is skipped.
     */
    public static void checkexecution(String testcasename, Hashtable<String, String> data) {
        // Check if the test case's run mode is set to "NO" in the test suite.
        if (!Utils.isTestRunnable(testcasename, excel)) {
            throw new SkipException("Skipping the test " + testcasename.toUpperCase() + " as the Run mode is NO");
        }

        // Check if the run mode for the current data set is set to "NO."
        if (!data.get("Runmode").equals("Y")) {
            throw new SkipException("Skipping the test case as the Run mode for data set is NO");
        }
    }

    /**
     * Checks if a given test case is marked as "Runnable" (i.e., should be executed) in the test suite Excel sheet.
     *
     * @param testName The name of the test case to be checked.
     * @param excel    An instance of the ExcelReader class used for reading test suite data.
     * @return True if the test case is marked as "Y" (Runnable) in the test suite, otherwise False.
     */
    public static boolean isTestRunnable(String testName, ExcelReader excel) {
        // Define the sheet name where test case data is stored.
        String sheetName = "TestSuite";
        // Get the total number of rows in the sheet.
        int rows = excel.getRowCount(sheetName);

        // Iterate through the rows starting from row 2 (assuming row 1 contains headers).
        for (int rNum = 2; rNum <= rows; rNum++) {
            // Get the test case ID from the "TCID" column.
            String testCase = excel.getCellData(sheetName, "TCID", rNum);

            // Check if the current row corresponds to the given test case name.
            if (testCase.equalsIgnoreCase(testName)) {
                // Get the run mode from the "Runmode" column.
                String runmode = excel.getCellData(sheetName, "Runmode", rNum);

                // Check if the run mode is "Y" (indicating that the test case should be run).
                if (runmode.equalsIgnoreCase("Y")) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        // If the test case is not found in the sheet or not marked as "Y," return false.
        return false;
    }


    public static String generateFormattedDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Extract the last two digits of the year
        int lastTwoDigitsOfYear = currentDate.getYear() % 100;

        // Format the date in the required format "dd-MM-yy"
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yy"));

        return formattedDate;
    }

    public static String generatedateandtime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh:mm:ss_a");
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }


    public static String getdevicetime() {
        String time  =  driver.getDeviceTime();
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(time);
        String devicetime = offsetDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        return devicetime;
    }

    public static String datevisitedtime() {
        String time  =  driver.getDeviceTime();
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(time);
        String devicetime = offsetDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return devicetime;
    }


    public static void gohomepage(String module){

        if(!Source(module)){
            click("xpath", Login_Page.menubutton);

        }
    }

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

    public static Map<String, String> queryloader() throws IOException {

        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(queryfilepath);
        properties.load(fileInputStream);
        String[] querykeys= {"Categorymasterquery","FormFieldsquery","QuestionFormFieldsquery",
                "ProductColumnquery","FormMasterquery","Productquery","EnumFieldquery"};

        Map<String,String>Queries = new HashMap<String,String>();

        for(String key : querykeys  ){
            Queries.put(key, properties.getProperty(key));
        }

        fileInputStream.close();
        return Queries;

    }

    public static String SetSpecialCharacter(String value) {

        // Map of special characters and their HTML entities
        Map<String, String> specialCharacters = new HashMap<>();
        specialCharacters.put("&", "&amp;");
        specialCharacters.put("<", "&lt;");
        specialCharacters.put(">", "&gt;");
        specialCharacters.put("\"", "&quot;");
        specialCharacters.put("'", "&apos;");
        specialCharacters.put("©", "&copy;");
        specialCharacters.put("®", "&reg;");
        specialCharacters.put("™", "&trade;");

        // Check if the input value contains any special characters
        boolean containsSpecialCharacter = false;
        for (String specialChar : specialCharacters.keySet()) {
            if (value.contains(specialChar)) {
                containsSpecialCharacter = true;
                break;
            }
        }
        // If the input value contains special characters, search and replace them with their HTML entities
        if (containsSpecialCharacter) {
            for (Map.Entry<String, String> entry : specialCharacters.entrySet()) {
                value = value.replaceAll(Pattern.quote(entry.getKey()), entry.getValue());
            }
        }
        // Return the modified value with HTML entities
        return value;
    }


    public static void pssshopfrontimage() throws InterruptedException {
        log.info("Starting PSS Shop front image capture process");
        Thread.sleep(2000);
        if(Source("Take Photo")){
            WebdriverWait("Xpath", Shutterbutton, 4);
            click("Xpath", Shutterbutton);
            log.info("Shutter button is Clicked");
            WebdriverWait("ACCESSIBILITYID", "Done", 3);
            click("ACCESSIBILITYID", "Done");
            log.info("Done button is Clicked");
            log.info("PSS Shop front image capture process completed successfully");
        }

    }


    public static void networkconditions(String mode,String duration) throws InterruptedException {

        int sleeptime = Integer.parseInt(duration+"000");
        if(mode.equalsIgnoreCase("Wifi")){
            networkconnections();
            Thread.sleep(sleeptime);
            driver.toggleWifi();
        } else if (mode.equalsIgnoreCase("MobileData")) {
            networkconnections();
            Thread.sleep(sleeptime);
            driver.toggleData();
        } else if (mode.equalsIgnoreCase("Disable")) {
            networkconnections();
            Thread.sleep(sleeptime);
            networkconnections();
        }

    }


    public static void networkconnections(){
        driver.toggleWifi();
        driver.toggleData();
    }


}





