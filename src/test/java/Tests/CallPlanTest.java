package Tests;

import Base.AppiumTestSetup;
import Utilities.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Hashtable;

import static Modules.Attendance_Module.validateAttendanceImageRequired;
import static Modules.Callplan_Module.validateUploadCall;

public class CallPlanTest extends AppiumTestSetup {

    @Test(dataProviderClass = Utils.class, dataProvider = "Testdatas",priority = 7,description = "This  test case verifies the Call Upload")
    public static void TC001_VerifyUploadcall(Hashtable<String, String> data, Method m) throws Exception {

        Utils.checkexecution(m.getName(),data);
        Assert.assertTrue(validateUploadCall(data.get("TargetID"),data.get("UploadType"),data.get("NetworkMode"),data.get("Duration")));

    }
}
