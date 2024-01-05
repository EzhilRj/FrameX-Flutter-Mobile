package Pages;

import static Base.AppiumTestSetup.driver;
import static Base.AppiumTestSetup.log;
import static Utilities.Actions.Enter;

public class Login_Page {

    public static String username = "//*[@hint='Username']";
    private static String password = "//*[@hint='Password']";
    private static String project = "//*[@hint='Project']";
    private static String Mobiileno = "//*[@hint='Mobile no.']";
    public static String LoginButton = "Login";
    public static String invaliderrormsgpopup = "//android.view.View[1]";
    public static String menubutton = "//android.widget.Button[1]";
    public static String Logoutbutton = "Logout";
    public static String LogoutWarningPopup = "This will delete the database. Are you sure?";
    public static String yes = "Yes";
    public static String usernamePasswordErrMsg = "Please Enter a valid UserName and Password and try again.....";
    public static String invalidProjectErrMsg ="Please enter a valid Project Name and try again.";
    public static String usernameRequiredErrMsg ="Username is Required";
    public static String passwordRequiredErrMsg ="Password is Required";
    public static String projectRequiredErrMsg ="Project is Required";
    public static String mobileNoRequiredErrMsg ="Mobile Number is Required";


    public static void performLoginActivity(String Username,String Password,String Project,String Mobileno){

        Enter("Xpath", username, Username);
        Enter("Xpath", password, Password);
        Enter("Xpath", project, Project);
        driver.hideKeyboard();
        Enter("Xpath", Mobiileno, Mobileno);
        log.info("Username : " + Username );
        log.info("Password : " + Password );
        log.info("Project : " + Project );
        log.info("Mobilenumber : " + Mobileno );
    }

}
