package Tests;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Modules.Attendance_Module.*;
import static Pages.Attendance_page.Submit;
import static Pages.Attendance_page.imgmandatorymsg;
import static Pages.HomePage_page.Attendance;
import static Pages.Login_Page.login;
import static Utilities.Actions.*;
import static Utilities.TestDataUtil.gettestdata;
import static Utilities.Utils.Source;

public class AttendanceTest {
    @Test//Positive
    public static void TC_001verifyAttendanceModuleIsDisplayed() throws InterruptedException {
        JSONObject user1 = gettestdata("Login","User1");
        login(user1.getString("username"), user1.getString("password"),user1.getString("project"),user1.getString("mobileno"));
        Thread.sleep(2000);
        if(!Source(Attendance)){
            logAndReportFailure("Attendance module is not displayed");
            Assert.fail("Attendance module is not displayed");
        }
    }

    @Test//Negative
    public static void TC_002VerifyAttendancewithoutcapturingimage() {
        JSONObject attendancedata = gettestdata("Attendance","Attendancewithoutcapturingimage");
        click("ACCESSIBILITYID", Attendance);
        click("ACCESSIBILITYID", "Present");
        click("ACCESSIBILITYID", attendancedata.getString("type"));
        click("ACCESSIBILITYID", Submit);
        if(!Source(attendancedata.getString("expectedErrorMessage"))){
            logAndReportFailure(imgmandatorymsg+" is not Displayed");
            Assert.fail("Image mandatory message is not displayed");
        }
    }


    //Functional
    @Test(dependsOnMethods = { "TC_001verifyAttendanceModuleIsDisplayed" })
    public void TC_003verifyAttendanceSubmission() throws InterruptedException {
        JSONObject attendancedata = gettestdata("Attendance","Attendance Submission");
        click("ACCESSIBILITYID", Attendance);
        if(!validateattendancesubmission(attendancedata.getString("type"),attendancedata.getString("Image"), attendancedata.getString("expectedMessage"))){
            logAndReportFailure("Attendance Submission Failed");
            Assert.fail("Attendance Submission Failed");
        }
    }


}
