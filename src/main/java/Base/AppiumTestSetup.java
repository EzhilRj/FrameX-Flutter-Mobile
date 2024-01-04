package Base;

import Utilities.Constants;
import Utilities.ExcelReader;
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
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static Modules.Login_Module.checkVersion;
import static Utilities.Constants.*;
import static Utilities.Mailconfig.sendMailReport;


public class AppiumTestSetup {

    public static AndroidDriver driver;
    public static AppiumDriverLocalService service;
    public static Logger log = Logger.getLogger(AppiumTestSetup.class);
    public static String testSuiteName;
    public static String devicemodel;
    public static ExcelReader excel = new ExcelReader(Excelpath);
    public static DesiredCapabilities capabilities ;

    // Method to start the app and set up the test environment
    @BeforeSuite
    public static void StartApp(ITestContext context) throws IOException {
        try {
            PropertyConfigurator.configure(LogConfiguration);

            // Start the Appium service
            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File(ServerPath))
                    .withIPAddress("127.0.0.1").usingPort(4723)
                    .build();
            service.start();

            // Set desired capabilities for Android driver
            capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", Devicename);
            capabilities.setCapability("app", Apppath);
            capabilities.setCapability("appPackage", "com.fieldlytics.framex");
            capabilities.setCapability("appActivity", "com.fieldlytics.frame.MainActivity");
            capabilities.setCapability("autoGrantPermissions", false);
            capabilities.setCapability("automationName", "UIAutomator2");
            capabilities.setCapability("skipDeviceInitialization", true);
            capabilities.setCapability("ignoreUnimportantViews", true);
            capabilities.setCapability("newCommandTimeout", 120);

            // Specify the URL with the correct IP address and port for the Appium server
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
            devicemodel = driver.getCapabilities().getCapability("deviceModel").toString();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            checkVersion("3.1.6");
        } catch (IOException e) {
            log.error("An error occurred while starting the app:", e);
            e.printStackTrace();
        }
    }



    // Method to tear down the test environment after test execution
    @AfterSuite
    public static void tearDownApp() throws InterruptedException, MessagingException {

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
        //sendMailReport();

        // Open the generated report file in the default web browser
        File extentReport = new File(ReportPath);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Log completion message
        log.info("Test Execution Completed");

    }

}
