package Utilities;


import java.io.*;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Base.AppiumTestSetup;
import Pages.Login_Page;
import io.appium.java_client.AppiumBy;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import static Base.AppiumTestSetup.driver;
import static Base.AppiumTestSetup.excel;
import static Pages.CallPlan_page.*;
import static Utilities.Actions.*;
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
        } else if (type.contains("Varchar")) {
            return generateRandomString();
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

    private static final String[] DICTIONARY = {
            "Testdata0", "Testdata1", "Testdata2", "Testdata3", "Testdata4",
            "Testdata5", "Testdata6", "Testdata7", "Testdata8", "Testdata9"
    };
    private static final int DEFAULT_LENGTH = 1;


    public static String generateRandomString() {
        return generateRandomString(DEFAULT_LENGTH);
    }

    public static String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(DICTIONARY.length);
            String randomWord = DICTIONARY[randomIndex];
            randomString.append(randomWord).append(" ");

        }

        return randomString.toString().trim();
    }


   /* public static void Dropdownsetter() throws Exception {

        List<String> dropList = getColumnValues(MessageFormat.format(EnumFieldquery, "'" + fieldName.replace(" *", "") + "'"), "FieldOption");
        log.info("Dropdown Query"+EnumFieldquery);
        Collections.shuffle(dropList);
        int size = dropList.size();
        Random random = new Random();
        int count = 0;
        for (String drop : dropList) {
            if (count >= size) {
                break;
            }
            click("ACCESSIBILITYID", fieldName);
            log.info("Dropdown field is Cliked");
            click("ACCESSIBILITYID", drop);
            log.info("Fieldname is "+fieldName+"Data is  "+ drop);
            test.info("<span style=\"color: Blue; font-weight: bold;\">FieldName is : </span><span style=\"color: Black;\">" + fieldName + "</span>"+"   |  "+"<span style=\"color: Blue; font-weight: bold;\">Data is  : </span><span style=\"color: Black;\">" + drop + "</span>");
            break;


        }
    }*/

    public static void ImageCapture() throws InterruptedException {

        click("Xpath", Camerabutton);
        AppiumTestSetup.log.info("Camera is Cliked");
        WebdriverWait("Xpath", Shutterbutton,4);
        click("Xpath", Shutterbutton);
        AppiumTestSetup.log.info("Shutterbutton is Cliked");
        Thread.sleep(3000);

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


    public static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }


    public static void Scrollto(String text) throws InterruptedException {
        String scrollCommand = "new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));";
        driver.findElement(AppiumBy.androidUIAutomator(scrollCommand));
    }

    public static void captureScreenshot() throws IOException {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Date d = new Date();
        screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

        FileUtils.copyFile(scrFile,
                new File(System.getProperty("user.dir") + "\\src\\test\\resources\\Reports\\" + screenshotName));
        FileUtils.copyFile(scrFile, new File("\\src\\test\\resources\\Reports\\" + screenshotName));

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


    public static String generateDate() {
        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Format the date in the desired format
        String formattedDate = currentDate.format(formatter);

        return formattedDate;
    }

    public static String generatedateandtime() {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Define the desired date and time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");

        // Format the current date and time using the defined formatter
        String formattedDateTime = now.format(formatter);

        return formattedDateTime;
    }

    public static void gohomepage(String module){

        if(!Source(module)){
            click("xpath", Login_Page.menubutton);
            //click("ACCESSIBILITYID",HomePage_Objects.home );
        }
    }

}





