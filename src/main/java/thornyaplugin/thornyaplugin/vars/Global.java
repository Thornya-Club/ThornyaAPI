package thornyaplugin.thornyaplugin.vars;

import thornyaplugin.thornyaplugin.ThornyaAPI;

import java.util.ArrayList;

public class Global {

    private final ThornyaAPI pl;

    public Global(ThornyaAPI main){
        this.pl = main;
    }

    public ArrayList<String> getStaffs(){

        ArrayList<String> staffs = new ArrayList<>();

        if (!pl.config.getFile("configuration").getStringList("staffs").isEmpty()) {
            staffs.addAll(pl.config.getFile("configuration").getStringList("staffs"));
        }

        return staffs;
    }

    public ArrayList<String> getCandidatos(){

        ArrayList<String> candidatos = new ArrayList<>();

        if(!pl.config.getFile("prefeitura").getStringList("votacao.candidatos").isEmpty()){
            candidatos.addAll(pl.config.getFile("prefeitura").getStringList("votacao.candidatos"));
        }

        return candidatos;
    }

}
