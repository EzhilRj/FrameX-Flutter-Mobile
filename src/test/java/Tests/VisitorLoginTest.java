package Tests;

import Utilities.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Hashtable;

import static Modules.Resourcentre_Module.validateFiles;

public class VisitorLoginTest {


    @Test(dataProviderClass = Utils.class, dataProvider = "Testdatas",priority = 5,description = "This  test case verifies the all type of extension files is downloaded or not .")
    public static void TC001_VerifyVistorlogin(Hashtable<String, String> data, Method m) throws Exception {

        Utils.checkexecution(m.getName(),data);
        Assert.assertTrue(validateFiles(data.get("Filename")));

    }



}
