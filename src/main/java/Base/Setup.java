package Base;

import static Utilities.Constants.Apppath;
import static Utilities.Constants.Devicename;
import static Utilities.Constants.LogConfiguration;
import static Utilities.Constants.ServerPath;
import static Utilities.Listeners.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class Setup {

    public static AndroidDriver driver;
    public static AppiumDriverLocalService service;
    public static Logger log = Logger.getLogger(Setup.class);

    public static long startTime;
    public static long endTime;

    @BeforeSuite
    public static void StartApp() throws IOException {

        try {
            PropertyConfigurator.configure(LogConfiguration);
            startTime  = System.currentTimeMillis();
            service = new AppiumServiceBuilder().withAppiumJS(new File(ServerPath)).withIPAddress("127.0.0.1").usingPort(4723).build();
            service.start();
            log.info("-----Appium Service started -----");
            UiAutomator2Options options =  new UiAutomator2Options();
            options.setDeviceName(Devicename);
            log.info("Devicename : "+Devicename);
            options.setApp(Apppath);
            log.info("AppName : "+Apppath);
            options.setCapability("autoGrantPermissions", true);
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"),options);
            log.info("-----Application is started -----");
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (IOException e) {
            test.log(Status.ERROR,e.getMessage());
            log.error("An error occurred while starting the app:", e);
            e.printStackTrace();

        }
    }

    @AfterSuite(enabled = true)
    public static  void TearApp(){

        endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double totalTimeSeconds = totalTime / 1000.0;
        driver.quit();
        log.info("-----Application is Closed -----");
        service.stop();
        log.info("-----Appium Service Stoped -----");


    }
}
