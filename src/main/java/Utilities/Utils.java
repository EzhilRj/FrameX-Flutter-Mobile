package Utilities;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import static Base.Setup.driver;
import static Utilities.Constants.Screenshotpath;

public class Utils {

    public static  List<HashMap<String, String>> getjsondata(String jsonfilepath) throws IOException {

        //String jsoncontent = FileUtils.readFileToString(new File(jsonfilepath));

        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String,String>> data = objectMapper.readValue(jsonfilepath, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;

    }


    public static void scrollto(String txt){

        try {
            driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + txt + "\"));"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    @AfterMethod
    public void captureScreen(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE); // capture screenshot file
            File target = new File(Screenshotpath + result.getName() + ".png");

            FileUtils.copyFile(source, target);
        }

    }

}
