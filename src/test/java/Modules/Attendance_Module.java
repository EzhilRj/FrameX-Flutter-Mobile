package Modules;

import Base.AppiumTestSetup;
import org.testng.Assert;

import java.util.HashMap;

import static Listeners.FrameX_Listeners.*;
import static Pages.Attendance_page.*;
import static Pages.HomePage_page.Attendance;
import static Pages.HomePage_page.Callplan;
import static Pages.Login_Page.menubutton;
import static Utilities.Actions.*;
import static Utilities.Utils.Assertion;
import static Utilities.Utils.Source;

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
            return attendancesubmittedvalidation(attendancetype,savedmsg,expected);

        } catch (Exception e) {
            logAndReportFailure("Exception occurred: " + e.getMessage());
            e.getMessage();
            return false;
        }
    }


     static boolean attendancesubmittedvalidation(String type , String response,String confirmresponse) throws InterruptedException {

        if (Source(response)){
            click("ACCESSIBILITYID", Attendance);
            log.info(response);
            if(Source(confirmresponse)){
                return true;
            }else{
                driver.navigate().back();
                click("ACCESSIBILITYID", Callplan);
                Thread.sleep(2000);
                driver.navigate().back();
                click("ACCESSIBILITYID", Attendance);
                Thread.sleep(700);
                if(Source(confirmresponse)) {
                    if(!Source("Submit")){
                        logAndReportSuccess("Attendance Submitted successfully");
                        return true;
                    }else{
                        logAndinfo("Attendance Submitted successfully But submit button is showing");
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static void performAttendanceActivity(String type , String img) throws InterruptedException {

        for (String key : attendancemessages().keySet()) {
            if(Source(key)) {
                click("ACCESSIBILITYID", key);
                break;
            }
        }
        click("ACCESSIBILITYID", type);
        if(img.equalsIgnoreCase("True")){
            click("xpath", Attendancecamera);
            WebdriverWait("xpath",shutterbutton,15);
            click("xpath", shutterbutton);
        }
        click("ACCESSIBILITYID", Submit);
    }

    public static void doattendance(String type,String image,String expectedmessage) throws InterruptedException {
        if(!validateattendancesubmission(type,image,expectedmessage)){
            logAndReportFailure("Attendance Submission Failed");
            Assert.fail("Attendance Submission Failed");
        }
    }

    public static void navigateToAttendancePage() {
        if (!Source("Attendance")) {
            click("Xpath", menubutton);
        }
        click("ACCESSIBILITYID", Attendance);
    }

    public static void validateimgrequired(String type,String errmsg){

        if(Source("Attendance")){
            click("ACCESSIBILITYID", Attendance);
            if(Source("Your attendance Marked for today")){
                logAndReportFailure("Attendance is already Marked for this user Submit button is not showing");
                Assert.fail("Attendance is already Marked ");
            }
            click("ACCESSIBILITYID", "Present");
            click("ACCESSIBILITYID", type);
            click("ACCESSIBILITYID", Submit);
            Assertion(errmsg,"Please, Take Photo for submit attendance is not displayed");
        }else{
            Assert.fail("Attendance Module is Not showing");
        }
    }

}
