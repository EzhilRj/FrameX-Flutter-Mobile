package Modules;

import Base.AppiumTestSetup;
import Utilities.ValidationManager;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Listeners.FrameX_Listeners.logAndReportSuccess;
import static Pages.CallPlan_page.*;
import static Pages.HomePage_page.ActivityLog;
import static Pages.HomePage_page.Callplan;
import static Utilities.Actions.*;
import static Utilities.DBConfig.getColumnNamesFromDatabase;
import static Utilities.DBConfig.getDataObject;
import static Utilities.Utils.*;
import static Utilities.ValidationManager.*;

public class Callplan_Module extends AppiumTestSetup {

    public static String fieldName;
    private static String Ctrltype;
    private static String Datatype;
    private static String enumfieldName;

    private static List<Object> formDatas;

    // Initialize formDatas before the program starts
    static {
        try {
            formDatas = getDataObject(queries.get("FormMasterquery"));
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log or rethrow)
            e.printStackTrace();
        }
    }



    public static String targetid ;
    public static boolean validateUploadCall(String tid, String uploadType,String Closecallreason,String Closecallimage, String networkmode,String networkduration,String fieldtype) throws Exception {

        targetid = tid;

        List<String> categories = getColumnNamesFromDatabase(queries.get("Categorymasterquery"), "Name");
        log.info("Category Master Query : "+queries.get("Categorymasterquery"));
        String targetXPath = "//android.view.View[contains(@content-desc, 'Target ID: " + targetid + "')]";
        log.info("Xpath of targetid ; "+targetXPath);

        click("ACCESSIBILITYID", Callplan);
        log.info("Call plan is Clicked");
        if (!Source(targetid)) {
            log.info("Source not having target id");
            driver.navigate().back();
            Thread.sleep(3000);
            click("ACCESSIBILITYID", Callplan);
            log.info("Call plan is Clicked");
        }
        if (Source(targetid)) {
            click("Xpath", targetXPath);
            log.info("Target is Clicked");
            String startTime = datevisitedvalidation();
            click("Xpath", Startworkbutton);
            log.info("Start work button is clicked");
            pssshopfrontimage();
            WebdriverWait("ACCESSIBILITYID", UploadcallButton, 15);
            if (uploadType.equalsIgnoreCase("Upload")) {
                log.info("Call Type : Upload");
                if (dataBinder(categories,fieldtype)) {
                    return Uploadcallfunction(startTime,networkmode,networkduration);
                }
            } else if (uploadType.equalsIgnoreCase("Close")) {
                log.info("Call Type : Close");
                return closecallfunction(Closecallreason,Closecallimage,networkmode,networkduration);
            }
        } else {
            logAndReportFailure("Target ID "+targetid + " is not Displayed ");
        }
        return false;
    }


    private static boolean dataBinder(List<String> categories, String fieldtype) throws Exception {
        boolean isExecutionSuccessful = true;
        for (String category : categories) {
            String modifiedCategory = SetSpecialCharacter(category); // Apply SetSpecialCharacter to category name
            if (Source(modifiedCategory)) {
                boolean isCategoryExecutionSuccessful = categoryprocess(category,fieldtype);
                if (!isCategoryExecutionSuccessful) {
                    isExecutionSuccessful = false;
                    break;
                }
            }
        }
        return isExecutionSuccessful;
    }

    private static boolean categoryprocess(String category, String fieldtype) throws Exception {

        String modifiedCategory = SetSpecialCharacter(category);
        String catexpath = generatecategorylocator(category);

        if (!isElementDisplayed("Xpath", catexpath)) {
            return false;
        }
        click("Xpath", catexpath);

        if (!Source(modifiedCategory)) {
            Scroll("up");
        }

        if (Source(modifiedCategory)) {
            for (Object formData : formDatas) {
                if (formData instanceof LinkedHashMap<?, ?>) {
                    LinkedHashMap<?, ?> formDataMap = (LinkedHashMap<?, ?>) formData;
                    String formName = (String) formDataMap.get("FormName");
                    String isQuestionForm = (String) formDataMap.get("IsQuestionForm");
                    try {
                        formprocess(category, formName, isQuestionForm, fieldtype);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return true;
        }
        return false;

    }


    public static boolean formprocess(String category, String form, String IsQuestionForm, String fieldtype) throws Exception {
        boolean isformExecutionSuccessful = false;

        if (Source(form)) {
            click("ACCESSIBILITYID", form);
            String formName = form.replace(" ", "_");

            // Use a constant for queries
            String productColumnQuery = MessageFormat.format(queries.get("ProductColumnquery"), "'" + formName + "'");
            String productColumn = getColumnNamesFromDatabase(productColumnQuery, "ProductColumn").get(0);
            productColumn = productColumn.replace("*", "+ ' *'");

            boolean pictureform = formName.equalsIgnoreCase("picture");
            String questionColumns = (IsQuestionForm.equals("1") || pictureform) ? "Rtrim(Ltrim(" + productColumn + ")) + CASE\n" +
                    "        WHEN sm.QuestionRequired = '1' THEN ' *'\n" +
                    "        ELSE  '' End " : "Rtrim(Ltrim(" + productColumn + "))";

            // Use a constant for the Product query
            String productQuery = MessageFormat.format(queries.get("Productquery"), formName, targetid, "'" + category + "'", questionColumns);
            List<String> productNames = getColumnNamesFromDatabase(productQuery, "ProductName");

            // Use Collection.removeIf to filter out non-mandatory elements
            if (fieldtype.equalsIgnoreCase("Mandatory")) {
                productNames.removeIf(ele -> !ele.contains("*"));
            }

            for (String productName : productNames) {
                try {
                    productprocess(formName, productName, IsQuestionForm);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            // Refactor keyboard handling
            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
            }

            // Refactor button handling
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

        if (!Source(modifiedProductName)) {
            Scroll("up");
        }

        if (Source(modifiedProductName)) {
            click("ACCESSIBILITYID", productName);
            String cleanProductName = productName.replace(" *", "");
            enterfieldprocess(formName, IsQuestionForm, cleanProductName);
            return true;
        }

        return false;
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

            updateFieldNameForRequired(formName, productName, fieldData);

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

            handleControlType(formName, Ctrltype, fieldName, Datatype,productName);
        }
        return true;
    }


    private static boolean Uploadcallfunction(String starttime, String networkmode , String networkoffduration) throws InterruptedException {

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
        networkconditions(networkmode,networkoffduration);
        if(calluploadvalidation(networkmode,"Target "+ targetid+" successfully uploaded")){
            logAndReportSuccess(targetid+" Call Uploaded Successfully ");
            driver.navigate().back();
            return true;
        }else{
            driver.navigate().back();
            logAndReportFailure(targetid+" Call is not Uploaded Successfully ");
            return false;
        }
    }

    private static boolean closecallfunction(String reason, String closecallimage,String networkmode , String networkoffduration) throws InterruptedException {

        click("ACCESSIBILITYID",CloseCallButton);
        click("ACCESSIBILITYID","Yes");
        click("ACCESSIBILITYID","Reason *");
        click("ACCESSIBILITYID",reason);
        if(closecallimage.equalsIgnoreCase("True")){
            captureCloseCallImage();
        }
        click("ACCESSIBILITYID", "Done");
        networkconditions(networkmode,networkoffduration);
        if(Source("Please, Take  Reason")){
            logAndReportFailure("Please Take Close Call Reason Image ");
            return false;
        }
        // Validate call upload
        String successMessage = "Target " + targetid + " Close Call successfully uploaded";
        if (calluploadvalidation(networkmode, successMessage)) {
            logAndReportSuccess(targetid + " Close Call Uploaded Successfully ");
        } else {
            logAndReportFailure(targetid + " Close Call Uploaded Failed ");
        }

        driver.navigate().back();
        return calluploadvalidation(networkmode, successMessage);

    }


    private static void updateFieldNameForRequired(String formName, String productName, LinkedHashMap<?, ?> fieldData) {
        if(formName.contains("Picture")){
            fieldName += productName.contains("")?  " *":"";
        } else if ("1".equals(fieldData.get("Required"))) {
            fieldName += " *";
        }
    }

    private static void handleControlType(String formName, String Ctrltype, String fieldName, String Datatype,String prodname) throws Exception {
        if (Ctrltype.equals("TextBox")) {
            Enter("Xpath", generatetextfieldlocator(fieldName),  Datasetter(Datatype, fieldName));
            driver.hideKeyboard();
        } else if (Ctrltype.equals("DropDownList")) {
            Dropdownsetter(formName, prodname);
        } else if (Ctrltype.contains("FileUpload")) {
            ImageCapture();
        }
    }


    // Helper method to capture close call image
    private static void captureCloseCallImage() {
        click("Xpath", "//android.view.View[@content-desc=\" \"]/android.view.View[3]");
        click("Xpath", Shutterbutton);
        WebdriverWait("ACCESSIBILITYID", "Done", 3);
        click("ACCESSIBILITYID", "Done");
    }

}

