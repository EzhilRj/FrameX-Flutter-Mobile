package Tests;

import Utilities.Utils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Hashtable;

import static Listeners.FrameX_Listeners.logAndReportFailure;
import static Modules.Resourcentre_Module.navigateToResourceCentrePage;
import static Modules.Resourcentre_Module.validateFiles;
import static Pages.HomePage_page.ResourceCentre;
import static Utilities.TestDataUtil.gettestdata;
import static Utilities.Utils.navigateto;
import static Utilities.Utils.sourceExists;

public class ResourceCentreTest {

    /**
     * This method is used to verify the resource centre files.
     *
     * @throws Exception if an error occurs during the execution of the method.
     *
     */
    @Test(priority = 1, groups = {"smoke", "regression"},enabled = true)
    public static void TC001_VerifyResourcecentrefiles() throws Exception {
        navigateto(ResourceCentre);
        if(!sourceExists(ResourceCentre)){
            logAndReportFailure("ResourceCentre module is not displayed");
            Assert.fail("ResourceCentre module is not displayed");
        }
    }

    /**
     * This method is used to verify if a PPTX file is downloaded successfully.
     *
     * @throws Exception if an error occurs during the execution of the method.
     *
     *
     * @returns void.
     */
    @Test(priority = 2, groups = {"smoke", "regression"},enabled = true)
    public static void TC002_VerifyPPTXFileDownloaded() throws Exception {

        navigateToResourceCentrePage(ResourceCentre);
        JSONObject resourcecenterdata = gettestdata("ResourceCenter","validatingpptx");
        validateFiles(resourcecenterdata.getString("filename"));
    }


    /**
     * This method is used to verify if an MP4 file is downloaded successfully.
     *
     * @throws Exception if an error occurs during the execution of the method.
     * @returns void.
     */
    @Test(priority = 3, groups = {"smoke", "regression"},enabled = true)
    public static void TC003_VerifyMP4FileDownloaded() throws Exception {

        navigateToResourceCentrePage(ResourceCentre);
        JSONObject resourcecenterdata = gettestdata("ResourceCenter","validatingmp4");
        validateFiles(resourcecenterdata.getString("filename"));

    }


    /**
     * This method is used to verify if a PDF file is downloaded successfully.
     *
     * @throws Exception if there is an error while validating the PDF file.
     *
     * @returns void.
     */
    @Test(priority = 4, groups = {"smoke", "regression"},enabled = true)
    public static void TC004_VerifyPDFFileDownloaded() throws Exception {

        navigateToResourceCentrePage(ResourceCentre);
        JSONObject resourcecenterdata = gettestdata("ResourceCenter","validatingpdf");
        validateFiles(resourcecenterdata.getString("filename"));

    }



    /**
     * This method is used to verify if a DOCX file is downloaded successfully.
     *
     * @throws Exception if an error occurs during the execution of the method.
     * @returns void.
     */
    @Test(priority = 5, groups = {"smoke", "regression"},enabled = true)
    public static void TC005_VerifyDOCXFileDownloaded() throws Exception {

        navigateToResourceCentrePage(ResourceCentre);
        JSONObject resourcecenterdata = gettestdata("ResourceCenter","validatingdocx");
        validateFiles(resourcecenterdata.getString("filename"));

    }

    /**
     * This method is used to verify the download of a JPG file.
     *
     * @throws Exception if an error occurs during the execution of the test case
     * @returns void
     */
    @Test(priority = 6, groups = {"smoke", "regression"},enabled = true)
    public static void TC006_VerifyJPGFileDownloaded() throws Exception {

        navigateToResourceCentrePage(ResourceCentre);
        JSONObject resourcecenterdata = gettestdata("ResourceCenter","validatingjpg");
        validateFiles(resourcecenterdata.getString("filename"));

    }


    /**
     * This method is used to verify if an XLSX file is downloaded successfully.
     *
     * @throws Exception if an error occurs during the execution of the method.
     * @returns void.
     */
    @Test(priority = 7, groups = {"smoke", "regression"},enabled = true)
    public static void TC007_VerifyXLSXFileDownloaded() throws Exception {

        navigateToResourceCentrePage(ResourceCentre);
        JSONObject resourcecenterdata = gettestdata("ResourceCenter","validatingxlsx");
        validateFiles(resourcecenterdata.getString("filename"));

    }


}
