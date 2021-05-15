package thornyaplugin.thornyaplugin.vars;

import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class candidatos {

    public ArrayList<String> candidatos = new ArrayList<>();
    private ThornyaPlugin pl;

    public candidatos(ThornyaPlugin main){
        this.pl = main;
        updateCandidatos();
    }

    public void updateCandidatos(){
        if(!pl.getFile("prefeitura").getStringList("votacao.candidatos").isEmpty()){
            candidatos.clear();
            for (String str : pl.getFile("prefeitura").getStringList("votacao.candidatos")){
                this.candidatos.add(str);
            }
        }
    }


}
