package Tests;

import Base.Setup;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static Modules.Login_Module.Login_in_App;
import static Utilities.DBConfig.testDatas;


public class LoginTest extends Setup {

    @Test
    public static void TC_001_VerifyLogin() throws Exception {

        for(Map<String, String> Login   : testDatas("Select * from Login") ){

            Assert.assertTrue(Login_in_App(Login.get("Username"),Login.get("Password"),Login.get("Projectname"),Login.get("Mobilenumber"),Login.get("ExpectedResult")));
        }



    }

}
