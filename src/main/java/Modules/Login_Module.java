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

import java.util.concurrent.TimeUnit;

import static UiObjects.CallPlan_Objects.clear;
import static UiObjects.Login_PageObjects.*;
import static Utilities.Actions.*;
import static Utilities.Listeners.test;
import static Utilities.XLUtils.getJsonData;

public class Login_Module extends Setup {

    public static boolean LoginintoApp(String Username, String Password, String project, String Mobileno) throws Exception {

        try {
            stopWatch.start();
            Enter("XPATH", username, Username);
            log.info("Username is Entered: " + Username);
            Enter("XPATH", password, Password);
            log.info("Password is Entered: " + Password);
            Enter("XPATH", Project, project);
            log.info("Project is Entered: " + project);
            driver.hideKeyboard();
            Enter("XPATH", Mobiileno, Mobileno);
            log.info("Mobileno is Entered: " + Mobileno);
            test.info( "<span style=\"color: Blue; font-weight: bold;\">USERNAME : </span><span style=\"color: Black;\">" + Username + "</span>"
                    +"  |  "+"<span style=\"color: Blue; font-weight: bold;\">PASSWORD : </span><span style=\"color: Black;\">" + Password + "</span>"
                    +"   |  "+"<span style=\"color: Blue; font-weight: bold;\">PROJECT : </span><span style=\"color: Black;\">" + project + "</span>"
                    +"  |  "+"<span style=\"color: Blue; font-weight: bold;\">MOBILE NO : </span><span style=\"color: Black;\">" + Mobileno + "</span>");
            click("ACCESSIBILITYID", LoginButton);
            log.info("Login button is Clicked");
            driver.openNotifications();
            boolean clr = driver.findElement(By.xpath(clear)).isEnabled();
            if(clr){
                click("xpath", clear);
            }else{
                driver.navigate().back();
            }
            WebdriverWait("Xpath","//android.widget.ImageView[@content-desc='Call Plan']",20);
            boolean loginsts = driver.findElement(By.xpath("//android.widget.ImageView[@content-desc='Call Plan']")).isDisplayed();
            return loginsts;
        } catch (Exception e) {
            log.error("An exception occurred during login: " + e.getMessage());
            test.error( "<span style=\"color: Black; font-weight: bold;\">An exception occurred during login : </span><span style=\"color: Red;\">" +  e.getMessage() + "</span>");
            throw e;
        } finally {
            stopWatch.stop();
            log.info("Time taken for Login: " + String.valueOf(stopWatch.getTime(TimeUnit.SECONDS))+ " Seconds");
            test.info( "<span style=\"color: Blue; font-weight: bold;\"> Time taken for Login : </span><span style=\"color: DodgerBlue;\">"+String.valueOf(stopWatch.getTime(TimeUnit.SECONDS)) + " Seconds"+ "</span>");
            stopWatch.reset();
        }
    }


}