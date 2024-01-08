package Tests;

import Base.AppiumTestSetup;
import Utilities.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.text.Utilities;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

import static Modules.Login_Module.*;


public class LoginTest extends AppiumTestSetup {

    @Test(dataProviderClass = Utils.class, dataProvider = "Testdatas",priority = 1,description = "This  test case verifies the login functionality of the application using the provided test data.",retryAnalyzer = Tests.RetryAnalyser .class)
    public static void TC001_VerifyLogin(Hashtable<String, String> data, Method m) throws Exception {

        Utils.checkexecution(m.getName(),data);
        Assert.assertTrue(loginToApp(data.get("Username"), data.get("Password"),data.get("Project"),data.get("Mobilenumber")));

    }

}
