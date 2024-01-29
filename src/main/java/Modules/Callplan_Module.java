package Modules;

import Base.AppiumTestSetup;
import Utilities.ValidationManager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Pages.CallPlan_page.*;
import static Pages.HomePage_page.Callplan;
import static Utilities.Actions.*;
import static Utilities.DBConfig.getColumnNamesFromDatabase;
import static Utilities.DBConfig.getDataObject;
import static Utilities.Utils.*;
import static Utilities.ValidationManager.Source;
import static Utilities.ValidationManager.datevisitedvalidation;

public class Callplan_Module extends AppiumTestSetup {

    public static String fieldName;
    public static String Ctrltype;
    public static String Datatype;
    public static String enumfieldName;
    public static boolean validateUploadCall(String targetId, String uploadType) throws Exception {

        List<String> categories = getColumnNamesFromDatabase(queries.get("Categorymasterquery"), "Name");
        String targetXPath = "//android.view.View[contains(@content-desc, 'Target ID: " + targetId + "')]";

        click("ACCESSIBILITYID", Callplan);
        if (!Source(targetXPath)) {
            driver.navigate().back();
            Thread.sleep(3000);
            click("ACCESSIBILITYID", Callplan);
        }
        if (isElementDisplayed("Xpath", targetXPath)) {
            click("Xpath", targetXPath);
            String startTime = datevisitedvalidation();
            click("Xpath", Startworkbutton);
            WebdriverWait("ACCESSIBILITYID", UploadcallButton, 30);

            if (dataBinder(categories, targetId)) {
                if (uploadType.equalsIgnoreCase("Upload")) {
                    return Uploadcallfunction(startTime, targetId);
                } else {
                    closecallfunction();
                }
            }
        } else {
            logAndReportFailure(targetId + " is not showing");
        }
        return false;
    }


    private static boolean dataBinder(List<String> categories, String targetID) throws Exception {
        boolean isExecutionSuccessful = true;
        for (String category : categories) {
            String modifiedCategory = SetSpecialCharacter(category); // Apply SetSpecialCharacter to category name
            if (Source(modifiedCategory)) {
                boolean isCategoryExecutionSuccessful = categoryprocess(category, targetID);
                if (!isCategoryExecutionSuccessful) {
                    isExecutionSuccessful = false;
                    break;
                }
            }
        }
        return isExecutionSuccessful;
    }

    private static boolean categoryprocess(String category, String targetID) throws Exception {

        String modifiedCategory = SetSpecialCharacter(category);
        boolean isCategoryExecutionSuccessful = false;
        String catexpath = generatecategorylocator(category);
        if(isElementDisplayed("Xpath",catexpath)){
            click("Xpath",catexpath);
            if(!Source(modifiedCategory)){
                Scroll("up");
            }
            if (Source(modifiedCategory)) {
                List<Object> formDatas = getDataObject(queries.get("FormMasterquery"));
                for (Object formData : formDatas) {
                    if (formData instanceof LinkedHashMap<?, ?>) {
                        LinkedHashMap<?, ?> formDataMap = (LinkedHashMap<?, ?>) formData;
                        String formName = (String) formDataMap.get("FormName");
                        String isQuestionForm = (String) formDataMap.get("IsQuestionForm");
                        try {
                            formprocess(category, formName, isQuestionForm, targetID);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                isCategoryExecutionSuccessful = true;
            }
        }
        return isCategoryExecutionSuccessful;
    }

    public static boolean formprocess(String category, String form, String IsQuestionForm, String targetID) throws Exception {

        boolean isformExecutionSuccessful = false;
        if(Source(form)){
            click("ACCESSIBILITYID", form);
            String formName = form.replace(" ", "_");
            String productColumn = getColumnNamesFromDatabase(MessageFormat.format(queries.get("ProductColumnquery"), "'" + formName + "'"), "ProductColumn").get(0);
            productColumn = productColumn.replace("*", "+ ' *'");
            boolean pictureform = formName.toLowerCase().equals("picture") ? true: false;
            String questionColumns = (IsQuestionForm.equals("1") || pictureform == true) ?  "Rtrim(Ltrim("+productColumn+  "))  + CASE\n" +
                    "        WHEN sm.QuestionRequired = '1' THEN ' *'\n" +
                    "        ELSE  '' End "  : "Rtrim(Ltrim("+ productColumn+"))";
            List<String> productNames = getColumnNamesFromDatabase(MessageFormat.format(queries.get("Productquery"), formName, targetID, "'" + category + "'", questionColumns), "ProductName");
            for (String productName : productNames) {
                try {
                    productprocess(formName, productName, IsQuestionForm);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
            }

            if (Source("Next")) {
                click("ACCESSIBILITYID", NextButton);
            } else if (Source("Done")) {
                click("ACCESSIBILITYID", Donebutton);
            }
            // Data binding for the form is successful
            isformExecutionSuccessful = true;
        }
        return isformExecutionSuccessful;
    }


    private static boolean productprocess(String formName, String productName, String IsQuestionForm) throws Exception {

        String modifiedProductName = SetSpecialCharacter(productName);
        boolean isProductExecutionSuccessful = false;

        if(!Source(modifiedProductName)){
            Scroll("up");
        }

        if (Source(modifiedProductName)) {
            click("xpath", generateproductlocator(productName));
            productName = productName.replace(" *","");
            enterfieldprocess(formName, IsQuestionForm, productName);

            isProductExecutionSuccessful = true;
        }

        return isProductExecutionSuccessful;
    }


    private static boolean enterfieldprocess(String formName, String IsQuestionForm, String productName) throws Exception {
        int forDEO = IsQuestionForm.equals("1") ? 0 : 1;
        List<Object> fieldNames = getDataObject(IsQuestionForm.equals("1") ?
                MessageFormat.format(queries.get("QuestionFormFieldsquery"), formName, "'" + productName + "'", "'" + formName + "'") :
                MessageFormat.format(queries.get("FormFieldsquery"), "'" + formName + "'", IsQuestionForm, forDEO));

        for (Object field : fieldNames) {
            if (!(field instanceof LinkedHashMap<?, ?>)) {
                continue;
            }

            LinkedHashMap<?, ?> fieldData = (LinkedHashMap<?, ?>) field;
            Ctrltype = (String) fieldData.get("ControlType");
            Datatype = (String) fieldData.get("DataType");
            fieldName = IsQuestionForm.equals("1") ? Ctrltype : (String) fieldData.get("FieldName");
            enumfieldName = (String) fieldData.get("FieldName");

            // Check if the field is required and add '*' if necessary
            if ("1".equals(fieldData.get("Required"))) {
                fieldName += " *";
                click("xpath", fieldName);
            }

            // Skip field if it contains "Gap Facings"
            if (fieldName.contains("Gap Facings")) {
                continue;
            }

            // Check if the field element is present
            if (!Source(fieldName)) {
                Scroll("up");
                if (!Source(fieldName)) {
                    return false;
                }
            }

            if (Ctrltype.equals("TextBox")) {
                String attribute = generatetextfieldlocator(fieldName);
                String dataset = Datasetter(Datatype, fieldName);
                Enter("Xpath", attribute, dataset);
                driver.hideKeyboard();
            } else if (Ctrltype.equals("DropDownList")) {
                Dropdownsetter(formName, productName);
            } else if (Ctrltype.contains("FileUpload")) {
                ImageCapture();
            }
        }
        return true;
    }




    private static boolean Uploadcallfunction(String starttime, String tid) throws InterruptedException {

        click("ACCESSIBILITYID",UploadcallButton);
        if (isElementDisplayed("ACCESSIBILITYID", Uploadcallconfirmpopup)) {
            click("ACCESSIBILITYID", Yesbutton);
            if(Source(starttime)){
                click("ACCESSIBILITYID", Uploadcallsbutton);
            }
        } else if (isElementDisplayed("Xpath", Perfectstorescorepopup)) {
            click("ACCESSIBILITYID", Okbutton);
            if(Source(starttime)){
                click("ACCESSIBILITYID", Uploadcallsbutton);
            }
        }
        if(ValidationManager.calluploadvalidation(tid)){
            return true;
        }else{
            return false;
        }
    }

    private static void closecallfunction(){

    }
}
