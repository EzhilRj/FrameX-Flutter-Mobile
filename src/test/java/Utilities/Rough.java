package Utilities;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static Pages.CallPlan_page.gettargetsfrom_db;
import static Utilities.DBConfig.*;
import static Utilities.TestDataUtil.gettestdata;
import static Utilities.Utils.generateFormattedDate;

public class Rough {


    public static void main(String[] args) throws Exception {

        JSONObject user1 = gettestdata("Login","User1");
        System.out.println(gettargetsfrom_db(user1.getString("username")));
    }
}
