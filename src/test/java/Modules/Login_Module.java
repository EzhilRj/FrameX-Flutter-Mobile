package Modules;

import Base.AppiumTestSetup;

import static Pages.Login_Page.*;
import static Utilities.Actions.*;
import static Utilities.Utils.Source;

public class Login_Module extends AppiumTestSetup {

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
				tearDownApp();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



}
