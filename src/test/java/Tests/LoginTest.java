package Tests;

import Base.Setup;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static Modules.Login_Module.LoginintoApp;
import static Utilities.DBConfig.Logindata;
import static Utilities.XLUtils.getJsonData;

public class LoginTest extends Setup {

    @Test
    public static void TC_001_VerifyLogin() throws Exception {

        for(Map<String, String> Login   : Logindata("Select * from Login") ){

            Assert.assertTrue(LoginintoApp(Login.get("Username"),Login.get("Password"),Login.get("Projectname"),Login.get("Mobilenumber"),Login.get("ExpectedResult")));
        }



    }

}
