package Utilities;

import java.util.List;

import static Pages.Downloadcalls_page.getyesterdaydate;
import static Utilities.DBConfig.*;
import static Utilities.Utils.fetchTargetsFromDatabase;

public class Rough {
    
    public static void main(String[] args) throws Exception {

       List<String> todaycalls =  fetchTargetsFromDatabase("TGS1296").get("todaycalls");
        List<String> downloadcalls =  fetchTargetsFromDatabase("TGS1296").get("downloadcalls");

       for(String todaycall : todaycalls){

           System.out.println("Todaycalls : "+todaycall);
       }

        for(String downloadcall : downloadcalls){

            System.out.println("download : "+downloadcall);
        }
    }



}
