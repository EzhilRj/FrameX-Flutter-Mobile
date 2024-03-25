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
    private  void TC_001_Verify_Attendance_Module_Displayed() throws InterruptedException {
        applogin(Attendance);
        if(!sourceExists(Attendance)){
            logAndReportFailure("Attendance module is not displayed");
            Assert.fail("Attendance module is not displayed");
        }
    }

    @Test( priority = 2,groups = {"negative", "regression"},enabled = true)
    private void TC_002_Verify_Attendance_Without_Capturing_Image() throws InterruptedException {
        JSONObject attendancedata = gettestdata("Attendance","Attendancewithoutcapturingimage");
        applogin(Attendance);
        validateimgrequired(attendancedata.getString("type"),attendancedata.getString("expectedErrorMessage"));
    }


    @Test(priority = 3,dependsOnMethods = { "TC_001verifyAttendanceModuleIsDisplayed" },groups = {"smoke", "regression"},enabled = true)
    public void TC_003_Verify_Attendance_Submission() throws InterruptedException {
        JSONObject attendanceData = gettestdata("Attendance", "Attendance Submission");
        if (sourceExists("Submit") || (sourceExists("Attendance") && sourceExists("Submit"))) {
            doattendance(attendanceData.getString("type"), attendanceData.getString("Image"), attendanceData.getString("expectedMessage"));
        } else {
            navigateToAttendancePage();
            doattendance(attendanceData.getString("type"), attendanceData.getString("Image"), attendanceData.getString("expectedMessage"));
        }
    }

    @Test(priority = 4,dependsOnMethods = { "TC_003verifyAttendanceSubmission" },groups = {"smoke", "regression"},enabled = true)
    public void TC_004_Verify_Attendance_Recorded_In_Database() throws Exception {

        verifyattendancedatainDB();
    }


}
