package Utilities;


import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.AppiumBy;

import static Base.Setup.driver;
import static Modules.CallPlan_Module.fieldName;
import static UiObjects.CallPlan_Objects.*;
import static Utilities.Actions.click;
import static Utilities.Constants.EnumFieldquery;
import static Utilities.DBConfig.GetDatas;

public class Utils {

    public static  List<HashMap<String, String>> getjsondata(String jsonfilepath) throws IOException {

        //String jsoncontent = FileUtils.readFileToString(new File(jsonfilepath));

        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String,String>> data = objectMapper.readValue(jsonfilepath, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;

    }


    public static void scrollto(String txt){

        try {
            driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + txt + "\"));"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static String Datasetter(String type,String facingtype  ){

        if(type.equals("Int")){
            if(facingtype.equalsIgnoreCase("Industry Facing *")) {
                return Randomint().get(1).toString();
            }else if (facingtype.equalsIgnoreCase("Our Brand Facing *")) {
                return Randomint().get(0).toString();
            }
            return Randomint().toString();
        } else if (type.contains("Varchar")) {
            return RandomStrings();
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

    public static String RandomStrings() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public static void Dropdownsetter() throws Exception {

        List<String> dropList = GetDatas(MessageFormat.format(EnumFieldquery, "'" + fieldName.replace(" *", "") + "'"), "FieldOption");
        Collections.shuffle(dropList);
        int size = dropList.size();
        Random random = new Random();
        int count = 0;
        for (String drop : dropList) {
            if (count >= size) {
                break;
            }
            click("ACCESSIBILITYID", fieldName);
            click("ACCESSIBILITYID", drop);
            break;


        }
    }

    public static void ImageCapture( ) throws InterruptedException {
        click("Xpath",Camerabutton);
        Thread.sleep(2000);
        click("Xpath",Shutterbutton);

    }

}
