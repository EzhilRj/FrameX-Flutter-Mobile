package Pages;

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
    public static final String atofficesavedmsg = "Your Attendance is Marked for Today  At office";
    public static final String weekoffsavedmsg = "Your Attendance is Marked for Today  Week off";
    public static final String holidaysavedmsg = "Your Attendance is Marked for Today  Holiday";
    public static final String toursavedmsg = "Your Attendance is Marked for Today  Tour";
    public static final String trainingsavedmsg = "Your Attendance is Marked for Today  Training";
    public static final String monthlymeetingsavedmsg = "Your Attendance is Marked for Today  Monthly Meeting";
    public static final String resignsavedmsg = "Your Attendance is Marked for Today  Resign";
    public static String Attendacealreadysaved = "Attendance data is already saved";
    public static String Attendacemarkedmessage = "Your attendance Marked for today as Present";

    public static void performAttendanceActivity(String type , String img) throws InterruptedException {

        click("ACCESSIBILITYID", Attendance_page.Present);
        click("ACCESSIBILITYID", type);

        // Check if image is required and interact accordingly
        if(img.equalsIgnoreCase("True")){
            click("xpath", Attendancecamera);
            Thread.sleep(1000);
            click("xpath", shutterbutton);
        }
        click("ACCESSIBILITYID", Submit);

    }

}



