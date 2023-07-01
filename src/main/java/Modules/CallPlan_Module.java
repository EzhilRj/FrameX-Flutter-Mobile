package Modules;

import Base.Setup;
import io.appium.java_client.AppiumBy;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.MessageFormat;
import java.util.*;

import static UiObjects.CallPlan_Objects.*;
import static UiObjects.HomePage_Objects.Callplan;
import static Utilities.Actions.*;
import static Utilities.Actions.isElementDisplayed;
import static Utilities.Constants.*;
import static Utilities.DBConfig.*;
import static Utilities.Utils.*;

public class CallPlan_Module extends Setup {

    public static String fieldName;
    public static String Ctrltype;
    public static String Datatype;


    public static void CallPlan() throws Exception {

        driver.openNotifications();
        if (isElementDisplayed("xpath",notification)) {
            driver.navigate().back();
            click("ACCESSIBILITYID", Callplan);
            click("Xpath", TargetID);
            if(isElementDisplayed("xpath",Startworkbutton)) {
                click("Xpath", Startworkbutton);
                if(isElementDisplayed("ACCESSIBILITYID", Callplan)) {
                    Thread.sleep(2000);
                    click("ACCESSIBILITYID", Callplan);
                    click("Xpath", TargetID);
                    click("Xpath", Startworkbutton);
                }
                if(isElementDisplayed("xpath",UploadcallButton)){
                    Assert.assertTrue(true);
                }else{
                    Assert.assertTrue(false);
                }
            }
        }
    }


    public static void DataBinder() throws Exception {
        // Getting Category lists
        List<String> categoryNames = GetDatas(Categorymasterquery, "Name");
        categoryNames.forEach(category -> {
            try {
                processCategory(category);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void processCategory(String category) throws Exception {
        if (Source(category)) {
            click("Xpath", SetCategoryAttribute(category));
            Thread.sleep(2000);
            if (isElementDisplayed("ACCESSIBILITYID", category)) {
                // Getting Formnames
                List<String> formNames = GetDatas(FormMasterquery, "FormName");
                formNames.forEach(form -> {
                    try {
                        processForm(category, form);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } else {
            Scroll("up");
            click("Xpath", SetCategoryAttribute(category));
            List<String> formNames = GetDatas(FormMasterquery, "FormName");
            formNames.forEach(form -> {
                try {
                    processForm(category, form);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


    public static void processForm(String category, String form) throws Exception {
        if (Source(form)) {
            click("ACCESSIBILITYID", form);
            String formName = form.replace(" ", "_");
            String productColumn = GetDatas(MessageFormat.format(ProductColumnquery, "'" + formName + "'"), "ProductColumn").get(0);

            // Get Product Column List
            List<String> productNames = GetDatas(MessageFormat.format(Productquery, formName, tid, "'" + category + "'", productColumn), "ProductName");
            productNames.forEach(productName -> {
                try {
                    processProduct(formName, productName);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
            }
            if (Source("Next")) {
                click("Xpath", NextButton);
            } else if (Source("Done")) {
                click("Xpath", Donebutton);
            }
        }
    }

    public static void processProduct(String formName, String productName) throws Exception {
        if (Source(productName)) {
            click("Xpath", SetCategoryAttribute(productName));
            enterFieldData(formName);
        }
    }

    private static void enterFieldData(String formName) throws Exception {
        List<Object> fieldNames = GetDataObject(MessageFormat.format(FormFieldsquery, "'" + formName + "'"));

        for (Object field : fieldNames) {
            if (field instanceof LinkedHashMap<?, ?> fieldData) {
                fieldName = (String) fieldData.get("FieldName");
                if (fieldName.contains("Photo *")) {
                    fieldName = "Photo";
                }
                Ctrltype = (String) fieldData.get("ControlType");
                Datatype = (String) fieldData.get("DataType");

                if (fieldName.contains("Gap Facings")) {
                    continue;
                }

                if (Source(fieldName)) {
                    if (Ctrltype.equals("TextBox")) {
                        String attribute = SetTextFieldAttribute(fieldName);
                        String dataset = Datasetter(Datatype, fieldName);
                        Enter("Xpath", attribute, dataset);
                    } else if (Ctrltype.equals("DropDownList")) {
                        Dropdownsetter();
                    } else if (Ctrltype.contains("FileUpload")) {
                        Thread.sleep(1000);
                        ImageCapture();
                    }
                }
            }
        }
    }

}
