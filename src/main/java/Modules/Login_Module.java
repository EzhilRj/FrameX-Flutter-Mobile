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

import static UiObjects.CallPlan_Objects.clear;
import static UiObjects.HomePage_Objects.Callplan;
import static UiObjects.Login_PageObjects.*;
import static Utilities.Actions.*;
import static Utilities.DBConfig.*;
import static Utilities.Listeners.test;
import static Utilities.XLUtils.getJsonData;

public class Login_Module extends Setup {

    public static boolean LoginintoApp(String Username, String Password, String project, String Mobileno,String Expected) throws Exception {

        try {
            Enter("XPATH", username, Username);
            Enter("XPATH", password, Password);
            Enter("XPATH", Project, project);
            driver.hideKeyboard();
            Enter("XPATH", Mobiileno, Mobileno);
            click("ACCESSIBILITYID", LoginButton);
            Thread.sleep(1000);
            if(Source(Callplan)){
                click("Xpath",menubutton);
                click("ACCESSIBILITYID",Logoutbutton);
                if(isElementDisplayed("ACCESSIBILITYID",LogoutWarningPopup)){
                    click("ACCESSIBILITYID",yes);
                }
                test.log(Status.PASS,"<span style=\"color: Blue; font-weight: bold;\">Postive Data is Given : </span><span style=\"color: Black;\">" + Username+" |  "+Password+" |  "+project+" |  "+Mobileno+ "</span>");
                return true;
            } else if (Source("##"+Expected)) {
                click("ACCESSIBILITYID","Ok");
                makeReport(Username,Password,project,Mobileno,Expected);
                driver.closeApp();
                driver.launchApp();
                return true;
            } else if (Username == null || Password == null || project == null || Mobileno == null) {
                if(Source(Expected)){
                    makeReport(Username,Password,project,Mobileno,Expected);
                    driver.closeApp();
                    driver.launchApp();
                }
                return true;
            }
        } catch (Exception e) {
            log.error("An exception occurred during login: " + e.getMessage());
            test.error( "<span style=\"color: Black; font-weight: bold;\">An exception occurred during login : </span><span style=\"color: Red;\">" +  e.getMessage() + "</span>");
            throw e;
        }
        return  false;
    }

    static void makeReport(String un,String Pw,String project,String Mno,String Expected){

        test.log(Status.FAIL,"<span style=\"color: Blue; font-weight: bold;\">Negative Data is Given : </span><span style=\"color: Black;\">" + un+" |  "+Pw+" |  "+project+" |  "+Mno+"   |   "+Expected +"</span>");

    }



}
