package Pages;

import org.json.JSONObject;

import static Base.AppiumTestSetup.driver;
import static Base.AppiumTestSetup.log;
import static Utilities.Actions.Enter;
import static Utilities.Actions.click;
import static Utilities.TestDataUtil.gettestdata;

public class Login_Page {

    public static String username = "//*[@hint='Username']";
    public static String password = "//*[@hint='Password']";
    public static String project = "//*[@hint='Project']";
    public static String Mobiileno = "//*[@hint='Mobile no.']";
    public static String LoginButton = "Login";
    public static String menubutton = "//android.widget.Button[1]";
    public static String Logoutbutton = "Logout";
    public static String yes = "Yes";


    public static void login(String Username,String Password,String Project,String Mobileno){

        Enter("Xpath", username, Username);
        Enter("Xpath", password,  Password);
        Enter("Xpath", project, Project);
        driver.hideKeyboard();
        Enter("Xpath", Mobiileno, Mobileno);
        click("ACCESSIBILITYID",LoginButton);
        log.info("Username : " + Username );
        log.info("Password : " + Password );
        log.info("Project : " + Project );
        log.info("Mobilenumber : " + Mobileno );
    }



    public static void logout(){
        click("Xpath",menubutton);
        click("ACCESSIBILITYID",Logoutbutton);
        click("ACCESSIBILITYID",yes);
    }

}
