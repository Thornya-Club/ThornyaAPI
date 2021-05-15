package thornyaplugin.thornyaplugin.vars;

import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.util.HashMap;
import java.util.Map;

public class candidatos {

    public Map<String, Boolean> candidatos = new HashMap<String, Boolean>();
    private ThornyaPlugin pl;

    public candidatos(ThornyaPlugin main){
        this.pl = main;
        updateCandidatos();
    }

    public void updateCandidatos(){
        if(!pl.getFile("prefeitura").getStringList("votacao.candidatos").isEmpty()){
            candidatos.clear();
            for (String str : pl.getFile("prefeitura").getStringList("votacao.candidatos")){
                this.candidatos.put(str, false);
            }
        }
    }


}
