package Base;

import Interfaces.Setup;
import Utilities.Constants;
import Utilities.ExcelReader;
import Utilities.Utils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static Listeners.FrameX_Listeners.fileName;
import static Modules.Login_Module.checkVersion;
import static Pages.Attendance_page.presentsavedmsg;
import static Utilities.Constants.*;
import static Utilities.Mailconfig.sendMailReport;
import static Utilities.Utils.*;


public class AppiumTestSetup implements Setup {

    public static AndroidDriver driver;
    public static AppiumDriverLocalService service;
    public static Logger log = Logger.getLogger(AppiumTestSetup.class);
    public static String devicemodel;
    public static ExcelReader excel;
    public static HashMap<String,String>props;


    static {
        try {
            props = propertyloader();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        excel = new ExcelReader(props.get("Datafilepath"));
    }


    public static HashMap<String,String>queries;
    static {
        try {
            queries = (HashMap<String, String>) queryloader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static DesiredCapabilities capabilities ;

    {
        PropertyConfigurator.configure(props.get("Logpropertiesfilepath"));
    }



    // Method to start the app and set up the test environment
    @BeforeSuite
    public static void StartApp(ITestContext context) throws IOException {
        try {

            // Start the Appium service
            service = new AppiumServiceBuilder().withAppiumJS(new File(props.get("Server"))).withIPAddress("127.0.0.1").usingPort(4723).build();
            service.start();

            String[]  capabs = {"platformName","appPackage","appActivity","autoGrantPermissions","automationName","skipDeviceInitialization","ignoreUnimportantViews","skipUnlock"};
            // Set desired capabilities for Android driver
            capabilities = new DesiredCapabilities();
            for(String capkey : capabs){
                if(capkey.equalsIgnoreCase("autoGrantPermissions")||capkey.equalsIgnoreCase("skipDeviceInitialization")|| capkey.equalsIgnoreCase("ignoreUnimportantViews")||capkey.equalsIgnoreCase("skipUnlock")){
                    capabilities.setCapability(capkey,Boolean.parseBoolean(props.get(capkey)));
                }else{
                    capabilities.setCapability(capkey,props.get(capkey));
                }

            }
            capabilities.setCapability("app", props.get("Apppath"));
            capabilities.setCapability("deviceName", Devicename);
            capabilities.setCapability("adbExecTimeout", "120000");
            // Specify the URL with the correct IP address and port for the Appium server
            driver = new AndroidDriver(new URL(props.get("Serverurl")), capabilities);
            devicemodel = driver.getCapabilities().getCapability("deviceModel").toString();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(props.get("Implicitywaittimeout")),TimeUnit.SECONDS);
            checkVersion(props.get("Appversion"));
        } catch (IOException e) {
            log.error("An error occurred while starting the app:", e);
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    @Override
    public void startapp() {

    }
}
