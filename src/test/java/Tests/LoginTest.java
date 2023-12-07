package Tests;

import Base.AppiumTestSetup;
import Utilities.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

import static Modules.Login_Module.*;
import static Utilities.DBConfig.testDatas;


public class LoginTest extends AppiumTestSetup {

    @Test(dataProviderClass = Utils.class, dataProvider = "Testdatas",priority = 1,description = "This  test case verifies the login functionality of the application using the provided test data.")
    public static void TC_001_VerifyLogin(Hashtable<String, String> data, Method m) throws Exception {

        Utils.checkexecution(m.getName(),data);
        Assert.assertTrue(Login_in_App(data.get("Username"), data.get("Password"),data.get("Project"),data.get("Mobilenumber")));

    }

    @Test(dataProviderClass = Utils.class, dataProvider = "Testdatas",priority = 2,description = "This  test case verifies the version of the application.")
    public static void TC_002_Verify_AppVersion(Hashtable<String, String> data, Method m) throws Exception {

        Utils.checkexecution(m.getName(),data);
        Assert.assertTrue(checkVersion(data.get("Version")));


    }

}
