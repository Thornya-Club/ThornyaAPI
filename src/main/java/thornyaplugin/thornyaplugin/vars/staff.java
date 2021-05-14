package thornyaplugin.thornyaplugin.vars;

import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.util.HashMap;
import java.util.Map;

public class staff {

    public Map<String, Boolean> staffs = new HashMap<String, Boolean>();
    private ThornyaPlugin pl;

    public staff(ThornyaPlugin main){

        this.pl = main;

    }
    public void staffDetect(){

        if (!pl.getFile("configuration.yml").getStringList("staffs").isEmpty()) {
            for (String str : pl.getFile("configuration.yml").getStringList("staffs")) {
               this.staffs.put(str, false);
            }
        }
    }

}
