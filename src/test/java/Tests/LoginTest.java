package Tests;

import Base.Setup;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Modules.Login_Module.LoginintoApp;
import static Utilities.XLUtils.getJsonData;

public class LoginTest extends Setup {

    @Test
    public static void TC_001_VerifyLogin() throws Exception {

        Assert.assertTrue(LoginintoApp("akamali","akamali","framedemo_d1","8248606917"));

    }

}
