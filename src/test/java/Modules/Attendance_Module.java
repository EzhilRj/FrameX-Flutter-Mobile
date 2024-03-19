package Modules;

import Base.AppiumTestSetup;

import java.util.HashMap;

import static Listeners.FrameX_Listeners.*;
import static Pages.Attendance_page.*;
import static Pages.HomePage_page.Attendance;
import static Pages.HomePage_page.Callplan;
import static Utilities.Actions.*;
import static Utilities.Utils.Source;
import static Utilities.Utils.gohomepage;

public class Attendance_Module extends AppiumTestSetup {

    public static HashMap<String,String>attendanceimagerule =  attendanceimagevalidation();

    public static boolean validateattendancesubmission(String attendancetype,String image,String expected) throws InterruptedException {

        HashMap<String,String>attendancesuccessmessages =  attendancemessages();
        String savedmsg = "";

        try {
            if(Source("Your attendance Marked for today")){
                logAndReportFailure("Attendance is already Marked for this user");
                return false;
            }
            performAttendanceActivity(attendancetype,image);
            for (String key : attendancesuccessmessages.keySet()) {
                if(key.equalsIgnoreCase(attendancetype)){
                    savedmsg =  attendancesuccessmessages.get(key);
                }
            }
            WebdriverWait("ACCESSIBILITYID",savedmsg,15);
            // Verifying attendance submission success/failure
            return attendancesubmittedvalidation(attendancetype,savedmsg,expected+attendancetype);

        } catch (Exception e) {
            logAndReportFailure("Exception occurred: " + e.getMessage());
            e.getMessage();
            return false;
        }
    }


     static boolean attendancesubmittedvalidation(String type , String response,String confirmresponse) throws InterruptedException {

        if (Source(response)){
            click("ACCESSIBILITYID", Attendance);
            if(Source(confirmresponse)){
                return true;
            }else{
                driver.navigate().back();
                click("ACCESSIBILITYID", Callplan);
                Thread.sleep(1500);
                driver.navigate().back();
                click("ACCESSIBILITYID", Attendance);
                if(Source(confirmresponse)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    // Method to validate image requirement for different attendance types
   /* public static boolean validateAttendanceImageRequired(String attendancetype)  {
        try {
            if (!Source(attendancetype)) {
                click("ACCESSIBILITYID", Attendance);
                click("ACCESSIBILITYID", attendancetype);
            }
            return attendanceimagerequiredvalidation(attendancetype);

        }catch (Exception e) {
            logAndReportFailure("Exception occurred: " + e.getMessage());
            e.getMessage();
            return false;
        }
    }

     static boolean attendanceimagerequiredvalidation(String type){

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
    }*/
}
