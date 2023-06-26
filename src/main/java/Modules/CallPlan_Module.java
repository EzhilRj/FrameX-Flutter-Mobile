package Modules;

import Base.Setup;

import java.text.MessageFormat;
import java.util.*;

import static UiObjects.CallPlan_Objects.*;
import static UiObjects.HomePage_Objects.Callplan;
import static Utilities.Actions.*;
import static Utilities.Constants.*;
import static Utilities.DBConfig.*;
import static Utilities.Utils.Datasetter;

public class CallPlan_Module extends Setup {

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
                        WebdriverWait("ACCESSIBILITYID", form,10);
                        //Getting Productnames
                        //Get  Product Column Header
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
                                        String fieldName = (String) fieldData.get("FieldName");
                                        String Ctrltype = (String) fieldData.get("ControlType");
                                        String Datatype = (String) fieldData.get("DataType");
                                        if (Source(fieldName)) {
                                            if (Ctrltype.equals("TextBox")) {
                                                if(Datatype.equalsIgnoreCase("int")){
                                                    Enter("Xpath", SetTextFieldAttribute(fieldName), Datasetter(Datatype,fieldName));
                                                } else if (Datatype.contains("Varchar")) {
                                                    Enter("Xpath", SetTextFieldAttribute(fieldName), Datasetter(Datatype,fieldName));
                                                }
                                            } else if (Ctrltype.equals("DropDownList")) {
                                                click("ACCESSIBILITYID", fieldName);
                                                click("ACCESSIBILITYID", Stocknotavailable);
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

