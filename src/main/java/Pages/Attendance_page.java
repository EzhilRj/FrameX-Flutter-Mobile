package Pages;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static Utilities.Actions.click;
import static Utilities.Utils.generatedateandtime;

public class Attendance_page {

    public static final String attendance_dropdown = "Present";
    public static final String Present = "Present";
    public static final String Leave = "Leave";
    public static final String Absent = "Absent";
    public static final String atoffice = "At Office";
    public static final String weekoff = "Week off";
    public static final String holiday = "Holiday";
    public static final String tour = "Tour";
    public static final String resign = "Resign";
    public static String Attendancecamera = "//android.view.View[2]";
    public static String Frontcamerabutton = "//android.widget.ImageView[4]";
    public static String shutterbutton = "//android.view.View[3]";
    public static String Submit = "Submit";
    public static String imgnonmandatorymsg = "No Image Required";
    public static String imgmandatorymsg = "Please, Take Photo for submit attandance.";
    public static final String presentsavedmsg = "Your Attendance is Marked for Today  Present "+generatedateandtime();
    public static final String leavesavedmsg = "Your Attendance is Marked for Today  Leave";
    public static final String absentsavedmsg = "Your Attendance is Marked for Today  Absent";
    public static final String atofficesavedmsg = "Your Attendance is Marked for Today  At Office";
    public static final String weekoffsavedmsg = "Your Attendance is Marked for Today  Week off";
    public static final String holidaysavedmsg = "Your Attendance is Marked for Today  Holiday";
    public static final String toursavedmsg = "Your Attendance is Marked for Today  Tour";
    public static final String trainingsavedmsg = "Your Attendance is Marked for Today  Training";
    public static final String monthlymeetingsavedmsg = "Your Attendance is Marked for Today  Monthly Meeting";
    public static final String resignsavedmsg = "Your Attendance is Marked for Today  Resign";
    public static String Attendacealreadysaved = "Attendance data is already saved";

    public static HashMap<String,String>attendancemessages(){

        LinkedHashMap<String,String> attendancesuccessmesssages = new LinkedHashMap<String,String>();
        attendancesuccessmesssages.put("Present",presentsavedmsg);
        attendancesuccessmesssages.put("Absent",absentsavedmsg);
        attendancesuccessmesssages.put("Leave",leavesavedmsg);
        attendancesuccessmesssages.put("At office",atofficesavedmsg);
        attendancesuccessmesssages.put("Week off",weekoffsavedmsg);
        attendancesuccessmesssages.put("Holiday",holidaysavedmsg);
        attendancesuccessmesssages.put("Tour",toursavedmsg);
        attendancesuccessmesssages.put("Training",trainingsavedmsg);
        attendancesuccessmesssages.put("Monthly Meeting",monthlymeetingsavedmsg);
        attendancesuccessmesssages.put("Resign",resignsavedmsg);

        return attendancesuccessmesssages;
    }

    public static HashMap<String,String>attendanceimagevalidation(){

        LinkedHashMap<String, String> attendanceMap = new LinkedHashMap<>();
        attendanceMap.put("Present", Attendancecamera);
        attendanceMap.put("Leave", imgnonmandatorymsg);
        attendanceMap.put("Absent", imgnonmandatorymsg);
        attendanceMap.put("At Office", Attendancecamera);
        attendanceMap.put("Week off", imgnonmandatorymsg);
        attendanceMap.put("Holiday", imgnonmandatorymsg);
        attendanceMap.put("Tour", imgnonmandatorymsg);
        attendanceMap.put("Training", Attendancecamera);
        attendanceMap.put("Monthly Meeting", Attendancecamera);

        return attendanceMap;
    }

    public static void performAttendanceActivity(String type , String img) throws InterruptedException {

        click("ACCESSIBILITYID", Attendance_page.Present);
        click("ACCESSIBILITYID", type);

        if(type.equalsIgnoreCase("Present")||type.equalsIgnoreCase("At Office")||type.equalsIgnoreCase("Training")||type.equalsIgnoreCase("Monthly Meeting")){
            // Check if image is required and interact accordingly
            if(img.equalsIgnoreCase("True")){
                click("xpath", Attendancecamera);
                Thread.sleep(1000);
                click("xpath", shutterbutton);
            }
        }
        click("ACCESSIBILITYID", Submit);

    }

}



