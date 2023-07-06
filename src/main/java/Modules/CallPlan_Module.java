package Modules;

import Base.Setup;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.Assert;

import javax.xml.datatype.Duration;
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
            stopWatch.start();
            driver.openNotifications();
            log.info("Notification is Open");
            WebdriverWait("xpath", notification,15);
            if (isElementDisplayed("xpath", notification)) {
                driver.navigate().back();
                stopWatch.stop();
                log.info(tid + " Downloaded successfully");
                test.info( tid+" Downloaded successfully");
                log.info("Time taken for Download call: " + stopWatch.getTime(TimeUnit.SECONDS)+ " Seconds");
                test.info(MarkupHelper.createLabel("  Time taken for Download call :  <b>" +  String.valueOf(stopWatch.getTime(TimeUnit.SECONDS)) + " Seconds"+ "</b>", ExtentColor.ORANGE));
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
                        click("Xpath", TargetID);
                        log.info("Targetid is clicked");
                        click("Xpath", Startworkbutton);
                        log.info("Startworkbutton is clicked");
                    }
                    if (isElementDisplayed("xpath", UploadcallButton)) {
                        stopWatch.stop();
                        log.info("Time required to download the Target data : "+stopWatch.getTime(TimeUnit.SECONDS)+" Seconds");
                        test.info(MarkupHelper.createLabel("  Time required to download the Target data : <b>" +  String.valueOf(stopWatch.getTime(TimeUnit.SECONDS)) + " Seconds"+ "</b>", ExtentColor.ORANGE));
                        stopWatch.reset();
                        log.info("Categorylist Page is showing");
                        test.pass(MarkupHelper.createLabel("Categorylist Page is showing",ExtentColor.GREEN));
                        Assert.assertTrue(true);
                    } else {
                        test.fail(MarkupHelper.createLabel("Categorylist Page is Not showing",ExtentColor.RED));
                        log.error("Categorylist Page is Not showing");
                        Assert.assertTrue(false);

                    }
                }
            }
        } catch (Exception e) {
            log.error("An exception occurred: " + e.getMessage());
            test.error(MarkupHelper.createLabel("  An exception occurred:  <b>" + e.getMessage() + "</b>", ExtentColor.RED));
        }
    }



    public static void DataBinder() throws Exception {

        log.info("--------------------------" + getCurrentMethodName() + " is started--------------------------------------");

        // Getting Category lists
        List<String> categoryNames = GetDatas(Categorymasterquery, "Name");
        log.info("Category Query -------->"+Categorymasterquery);
        categoryNames.forEach(category -> {
            try {
                processCategory(category);
            } catch (Exception e) {
                log.error("An exception occurred during processing the data  :  " + e.getMessage());
                test.error(MarkupHelper.createLabel("  An exception occurred during processing the Data :  <b>" + e.getMessage() + "</b>", ExtentColor.RED));
                try {
                    throw e;
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void processCategory(String category) throws Exception {

        if (Source(category)) {
            log.info("Category Name : " + category);
            test.log(Status.INFO, "<span style=\"color: Blue; font-weight: bold;\">Category Name is : </span><span style=\"color: Black;\">" + category + "</span>");
            click("Xpath", SetCategoryAttribute(category));
            stopWatch.start();
            WebdriverWait("ACCESSIBILITYID", category,10);
            if (isElementDisplayed("ACCESSIBILITYID", category)) {
                stopWatch.stop();
                log.info("Time taken to display the form page after selecting a category : "+stopWatch.getTime(TimeUnit.SECONDS)+" Seconds");
                test.info(MarkupHelper.createLabel(" Time taken to display the form page after selecting a category :  <b>" +  String.valueOf(stopWatch.getTime(TimeUnit.SECONDS)) + " Seconds"+ "</b>", ExtentColor.ORANGE));
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
        }else{
            click("Xpath",UploadcallButton);
            log.info("Upload Button is clicked");
            if(isElementDisplayed("Xpath",Perfectstorescorepopup)){
                click("ACCESSIBILITYID",Okbutton);
                if(isElementDisplayed("ACCESSIBILITYID",Uploadcallconfirmpopup)){
                    click("ACCESSIBILITYID",Yesbutton);
                    if(isElementDisplayed("ACCESSIBILITYID",Attendancepopup)){
                        click("ACCESSIBILITYID",Okbutton);
                        if(isElementDisplayed("Xpath",Attendancdropdown)){
                            click("ACCESSIBILITYID","present");
                            click("Xpath",Attendancecamera);
                            click("Xpath",Frontcamerabutton);
                            click("ACCESSIBILITYID",Submit);
                            if(isElementDisplayed("ACCESSIBILITYID",Uploadcallconfirmpopup)){
                                click("ACCESSIBILITYID",Yesbutton);
                                click("ACCESSIBILITYID",Uploadcallsbutton);
                            }
                        }
                    }
                }
            }

        }

    }


    public static void processForm(String category, String form, String IsQuestionForm) throws Exception {
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
        }
    }

    public static void processProduct(String formName, String productName, String IsQuestionForm) throws Exception {
        if (Source(productName)) {
            log.info("Product Name is : " + productName);
            test.info("<span style=\"color: Blue; font-weight: bold;\">Product Name is : </span><span style=\"color: Black;\">" + productName + "</span>");
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
                        log.info("Fieldname is "+fieldName+"    |   "+"Data is  "+ dataset);
                        test.info( "<span style=\"color: Blue; font-weight: bold;\">FieldName is : </span><span style=\"color: Black;\">" + fieldName + "</span>"+"  | "+"<span style=\"color: Blue; font-weight: bold;\">Data is : </span><span style=\"color: Black;\">" + dataset + "</span>");
                        Enter("Xpath", attribute, dataset);
                        log.info("Test Data is Entered");
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
