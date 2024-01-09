package Modules;

import Base.AppiumTestSetup;
import Utilities.Actions;
import Utilities.ValidationManager;

import static Listeners.FrameX_Listeners.logAndinfo;
import static Pages.CallPlan_page.TargetID;
import static Pages.CallPlan_page.targetdownloadnotification;
import static Pages.HomePage_page.Callplan;
import static Utilities.Actions.*;
import static Utilities.Utils.datevisitedtime;
import static Utilities.ValidationManager.Source;
import static Utilities.ValidationManager.datevisitedvalidation;

public class Callplan_Module extends AppiumTestSetup {

    public static boolean Validateuploadcall(String targetid) throws InterruptedException {

        click("ACCESSIBILITYID",Callplan);
        if(!Source(targetid)){
            driver.navigate().back();
            Thread.sleep(3000);
            click("ACCESSIBILITYID",Callplan);
        }
        if(isElementDisplayed("Xpath",TargetID)){
            click("Xpath",TargetID);
            datevisitedvalidation();

        }
        return true;
    }




}
