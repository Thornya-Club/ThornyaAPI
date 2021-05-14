package thornyaplugin.thornyaplugin.vars;

import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.util.ArrayList;

public class leis {
    public ArrayList<Integer> id = new ArrayList();
    public ArrayList<String> regra = new ArrayList();
    public ArrayList<String> descricao = new ArrayList();
    public ArrayList<Integer> multa = new ArrayList();
    public ArrayList<String> punicao = new ArrayList();
    public ArrayList<String> criador = new ArrayList();
    private ThornyaPlugin pl;

    public leis(ThornyaPlugin pl){
        this.pl = pl;
        updateLeis();
    }

    public void updateLeis(){
        id.clear();
        regra.clear();
        descricao.clear();
        multa.clear();
        punicao.clear();
        criador.clear();
    }

}
