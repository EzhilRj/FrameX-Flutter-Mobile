package Modules;

import Base.TestSetup;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Listeners.FrameX_Listeners.*;
import static Pages.Attendance_page.*;
import static Pages.HomePage_page.Attendance;
import static Pages.HomePage_page.Callplan;
import static Pages.Login_Page.menubutton;
import static Utilities.Actions.*;
import static Utilities.DBConfig.executeQuery;
import static Utilities.TestDataUtil.gettestdata;
import static Utilities.Utils.*;

public class Attendance_Module extends TestSetup {

    public static String savedmsg;

    public static boolean validateattendancesubmission(String attendancetype,String image,String expected) throws InterruptedException {

        HashMap<String,String>attendancesuccessmessages =  attendancemessages();
        savedmsg = "";

        try {
            if(sourceExists("Your attendance Marked for today")){
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

        if (sourceExists(response)){
            click("ACCESSIBILITYID", Attendance);
            log.info(response);
            if(sourceExists(confirmresponse)){
                return true;
            }else{
                driver.navigate().back();
                click("ACCESSIBILITYID", Callplan);
                Thread.sleep(2000);
                driver.navigate().back();
                click("ACCESSIBILITYID", Attendance);
                Thread.sleep(700);
                if(sourceExists(confirmresponse)) {
                    if(!sourceExists("Submit")){
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
            if(sourceExists(key)) {
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
        if (!sourceExists("Attendance")) {
            click("Xpath", menubutton);
        }
        click("ACCESSIBILITYID", Attendance);
    }

    public static void validateimgrequired(String type,String errmsg){

        if(sourceExists("Attendance")){
            click("ACCESSIBILITYID", Attendance);
            if(sourceExists("Your attendance Marked for today")){
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

    public static String getstatusandtime(String username, String columnName) throws Exception {
        String todaydate = generateFormattedDate("yyyy-MM-dd");
        List<Map<String, String>> result = executeQuery("select username, status, createddate from Attendancedetail where username = '"+username+"' and date = '"+todaydate+"'");
        if (!result.isEmpty()) {
            Map<String, String> firstRow = result.get(0); // Assuming there's only one row
            return firstRow.get(columnName);
        }
        return null;
    }

    public static void verifyattendancedatainDB() throws Exception {

        JSONObject user1 = gettestdata("Login","User1");
        String attendancetime  = savedmsg;
        String username = user1.getString("username");
        String attendancestatus  = getstatusandtime(username,"status");
        String createdTime = getstatusandtime(username,"createddate").split(" ")[1].substring(0, 5);

        if(attendancestatus.equals("P")&&attendancetime.contains(createdTime)){
            Assert.assertTrue(true);
            logAndReportSuccess("Attendance datas is matched in Database");
        }else{
            Assert.fail("Attendance data is Mismatch in Database");
            logAndinfo("Attendance time : " + attendancetime);
            logAndinfo("Time fetched in Database : " + createdTime);
            logAndReportFailure("Attendance data is Mismatch in Database");

        }

    }

}
