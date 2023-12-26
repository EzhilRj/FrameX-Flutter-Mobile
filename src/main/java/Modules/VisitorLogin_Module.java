package Modules;

import static Pages.HomePage_page.visitorlogin;
import static Pages.Visitorlogin_page.*;
import static Utilities.Actions.*;
import static Utilities.Utils.gohomepage;
import static io.appium.java_client.touch.offset.PointOption.point;

import java.awt.*;

public class VisitorLogin_Module {

	public static boolean validatevisitorsubmission(String visitor,String type,String empid, String name,
													String desig , String remarks , String storeinfo , String promgroom ,String maintaincat,String awareabttargets , String selfieimg) throws InterruptedException, AWTException {

		gohomepage(visitorlogin);
		if(!Source("Select Visitor")){
			click("ACCESSIBILITYID", visitorlogin);
		}
		if(Source("There is no Visitor")) {
			click("ACCESSIBILITYID", selectvisitordd);
			click("ACCESSIBILITYID", "Visitor"+visitor);
			click("ACCESSIBILITYID", visitortype_dd);
			click("ACCESSIBILITYID", type);
			Enter("Xpath",visitorempid_txtbox,empid);
			driver.hideKeyboard();
			Enter("Xpath",visitorname_txtbox,name);
			driver.hideKeyboard();
			Enter("Xpath",designation_txtbox,desig);
			driver.hideKeyboard();
			Enter("Xpath",remarks_txtbox,remarks);



			/*click("ACCESSIBILITYID",storeinfo_dd);
			click("ACCESSIBILITYID",storeinfo);
			click("ACCESSIBILITYID",promoterwellgroomed_dd);
			click("ACCESSIBILITYID",promgroom);
			click("ACCESSIBILITYID",maintainedwellallcat_dd);
			click("ACCESSIBILITYID",maintaincat);
			click("ACCESSIBILITYID",awareallabttargts_dd);
			click("ACCESSIBILITYID",awareabttargets);
			click("ACCESSIBILITYID",awareabttargets);
			Scroll("up");
			if(selfieimg.equalsIgnoreCase("True")){
				click("xpath",selfie_img);
				click("id",shutter_btn);
			}
			click("ACCESSIBILITYID",submit_btn);

			if(Source(success_msg)){
				return true;
			}*/
		}
		return false;
	}








}
