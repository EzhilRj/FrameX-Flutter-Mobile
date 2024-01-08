package Base;

import Utilities.Constants;
import Utilities.ExcelReader;
import Utilities.Utils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import javax.mail.MessagingException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static Listeners.FrameX_Listeners.fileName;
import static Modules.Login_Module.checkVersion;
import static Pages.Attendance_page.presentsavedmsg;
import static Utilities.Constants.*;
import static Utilities.Mailconfig.sendMailReport;


public class AppiumTestSetup {

    public static AndroidDriver driver;
    public static AppiumDriverLocalService service;
    public static Logger log = Logger.getLogger(AppiumTestSetup.class);
    public static String testSuiteName;
    public static String devicemodel;
    public static ExcelReader excel;

    public static HashMap<String,String>props;

    static {
        try {
            props = Utils.propertyloader();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        excel = new ExcelReader(props.get("Datafilepath"));
    }

    public static DesiredCapabilities capabilities ;

    // Method to start the app and set up the test environment
    @BeforeSuite
    public static void StartApp(ITestContext context) throws IOException {
        try {
            PropertyConfigurator.configure(props.get("Logpropertiesfilepath"));

            // Start the Appium service
            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File(props.get("Server")))
                    .withIPAddress("127.0.0.1").usingPort(4723)
                    .build();
            service.start();

            // Set desired capabilities for Android driver
            capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", props.get("PlatformName"));
            capabilities.setCapability("deviceName", Devicename);
            capabilities.setCapability("app", props.get("Apppath"));
            capabilities.setCapability("appPackage", props.get("AppPackage"));
            capabilities.setCapability("appActivity", props.get("AppActivity"));
            capabilities.setCapability("autoGrantPermissions", Boolean.parseBoolean(props.get("AutoGrantPermissions")));
            capabilities.setCapability("automationName", props.get("AutomationName"));
            capabilities.setCapability("skipDeviceInitialization",Boolean.parseBoolean(props.get("SkipDeviceInitialization")));
            capabilities.setCapability("ignoreUnimportantViews", Boolean.parseBoolean(props.get("IgnoreUnimportantViews")));
            capabilities.setCapability("skipUnlock", Boolean.parseBoolean(props.get("SkipUnlock")));
            capabilities.setCapability("newCommandTimeout",Integer.parseInt(props.get("NewCommandTimeout")) );
            // Specify the URL with the correct IP address and port for the Appium server
            driver = new AndroidDriver(new URL(props.get("Serverurl")), capabilities);
            devicemodel = driver.getCapabilities().getCapability("deviceModel").toString();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(props.get("Implicitywaittimeout")),TimeUnit.SECONDS);
            checkVersion(props.get("Appversion"));
        } catch (IOException e) {
            log.error("An error occurred while starting the app:", e);
            e.printStackTrace();
        }
    }


    // Method to tear down the test environment after test execution
    @AfterSuite
    public static void tearDownApp() throws InterruptedException, MessagingException, FileNotFoundException {

        // Close the AndroidDriver instance if it exists
        if (driver != null) {
            log.info("AndroidDriver is Quited");
            driver.quit();
        }
        // Stop the Appium service if running
        if (service != null && service.isRunning()) {
            log.info("Appium service is Stopped");
            service.stop();
        }

        // Send mail report and open the generated report file in the default web browser
       // sendMailReport();

        // Open the generated report file in the default web browser
        File extentReport = new File(props.get("TestReportspath")+fileName);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Log completion message
        log.info("Test Execution Completed");

    }

}
