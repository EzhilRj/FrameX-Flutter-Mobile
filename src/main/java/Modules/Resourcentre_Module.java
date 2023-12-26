package Modules;

import Base.AppiumTestSetup;

import java.util.Set;

import static Listeners.FrameX_Listeners.formatData;
import static Listeners.FrameX_Listeners.testReport;
import static Pages.HomePage_page.ResourceCentre;
import static Utilities.Actions.*;
import static Utilities.Utils.gohomepage;

public class Resourcentre_Module extends AppiumTestSetup {
	
    public static boolean validateFiles(String filename) throws InterruptedException {
        try {
            gohomepage(ResourceCentre);
            if(!Source( filename)){
                click("ACCESSIBILITYID", ResourceCentre);
            }
            if (isElementDisplayed("ACCESSIBILITYID", filename)) {
                click("ACCESSIBILITYID", filename);
                Thread.sleep(3500);
                Set<String> contextHandles = driver.getContextHandles();
                for (String context : contextHandles) {
                    if (context.contains("WEBVIEW")) {
                        if (filename.contains("jpg") || filename.contains("mp4")) {
                            testReport.get().pass(formatData(filename + " file downloaded Successfully"));
                            log.info(filename + " File downloaded Successfully");
                            return true;
                        } else if (Source(filename)) {
                            testReport.get().pass(formatData(filename + " file downloaded Successfully"));
                            log.info(filename + " File downloaded Successfully");
                            return true;
                        } else {
                            testReport.get().fail(formatData(filename + " is not downloaded Successfully"));
                            log.error(filename + " is not downloaded Successfully");
                            return false;
                        }
                    }
                    driver.terminateApp("com.android.chrome");
                }
            } else {
                if (Source("No Resource Center record")) {
                    testReport.get().fail(formatData("No record found in Resource center"));
                    log.debug("No record found in Resource center");
                    return false;
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
