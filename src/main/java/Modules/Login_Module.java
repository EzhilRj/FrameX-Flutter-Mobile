package Modules;

import Base.AppiumTestSetup;
import Pages.Login_Page;
import Utilities.ValidationManager;
import com.sun.jdi.connect.spi.Connection;

import static Listeners.FrameX_Listeners.*;
import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Pages.Login_Page.*;
import static Utilities.Actions.*;
import static Utilities.ValidationManager.Source;

public class Login_Module extends AppiumTestSetup {

	public static String dburlproject = "";

	/**
	 * Attempts to log in to the application with provided credentials.
	 *
	 * @param username The username for login.
	 * @param password The password for login.
	 * @param project  The project information.
	 * @param mobileNo The mobile number for login.
	 * @return True if login is successful, false otherwise.
	 */
	public static boolean loginToApp(String username, String password, String project, String mobileNo) {

		 dburlproject = project;
		try {
			String[] requiredFieldErrors = {usernameRequiredErrMsg, passwordRequiredErrMsg, projectRequiredErrMsg, mobileNoRequiredErrMsg};

            // Perform login actions
			performLoginActivity(username, password, project, mobileNo);
			click("ACCESSIBILITYID",LoginButton);
			// Check login status and handle accordingly
			if (ValidationManager.isLoggedIn("Call Plan")) {
				logAndReportSuccess("Login Successful.");
				return true;
			} else {
				// Handle login failure scenarios
				if (ValidationManager.hasErrorMessage(usernamePasswordErrMsg) || ValidationManager.hasErrorMessage(invalidProjectErrMsg)) {
					click("ACCESSIBILITYID","Ok");
					logAndReportFailure("Negative data is given: Login was not successful.");
					return false;
				} else if (ValidationManager.areFieldsRequired(requiredFieldErrors)) {
					logAndReportFailure("Negative data is given: Please Fill all the credentials.");
					return false;
				}
			}
		} catch (Exception e) {
			logAndReportFailure("Error during login validation: " + e.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * Validates if the specified application version is displayed.
	 *
	 * @param versionToCheck The version of the application to verify.
	 * @return True if the specified version is displayed, false otherwise.
	 */
	public static boolean checkVersion(String versionToCheck) {

		try {
			WebdriverWait("Xpath", username,15);
			boolean isVersionDisplayed = Source(versionToCheck);
			if (isVersionDisplayed) {
				log.info("App version is Matched");
				return true;
			} else {
				System.out.println("App Version is not matched: " + versionToCheck);
				log.error("App Version is not matched :  "+versionToCheck);
				driver.quit();
				service.stop();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}
