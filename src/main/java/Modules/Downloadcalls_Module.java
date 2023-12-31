package Modules;

import Base.AppiumTestSetup;
import Pages.Downloadcalls_page;
import Pages.HomePage_page;

import static Listeners.FrameX_Listeners.formatData;
import static Listeners.FrameX_Listeners.testReport;
import static Pages.Downloadcalls_page.*;
import static Utilities.Actions.*;
import static Utilities.Utils.gohomepage;
import static Utilities.ValidationManager.Source;

public class Downloadcalls_Module extends AppiumTestSetup {


	public static boolean validatedownloadcalls(String trgtid) throws InterruptedException {

		String invalididmsg = "Info&#10;Target ID " + trgtid
				+ " is invalid. Please enter a valid Target Id and try again.";
		String alreadydownloadedmsg = "Info&#10;Store " + trgtid + " already downloaded.";

		try {
			gohomepage(HomePage_page.DownloadCalls);
			click("ACCESSIBILITYID", HomePage_page.DownloadCalls);
			click("ACCESSIBILITYID", Downloadcalls_page.addtarget);
			Enter("classname", targetidtxtbox, trgtid);
			click("ACCESSIBILITYID", addbtn);
			if (Source(trgtid)) {
				click("ACCESSIBILITYID", submit);
				Thread.sleep(3000);
				if (Source("Downloaded Successfully for target " + trgtid)) {
					click("ACCESSIBILITYID", HomePage_page.Callplan);
					Scroll("up");
					if (Source("&#10;Unplanned Calls")) {
						if (isElementDisplayed("xpath",
								"//android.view.View[contains(@content-desc, 'Target ID: " + trgtid + "')]")) {
							testReport.get().pass(formatData(targetdownloadsucessmsg + trgtid) + " and showing in unplanned calls");
							log.info(targetdownloadsucessmsg + trgtid + " and showing in unplanned calls");
							return true;
						}
					} else {
						testReport.get().fail(formatData("Target is downloaded but not showing in Unplanned calls"));
						log.error("Target is downloaded but not showing in Unplanned calls");
						return false;
					}
				} else if (Source(invalididmsg)) {
					testReport.get().fail(formatData("Negative data is givern : Info Target ID " + trgtid+ " is invalid. Please enter a valid Target Id and try again."));
					log.error("Negative data is given : " + invalididmsg);
					return false;
				} else if (Source(alreadydownloadedmsg)) {
					testReport.get().fail(formatData("Negative data is given :  Info Store " + trgtid + " already downloaded."));
					log.error("Negative data is given : " + alreadydownloadedmsg);
					return false;
				} else {
					testReport.get().fail(formatData("Info or success Message is not showing"));
					log.error("Message is not showing");
					return false;
				}
			} else {
				testReport.get().fail(formatData("After clicking add button target id is not showing in page"));
				log.error("After clicking add button target id is not showing in page");
				return false;
			}
		} catch (Exception e) {
			log.error("Exception occurred during login: " + e.getMessage());
			testReport.get().fail(formatData("Exception occurred during login: " + e.getMessage()));
			return false;
		}
		return false;
	}
	

}
