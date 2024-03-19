package Tests;

import Utilities.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import Modules.VisitorLogin_Module;

import java.lang.reflect.Method;
import java.util.Hashtable;

public class VisitorLoginTest {

    @Test(dataProviderClass = Utils.class, dataProvider = "Testdatas",priority = 6,retryAnalyzer = Tests.RetryAnalyser .class)
    public static void TC001_VerifyVistorlogin(Hashtable<String, String> data, Method m) throws Exception {

        Utils.checkexecution(m.getName(),data);
        Assert.assertTrue(VisitorLogin_Module.validatevisitorsubmission(data.get("Visitor"),data.get("Visitor type"),data.get("VisitorEmpid")
                ,data.get("VisitorName"), data.get("Designation"),data.get("Remarks"),data.get("Storeinfo"),data.get("Promotergroomed"),
                data.get("Maintainedcategories"),data.get("Aware about all the targets"),data.get("Selfie")));

    }

}