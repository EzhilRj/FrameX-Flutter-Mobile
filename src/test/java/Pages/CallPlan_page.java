package Pages;

import java.util.List;

public class CallPlan_page {

	public static String sync = "Sync";
	public static String TargetID = "//android.view.View[contains(@content-desc, 'Target ID: "+38356+"')]";
	public static String targetdownloadnotification = "//*[@text='Downloaded Successfully for target " + 38356+"']";
	public static String TodayCalls = "//android.view.View[contains(@content-desc, 'Today Calls')]";
	public static String syncbuttonvalidation = "You can click after 5 minutes, since it is in Downloading or Uploading Call process.";
	public static String startworkvalidation = "You cannot view this target because another target  38353 is already in process. Please complete it first";
	public static String formcompleted = "Store Front Photo Completed";
	public static String UploadcallButton = "Upload Call";
	public static String CloseCallButton = "Close Call";
	//public static String targetUploadnotification =  "//*[@text='Uploading process for "+tid+" is Completed']";  ////android.view.View[@content-desc="You have already uploaded 14906 target"]
	public static String Startworkbutton = "//android.view.View[@content-desc='Start Work']";
	public static String samplephotobtn = "Sample Photo";
	public static String samplephotonotavailable_msg = "Sample Photo not available";
	public static String imagemandatory_msg = "Please, Take  Photo";
	public static String fieldsmandatory_msg = "Fields listed below are Required and cannot be left blank";
	public static String categorycompleted_msg = "Store Front Photo Completed";
	public static String mandatorydropdown_msg = "Enter DropDownList";
	public static String Uploadcallconfirmpopup = "Are you sure you want to upload Call ?";
	public static String Viewimagesbutton = "View Images";
	public static String Uploadcallsbutton = "Upload Calls";
	public static String uploadsuccessActivitylog_msg = "Target 38353 successfully uploaded";
	public static String stattinguploadprocessActivitylog_msg = "Starting the Upload process for Target . 38353 08/01/24 19:34:23";
	public static String sospopup_msg = "Industry Facing should be greater than Our Brand Facing";
	public static String enterourbrandfacing_msg = "Enter Our Brand Facing";
	public static String enterIndustryfacing_msg = "Enter Industry Facing";
	public static String NextButton = "Next";
	public static String Donebutton = "Done";
	public static String Camerabutton = "//android.view.View[@content-desc='Photo']/android.view.View[3]";
	public static String Shutterbutton = "//android.view.View[3]";
	public static String Perfectstorescorepopup = "//android.view.View[@content-desc='Perfect Store Score']";
	public static String Okbutton = "ok";
	public static String psssuccs = "Pss Upload Success";
	public static String Yesbutton = "Yes";
	public static String Nobutton = "No";
	public static String Attendancepopup = "Please also mark your Attendance for today!";
	public static String Underprocesspopup = "Attendance is under process  please Proceed to next step ?";
	public static String Camerabutton_M = "//android.view.View[@content-desc='Photo *']/android.view.View[2]";
	public static String Camerabutton_NM= "//android.view.View[@content-desc='Photo']/android.view.View[2]";

	public static String uploadcallerr_msg= "Internal server error, upload could not completed. Contact support 500";


	public static String generatecategorylocator(String cate){
	  String categorylocator = "//android.view.View[contains(@content-desc, '"+cate+"')]";
		return categorylocator;
	}

	public static String generateproductlocator(String prodname){
		String productnamelocator = "//android.view.View[@content-desc=\"" + prodname + "\"]";
		return productnamelocator;
	}


	//This Return Xpath
	public static String generatetextfieldlocator(String field){
		String FormslistsLocator = " //*[@hint='"+field+"']";
		return FormslistsLocator;
	}


/*	public static String DataTypeGetter(String form) throws Exception {

		List<Object> fieldnames = getDataObject("select FieldName,DataType,ControlType from FormFieldsDetail where [Form Name] = '"+form+"'");

		return fieldnames.toString();
	}*/


}
