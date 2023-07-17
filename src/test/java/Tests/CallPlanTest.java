package Tests;

import Base.Setup;
import Utilities.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;
import static Modules.CallPlan_Module.*;

public class CallPlanTest extends Setup {

    @Test(dependsOnMethods = "TC_001_VerifyLogin")
    public static void TC_002_VerifyCallPlan() throws Exception {
        CallPlan();
    }


    @Test(dependsOnMethods = "TC_002_VerifyCallPlan")
    public static void TC_003_VerifyCategoryLists() throws Exception {

        Assert.assertTrue( DataBinder());

    }

    @Test(dependsOnMethods = "TC_003_VerifyCategoryLists")
    public static void TC_004_Verify_UploadCalls() throws Exception {

        Assert.assertTrue(Uploadfunction());

    }

}
