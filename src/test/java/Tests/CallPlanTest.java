package Tests;

import Base.Setup;
import org.testng.annotations.Test;

import static Modules.CallPlan_Module.*;
import static Utilities.DBConfig.Db;

public class CallPlanTest extends Setup {

    @Test
    public static void TC_002_VerifyCallPlan() throws Exception {

        CallPlan();
    }

    @Test
    public static void TC_003_VerifyCategoryLists() throws Exception {

        Category_Lists("SKIN CARE");
    }



}
