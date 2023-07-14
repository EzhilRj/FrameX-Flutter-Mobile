package Utilities;


import java.io.*;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.aventstack.extentreports.Status;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;

import static Base.Setup.*;
import static Modules.CallPlan_Module.fieldName;
import static UiObjects.CallPlan_Objects.*;
import static Utilities.Actions.WebdriverWait;
import static Utilities.Actions.click;
import static Utilities.Constants.EnumFieldquery;
import static Utilities.Constants.ScreenRecpath;
import static Utilities.DBConfig.GetDatas;
import static Utilities.Listeners.test;
import static Utilities.Listeners.timestamp;

public class Utils {

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


    public static void Dropdownsetter() throws Exception {

        List<String> dropList = GetDatas(MessageFormat.format(EnumFieldquery, "'" + fieldName.replace(" *", "") + "'"), "FieldOption");
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
    }

    public static void ImageCapture() throws InterruptedException {

        click("Xpath", Camerabutton);
        log.info("Camera is Cliked");
        test.log(Status.INFO,"Camera is Clicked");
        WebdriverWait("Xpath", Shutterbutton,4);
        click("Xpath", Shutterbutton);
        log.info("Shutterbutton is Cliked");
        test.info("Shutterbutton is Clicked  | "+" Image is Captured");
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



}





