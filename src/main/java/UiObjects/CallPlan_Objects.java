package UiObjects;

import java.util.List;

import static Utilities.DBConfig.GetDataObject;

public class CallPlan_Objects {

	public static String sync = "Sync";
	public static String tid = "10535";
	public static String TargetID = "//android.view.View[contains(@content-desc, 'Target ID: "+tid+"')]";
	public static String notification = "//*[@text='Target "+tid+" downloaded successfully.']";
	public static String TodayCalls = "//android.view.View[contains(@content-desc, 'Today Calls')]";
	public static String Startworkbutton = "//android.view.View[@content-desc='Start Work']";



	//This Return Xpath
	public static String SetCategoryAttribute(String cate){

	  String categorylocator = "//android.view.View[contains(@content-desc, '"+cate+"')]";

		return categorylocator;
	}


	//This Return Xpath
	public static String SetTextFieldAttribute(String field){

		String FormslistsLocator = " //*[@text='"+field+"']";

		return FormslistsLocator;
	}

	public static String DataTypeGetter(String form) throws Exception {

		List<Object> fieldnames = GetDataObject("select FieldName,DataType,ControlType from FormFieldsDetail where [Form Name] = '"+form+"'");

		return fieldnames.toString();
	}

	public static String NextButton = "//android.view.View[@content-desc='Next']";
	public static String Donebutton = "//android.view.View[@content-desc='Done']";

	public static String Camerabutton = "//android.view.View[@content-desc='Photo']/android.view.View[3]";
	public static String Shutterbutton = "//android.view.View[3]";

	public static String UploadcallButton = "//android.view.View[@content-desc='Upload Call']";
	public static String CloseCallButton = "//android.view.View[@content-desc='Close Call']";


}
