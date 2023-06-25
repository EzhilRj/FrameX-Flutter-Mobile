package UiObjects;

import java.util.List;

import static Utilities.DBConfig.GetDataObject;

public class CallPlan_Objects {

	public static String sync = "Sync";
	public static String TargetID = "//android.view.View[contains(@content-desc, 'Target ID: 10043')]";
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

	//public static String NextButton = "//android.view.View[@content-desc='Next']";

	public static String DataTypeGetter(String form) throws Exception {

		List<Object> fieldnames = GetDataObject("select FieldName,DataType,ControlType from FormFieldsDetail where [Form Name] = '"+form+"'");


		return fieldnames.toString();
	}

	// Form
	public static String Form_StockAvailablity = "Stk_Availability";
	// Form_StockAvailablity Fields
	public static String Productcards = "//android.view.View[@content-desc=\"Real Activ Mixed Fruit 1 Ltr *\"]";
	public static String JuiceBrandName = "Real Activ Mixed Fruit 1 Ltr";
	public static String Quantity = "//android.widget.EditText(@text, 'Quantity *')]";
	public static String ReasonMbq = "Reason OSA Less than MBQ *";
	public static String Stocknotavailable = "Stock Not Available";
	public static String planogram = "Store Planogram";
	public static String NotAvailable = "NA";
	public static String Reciept = "//android.widget.EditText[contains(@text, 'Receipt *')]";
	public static String FISOLSLQTY = "//android.widget.EditText[contains(@text, 'FIFO LSL Qty')]";
	public static String FaclingLines = "//android.widget.EditText[contains(@text, 'No of Facing Lines')]";
	public static String NextButton = "//android.view.View[@content-desc='Next']";

	// Form
	public static String Form_ShareofShelf = "SOS";
	// Form_ShareofShelf Fields
	public static String SosProductname = "100 JUICE 1 LTR CAT";
	public static String SosProductTargetPercentage = "SOS_Target_Percentage 33 % ";
	public static String SosIndustryFacing = "//android.widget.EditText[contains(@text, 'Industry Facing *')]";
	public static String SosOurBrandFacing = "//android.widget.EditText[contains(@text, 'Our Brand Facing *')]";
	public static String SosGapFacings = "//android.widget.EditText[contains(@text, 'Gap Facings')]";
	public static String SosStockPlacement = "Stock Placement *";

//Form 
	public static String BrandFacings = "Brand_Facings";
	// Form_BrandFacings Fields
	public static String BrandFacingProductname0 = "Brand ACTIV FIBER 1 LTR ";
	public static String BrandFacing0 = "//android.widget.EditText[contains(@text, 'Brand Facings *')]";
	public static String BrandFacingProductname1 = "Brand Coconut-all packs *";
	public static String BrandFacing1 = "//android.widget.EditText[contains(@text, 'Brand Facings *')]";

	//Form
	public static String Form_Promotion = "Promotion";
	// Promotion Fields
	public static String PromotionProductname = "Buy Activ Coconut Water 200Ml Get Price Off";
	public static String impstatus = "Imp Status *";
	public static String impstatusddoption0 = "Yes As Approved";
	public static String impstatusddoption1 = "Yes Higher than Approved";
	public static String impstatusddoption2 = "Yes Lower than Approved";
	public static String impstatusddoption3 = "No Stock";
	public static String impstatusddoption4 = "Promo Not Active";
	public static String IfNothenReason = "If No then Reason *";
	public static String IfNothenReasonddoption1 = "Stock Not Present";
	public static String IfNothenReasonddoption2 = "Shelf Talker Absent";
	public static String IfNothenReasonddoption3 = "NA";

	//Form
	public static String  Form_Picture = "Picture";
	//Picture fields
	public static String Camerabutton = "//android.view.View[@content-desc=\"Photo\"]/android.view.View[3]";
	public static String Shutterbutton = "//android.view.View[3]";
	public static String PhotoFields = "//android.view.View[@content-desc='Photo']";
	

	public static String Aircare = "//android.view.View[contains(@content-desc, 'AIR CARE')]";
	public static String BabyCare = "//android.view.View[contains(@content-desc, 'BABY CARE')]";
	public static String Bleach = "//android.view.View[contains(@content-desc, 'BLEACH')]";
	public static String DCP = "//android.view.View[contains(@content-desc, 'DCP')]";
	public static String DigesConfec = "//android.view.View[contains(@content-desc, 'DIGES CONFEC')]";
	public static String HairOil = "//android.view.View[contains(@content-desc, 'HAIR OIL')]";
	public static String Honey = "//android.view.View[contains(@content-desc, 'HONEY')]";
	public static String Juices = "//android.view.View[contains(@content-desc, 'JUICES')]";
	public static String Oralcare = "//android.view.View[contains(@content-desc, 'ORAL CARE')]";
	public static String Shampoo = "//android.view.View[contains(@content-desc, 'SHAMPOOS')]";
	public static String Toiletcleaner = "//android.view.View[contains(@content-desc, 'TOILET CLEANER')]";
	public static String GLUCOSE = "//android.view.View[contains(@content-desc, 'GLUCOSE')]";
	public static String Drinks = "//android.view.View[contains(@content-desc, 'DRINKS')]";
	public static String CULINARY = "//android.view.View[contains(@content-desc, 'CULINARY')]";
	public static String MOSQREPELLANT = "//android.view.View[contains(@content-desc, 'MOSQ REPELLANT')]";
	public static String OTCBRANDED = "//android.view.View[contains(@content-desc, 'OTC BRANDED')]";
	public static String SKINCARE = "//android.view.View[contains(@content-desc, 'SKIN CARE')]";
	public static String REMARKS = "//android.view.View[contains(@content-desc, 'REMARKS')]";
	public static String UploadcallButton = "//android.view.View[@content-desc='Upload Call']";
	public static String CloseCallButton = "//android.view.View[@content-desc='Close Call']";

}
