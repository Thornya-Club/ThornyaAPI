package thornyaplugin.thornyaplugin.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import thornyaplugin.thornyaplugin.ThornyaAPI;

import java.io.File;


public class ConfigAPI {

    private final ThornyaAPI pl;
    private File file = null;
    private FileConfiguration fileC = null;

    public ConfigAPI(ThornyaAPI main){
        this.pl = main;
    }

    public void criarConfig(String nomedoarquivo){
        File fileVerifica = new File(pl.getDataFolder(), nomedoarquivo + ".yml");
        if(!fileVerifica.exists()){ pl.saveResource(nomedoarquivo + ".yml", false);}
    }

    public FileConfiguration getFile(String nomedoarquivo){
        this.file = new File(pl.getDataFolder(), nomedoarquivo + ".yml");
        this.fileC = YamlConfiguration.loadConfiguration(this.file);

        return this.fileC;

    }

    public void saveConfig(String nomedoarquivo){
        try {
            getFile(nomedoarquivo).save(this.file);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig(String nomedoarquivo){
        //this.tax.taxValue = this.getFile("configuration").getDouble("tax-prefeitura");
        //this.tax.taxClan = this.getFile("configuration").getDouble("tax-supremacia");
        //this.tax.taxTotal = this.getFile("configuration").getDouble("tax-prefeitura") + this.getFile("configuration").getDouble("tax-supremacia");
        if(this.file == null){
            this.file = new File(pl.getDataFolder(), nomedoarquivo);
        }
        this.fileC = YamlConfiguration.loadConfiguration(this.file);
        if(this.fileC != null){
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(this.file);
            this.fileC.setDefaults(defaults);
        }
    }

}
