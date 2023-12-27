package Utilities;

import static Base.AppiumTestSetup.driver;
import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Listeners.FrameX_Listeners.logAndReportSuccess;


public class ValidationManager {

    public static boolean Source(String value) {

        return driver.getPageSource().contains(value);
    }

    //Login page Validations and Verifications================================================================
    public static boolean hasErrorMessage(String errorMessage) {
        try {
            return Source(errorMessage);
        } catch (Exception e) {
            logAndReportFailure("Error while checking for error message: " + e.getMessage());
            return false;
        }
    }

    public static boolean isLoggedIn(String successMessage) {
        try {
            return Source(successMessage);
        } catch (Exception e) {
            logAndReportFailure("Error while checking login status: " + e.getMessage());
            return false;
        }
    }

    public static boolean areFieldsRequired(String... errorMessages) {
        try {
            for (String errorMessage : errorMessages) {
                if (hasErrorMessage(errorMessage)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logAndReportFailure("Error while checking field requirements: " + e.getMessage());
            return false;
        }
    }

    //Version Validations and Verifications================================================================




}
