package Modules;

import static UiObjects.HomePage_Objects.visitorlogin;
import static UiObjects.Visitorlogin_Objects.*;
import static Utilities.Actions.*;
import static Utilities.Utils.Scrollto;
import static io.appium.java_client.touch.offset.PointOption.point;

import UiObjects.HomePage_Objects;
import UiObjects.Visitorlogin_Objects;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.awt.*;
import java.awt.event.InputEvent;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

public class VisitorLogin_Module {

	public static boolean validatevisitorsubmission(String visitor,String type,String empid, String name,
													String desig , String remarks , String storeinfo , String promgroom ,String maintaincat,String awareabttargets , String selfieimg) throws InterruptedException, AWTException {

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


			click("ACCESSIBILITYID",storeinfo_dd);
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
			}
		}
		return false;
	}








}
