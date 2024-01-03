package Modules;

import Base.AppiumTestSetup;
import Pages.Attendance_page;
import Pages.HomePage_page;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static Listeners.FrameX_Listeners.*;
import static Pages.Attendance_page.*;
import static Pages.HomePage_page.Attendance;
import static Utilities.Actions.*;
import static Utilities.Utils.generatedateandtime;
import static Utilities.Utils.gohomepage;
import static Utilities.ValidationManager.*;

public class Attendance_Module extends AppiumTestSetup {

    public static HashMap<String,String>attendanceimagerule =  attendanceimagevalidation();
    public static HashMap<String,String>attendancesuccessmessages =  attendancemessages();

    public static boolean validateattendancesubmission(String attendancetype,String image) throws InterruptedException {

        String Attendacemarkedmessage = "Your attendance Marked for today as "+attendancetype;
        String savedmsg = "";

        try {
            gohomepage(Attendance);
            click("ACCESSIBILITYID", Attendance);
            performAttendanceActivity(attendancetype,image);
            for (String key : attendancesuccessmessages.keySet()) {
                if(key.equalsIgnoreCase(attendancetype)){
                    savedmsg =  attendancesuccessmessages.get(key);
                }
            }
            // Verifying attendance submission success/failure
            WebdriverWait("ACCESSIBILITYID",savedmsg,10);
            if(attendancesubmittedvalidation(attendancetype,savedmsg,Attendacemarkedmessage)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            logAndReportFailure("Exception occurred: " + e.getMessage());
            e.getMessage();
            return false;
        }
    }



    // Method to validate image requirement for different attendance types
    public static boolean validateAttendanceImageRequired(String attendancetype) throws InterruptedException {
        try {
            if (!Source(attendancetype)) {
                click("ACCESSIBILITYID", Attendance);
                click("ACCESSIBILITYID", attendancetype);
            }
            if(attendanceimagerequiredvalidation(attendancetype)){
                return true;
            }else{
                return false;
            }
        }catch (Exception e) {
            logAndReportFailure("Exception occurred: " + e.getMessage());
            e.getMessage();
            return false;
        }
    }
}
