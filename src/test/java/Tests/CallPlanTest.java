package Tests;

import Base.AppiumTestSetup;
import Utilities.Utils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;

import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Modules.Callplan_Module.*;
import static Pages.CallPlan_page.*;
import static Pages.HomePage_page.Attendance;
import static Pages.HomePage_page.Callplan;
import static Utilities.Actions.click;
import static Utilities.Actions.isElementDisplayed;
import static Utilities.TestDataUtil.gettestdata;
import static Utilities.Utils.*;

public class CallPlanTest extends AppiumTestSetup {

    @Test(priority = 1, groups = {"smoke", "regression"},enabled = true)
    public static void TC_001_Verify_Call_Plan_Module_Displayed() throws Exception {
        applogin(Callplan);
        if(!sourceExists(Callplan)){
            logAndReportFailure("CallPlan module is not displayed");
            Assert.fail("CallPlan module is not displayed");
        }
    }

    @Test(priority = 2, groups = {"smoke", "regression"},enabled = true)
    public static void TC_002_Verify_Sync_Button_Functionality() throws Exception {
        sync();
        Assert.assertTrue(sourceExists(Callplan),"Sync Button is not working");
    }

    @Test(priority = 3, groups = {"smoke", "regression"},enabled = true)
    public static void TC_003_Verify_5_Minute_Sync_Process() throws Exception {
        JSONObject callplandata = gettestdata("Callplan", "5minutesSync");
        fiveminssync(callplandata.getString("expectedMessage"));
    }

    @Test(priority = 4, groups = {"smoke", "regression"},enabled = true)
    public static void TC_004_Concurrent_Job_Start_Error() throws Exception {
        JSONObject user1 = gettestdata("Login","User1");
        List<String>Targets =  gettargetsfrom_db(user1.getString("username"));
        validate_Concurrent_Job_Start(Targets.get(0), Targets.get(1));
    }

    @Test(priority = 5, groups = {"smoke", "regression"},enabled = true)
    public static void TC_005_Verify_Upload_Button_Without_Completing_Form() throws Exception {

        JSONObject user1 = gettestdata("Login", "User1");
        List<String>Targets =  gettargetsfrom_db(user1.getString("username"));
        if(!sourceExists(sync)){
            click("ACCESSIBILITYID",Callplan);
        }
        click("xpath",gettargetxpath(Targets.get(0)));
        click("Xpath", Startworkbutton);
        click("ACCESSIBILITYID",UploadcallButton);
        if(sourceExists(uploadcall_errmsg)){
            Assert.assertTrue(true);
        }else{
            logAndReportFailure("First fill all categories data to upload is Not Displayed");
            Assert.assertTrue(false);
        }
    }

    @Test//Negative
    public static void TC_006_Verify_Mandatory_Fields_Error_Message_Displayed() throws Exception {

    }

    @Test//Negative
    public static void TC_007_Verify_Mandatory_Images_Error_Message_Displayed() throws Exception {

    }

    @Test//Negative
    public static void TC_008_Verify_Industry_And_Brand_Facing_Fields_Error_Messages() throws Exception {

    }

    @Test//Negative
    public static void TC_009_Verify_Call_Upload() throws Exception {

    }




}
