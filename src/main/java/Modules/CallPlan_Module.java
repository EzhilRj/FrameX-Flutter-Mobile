package Modules;

import Base.Setup;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.Assert;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static UiObjects.CallPlan_Objects.*;
import static UiObjects.HomePage_Objects.ActivityLog;
import static UiObjects.HomePage_Objects.Callplan;
import static Utilities.Actions.*;
import static Utilities.Constants.*;
import static Utilities.DBConfig.GetDataObject;
import static Utilities.DBConfig.GetDatas;
import static Utilities.Listeners.test;
import static Utilities.Utils.*;

public class CallPlan_Module extends Setup {

    public static String fieldName;
    public static String Ctrltype;
    public static String Datatype;


    public static void CallPlan() {
        try {
            log.info("--------------------------" + getCurrentMethodName() + " is started--------------------------------------");
            stopWatch.start();
            driver.openNotifications();
            log.info("Notification is Open");
            WebdriverWait("xpath", targetdownloadnotification,15);
            if (isElementDisplayed("xpath", targetdownloadnotification)) {
                driver.navigate().back();
                stopWatch.stop();
                log.info(tid + " Downloaded successfully");
                test.info( tid+" Downloaded successfully");
                log.info("Time taken for Download call: " + stopWatch.getTime(TimeUnit.SECONDS)+ " Seconds");
                test.info( "<span style=\"color: Blue; font-weight: bold;\"> Time taken for Login : </span><span style=\"color: DodgerBlue;\">"+String.valueOf(stopWatch.getTime(TimeUnit.SECONDS)) + " Seconds"+ "</span>");
                stopWatch.reset();
                click("ACCESSIBILITYID", Callplan);
                log.info("Callplan is clicked");
                click("Xpath", TargetID);
                log.info("Targetid is clicked");
                if (isElementDisplayed("xpath", Startworkbutton)) {
                    click("Xpath", Startworkbutton);
                    stopWatch.start();
                    log.info("Startworkbutton is clicked");
                    if (isElementDisplayed("ACCESSIBILITYID", Callplan)) {
                        log.info("Data is Not downloaded Please wait");
                        Thread.sleep(2000);
                        click("ACCESSIBILITYID", Callplan);
                        log.info("Callplan is clicked");
                        WebdriverWait("Xpath", TargetID,5);
                        click("Xpath", TargetID);
                        log.info("Targetid is clicked");
                        click("Xpath", Startworkbutton);
                        log.info("Startworkbutton is clicked");
                    }
                    if (isElementDisplayed("xpath", UploadcallButton)) {
                        stopWatch.stop();
                        log.info("Time required to download the Target data : "+stopWatch.getTime(TimeUnit.SECONDS)+" Seconds");
                        test.info( "<span style=\"color: Blue; font-weight: bold;\"> Time required to download the Target data : </span><span style=\"color: DodgerBlue;\">"+String.valueOf(stopWatch.getTime(TimeUnit.SECONDS)) + " Seconds"+ "</span>");
                        stopWatch.reset();
                        log.info("Categorylist Page is showing");
                        test.pass(MarkupHelper.createLabel("Categorylist Page is showing",ExtentColor.GREEN));
                        Assert.assertTrue(true);
                    } else {
                        test.fail(MarkupHelper.createLabel("Categorylist Page is Not showing",ExtentColor.RED));
                        test.error( "<span style=\"color: Red; font-weight: bold;\">\"Categorylist Page is Not showing\" </span>");
                        log.error("Categorylist Page is Not showing");
                        Assert.assertTrue(false);

                    }
                }
            }
        } catch (Exception e) {
            log.error("An exception occurred: " + e.getMessage());
            test.error( "<span style=\"color: Black; font-weight: bold;\">An exception occurred: </span><span style=\"color: Red;\">" +  e.getMessage() + "</span>");
        }
    }

    public static boolean DataBinder() throws Exception {
        log.info("--------------------------" + getCurrentMethodName() + " is started--------------------------------------");

        boolean isExecutionSuccessful = true;

        List<String> categoryNames = GetDatas(Categorymasterquery, "Name");
        log.info("Category Query -------->" + Categorymasterquery);

        for (String category : categoryNames) {
            boolean isCategoryExecutionSuccessful = processCategory(category);
            if (!isCategoryExecutionSuccessful) {
                isExecutionSuccessful = false;
                break;
            }
        }

        return isExecutionSuccessful;
    }

    public static boolean processCategory(String category) throws Exception {

        boolean iscategoryExecutionSuccessful = false;
        if (Source(category)) {
            log.info("Category Name : " + category);
            test.log(Status.INFO, "<span style=\"color: Blue; font-weight: bold;\">Category Name is : </span><span style=\"color: Black;\">" + category + "</span>");
            click("Xpath", SetCategoryAttribute(category));
            stopWatch.start();
            WebdriverWait("ACCESSIBILITYID", category,10);
            if (isElementDisplayed("ACCESSIBILITYID", category)) {
                stopWatch.stop();
                log.info("Time taken to display the form page after selecting a category : "+stopWatch.getTime(TimeUnit.SECONDS)+" Seconds");
                test.info( "<span style=\"color: Blue; font-weight: bold;\"> Time taken to display the form page after selecting a category : </span><span style=\"color: DodgerBlue;\">"+String.valueOf(stopWatch.getTime(TimeUnit.SECONDS)) + " Seconds"+ "</span>");
                stopWatch.reset();
                // Getting Formnames
                List<Object> formDatas = GetDataObject(FormMasterquery);
                log.info("FormMasterQuery------>"+FormMasterquery);
                formDatas.stream().filter(formData -> formData instanceof LinkedHashMap<?, ?>).map(formData -> (LinkedHashMap<?, ?>) formData).forEach(formData -> {
                    String formName = (String) formData.get("FormName");
                    String isQuestionForm = (String) formData.get("IsQuestionForm");
                    try {
                        processForm(category, formName, isQuestionForm);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                iscategoryExecutionSuccessful = true;
            }
        } else if (  Scroll("up")) {

            log.info("Page is Scroll");
            if (Source(category)) {
                click("Xpath", SetCategoryAttribute(category));
                log.info("Category is clicked");
                List<Object> formDatas = GetDataObject(FormMasterquery);
                formDatas.stream().filter(formData -> formData instanceof LinkedHashMap<?, ?>).map(formData -> (LinkedHashMap<?, ?>) formData).forEach(formData -> {
                    String formName = (String) formData.get("FormName");
                    String isQuestionForm = (String) formData.get("IsQuestionForm");
                    try {
                        processForm(category, formName, isQuestionForm);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            iscategoryExecutionSuccessful = true;
        }
        return iscategoryExecutionSuccessful;

    }

    public static boolean processForm(String category, String form, String IsQuestionForm) throws Exception {
        boolean isformExecutionSuccessful = false;

        if (Source(form)) {
            log.info("Form Name is : " + form);
            test.info("<span style=\"color: Blue; font-weight: bold;\">Form Name is : </span><span style=\"color: Black;\">" + form + "</span>");
            click("ACCESSIBILITYID", form);
            log.info(form+" is Clicked");
            String formName = form.replace(" ", "_");
            String productColumn = GetDatas(MessageFormat.format(ProductColumnquery, "'" + formName + "'"), "ProductColumn").get(0);
            log.info("Product Column query : " + ProductColumnquery);

            // Get Product Column List
            List<String> productNames = GetDatas(MessageFormat.format(Productquery, formName, tid, "'" + category + "'", productColumn), "ProductName");
            log.info("Product Query : "+Productquery);
            productNames.forEach(productName -> {
                try {
                    processProduct(formName, productName,IsQuestionForm);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
                log.info("Keyboard is hide");
            }
            if (Source("Next")) {
                click("Xpath", NextButton);
                log.info("Next Button is cliked");
            } else if (Source("Done")) {
                click("Xpath", Donebutton);
                log.info("Done Button is cliked");
            }
            isformExecutionSuccessful = true;
        }

        return isformExecutionSuccessful;
    }

    public static boolean processProduct(String formName, String productName, String IsQuestionForm) throws Exception {
        boolean isproductExecutionSuccessful = false;
        if (Source(productName)) {
            log.info("Product Name is : " + productName);
            test.info("<span style=\"color: Blue; font-weight: bold;\">Product Name is : </span><span style=\"color: Black;\">" + productName + "</span>");
            click("Xpath", SetCategoryAttribute(productName));
            enterFieldData(formName,IsQuestionForm);
            isproductExecutionSuccessful = true;
        }
        return isproductExecutionSuccessful;
    }

    private static boolean enterFieldData(String formName, String IsQuestionForm) throws Exception {
        boolean isfielddataExecutionSuccessful = true;
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
                        log.info("Fieldname is "+fieldName+"    |   "+"Data is  "+ dataset);
                        test.info( "<span style=\"color: Blue; font-weight: bold;\">FieldName is : </span><span style=\"color: Black;\">" + fieldName + "</span>"+"  | "+"<span style=\"color: Blue; font-weight: bold;\">Data is : </span><span style=\"color: Black;\">" + dataset + "</span>");
                        Enter("Xpath", attribute, dataset);
                        log.info("Test Data is Entered");
                    } else if (Ctrltype.equals("DropDownList")) {
                        Dropdownsetter();
                    } else if (Ctrltype.contains("FileUpload")) {
                        ImageCapture();
                    }
                }else {
                    isfielddataExecutionSuccessful = false;
                }

            }
        }
        return isfielddataExecutionSuccessful;
    }

    public static boolean Uploadfunction() {
        if (!isElementDisplayed("Xpath", UploadcallButton)) {
            return false;
        }
        click("Xpath", UploadcallButton);
        log.info("Upload Button is clicked");
        if (isElementDisplayed("Xpath", Perfectstorescorepopup)) {
            log.info("Perfect store popup is showing");
            click("ACCESSIBILITYID", Okbutton);
            log.info("Ok button is clicked");
        }
        if (isElementDisplayed("ACCESSIBILITYID", Uploadcallconfirmpopup)) {
            log.info("Upload call popup is showing");
            click("ACCESSIBILITYID", Yesbutton);
            log.info("Yes button is clicked");
        }
        if (Source( Attendancepopup)) {
            log.info("Attendance popup is showing");
            click("ACCESSIBILITYID", "Ok");
            if (isElementDisplayed("Xpath", Attendancdropdown)) {
                click("ACCESSIBILITYID", "present");
                log.info("Present is selected in Attendance");
                click("Xpath", Attendancecamera);
                log.info("Camera is opened");
                click("Xpath", Frontcamerabutton);
                log.info("Front camera is opened");
                click("ACCESSIBILITYID", Submit);
                log.info("Submit button is clikced");
                if (isElementDisplayed("ACCESSIBILITYID", Uploadcallconfirmpopup)) {
                    log.info(Uploadcallconfirmpopup +" is showing");
                    click("ACCESSIBILITYID", Yesbutton);
                    log.info("Yes button is clicked");
                    click("ACCESSIBILITYID", Uploadcallsbutton);
                    log.info("Uploadcalls button is clicked");
                }
            }
        }else{
            click("ACCESSIBILITYID", Uploadcallsbutton);
            log.info("Uploadcalls button is clicked");
        }
        driver.openNotifications();
        WebdriverWait("xpath", targetUploadnotification,10);
        if (isElementDisplayed("xpath", targetUploadnotification)) {
            driver.navigate().back();
            click("ACCESSIBILITYID", Callplan);
            log.info("Callplan button is clicked");
            click("Xpath", TargetID);
            log.info("Targetid id"+tid+" is clicked");
            if(Source("You have already uploaded "+tid+" target")){
                click("ACCESSIBILITYID", "Ok");
                log.info("You have already uploaded "+tid+" target is Showing");
                test.pass(MarkupHelper.createLabel("You have already uploaded  "+tid+"  target is Showing",ExtentColor.GREEN));
                return true;
            }
        }

        return false;
    }
}
