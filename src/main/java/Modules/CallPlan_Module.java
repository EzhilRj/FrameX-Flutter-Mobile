package Modules;

import Base.Setup;
import com.aventstack.extentreports.Status;
import org.testng.Assert;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static UiObjects.CallPlan_Objects.*;
import static UiObjects.HomePage_Objects.Callplan;
import static Utilities.Actions.*;
import static Utilities.Constants.*;
import static Utilities.DBConfig.GetDataObject;
import static Utilities.DBConfig.GetDatas;
import static Utilities.Listeners.test;
import static Utilities.Utils.*;

public class CallPlan_Module extends Setup {

    public static String fieldName;
    public static String Required;
    public static String FormName;
    public static String IsQuestionForm;
    public static String Ctrltype;
    public static String Datatype;


    public static void CallPlan() {
        try {
            log.info("--------------------------" + getCurrentMethodName() + " is started--------------------------------------");
            driver.openNotifications();
            log.info("Notification is Open");
            if (isElementDisplayed("xpath", notification)) {
                log.info(tid + " Downloaded successfully");
                log.info("Time taken for Download call: " + calculateDuration() + " Seconds");
                test.log(Status.INFO, "Time taken for Download call: " + calculateDuration() + " Seconds");
                driver.navigate().back();
                click("ACCESSIBILITYID", Callplan);
                log.info("Callplan is clicked");
                click("Xpath", TargetID);
                log.info("Targetid is clicked");
                if (isElementDisplayed("xpath", Startworkbutton)) {
                    click("Xpath", Startworkbutton);
                    log.info("Startworkbutton is clicked");
                    if (isElementDisplayed("ACCESSIBILITYID", Callplan)) {
                        log.info("Data is Not downloaded Please wait");
                        Thread.sleep(2000);
                        click("ACCESSIBILITYID", Callplan);
                        log.info("Callplan is clicked");
                        click("Xpath", TargetID);
                        log.info("Targetid is clicked");
                        click("Xpath", Startworkbutton);
                        log.info("Startworkbutton is clicked");
                    }
                    if (isElementDisplayed("xpath", UploadcallButton)) {
                        log.info("Categorylist Page is showing");
                        Assert.assertTrue(true);
                    } else {
                        Assert.assertTrue(false);
                        log.error("Categorylist Page is Not showing");
                    }
                }
            }
        } catch (Exception e) {
            log.error("An exception occurred: " + e.getMessage());
        }
    }



    public static void DataBinder() throws Exception {

        log.info("--------------------------" + getCurrentMethodName() + " is started--------------------------------------");

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
                List<Object> formDatas = GetDataObject(FormMasterquery);
                formDatas.stream()
                        .filter(formData -> formData instanceof LinkedHashMap<?, ?>)
                        .map(formData -> (LinkedHashMap<?, ?>) formData)
                        .forEach(formData -> {
                            String formName = (String) formData.get("FormName");
                            String isQuestionForm = (String) formData.get("IsQuestionForm");
                            try {
                                processForm(category, formName, isQuestionForm);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
        } else {
            Scroll("up");
            if (Source(category)) {
                click("Xpath", SetCategoryAttribute(category));
                List<Object> formDatas = GetDataObject(FormMasterquery);
                formDatas.stream()
                        .filter(formData -> formData instanceof LinkedHashMap<?, ?>)
                        .map(formData -> (LinkedHashMap<?, ?>) formData)
                        .forEach(formData -> {
                            String formName = (String) formData.get("FormName");
                            String isQuestionForm = (String) formData.get("IsQuestionForm");
                            try {
                                processForm(category, formName, isQuestionForm);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
        }
    }



    public static void processForm(String category, String form, String IsQuestionForm) throws Exception {
        if (Source(form)) {
            click("ACCESSIBILITYID", form);
            String formName = form.replace(" ", "_");
            String productColumn = GetDatas(MessageFormat.format(ProductColumnquery, "'" + formName + "'"), "ProductColumn").get(0);

            // Get Product Column List
            List<String> productNames = GetDatas(MessageFormat.format(Productquery, formName, tid, "'" + category + "'", productColumn), "ProductName");
            productNames.forEach(productName -> {
                try {
                    processProduct(formName, productName,IsQuestionForm);
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

    public static void processProduct(String formName, String productName, String IsQuestionForm) throws Exception {
        if (Source(productName)) {
            click("Xpath", SetCategoryAttribute(productName));
            enterFieldData(formName,IsQuestionForm);
        }
    }



    private static void enterFieldData(String formName, String IsQuestionForm) throws Exception {
        List<Object> fieldNames = GetDataObject(MessageFormat.format(FormFieldsquery, "'" + formName + "'", IsQuestionForm));

        for (Object field : fieldNames) {
            if (field instanceof LinkedHashMap<?, ?> fieldData) {

                Ctrltype = (String) fieldData.get("ControlType");
                Datatype = (String) fieldData.get("DataType");

                fieldName = IsQuestionForm.equals("1") ? Ctrltype : (String) fieldData.get("FieldName") ;

                if(fieldData.get("Required") .equals("1")){
                    fieldName=fieldName+" *";
                }

                if (fieldName.contains("Photo *")) {
                    fieldName = "Photo";
                }
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
                        ImageCapture();
                    }
                }
            }
        }
    }

}
