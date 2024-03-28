package Tests;

import Base.TestSetup;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Pages.CallPlan_page.*;
import static Pages.CallPlan_page.navigateToCallplanPage;
import static Pages.HomePage_page.Callplan;
import static Utilities.Actions.WebdriverWait;
import static Utilities.Actions.click;
import static Utilities.TestDataUtil.gettestdata;
import static Utilities.Utils.*;

/**
 * CallPlanTest class.
 */
public class CallPlanTest extends TestSetup {

    /**
     * This method is used to verify if the Call Plan module is displayed.
     * 
     * @throws Exception if an error occurs during the execution of the method
     */
    @Test(priority = 1, groups = {"smoke", "regression"},enabled = true)
    private static void TC_001_Verify_Call_Plan_Module_Displayed() throws Exception {
        navigateToCallplanPage();
        if(!sourceExists(Callplan)){
            logAndReportFailure("CallPlan module is not displayed");
            Assert.fail("CallPlan module is not displayed");
        }
    }


    /**
     * This method is used to verify the functionality of the Sync Button.
     * It performs the following steps:
     * 1. Calls the sync() method.
     * 2. Asserts that the source exists for the Callplan.
     *
     * @throws Exception if an error occurs during the execution of the test case.
     */
    @Test(priority = 2, groups = {"smoke", "regression"},enabled = true)
    private static void TC_002_Verify_Sync_Button_Functionality() throws Exception {
        sync();
        Assert.assertTrue(sourceExists(Callplan),"Sync Button is not working");
    }



    /**
     * Test case to verify the 5 Minute Sync Process.
     * 
     * @throws Exception if an error occurs during the test execution.
     */
    @Test(priority = 3, groups = {"smoke", "regression"},enabled = true)
    private static void TC_003_Verify_5_Minute_Sync_Process() throws Exception {
        JSONObject callplandata = gettestdata("Callplan", "5minutesSync");
        fiveminssync(callplandata.getString("expectedMessage"));
    }


    /**
     * This method is a test case for validating the error that occurs when starting a concurrent job.
     */
    @Test(priority = 4, groups = {"negative", "regression"},enabled = true)
    private static void TC_004_Concurrent_Job_Start_Error() throws Exception {
        navigateToCallplanPage();
        validate_Concurrent_Job_Start(todaycalls.get(0), todaycalls.get(1));
    }


    /**
     * This method is used to verify the upload button functionality without completing the form.
     * 
     * @throws Exception if an error occurs during the execution of the test case
     *
     */
    @Test(priority = 5, groups = {"negative", "regression"},enabled = true)
    private static void TC_005_Verify_Upload_Button_Without_Completing_Form() throws Exception {
        navigateToCallplanPage();
        formcompletingvalidation();
    }


    /**
     * Test case to verify that the mandatory fields error message is displayed.
     *
     * @throws Exception if an error occurs during the test execution.
     */
    @Test(priority = 6, groups = {"negative", "regression"},enabled = true)
    private static void TC_006_Verify_Mandatory_Fields_Error_Message_Displayed() throws Exception {

    }

    /**
     * Test case to verify that the mandatory images error message is displayed.
     *
     * @throws Exception if an error occurs during the test execution.
     */
    @Test(priority = 7, groups = {"negative", "regression"},enabled = true)
    private static void TC_007_Verify_Mandatory_Images_Error_Message_Displayed() throws Exception {

    }

    /**
     * Test case to verify the error messages for Industry and Brand facing fields.
     *
     * @throws Exception if an error occurs during the test execution.
     */
    @Test(priority = 8, groups = {"negative", "regression"},enabled = true)
    private static void TC_008_Verify_Industry_And_Brand_Facing_Fields_Error_Messages() throws Exception {

    }

    /**
     * Test case to verify the call upload functionality.
     *
     * @throws Exception if an error occurs during the test execution.
     */
    @Test(priority = 9, groups = {"smoke", "regression"},enabled = true)
    private static void TC_009_Verify_Call_Upload() throws Exception {

    }




}
