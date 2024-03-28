package Modules;

import Base.TestSetup;
import Pages.Downloadcalls_page;
import Pages.HomePage_page;

import java.util.List;

import static Listeners.FrameX_Listeners.formatData;
import static Listeners.FrameX_Listeners.testReport;
import static Pages.Downloadcalls_page.*;
import static Pages.HomePage_page.Callplan;
import static Pages.HomePage_page.DownloadCalls;
import static Pages.Login_Page.menubutton;
import static Utilities.Actions.*;
import static Utilities.DBConfig.getColumnNamesFromDatabase;
import static Utilities.Utils.*;

public class Downloadcalls_Module extends TestSetup {

	public static boolean validatedownloadcalls(String trgtid) throws InterruptedException {

		String invalididmsg = "Info&#10;Target ID " + trgtid + " is invalid. Please enter a valid Target Id and try again.";
		String alreadydownloadedmsg = "Info&#10;Store " + trgtid + " already downloaded.";

		try {
			gohomepage(HomePage_page.DownloadCalls);
			click("ACCESSIBILITYID", HomePage_page.DownloadCalls);
			click("ACCESSIBILITYID", Downloadcalls_page.addtarget);
			Enter("classname", targetidtxtbox, trgtid);
			click("ACCESSIBILITYID", addbtn);
			if (sourceExists(trgtid)) {
				click("ACCESSIBILITYID", submit);
				Thread.sleep(3000);
				if (sourceExists("Downloaded Successfully for target " + trgtid)) {
					click("ACCESSIBILITYID", HomePage_page.Callplan);
					Scroll("up");
					if (sourceExists("&#10;Unplanned Calls")) {
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
				} else if (sourceExists(invalididmsg)) {
					testReport.get().fail(formatData("Negative data is givern : Info Target ID " + trgtid+ " is invalid. Please enter a valid Target Id and try again."));
					log.error("Negative data is given : " + invalididmsg);
					return false;
				} else if (sourceExists(alreadydownloadedmsg)) {
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



	public static void navigateToDownloadcallsPage() {
		if(sourceExists("Username")){
			lgpage();
		}
		if (!sourceExists(DownloadCalls)) {
			if(isElementDisplayed("xpath",menubutton)){
				click("Xpath", menubutton);
			}else{
				lgpage();
				click("ACCESSIBILITYID", DownloadCalls);
			}
		}
	}



}
