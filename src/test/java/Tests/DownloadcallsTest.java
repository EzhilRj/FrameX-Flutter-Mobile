package Tests;

import Utilities.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Hashtable;

import static Modules.Downloadcalls_Module.validatedownloadcalls;

public class DownloadcallsTest {

    @Test(dataProviderClass = Utils.class, dataProvider = "Testdatas",priority = 4,description = "This  test case verifies the calls is downloaded or not in download calls",retryAnalyzer = Tests.RetryAnalyser .class)
    public static void TC001_VerifyDownloadcalls(Hashtable<String, String> data, Method m) throws Exception {

        Utils.checkexecution(m.getName(),data);
        Assert.assertTrue(validatedownloadcalls(data.get("TargetId")));

    }

}
