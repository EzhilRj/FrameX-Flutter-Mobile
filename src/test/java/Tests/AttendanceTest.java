package Tests;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Modules.Attendance_Module.*;
import static Pages.Attendance_page.*;
import static Pages.HomePage_page.Attendance;
import static Pages.Login_Page.login;
import static Utilities.Actions.*;
import static Utilities.TestDataUtil.gettestdata;
import static Utilities.Utils.*;

public class AttendanceTest {
    @Test//Positive and Smoke test
    public static void TC_001verifyAttendanceModuleIsDisplayed() throws InterruptedException {
        applogin();
        if(!Source(Attendance)){
            logAndReportFailure("Attendance module is not displayed");
            Assert.fail("Attendance module is not displayed");
        }
    }

    @Test//Negative
    public static void TC_002VerifyAttendancewithoutcapturingimage() throws InterruptedException {
        JSONObject attendancedata = gettestdata("Attendance","Attendancewithoutcapturingimage");
        applogin();
        click("ACCESSIBILITYID", Attendance);
        click("ACCESSIBILITYID", "Present");
        click("ACCESSIBILITYID", attendancedata.getString("type"));
        click("ACCESSIBILITYID", Submit);
        WebdriverWait("ACCESSIBILITYID","Please, Take Photo for submit attandance.",10);
        Assertion(attendancedata.getString("expectedErrorMessage"),"Please, Take Photo for submit attendance is not displayed");

    }

    //Functional
    @Test(dependsOnMethods = { "TC_001verifyAttendanceModuleIsDisplayed" })
    public void TC_003verifyAttendanceSubmission() throws InterruptedException {
        applogin();
        JSONObject attendancedata = gettestdata("Attendance","Attendance Submission");
        click("ACCESSIBILITYID", Attendance);
        if(!validateattendancesubmission(attendancedata.getString("type"),attendancedata.getString("Image"), attendancedata.getString("expectedMessage"))){
            logAndReportFailure("Attendance Submission Failed");
            Assert.fail("Attendance Submission Failed");
        }
    }


}
