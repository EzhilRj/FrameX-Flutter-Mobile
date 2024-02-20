package Utilities;

import Modules.Callplan_Module;
import Pages.CallPlan_page;
import Pages.HomePage_page;

import static Base.AppiumTestSetup.driver;
import static Base.AppiumTestSetup.log;
import static Listeners.FrameX_Listeners.*;
import static Listeners.FrameX_Listeners.logAndinfo;
import static Modules.Attendance_Module.attendanceimagerule;
import static Modules.Callplan_Module.targetid;
import static Pages.Attendance_page.imgmandatorymsg;
import static Pages.CallPlan_page.uploadcallerr_msg;
import static Pages.HomePage_page.*;
import static Utilities.Actions.*;
import static Utilities.Utils.datevisitedtime;


public class ValidationManager {

    public static boolean Source(String value) {

        return driver.getPageSource().contains(value);
    }

    //Login page Validations and Verifications================================================================
    public static boolean hasErrorMessage(String errorMessage) {
        try {
            return Source(errorMessage);
        } catch (Exception e) {
            logAndReportFailure("Error while checking for error message: " + e.getMessage());
            return false;
        }
    }

    public static boolean isLoggedIn(String successMessage) {
        try {
            return Source(successMessage);
        } catch (Exception e) {
            logAndReportFailure("Error while checking login status: " + e.getMessage());
            return false;
        }
    }

    public static boolean areFieldsRequired(String... errorMessages) {
        try {
            for (String errorMessage : errorMessages) {
                if (hasErrorMessage(errorMessage)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logAndReportFailure("Error while checking field requirements: " + e.getMessage());
            return false;
        }
    }

    //Attendance Validations and Verifications================================================================

    public static boolean attendancesubmittedvalidation(String type , String response,String confirmresponse) throws InterruptedException {

        if(Source(imgmandatorymsg)){
            logAndReportFailure("Negative data is given : Attendance submission failed , Image is mandatory for "+type);
            return false;
        } else if (Source(response)){
            click("ACCESSIBILITYID", Attendance);
            if(Source(confirmresponse)){
                logAndReportSuccess("Attendance Submitted successfully");
                return true;
            }else{
                driver.navigate().back();
                click("ACCESSIBILITYID", Callplan);
                Thread.sleep(1500);
                driver.navigate().back();
                click("ACCESSIBILITYID", Attendance);
                if(Source(confirmresponse)) {
                    logAndReportSuccess("Attendance Submitted successfully");
                    return true;
                } else {
                    logAndReportFailure("Attendance Submission Failed");
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean attendanceimagerequiredvalidation(String type){

        if (type.equalsIgnoreCase("Present") || type.equalsIgnoreCase("At office") || type.equalsIgnoreCase("Training") || type.equalsIgnoreCase("Monthly Meeting")) {
            click("ACCESSIBILITYID", type);
            if (isElementDisplayed("xpath", attendanceimagerule.get(type))) {
                click("ACCESSIBILITYID", type);
                logAndReportSuccess("Image is Required for " + type);
                if(type.equalsIgnoreCase("Monthly Meeting")){
                    driver.navigate().back();
                }
                return true;
            } else {
                logAndReportFailure("Image is Required for " + type + " but camera option is not displayed");
                return false;
            }
        } else {
            click("ACCESSIBILITYID", type);
            if (isElementDisplayed("ACCESSIBILITYID", attendanceimagerule.get(type))) {
                click("ACCESSIBILITYID", type);
                logAndReportSuccess("Image is Not Required for " + type);
                return true;
            } else {
                logAndReportFailure("Image is Not Required for " + type + " but camera option is displayed");
                return false;
            }
        }
    }

    //CallPlan Validations and Verifications================================================================

    public static String datevisitedvalidation() throws InterruptedException {
        String devicetime = datevisitedtime();
        if(Source(devicetime)){
            log.info("Target start time  :  "+devicetime);
            log.info("Visited date and time is Showing");
        }else{
            logAndReportFailure("Visited date and time is not Showing");
        }
        return devicetime;
    }

    public static boolean calluploadvalidation(String netmode, String successMsg) throws InterruptedException {
        String targetXPath = "//android.widget.ImageView[contains(@content-desc, 'Target ID: " + targetid + "')]";

        if (!netmode.equalsIgnoreCase("Enable")) {
            handleNoInternetConnection();
        }

        clickAndWait("ACCESSIBILITYID", ActivityLog, 15);
        click("ACCESSIBILITYID", ActivityLog);

        if (!Source(successMsg)) {
            retryUploadProcess();
        }

        if (Source(successMsg)) {
            return handleUploadSuccess(targetXPath);
        }

        return false;
    }

    private static void handleNoInternetConnection() throws InterruptedException {
        if (Source("Please, check internet connection")) {
            click("ACCESSIBILITYID", "Ok");
        }

        Thread.sleep(4000);
        click("ACCESSIBILITYID", Callplan);
        click("ACCESSIBILITYID", CallPlan_page.sync);

        if (Source("Please, check internet connection")) {
            click("ACCESSIBILITYID", "Ok");
        }

        Thread.sleep(3000);
        click("ACCESSIBILITYID", ActivityLog);
    }

    private static void clickAndWait(String locatorType, String locator, int waitTime) {
        WebdriverWait(locatorType, locator, waitTime);
        click(locatorType, locator);
    }

    private static void retryUploadProcess() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            if (!Source("Starting the Upload process for Target . " + targetid)) {
                driver.navigate().back();
                Thread.sleep(3000);
                clickAndWait("ACCESSIBILITYID", ActivityLog, 15);
                continue;
            } else {
                break;
            }
        }

        driver.navigate().back();
        Thread.sleep(5000);
        clickAndWait("ACCESSIBILITYID", ActivityLog, 15);
    }

    private static boolean handleUploadSuccess(String targetXPath) {
        driver.navigate().back();
        clickAndWait("ACCESSIBILITYID", Callplan, 15);
        click("xpath", targetXPath);

        if (Source("You have already uploaded " + targetid + " target")) {
            click("ACCESSIBILITYID", "Ok");
            return true;
        }

        return false;
    }

}
