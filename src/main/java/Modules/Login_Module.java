package Modules;

import Base.AppiumTestSetup;

import static Listeners.FrameX_Listeners.*;
import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Pages.Login_Page.*;
import static Utilities.Actions.*;

public class Login_Module extends AppiumTestSetup {

	public static boolean Login_in_App(String Username, String Password, String project, String Mobileno) throws Exception {

		try {
			Enter("Xpath", username, Username);
			Enter("Xpath", password, Password);
			Enter("Xpath", Project, project);
			driver.hideKeyboard();
			Enter("Xpath", Mobiileno, Mobileno);
			click("ACCESSIBILITYID", LoginButton);

			if (Source(usernamepassword_errmsg) || Source(invalidproject_errmsg)) {
				click("ACCESSIBILITYID", "Ok");
				logAndReportFailure("Negative data is given : Login was not successful. ");
				return false;
			} else if (Source("Call Plan")) {
				logAndReportSuccess("Login Successful.");
				return true;
			} else if (Source(usernamerequired_errmsg) || Source(passwordrequired_errmsg)||
					Source(projectrequired_errmsg) || Source(mobilenorequired_errmsg)) {
				logAndReportFailure("Negative data is given : Please Fill all the credentials" );
				return false;
			}
		} catch (Exception e) {
			logAndReportFailure("Exception occurred during login: " + e.getMessage());
		}
		return false;
	}

	public static boolean checkVersion(String versionToCheck) {

		try {
			if (Source(versionToCheck)) {
				logAndReportSuccess("App Version is Displayed: " + versionToCheck);
				return true;
			} else {
				logAndReportFailure("App Version is not Displayed: " + versionToCheck);
				return false;
			}
		} catch (Exception e) {
			logAndReportFailure("Exception occurred during version check: " + e.getMessage());
			return false;
		}
	}

}
