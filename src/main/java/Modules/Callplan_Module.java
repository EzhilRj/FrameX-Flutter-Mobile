package Modules;

import Base.AppiumTestSetup;

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
    public static boolean Validateuploadcall(String targetid) throws Exception {

        List<String> categories = getColumnNamesFromDatabase(queries.get("Categorymasterquery"), "Name");
        String TargetID = "//android.view.View[contains(@content-desc, 'Target ID: "+targetid+"')]";

        click("ACCESSIBILITYID",Callplan);
        if(!Source(TargetID)){
            driver.navigate().back();
            Thread.sleep(3000);
            click("ACCESSIBILITYID",Callplan);
        }
        if(isElementDisplayed("Xpath",TargetID)){
            click("Xpath",TargetID);
            datevisitedvalidation();
            click("Xpath",Startworkbutton);
            WebdriverWait("ACCESSIBILITYID", UploadcallButton,30);
            DataBinder(categories,targetid);
        }else {
            logAndReportFailure(targetid+" is not showing");
            return false;
        }
        return false;
    }


    private static boolean DataBinder(List<String> categories, String targetID) throws Exception {
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
        if(isElementDisplayed("Xpath",generatecategorylocator(category))){
            click("Xpath", generatecategorylocator(category));
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

        boolean isfielddataExecutionSuccessful = true;
        int ForDEO = IsQuestionForm.equals("1") ? 0 : 1;
        List<Object> fieldNames = new ArrayList<>();

        if (IsQuestionForm.equals("1")) {
            // For question forms, retrieve field names
            fieldNames = getDataObject(MessageFormat.format(queries.get("QuestionFormFieldsquery"), formName, "'" + productName + "'", "'" + formName + "'"));
        } else {
            // For regular forms, retrieve field names
            fieldNames = getDataObject(MessageFormat.format(queries.get("FormFieldsquery"), "'" + formName + "'", IsQuestionForm, ForDEO));
        }

        for (Object field : fieldNames) {
            if (field instanceof LinkedHashMap<?, ?>) {
                LinkedHashMap<?, ?> fieldData = (LinkedHashMap<?, ?>) field;
                Ctrltype = (String) fieldData.get("ControlType");
                Datatype = (String) fieldData.get("DataType");
                fieldName = IsQuestionForm.equals("1") ? Ctrltype : (String) fieldData.get("FieldName");
                enumfieldName = (String) fieldData.get("FieldName");

                // Check if the field is required and add '*' if necessary
                if (fieldData.get("Required").equals("1")) {
                    fieldName = fieldName + " *";
                    click("xpath", fieldName);
                }

                if (fieldName.contains("Photo ")) {
                    if(Source("Photo *")){
                        fieldName = "Photo *";
                    }else{
                        fieldName = "Photo";
                    }
                }

                // Skip field if it contains "Gap Facings"
                if (fieldName.contains("Gap Facings")) {
                    continue;
                }

                // Check if the field element is present
                if (Source(fieldName)) {
                    if (Ctrltype.equals("TextBox")) {
                        String attribute = generatetextfieldlocator(fieldName);
                        /*Scrollto(fieldName);*/
                        String dataset = Datasetter(Datatype, fieldName);
                        Enter("Xpath", attribute, dataset);
                        driver.hideKeyboard();
                    } else if (Ctrltype.equals("DropDownList")) {
                        Dropdownsetter(formName, productName);
                    } else if (Ctrltype.contains("FileUpload")) {
                        ImageCapture();
                    }
                } else {
                    Scroll("up");
                    if (Source(fieldName)) {
                        if (Ctrltype.equals("TextBox")) {
                            String attribute = generatetextfieldlocator(fieldName);
                            /*Scrollto(fieldName);*/
                            String dataset = Datasetter(Datatype, fieldName);
                            Enter("Xpath", attribute, dataset);
                            driver.hideKeyboard();
                        } else if (Ctrltype.equals("DropDownList")) {
                            Dropdownsetter(formName, productName);
                        } else if (Ctrltype.contains("FileUpload")) {
                            ImageCapture();
                        }
                    }
                    isfielddataExecutionSuccessful = false;

                }
            }
        }
        return isfielddataExecutionSuccessful;
    }
}
