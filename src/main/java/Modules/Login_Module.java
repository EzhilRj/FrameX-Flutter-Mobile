package Modules;

import Base.AppiumTestSetup;

import java.sql.Connection;

import static Listeners.FrameX_Listeners.formatData;
import static Listeners.FrameX_Listeners.testReport;
import static UiObjects.Login_PageObjects.*;
import static Utilities.Actions.*;

public class Login_Module extends AppiumTestSetup {

    public static boolean Login_in_App(String Username, String Password, String project, String Mobileno) throws Exception {

        try{
            Enter("Xpath", username, Username);
            Enter("Xpath", password, Password);
            Enter("Xpath", Project, project);
            driver.hideKeyboard();
            Enter("Xpath", Mobiileno, Mobileno);
            click("ACCESSIBILITYID", LoginButton);

            // Checking for various error messages or successful login conditions
            if (Source("Please Enter a valid UserName and Password and try again.....") || Source("Please enter a valid Project Name and try again.")) {
                click("ACCESSIBILITYID", "Ok");
                testReport.get().fail(formatData("Negative data is given : Login was not successful. "));
                log.error("Login failed");
                return false;
            } else if (Source("Call Plan")) {
                testReport.get().pass(formatData("Login Successfull"));
                log.info("Login Success");
                return true;
            }else if(Source("Username is Required")||Source("Password is Required|")
                    ||Source("Project is Required")||Source("Mobile Number is Required")){
                testReport.get().fail(formatData("Negative data is given : Login was not successful. "));
                log.error("Login failed");

                return false;
            }
        } catch (Exception e) {
            log.error("Exception occurred during login: " + e.getMessage());
            testReport.get().fail(formatData("Exception occurred during login: " + e.getMessage()));
        }
        return false;
    }



    public static boolean checkVersion(String ver) {

        try {
            // Checking the source for the version
            if (Source(ver)) {
                // If the version is displayed, log a pass and return true
                testReport.get().pass(formatData("App Version is Displayed: " + ver));
                log.info("App Version is Displayed: " + ver);
                return true;
            } else {
                // If the version is not displayed, log a fail and return false
                testReport.get().fail(formatData("App Version is Displayed: " + ver));
                log.error("App Version is not Displayed: " + ver);
                return false;
            }
        } catch (Exception e) {
            // Catching exceptions that might occur during version check and handling them
            log.error("Exception occurred during version check: " + e.getMessage());
            testReport.get().error(formatData("Exception occurred during version check: " + e.getMessage()));
            return false;
        }
    }

}
