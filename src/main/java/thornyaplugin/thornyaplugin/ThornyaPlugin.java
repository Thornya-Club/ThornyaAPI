package thornyaplugin.thornyaplugin;

import com.earth2me.essentials.Essentials;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import thornyaplugin.thornyaplugin.commands.Thornya;
import thornyaplugin.thornyaplugin.events.ThornyaE;
import thornyaplugin.thornyaplugin.prefeitura.commands.Prefeitura;
import thornyaplugin.thornyaplugin.prefeitura.commands.TabPrefeitura;
import thornyaplugin.thornyaplugin.prefeitura.database.SQLite;
import thornyaplugin.thornyaplugin.taxas.Economy;
import thornyaplugin.thornyaplugin.taxas.Tax;
import thornyaplugin.thornyaplugin.taxas.database.Mysql;
import thornyaplugin.thornyaplugin.taxas.events.QuickShop;
import thornyaplugin.thornyaplugin.vars.*;

import java.io.*;

public final class ThornyaPlugin extends JavaPlugin {

    public thornyaplugin.thornyaplugin.taxas.database.SQLite sqlitetaxas;
    public thornyaplugin.thornyaplugin.taxas.database.Mysql mysqltaxas;
    public Tax tax;
    private File file = null;
    private FileConfiguration fileC = null;
    public SimpleClans sc;
    public Essentials ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
    Plugin clanPL = getServer().getPluginManager().getPlugin("SimpleClans");
    public candidatos candidatosVar;
    public SQLite sqlLeis;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§ePluginAPI carregado com sucesso!");
        carregarconfigs();
        this.candidatosVar = new candidatos(this);
        registrarListener(new ThornyaE(this));
        registrarListener(new QuickShop(this));
        if (clanPL != null) { sc = ((SimpleClans) clanPL); }
        this.sqlitetaxas = new thornyaplugin.thornyaplugin.taxas.database.SQLite(this);
        sqlLeis = new SQLite(this);
        this.mysqltaxas = new Mysql(this);
        this.tax = new Tax(this);
        registrarComandos();
    }

    public void registrarComandos(){
        registrarComando("thornya", new Thornya(this));

        //registrarComando("leis", new Leis(this));
        registrarComando("prefeitura", new Prefeitura(this));
        getCommand("prefeitura").setTabCompleter(new TabPrefeitura(this));
        registrarComando("pagar", new Economy(this));
        registrarComando("taxa", new Economy(this));
    }
    public void registrarComando(String nome, CommandExecutor comando) {
        getCommand(nome).setExecutor(comando);
    }
    public void registrarListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }
    public void carregarconfigs(){
        criarConfig("configuration");
        criarConfig("prefeitura");
        criarConfig("translate");
    }
    public void criarConfig(String nomedoarquivo){
        File fileVerifica = new File(getDataFolder(), nomedoarquivo + ".yml");
        if(!fileVerifica.exists()){ saveResource(nomedoarquivo + ".yml", false);}
    }
    public FileConfiguration getFile(String nomedoarquivo){
        this.file = new File(getDataFolder(), nomedoarquivo + ".yml");
        this.fileC = YamlConfiguration.loadConfiguration(this.file);

        return this.fileC;

    }
    //////////////////////////////////////////////////////////////////////////\\/\/\/\/\//\/\
    public void reloadAll(){
        saveConfig();
        reloadConfig("configuration");
    }
    public void saveConfig(){
        try {
            getFile("configuration").save(this.file);
            getFile("prefeitura").save(this.file);
            getFile("translate").save(this.file);
        }catch (Exception e){

        }
    }
    public void reloadConfig(String nomedoarquivo){
        this.tax.taxValue = this.getFile("configuration").getDouble("tax-prefeitura");
        this.tax.taxClan = this.getFile("configuration").getDouble("tax-supremacia");
        this.tax.taxTotal = this.getFile("configuration").getDouble("tax-prefeitura") + this.getFile("configuration").getDouble("tax-supremacia");
        if(this.file == null){
            this.file = new File(getDataFolder(), nomedoarquivo);
        }
        this.fileC = YamlConfiguration.loadConfiguration(this.file);
        if(this.fileC != null){
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(this.file);
            this.fileC.setDefaults(defaults);
        }
    }
}
