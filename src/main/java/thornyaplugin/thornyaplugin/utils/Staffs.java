package thornyaplugin.thornyaplugin.utils;

import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.util.ArrayList;

public class Staffs {

    private static ThornyaPlugin pl;

    public Staffs(ThornyaPlugin main){
        pl = main;
    }

    public static ArrayList<String> getStaffs(){

        ArrayList<String> staffs = new ArrayList<>();

        if (!pl.getFile("configuration").getStringList("staffs").isEmpty()) {
            for (String str : pl.getFile("configuration").getStringList("staffs")) {
                staffs.add(str);
            }
        }

        return staffs;
    }

}
