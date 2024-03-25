package Tests;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Modules.Attendance_Module.*;
import static Pages.Attendance_page.*;
import static Pages.HomePage_page.Attendance;
import static Pages.Login_Page.menubutton;
import static Utilities.Actions.*;
import static Utilities.TestDataUtil.gettestdata;
import static Utilities.Utils.*;

public class AttendanceTest {

    @Test(priority = 1, groups = {"smoke", "regression"},enabled = true)
    private  void TC_001verifyAttendanceModuleIsDisplayed() throws InterruptedException {
        applogin();
        if(!Source(Attendance)){
            logAndReportFailure("Attendance module is not displayed");
            Assert.fail("Attendance module is not displayed");
        }
    }

    @Test( priority = 2,groups = {"negative", "regression"},enabled = true)
    private void TC_002VerifyAttendancewithoutcapturingimage() throws InterruptedException {
        JSONObject attendancedata = gettestdata("Attendance","Attendancewithoutcapturingimage");
        applogin();
        validateimgrequired(attendancedata.getString("type"),attendancedata.getString("expectedErrorMessage"));
    }


    @Test(priority = 3,dependsOnMethods = { "TC_001verifyAttendanceModuleIsDisplayed" },groups = {"smoke", "regression"},enabled = true)
    public void TC_003verifyAttendanceSubmission() throws InterruptedException {
        JSONObject attendanceData = gettestdata("Attendance", "Attendance Submission");
        if (Source("Submit") || (Source("Attendance") && Source("Submit"))) {
            doattendance(attendanceData.getString("type"), attendanceData.getString("Image"), attendanceData.getString("expectedMessage"));
        } else {
            navigateToAttendancePage();
            doattendance(attendanceData.getString("type"), attendanceData.getString("Image"), attendanceData.getString("expectedMessage"));
        }
    }

    @Test(priority = 4,dependsOnMethods = { "TC_003verifyAttendanceSubmission" },groups = {"smoke", "regression"},enabled = true)
    public void TC_004verifyAttendanceinDatabase() throws Exception {

        verifyattendancedatainDB();
    }


}
