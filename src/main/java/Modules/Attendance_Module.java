package Modules;

import Base.AppiumTestSetup;
import UiObjects.Attendance_Objects;
import UiObjects.HomePage_Objects;
import Utilities.Actions;

import java.util.LinkedHashMap;

import static Listeners.FrameX_Listeners.formatData;
import static Listeners.FrameX_Listeners.testReport;
import static UiObjects.Attendance_Objects.*;
import static UiObjects.HomePage_Objects.Attendance;
import static Utilities.Actions.*;

public class Attendance_Module extends AppiumTestSetup {

    public static boolean attendancesubmission(String attendancetype,String image) throws InterruptedException {
        try {
            click("ACCESSIBILITYID", Attendance);
            click("ACCESSIBILITYID", Attendance_Objects.Present);
            click("ACCESSIBILITYID", Attendance_Objects.Present);

            // Check if image is required and interact accordingly
            if(image.equalsIgnoreCase("True")){
                click("xpath", Attendancecamera);
                Thread.sleep(1000);
                click("xpath", shutterbutton);
            }
            click("ACCESSIBILITYID", Submit);

            // Handling negative scenario when image is not required but provided
            if(!image.equalsIgnoreCase("True")){
                if(Source(imgmandatorymsg)){
                    testReport.get().fail(formatData("Negative data given : "+imgmandatorymsg));
                    log.error("Negative data given : "+imgmandatorymsg);
                    return false;
                }
            }
            Thread.sleep(3000);
            // Verifying attendance submission success/failure
            if(Source(attendancesavedmsg)){
                Thread.sleep(1000);
                click("ACCESSIBILITYID", Attendance);
                if(Source(Attendacemarkedmessage)){
                    click("ACCESSIBILITYID",Present);
                    if(!Source(Leave)){
                        testReport.get().pass(formatData("Attendance Submitted successfully"));
                        log.info("Attendance Submitted successfully");
                        return true;
                    }
                }else{
                    driver.navigate().back();
                    click("ACCESSIBILITYID", HomePage_Objects.Callplan);
                    driver.navigate().back();
                    click("ACCESSIBILITYID", Attendance);
                    if(Source(Attendacemarkedmessage)) {
                        click("ACCESSIBILITYID", Present);
                        if (!Source(Leave)) {
                            testReport.get().pass(formatData("Attendance Submitted successfully"));
                            log.info("Attendance Submitted successfully");
                            return true;
                        } else {
                            testReport.get().fail(formatData("Attendance Submission Failed"));
                            log.error("Attendance Submission Failed");
                            return false;
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error("Exception occurred: " + e.getMessage());
            testReport.get().fail(formatData("Exception occurred: " + e.getMessage()));
            return false;
        }
        return false;
    }


    // Method to validate image requirement for different attendance types
    public static boolean validateattendanceimagerequired(String attendancetype) throws InterruptedException {

        try{
            // Checking image requirement based on attendance type
            if(attendancetype.equalsIgnoreCase("present")){
                if(!Source(Present)){
                    click("ACCESSIBILITYID", Attendance);
                }
                click("ACCESSIBILITYID",Present);
                click("ACCESSIBILITYID",Present);
                if(isElementDisplayed("xpath",Attendancecamera)){
                    testReport.get().pass(formatData("Image is Required for Present"));
                    click("ACCESSIBILITYID",Present);
                    return true;
                }else{
                    testReport.get().fail(formatData("Image is Required for Present but camera option is not displayed "));
                    log.error("Image is Required for Present but camera option is not displayed");
                    return false;
                }
            } else if (attendancetype.equalsIgnoreCase("Leave")) {
                if(!Source(Leave)){
                    click("ACCESSIBILITYID", Attendance);
                }
                click("ACCESSIBILITYID",Leave);
                if(Source(imgnonmandatorymsg)){
                    testReport.get().pass(formatData("Image is not Required for leave"));
                    click("ACCESSIBILITYID",Leave);
                    return true;
                }else{
                    testReport.get().fail(formatData("Image is not Required for Leave but camera option is displayed "));
                    log.error("Image is not Required for Leave but camera option is displayed");
                    return false;
                }
            } else if (attendancetype.equalsIgnoreCase("absent")) {
                if(!Source(Absent)){
                    click("ACCESSIBILITYID", Attendance);
                }
                click("ACCESSIBILITYID",Absent);
                if(Source(imgnonmandatorymsg)){
                    testReport.get().pass(formatData("Image is not Required for absent"));
                    click("ACCESSIBILITYID",Absent);
                    return true;
                }else{
                    testReport.get().fail(formatData("Image is not Required for absent but camera option is displayed "));
                    log.error("Image is not Required for absent but camera option is displayed");
                    return false;
                }
            } else if (attendancetype.equalsIgnoreCase("At office")) {
                if(!Source(atoffice)){
                    click("ACCESSIBILITYID", Attendance);
                }
                click("ACCESSIBILITYID",atoffice);
                if(isElementDisplayed("xpath",Attendancecamera)){
                    testReport.get().pass(formatData("Image is Required for At office"));
                    click("ACCESSIBILITYID",atoffice);
                    return true;
                }else{
                    testReport.get().fail(formatData("Image is Required for At office but camera option is not displayed "));
                    log.error("Image is Required for At office but camera option is not displayed ");
                    return false;
                }
            }else if (attendancetype.equalsIgnoreCase("Week off")){
                if(!Source(weekoff)){
                    click("ACCESSIBILITYID", Attendance);
                }
                click("ACCESSIBILITYID",weekoff);
                if(Source(imgnonmandatorymsg)){
                    testReport.get().pass(formatData("Image is not Required for Week off"));
                    click("ACCESSIBILITYID",weekoff);
                    return true;
                }else{
                    testReport.get().fail(formatData("Image is not Required for Week off but camera option is displayed "));
                    log.error("Image is not Required for Week off but camera option is displayed");
                    return false;
                }
            }else if (attendancetype.equalsIgnoreCase("Holiday")){
                if(!Source(holiday)){
                    click("ACCESSIBILITYID", Attendance);
                }
                click("ACCESSIBILITYID",holiday);
                if(Source(imgnonmandatorymsg)){
                    testReport.get().pass(formatData("Image is not Required for Holiday"));
                    click("ACCESSIBILITYID",holiday);
                    return true;
                }else{
                    testReport.get().fail(formatData("Image is not Required for Holiday but camera option is displayed "));
                    log.error("Image is not Required for Holiday but camera option is displayed");
                    return false;
                }
            }else if (attendancetype.equalsIgnoreCase("Tour")){
                if(!Source(tour)){
                    click("ACCESSIBILITYID", Attendance);
                }
                click("ACCESSIBILITYID",tour);
                if(Source(imgnonmandatorymsg)){
                    testReport.get().pass(formatData("Image is not Required for Tour"));
                    click("ACCESSIBILITYID",tour);
                    return true;
                }else{
                    testReport.get().fail(formatData("Image is not Required for Tour but camera option is displayed "));
                    log.error("Image is not Required for Tour but camera option is displayed");
                    return false;
                }
            }else if (attendancetype.equalsIgnoreCase("Training")){
                if(!Source("Training")){
                    click("ACCESSIBILITYID", Attendance);
                }
                click("ACCESSIBILITYID","Training");
                if(isElementDisplayed("xpath",Attendancecamera)){
                    testReport.get().pass(formatData("Image is  Required for Training"));
                    click("ACCESSIBILITYID","Training");
                    return true;
                }else{
                    testReport.get().fail(formatData("Image is Required for Training but camera option is not displayed "));
                    log.error("Image is Required for Training but camera option is not displayed ");
                    return false;
                }
            }else if (attendancetype.equalsIgnoreCase("Monthly Meeting")){
                if(!Source("Monthly Meeting")){
                    click("ACCESSIBILITYID", Attendance);
                }
                click("ACCESSIBILITYID","Monthly Meeting");
                if(isElementDisplayed("xpath",Attendancecamera)){
                    testReport.get().pass(formatData("Image is  Required for Monthly Meeting"));
                    click("ACCESSIBILITYID","Monthly Meeting");
                    return true;
                }else{
                    testReport.get().fail(formatData("Image is Required for Training but camera option is not displayed "));
                    log.error("Image is Required for Training but camera option is not displayed ");
                    return false;
                }
            }
        }catch (Exception e) {
            log.error("Exception occurred: " + e.getMessage());
            testReport.get().fail(formatData("Exception occurred: " + e.getMessage()));
            return false;
        }
        return false;
    }
}