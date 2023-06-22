package Utilities;

import io.appium.java_client.AppiumBy;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static Base.Setup.driver;
import static Utilities.Constants.Screenshotpath;

public class Actions {

    public static WebDriver wait;

    public static WebElement element;

    public static void click(String attributeName, String attributeValue) {
        try {
            String AN = attributeName.toUpperCase();
            switch (AN) {
                case "ID" -> driver.findElement(By.id(attributeValue)).click();
                case "NAME" -> driver.findElement(By.name(attributeValue)).click();
                case "XPATH" -> driver.findElement(By.xpath(attributeValue)).click();
                case "CLASSNAME" -> driver.findElement(By.className(attributeValue)).click();
                case "CSSSELECTOR" -> driver.findElement(By.cssSelector(attributeValue)).click();
                case "TAGNAME" -> driver.findElement(By.tagName(attributeValue)).click();
                case "ACCESSIBILITYID" ->  driver.findElement(AppiumBy.accessibilityId(attributeValue)).click();
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void Enter(String attributeName, String attributeValue, String inputText) {

        try {
            String AN = attributeName.toUpperCase();
            switch (AN) {
                case "ID" -> {
                    driver.findElement(By.id(attributeValue)).click();
                    driver.findElement(By.id(attributeValue)).clear();
                    driver.findElement(By.id(attributeValue)).sendKeys(inputText);
                }
                case "NAME" -> {
                    driver.findElement(By.name(attributeValue)).click();
                    driver.findElement(By.name(attributeValue)).clear();
                    driver.findElement(By.name(attributeValue)).sendKeys(inputText);
                }
                case "XPATH" -> {
                    driver.findElement(By.xpath(attributeValue)).click();
                    driver.findElement(By.xpath(attributeValue)).clear();
                    driver.findElement(By.xpath(attributeValue)).sendKeys(inputText);
                }
                case "CLASSNAME" -> {
                    driver.findElement(By.className(attributeValue)).click();
                    driver.findElement(By.className(attributeValue)).clear();
                    driver.findElement(By.className(attributeValue)).sendKeys(inputText);
                }
                case "CSSSELECTOR" -> {
                    driver.findElement(By.cssSelector(attributeValue)).click();
                    driver.findElement(By.cssSelector(attributeValue)).clear();
                    driver.findElement(By.cssSelector(attributeValue)).sendKeys(inputText);
                }
                case "TAGNAME" -> {
                    driver.findElement(By.tagName(attributeValue)).click();
                    driver.findElement(By.tagName(attributeValue)).clear();
                    driver.findElement(By.tagName(attributeValue)).sendKeys(inputText);
                }case "accessibilityId" -> {
                    driver.findElement(AppiumBy.accessibilityId(attributeValue)).click();
                    driver.findElement(AppiumBy.accessibilityId(attributeValue)).clear();
                    driver.findElement(AppiumBy.accessibilityId(attributeValue)).sendKeys(inputText);
                }
                default -> System.out.println("Invalid attribute name specified: " + attributeName + attributeValue);
            }
        }catch (Exception e) {
            e.getMessage();
        }
    }

    public static String gettext(String attributeName, String attributeValue) {

        String AN = attributeName.toUpperCase();
        String text = null;
        switch (AN) {
            case "ID":
                text = driver.findElement(By.id(attributeValue)).getText();
                break;
            case "NAME":
                text = driver.findElement(By.name(attributeValue)).getText();
                break;
            case "XPATH":
                text = driver.findElement(By.xpath(attributeValue)).getText();
                break;
            case "CLASSNAME":
                text = driver.findElement(By.className(attributeValue)).getText();
                break;
            case "CSSSELECTOR":
                text = driver.findElement(By.cssSelector(attributeValue)).getText();
                break;
            case "TAGNAME":
                text = driver.findElement(By.tagName(attributeValue)).getText();

            case "ACCESSIBILITYID":
                text =  driver.findElement(AppiumBy.accessibilityId(attributeValue)).getText();
        }

        return text;
    }


    public static void WebdriverWait(String attributeName, String attributeValue, int seconds) {
        try {
            String AN = attributeName.toUpperCase();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            switch (AN) {
                case "ID" -> {
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(attributeValue)));
                }
                case "XPATH" -> {
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(attributeValue)));
                }
                case "CLASSNAME" -> {
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(attributeValue)));
                }
                case "CSSSELECTOR" -> {
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(attributeValue)));
                }
                case "ACCESSIBILITYID"-> {
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(attributeValue)));
                }
                default -> System.out.println("Invalid attribute name specified: " + attributeName + attributeValue);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static boolean Source(String value) {
        if (driver.getPageSource().contains(value)) {
            return true;
        }
        return false;
    }

    public static void Scroll(String action) throws InterruptedException {
        Thread.sleep(1000);
        try {
            if (action == "up") {
                driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(100000)"));
            }else{
                driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollToBeginning(100000)"));
            }
        }catch (Exception e) {
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
