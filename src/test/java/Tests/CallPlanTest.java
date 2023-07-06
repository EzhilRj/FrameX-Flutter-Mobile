package Tests;

import Base.Setup;
import Utilities.Utils;
import org.testng.annotations.Test;

import static Modules.CallPlan_Module.*;
import static Utilities.DBConfig.Db;

public class CallPlanTest extends Setup {

    @Test(dependsOnMethods = "TC_001_VerifyLogin", retryAnalyzer = Utilities.RetryAnalyser.class)
    public static void TC_002_VerifyCallPlan() throws Exception {
        CallPlan();
    }


    @Test(dependsOnMethods = "TC_002_VerifyCallPlan", retryAnalyzer = Utilities.RetryAnalyser.class)
    public static void TC_003_VerifyCategoryLists() throws Exception {

        DataBinder();

    }



}
