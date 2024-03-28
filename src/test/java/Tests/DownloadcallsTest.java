package Tests;

import Utilities.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Hashtable;

import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Modules.Downloadcalls_Module.navigateToDownloadcallsPage;
import static Modules.Downloadcalls_Module.validatedownloadcalls;
import static Pages.HomePage_page.Attendance;
import static Pages.HomePage_page.DownloadCalls;
import static Utilities.Utils.navigateto;
import static Utilities.Utils.sourceExists;

public class DownloadcallsTest {

    @Test(priority = 1, groups = {"smoke", "regression"},enabled = true)
    private  void TC_001_Verify_DownloadCallsModuleIsDisplayed() throws InterruptedException {
        navigateto(DownloadCalls);
        if(!sourceExists(DownloadCalls)){
            logAndReportFailure("DownloadCalls module is not displayed");
            Assert.fail("DownloadCalls module is not displayed");
        }
    }

    @Test(priority = 2, groups = {"smoke", "regression"},enabled = true)
    private  void TC_002_Verify_SingleCallIsDownloaded() throws InterruptedException {

        navigateToDownloadcallsPage();




    }

    @Test(priority = 3, groups = {"smoke", "regression"},enabled = true)
    private  void TC_003_Verify_MultipleCallsAreDownloaded() throws InterruptedException {

    }

    @Test(priority = 4, groups = {"smoke", "regression"},enabled = true)
    private  void TC_004_Verify_DuplicateCallIsDownloaded() throws InterruptedException {

    }

    @Test(priority = 5, groups = {"smoke", "regression"},enabled = true)
    private  void TC_005_Verify_InvalidTargetIsDownloaded() throws InterruptedException {

    }


}


