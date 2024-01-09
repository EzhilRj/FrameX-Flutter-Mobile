package Utilities;

import static Listeners.FrameX_Listeners.fileName;
import static Utilities.Utils.*;

public class Constants {
    public static final String Devicename  = getDeviceName();
    public static final String queryfilepath = System.getProperty("user.dir")+"\\src\\test\\resources\\Properties\\queries.sql";

    public static String body = "Dear Team,\n" +
            "\n" +
            "Please find the attached test automation report for FrameX Mobile executed on "+generateFormattedDate()+" . The test suite covered various scenarios validating the functionalities of FrameX mobile.\n" +
            "\n" +
            "The test suite execution results indicate [summary of test outcomes - overall success, challenges, critical issues, etc.].\n" +
            "\n" +
            "Attached Test Report:\n" +
            "The attached test report provides detailed information on individual test cases, their status, logs, and any errors encountered during execution.\n" +
            "\n" +
            "Please review the attached report for a comprehensive understanding of the test execution results.\n" +
            "\n" +
            "\n" +
            "Thank you,\n" +
            "Fieldlytics QA Team\n";

}
