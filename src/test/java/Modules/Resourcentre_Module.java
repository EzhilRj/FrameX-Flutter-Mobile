package Modules;

import Base.TestSetup;

import java.util.Set;

import static Listeners.FrameX_Listeners.*;
import static Pages.HomePage_page.Callplan;
import static Pages.HomePage_page.ResourceCentre;
import static Pages.Login_Page.menubutton;
import static Utilities.Actions.*;
import static Utilities.Utils.*;

public class Resourcentre_Module extends TestSetup {

    /**
     * Validates the download of a file.
     *
     * @param filename the name of the file to be validated
     * @return true if the file is downloaded successfully, false otherwise
     * @throws Exception if an exception occurs during the validation process
     */
    public static boolean validateFiles(String filename) {
        try {

            if (isElementDisplayed("ACCESSIBILITYID", filename)) {
                click("ACCESSIBILITYID", filename);
                Thread.sleep(3500); // Consider using WebDriverWait instead
                Set<String> contextHandles = driver.getContextHandles();
                for (String context : contextHandles) {
                    if (context.contains("WEBVIEW")) {
                        if (isFileDownloadedSuccessfully(filename)) {
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
                testReport.get().fail(formatData("No record found for filename: " + filename + " in Resource center"));
                log.debug("No record found for filename: " + filename + " in Resource center");
                return false;
            }
        } catch (Exception e) {
            logAndReportFailure("Exception occurred: " + e.getMessage());
            return false;
        }
        return false;
    }




    /**
     * Checks if a file has been downloaded successfully.
     *
     * @param filename the name of the file to check
     * @return true if the file has been downloaded successfully, false otherwise
     * @throws SourceNotFoundException if the source of the file is not found
     */
    private static boolean isFileDownloadedSuccessfully(String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".mp4")) {
            return true;
        } else if (sourceExists(filename)) {
            return true;
        }
        return false;
    }

    public static void navigateToResourceCentrePage(String module) {
        if (sourceExists(module)) {
            click("ACCESSIBILITYID", module);
        } else if (sourceExists("Username")) {
            lgpage();
            click("ACCESSIBILITYID", module);
        } else {
            if (isElementDisplayed("xpath", menubutton)) {
                click("xpath", menubutton);
            }
        }
    }
}
