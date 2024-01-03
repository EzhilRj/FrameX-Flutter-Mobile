package Tests;

import Utilities.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Hashtable;

import static Modules.Attendance_Module.*;
import static Modules.Login_Module.checkVersion;

public class AttendanceTest {



    @Test(dataProviderClass = Utils.class, dataProvider = "Testdatas",priority = 3,description = "This  test case verifies the attendance images is mandatory or not for each types")
    public static void TC001_VerifyImagerequired(Hashtable<String, String> data, Method m) throws Exception {

        Utils.checkexecution(m.getName(),data);
        Assert.assertTrue(validateAttendanceImageRequired(data.get("Attendancetype")));

    }

    @Test(dataProviderClass = Utils.class, dataProvider = "Testdatas",priority = 4,description = "This  test case verifies the Attendance is submit or not")
    public static void TC002_VerifyAttendance(Hashtable<String, String> data, Method m) throws Exception {

        Utils.checkexecution(m.getName(),data);
        Assert.assertTrue(validateattendancesubmission(data.get("Attendancetype"),data.get("Image Required")));

    }

















}
