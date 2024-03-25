package Tests;

import Base.AppiumTestSetup;
import Utilities.Utils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Modules.Login_Module.login;
import static Modules.Login_Module.logout;
import static Pages.Login_Page.*;
import static Utilities.Actions.click;
import static Utilities.Actions.gettext;
import static Utilities.TestDataUtil.gettestdata;
import static Utilities.Utils.Assertion;


public class LoginTest extends AppiumTestSetup {

    public static String dburlproject = "";
    @Test(priority = 1, groups = {"smoke", "regression"},enabled = true)
      private void TC_001_Verify_Login_With_Valid_Credentials() {

        JSONObject user1 = gettestdata("Login","User1");
        dburlproject = user1.getString("project");
        login(user1.getString("username"), user1.getString("password"),user1.getString("project"),user1.getString("mobileno"));
        Assertion(user1.getString("expected"), "Login Failed");
        logout();
    }

    @Test(priority = 2, groups = {"negative", "regression"},enabled = false)
    private void TC_002_Verify_Login_With_Invalid_Username() {
        JSONObject user2 = gettestdata("Login","User2");
        login(user2.getString("username"), user2.getString("password"),user2.getString("project"),user2.getString("mobileno"));
        Assertion(user2.getString("expectedErrorMessage"), "Invalid Username Error message is Not showing");
        click("ACCESSIBILITYID","Ok");
    }

    @Test(priority = 3, groups = {"negative", "regression"},enabled = false)
    private void TC_003_Verify_Login_With_Invalid_Password() {
        JSONObject user3 = gettestdata("Login","User3");
        login(user3.getString("username"), user3.getString("password"),user3.getString("project"),user3.getString("mobileno"));
        Assertion(user3.getString("expectedErrorMessage"), "Invalid password Error message is Not showing");
        click("ACCESSIBILITYID","Ok");
    }

    @Test(priority = 4, groups = {"negative", "regression"},enabled = false)
    private void TC_004_Verify_Login_With_Invalid_Project() {
        JSONObject user4 = gettestdata("Login","User4");
        login(user4.getString("username"), user4.getString("password"),user4.getString("project"),user4.getString("mobileno"));
        Assertion(user4.getString("expectedErrorMessage"), "Invalid project Error message is Not showing");
        click("ACCESSIBILITYID","Ok");
    }

    @Test(priority = 5, groups = {"negative", "regression"},enabled = false)
    private void TC_005_Verify_Login_With_Invalid_Mobile_Number() {
        JSONObject user5 = gettestdata("Login","User5");
        login(user5.getString("username"), user5.getString("password"),user5.getString("project"),user5.getString("mobileno"));
        Assertion(user5.getString("expectedErrorMessage"), "Invalid Mobilenumber Error message is Not showing");
    }

    @Test(priority = 6, groups = {"negative", "regression"},enabled = false)
    private void TC_006_Verify_Login_Without_Entering_Username() {
        JSONObject user6 = gettestdata("Login","User6");
        login(user6.getString("username"), user6.getString("password"),user6.getString("project"),user6.getString("mobileno"));
        Assertion(user6.getString("expectedErrorMessage"), "Please Enter username Error message is Not showing");
    }

    @Test(priority = 7, groups = {"negative", "regression"},enabled = false)
    private void TC_007_Verify_Login_Without_Entering_Password() {
        JSONObject user7 = gettestdata("Login","User7");
        login(user7.getString("username"), user7.getString("password"),user7.getString("project"),user7.getString("mobileno"));
        Assertion(user7.getString("expectedErrorMessage"), "Please Enter password Error message is Not showing");
    }

    @Test(priority = 8, groups = {"negative", "regression"},enabled = false)
    private void TC_008_Verify_Login_Without_Entering_Project() {
        JSONObject user8 = gettestdata("Login","User8");
        login(user8.getString("username"), user8.getString("password"),user8.getString("project"),user8.getString("mobileno"));
        Assertion(user8.getString("expectedErrorMessage"), "Please Enter project Error message is Not showing");
    }

    @Test(priority = 9, groups = {"negative", "regression"},enabled = false)
    private void TC_009_Verify_Login_Without_Entering_Mobile_Number() {
        JSONObject user9 = gettestdata("Login","User9");
        login(user9.getString("username"), user9.getString("password"),user9.getString("project"),user9.getString("mobileno"));
        Assertion(user9.getString("expectedErrorMessage"), "Please Enter mobilenumber Error message is Not showing");
    }


}
