package Tests;

import Utilities.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Hashtable;

import static Modules.Downloadcalls_Module.validatedownloadcalls;
import static Modules.Resourcentre_Module.validateFiles;

public class ResourceCentreTest {

    @Test(dataProviderClass = Utils.class, dataProvider = "Testdatas",priority = 5,description = "This  test case verifies the all type of extension files is downloaded or not .",retryAnalyzer = Tests.RetryAnalyser .class)
    public static void TC001_VerifyResourcecentrefiles(Hashtable<String, String> data, Method m) throws Exception {

        Utils.checkexecution(m.getName(),data);
        Assert.assertTrue(validateFiles(data.get("Filename")));

    }


}
