package Listeners;

import Utilities.Utils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static Utilities.Constants.ReportPath;

public class FrameX_Listeners implements ITestListener, ISuiteListener {
    static Date d = new Date();
    public static final String fileName = "FrameX_Mobile_Automation_Report_" + d.toString().replace(":", "_").replace(" ", "_")
            + ".html";
    private static ExtentReports extent = ExtentManager.createInstance(ReportPath);
    public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();

    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestClass().getName() + "     @TestCase : " + result.getMethod().getMethodName());
        testReport.set(test);
    }

    public void onTestSuccess(ITestResult result) {

        String methodName = result.getMethod().getMethodName();
        String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " PASSED" + "</b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        testReport.get().pass(m);

    }

    public void onTestFailure(ITestResult result) {

        String excepionMessage= Arrays.toString(result.getThrowable().getStackTrace());
        testReport.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
                + "</font>" + "</b >" + "</summary>" +excepionMessage.replaceAll(",", "<br>")+"</details>"+" \n");

        try {
            Utils.captureScreenshot();
            testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(Utils.screenshotName)
                            .build());
        } catch (IOException e) {

        }

        String failureLogg="TEST CASE FAILED";
        Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
        testReport.get().log(Status.FAIL, m);

    }

    public void onTestSkipped(ITestResult result) {
        String methodName=result.getMethod().getMethodName();
        String logText="<b>"+"Test Case:- "+ methodName+ " Skipped"+"</b>";
        Markup m=MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        testReport.get().skip(m);

    }

    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    public static String formatData(String message, String... values) {
        String formattedData = "<span style=\"color: Blue; font-weight: bold;\">" + message + " </span><span style=\"color: Black;\">";
        formattedData += String.join(" | ", values);
        formattedData += "</span>";
        return formattedData;
    }
}
