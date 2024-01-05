package Utilities;

import static Listeners.FrameX_Listeners.fileName;
import static Utilities.Utils.*;

public class Constants {

    public static final String Devicename  = getDeviceName();
    public static final String Apppath = System.getProperty("user.dir") + "\\src\\test\\resources\\Apps\\FrameFlutterTest.apk";
    public static final String ServerPath = "C:\\Users\\Ezhil Rj\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    public static final String Nodepath = "C:\\Program Files\\nodejs\\node.exe";
    public static final String Screenshotpath = System.getProperty("user.dir") + "\\Screenshots";
    public static final String Excelpath  = System.getProperty("user.dir") + "\\src\\test\\resources\\Datas\\Testdatas.xlsx";
    public static String LogConfiguration = System.getProperty("user.dir") + "\\Log4j.properties";

    public static  final String appiumserverurl = "http://127.0.0.1:4723";

    public static final String LiveDatabaseurl ="jdbc:sqlserver://65.1.119.118:1433;DatabaseName=framedemo_d1;encrypt=true;trustServerCertificate=true;";
    public static final String LiveDbusername = "Field2020";
    public static final String LiveDbpassword = "Fieldlytics@#@2020";

    public static final String LocalDatabaseurl = "jdbc:sqlserver://DESKTOP-OET6KL6\\SQLEXPRESS:1433;DatabaseName=FrameX_Mobile_DB;encrypt=true;trustServerCertificate=true";
    public static final String LocalDbusername = "sa";
    public static final String LocalDbpassword = "sa";


    public static String Categorymasterquery = "select Name  from Categorymaster where status = 1 order by sequence ";
    public static String FormFieldsquery = "select Replace(fm.Name,''_'','' '') as formName, Replace(ffd.FieldName,''_'','' '') as FieldName,DataType,ControlType,Required from FormFieldDetail ffd \n" +
            "join FormMaster fm on fm.FormID = ffd.FormID where fm.FormStatus = 1 and ffd.ForDEO = 1 and ffd.ForPM = {1} and  fm.Name  = {0} " +
            "order by fm.FormSequence,ffd.Sequence";
    public static String ProductColumnquery = "select FieldName as ProductColumn  from FormFieldDetail ffd \n" +
            "join FormMaster fm on fm.FormID = ffd.FormID where fm.FormStatus = 1 and ffd.ForDEO = 1 and ffd.ForPM = 1 and  fm.Name  = {0} ";

    public static String FormMasterquery = "select  Replace(Name,'_',' ') as FormName,IsQuestionForm from FormMaster where FormStatus = 1 order by FormSequence";
    public static String Productquery = "select {3} as ProductName from {0}master sm" +
            " join {0}Relation sr on sr.{0}ID = sm.{0}ID \n" +
            "join CategoryMaster cm on cm.CategoryID = sm.CategoryID" +
            " where TargetID = {1} and name = {2}";
    public static String EnumFieldquery="select ffo.FormFieldOption as FieldOption from FormFieldOption ffo " +
            "join FormFieldDetail ffd on ffd.FormFieldID = ffo.FormFieldID where Replace(FieldName,''_'','' '') ={0}";

    public static String ReportPath = System.getProperty("user.dir") + "/src/test/resources/Reports/" + fileName;
    public static   String sendermail  = "fieldlytics.test@gmail.com";
    public static  String senderpassword ="nvscghckuwobmhiv";
    public static  String subject = "FrameX Automation Results";

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

    public static String logfilepath = System.getProperty("user.dir") + "/src/test/resources/Logs/FrameXmobile.log";
}
