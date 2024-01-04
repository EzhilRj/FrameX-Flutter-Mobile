package Utilities;

import Pages.HomePage_page;

import static Base.AppiumTestSetup.driver;
import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Listeners.FrameX_Listeners.logAndReportSuccess;
import static Modules.Attendance_Module.attendanceimagerule;
import static Pages.Attendance_page.imgmandatorymsg;
import static Pages.HomePage_page.Attendance;
import static Utilities.Actions.click;
import static Utilities.Actions.isElementDisplayed;


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
            Thread.sleep(1000);
            click("ACCESSIBILITYID", Attendance);
            if(Source(confirmresponse)){
                logAndReportSuccess("Attendance Submitted successfully");
                return true;
            }else{
                driver.navigate().back();
                click("ACCESSIBILITYID", HomePage_page.Callplan);
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




}
