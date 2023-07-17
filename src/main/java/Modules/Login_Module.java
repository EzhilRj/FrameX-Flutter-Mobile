package Modules;

import Base.Setup;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static UiObjects.CallPlan_Objects.clear;
import static UiObjects.HomePage_Objects.Callplan;
import static UiObjects.Login_PageObjects.*;
import static Utilities.Actions.*;
import static Utilities.DBConfig.*;
import static Utilities.Listeners.test;
import static Utilities.XLUtils.getJsonData;

public class Login_Module extends Setup {

    public static boolean Login_in_App(String Username, String Password, String project, String Mobileno,String Expected) throws Exception {

        try {
            Enter("XPATH", username, Username);
            Enter("XPATH", password, Password);
            Enter("XPATH", Project, project);
            driver.hideKeyboard();
            Enter("XPATH", Mobiileno, Mobileno);
            click("ACCESSIBILITYID", LoginButton);
            Thread.sleep(2000);
            if(Source(Callplan)){
                click("Xpath",menubutton);
                click("ACCESSIBILITYID",Logoutbutton);
                if(isElementDisplayed("ACCESSIBILITYID",LogoutWarningPopup)){
                    click("ACCESSIBILITYID",yes);
                }
                test.log(Status.PASS, formatData("Postive Data is Given", Username, Password, project, Mobileno));
                return true;
            } else if (Source("##"+Expected)) {
                click("ACCESSIBILITYID","Ok");
                makeReport(Username,Password,project,Mobileno,Expected);
                driver.closeApp();
                driver.launchApp();
                return true;
            } else if (anyNull(Username, Password, project, Mobileno)) {
                if(Source(Expected)){
                    makeReport(Username,Password,project,Mobileno,Expected);
                    driver.closeApp();
                    driver.launchApp();
                }
                return true;
            }
        } catch (Exception e) {
            log.error("An exception occurred during login: " + e.getMessage());
            test.error(formatData("An exception occurred during login", e.getMessage()));
            throw e;
        }
        return  false;
    }

    static void makeReport(String un,String Pw,String project,String Mno,String Expected){

        test.log(Status.FAIL, formatData("Negative Data is Given", un, Pw, project, Mno, Expected));

    }

    static String formatData(String message, String... values) {
        String formattedData = "<span style=\"color: Blue; font-weight: bold;\">" + message + ": </span><span style=\"color: Black;\">";
        formattedData += String.join(" | ", values);
        formattedData += "</span>";
        return formattedData;
    }

    static boolean anyNull(Object... values) {
        return Stream.of(values).anyMatch(value -> value == null);
    }



}
