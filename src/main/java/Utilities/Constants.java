package Utilities;

import static Utilities.Utils.getDeviceName;

public class Constants {

    public static final String Devicename  = getDeviceName();
    public static final String Apppath = System.getProperty("user.dir") + "\\src\\main\\resources\\Apps\\FrameFlutterTest.apk";
    public static final String ServerPath = "C:\\Users\\Ezhil Rj\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    public static final String Nodepath = "C:\\Program Files\\nodejs\\node.exe";
    public static final String Screenshotpath = System.getProperty("user.dir") + "\\Screenshots";
    public static final String ScreenRecpath  =   System.getProperty("user.dir")+"\\Screen Recordings";
    public static final String Excelpath  = System.getProperty("user.dir") + "\\src\\main\\resources\\Datas\\FrameXMobile_Datas.xlsx";
    public static String LogConfiguration = System.getProperty("user.dir") + "\\Log4j.properties";

    public static final String Databaseurl = "jdbc:sqlserver://65.1.119.118;DatabaseName=framedemo_d1;encrypt=true;trustServerCertificate=true";

    public static final String Dbusername = "Field2020";
    public static final String Dbpassword = "Fieldlytics@#@2020";
    public static final String classname = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public static String Loginquery = "select * from Login";
    public static String Categorymasterquery = "select Name  from Categorymaster where status = 1 ";
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
}
