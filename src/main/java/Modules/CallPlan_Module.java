package Modules;

import Base.Setup;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.MessageFormat;
import java.util.*;

import static UiObjects.CallPlan_Objects.*;
import static UiObjects.HomePage_Objects.Callplan;
import static Utilities.Actions.*;
import static Utilities.Constants.*;
import static Utilities.DBConfig.*;
import static Utilities.Utils.*;

public class CallPlan_Module extends Setup {

    public static  String fieldName;
    public static  String Ctrltype;
    public static String Datatype;

    public static void CallPlan() throws Exception {

        click("ACCESSIBILITYID", Callplan);
        driver.navigate().back();
        click("ACCESSIBILITYID", Callplan);
        click("Xpath", TargetID);
        WebdriverWait("xpath", Startworkbutton, 10);
        click("Xpath", Startworkbutton);
        if (Source("please wait")){
            Thread.sleep(4000);
            click("ACCESSIBILITYID", Callplan);
            click("Xpath", TargetID);
            WebdriverWait("xpath", Startworkbutton, 10);
            if (Source("Start Work")) {
                click("xpath", Startworkbutton);
            }
        }
    }


    public static void DataBinder() throws Exception {

        //Getting Category lists
        List<String> categoryNames = GetDatas(Categorymasterquery, "Name");
        for (String category : categoryNames) {
            if (Source(category)) {
                click("Xpath", SetCategoryAttribute(category));
                //Getting Formnames
                List<String> formNames = GetDatas(FormMasterquery, "FormName");
                for (String form : formNames) {
                    if (Source(form)) {
                        String formName = form.replace(" ","_");
                        //Getting Productnames
                        String ProductColumn = GetDatas( MessageFormat.format(ProductColumnquery , "'"+formName+"'"),"ProductColumn").get(0);
                        // Get Product  Column List
                        List<String> Productnames = GetDatas( MessageFormat.format(Productquery , formName,"10535","'"+ category +"'",ProductColumn),"ProductName");
                        for (String productName : Productnames) {
                            if(Source(productName)){
                                click("Xpath",SetCategoryAttribute(productName));
                                //Getting Fieldnames
                                List<Object> fieldnames = GetDataObject( MessageFormat.format(FormFieldsquery , "'"+formName+"'"));
                                for (Object field : fieldnames) {
                                    if (field instanceof LinkedHashMap<?, ?> fieldData) {
                                        fieldName = (String) fieldData.get("FieldName");
                                        Ctrltype = (String) fieldData.get("ControlType");
                                        Datatype = (String) fieldData.get("DataType");
                                        if (Source(fieldName)) {
                                            if (Ctrltype.equals("TextBox")) {
                                                if(Datatype.equalsIgnoreCase("int")){
                                                    Enter("Xpath", SetTextFieldAttribute(fieldName), Datasetter(Datatype,fieldName));
                                                } else if (Datatype.contains("Varchar")) {
                                                    Enter("Xpath", SetTextFieldAttribute(fieldName), Datasetter(Datatype,fieldName));
                                                }
                                            } else if (Ctrltype.equals("DropDownList")) {
                                                Dropdownsetter();
                                            } else if (Ctrltype.contains("FileUpload")) {
                                                ImageCapture();
                                            }
                                        }
                                    }
                                }
                            }
                            driver.hideKeyboard();
                        }
                        click("Xpath",NextButton);
                    }
                }
            }
        }
    }

}

