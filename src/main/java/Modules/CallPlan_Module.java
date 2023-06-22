package Modules;

import Base.Setup;
import io.appium.java_client.AppiumBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static UiObjects.CallPlan_Objects.*;
import static UiObjects.HomePage_Objects.Callplan;
import static Utilities.Actions.*;
import static Utilities.Constants.*;
import static Utilities.DBConfig.Db;
import static Utilities.DBConfig.GetDatas;

public class CallPlan_Module extends Setup {

    public static void CallPlan() throws Exception {
        click("ACCESSIBILITYID", Callplan);
        driver.navigate().back();
        click("ACCESSIBILITYID", Callplan);
        click("Xpath", TargetID);
        WebdriverWait("xpath", Startworkbutton, 10);
        click("Xpath", Startworkbutton);
        if (Source("please wait")){
            Thread.sleep(4000);
            click("ACCESSIBILITYID", Callplan);
            click("Xpath", TargetID);
            WebdriverWait("xpath", Startworkbutton, 10);
            if (Source("Start Work")) {
                click("xpath", Startworkbutton);
            }
        }
    }

    public static void Category_Lists(String Category) throws Exception {

        List<String> categoryNames = GetDatas(Categorymasterquery, "Name");
        categoryNames.forEach(categoryName -> {
            if (Source(categoryName)) {
                click("Xpath", getcategoryattribute(categoryName));

                //Getting Formnames
                List<String> formNames = null;
                try {
                    formNames = GetDatas(FormFieldsquery, "Form Name");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                for (String formname : formNames) {
                    if (Source(formname)) {
                        click("ACCESSIBILITYID",formname );

                    } else {
                        System.out.println("App is not have formlist  "+formname);

                    }
                }

            } else {
                try {
                    Scroll("up");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (Source(categoryName)) {
                    click("Xpath", getcategoryattribute(categoryName));
                }
            }
        });
    }

    public static void Forms_Lists() throws Exception {



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

}