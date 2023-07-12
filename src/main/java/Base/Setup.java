package Base;

import static Utilities.Constants.*;
import static Utilities.Listeners.test;
import static Utilities.Listeners.timestamp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.BeforeTest;

public class Setup {

    public static AndroidDriver driver;
    public static AppiumDriverLocalService service;
    public static Logger log = Logger.getLogger(Setup.class);

   public static String devicemodel;
    public static StopWatch stopWatch = new StopWatch();


    @BeforeSuite
    public static void StartApp() throws IOException {

        try {
            PropertyConfigurator.configure(LogConfiguration);
            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File(ServerPath))
                    .withIPAddress("127.0.0.1").usingPort(4723)
                    .build();
            service.start();
            log.info("-----Appium Service started -----");

            UiAutomator2Options options =  new UiAutomator2Options();
            options.setDeviceName(Devicename);
            log.info("Devicename : "+Devicename);
            options.setApp(Apppath);
            log.info("AppName : "+Apppath);
            options.setCapability("autoGrantPermissions", true);
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"),options);
             devicemodel = driver.getCapabilities().getCapability("deviceModel").toString();
            log.info("DeviceModel : "+devicemodel);
            log.info("-----Application is started -----");
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (IOException e) {
            test.log(Status.ERROR,e.getMessage());
            log.error("An error occurred while starting the app:", e);
            e.printStackTrace();

        }
    }

    @AfterSuite(enabled = true)
    public static  void TearApp() throws IOException {
        driver.quit();
        log.info("-----Application is Closed -----");
        service.stop();
        log.info("-----Appium Service Stoped -----");


    }

}
