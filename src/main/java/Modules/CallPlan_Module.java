package Modules;

import Base.Setup;

import java.util.*;

import static UiObjects.CallPlan_Objects.*;
import static UiObjects.HomePage_Objects.Callplan;
import static Utilities.Actions.*;
import static Utilities.Constants.*;
import static Utilities.DBConfig.*;
import static Utilities.Utils.Datasetter;

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


    public static void Category_Lists() throws Exception {
        List<String> categoryNames = GetDatas(Categorymasterquery, "Name");
        for (String category : categoryNames) {
            if (Source(category)) {
                click("Xpath", SetCategoryAttribute(category));
                List<String> formNames = GetDatas(FormMasterquery, "FormName");
                for (String form : formNames) {
                    if (Source(form)) {
                        WebdriverWait("ACCESSIBILITYID", form,10);
                        //   click("ACCESSIBILITYID", form);
                        List<Object> fieldnames = GetDataObject("select FieldName,DataType,ControlType from FormFieldsDetail where FormName = '"+form+"'");
                        for (Object field : fieldnames) {
                            if (field instanceof LinkedHashMap<?, ?> fieldData) {
                                String fieldName = (String) fieldData.get("FieldName");
                                String Ctrltype =  (String) fieldData.get("ControlType");
                                String Datatype =  (String) fieldData.get("DataType");
                                if (Source(fieldName)) {
                                    if(Ctrltype.equals("TextBox")){
                                        Enter("Xpath",SetTextFieldAttribute(fieldName),  Datasetter(Datatype));
                                        System.out.println(driver.getPageSource());
                                    } else if (Ctrltype.equals("DropDownList")) {
                                        click("ACCESSIBILITYID",fieldName);
                                        click("ACCESSIBILITYID",Stocknotavailable);
                                    }

                                }
                            }
                        }
                    }
                    driver.navigate().back();
                    click("Xpath",NextButton);
                }
            }
        }
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

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }



}

