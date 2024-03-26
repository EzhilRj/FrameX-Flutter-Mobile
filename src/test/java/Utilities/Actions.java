package Utilities;

import Base.TestSetup;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Actions extends TestSetup {

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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
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

    public static void FluentWait(String attributeName, String attributeValue, int seconds) {
        try {
            String AN = attributeName.toUpperCase();
            FluentWait<AndroidDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(seconds))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(Exception.class);

            switch (AN) {
                case "ID":
                    element = wait.until(driver -> driver.findElement(By.id(attributeValue)));
                    break;
                case "XPATH":
                    element = wait.until(driver -> driver.findElement(By.xpath(attributeValue)));
                    break;
                case "CLASSNAME":
                    element = wait.until(driver -> driver.findElement(By.className(attributeValue)));
                    break;
                case "CSSSELECTOR":
                    element = wait.until(driver -> driver.findElement(By.cssSelector(attributeValue)));
                    break;
                case "ACCESSIBILITYID":
                    element = wait.until(driver -> driver.findElement(AppiumBy.accessibilityId(attributeValue)));
                    break;
                default:
                    System.out.println("Invalid attribute name specified: " + attributeName + attributeValue);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public static boolean Scroll(String action) throws InterruptedException {
        Thread.sleep(1000);
        try {
            if (action.equalsIgnoreCase("up") ) {
                driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(100000)"));
                return true;
            }else{
                driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollToBeginning(100000)"));
                return true;
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @AfterMethod
    public void captureScreen(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot ts = driver;
            File source = ts.getScreenshotAs(OutputType.FILE); // capture screenshot file
            File target = new File(props.get("Screenshotpath") + result.getName() + ".png");

            FileUtils.copyFile(source, target);
        }

    }


    public static boolean isElementDisplayed(String attributeName, String attributeValue) {

        String AN = attributeName.toUpperCase();
        boolean isDisplayed = false;

        switch (AN) {
            case "ID":
                isDisplayed = driver.findElement(By.id(attributeValue)).isDisplayed();
                break;

            case "NAME":
                isDisplayed = driver.findElement(By.name(attributeValue)).isDisplayed();
                break;

            case "XPATH":
                isDisplayed = driver.findElement(By.xpath(attributeValue)).isDisplayed();
                break;

            case "CLASSNAME":
                isDisplayed = driver.findElement(By.className(attributeValue)).isDisplayed();
                break;

            case "CSSSELECTOR":
                isDisplayed = driver.findElement(By.cssSelector(attributeValue)).isDisplayed();
                break;

            case "TAGNAME":
                isDisplayed = driver.findElement(By.tagName(attributeValue)).isDisplayed();
                break;

            case "ACCESSIBILITYID":
                isDisplayed = driver.findElement(AppiumBy.accessibilityId(attributeValue)).isDisplayed();
                break;
        }

        return isDisplayed;
    }


}
