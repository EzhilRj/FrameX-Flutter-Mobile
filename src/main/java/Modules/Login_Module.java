package Modules;

import Base.Setup;
import com.aventstack.extentreports.Status;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;

import static UiObjects.Login_PageObjects.*;
import static Utilities.Actions.Enter;
import static Utilities.Actions.click;
import static Utilities.Listeners.test;
import static Utilities.XLUtils.getJsonData;

public class Login_Module extends Setup {

    public static boolean LoginintoApp(String Username, String Password, String project, String Mobileno) throws Exception {

        Enter("XPATH",username,Username);
        Enter("XPATH",password,Password);
        Enter("XPATH",Project,project);
        driver.hideKeyboard();
        Enter("XPATH",Mobiileno,Mobileno);
        log.info(Username + Password + project + Mobileno +" is Entered");
        test.log(Status.INFO,"USERNAME : "+Username + " | PASSWORD : "+Password+ " | PROJECT : "+project+" | MOBILE NO : "+Mobileno);
        click("ACCESSIBILITYID",LoginButton);
        log.info("Login button is Clicked");
        boolean loginsts = driver.findElement(By.xpath("//android.widget.ImageView[@content-desc='Call Plan']")).isDisplayed();

        return loginsts;
    }

}
