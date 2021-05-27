package thornyaplugin.thornyaplugin;

import com.earth2me.essentials.Essentials;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import thornyaplugin.thornyaplugin.utils.ConfigAPI;
import thornyaplugin.thornyaplugin.vars.*;

import java.io.*;
import java.util.Objects;

public final class ThornyaAPI extends JavaPlugin {

    public SimpleClans sc;
    public Essentials ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
    Plugin clanPL = getServer().getPluginManager().getPlugin("SimpleClans");
    public Global global;
    public ConfigAPI config;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§ePluginAPI carregado com sucesso!");
        carregarconfigs();
        if (clanPL != null) { sc = ((SimpleClans) clanPL); }
        this.global = new Global(this);
        this.config = new ConfigAPI(this);
        registrarComandos();
    }

    public void registrarComandos(){
        //registrarComando("template", new Thornya(this));
        //Objects.requireNonNull(getCommand("template")).setTabCompleter(new TabPrefeitura(this));
    }

    public void registrarComando(String nome, CommandExecutor comando) {
        Objects.requireNonNull(this.getCommand(nome)).setExecutor(comando);
    }

    public void registrarListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public void carregarconfigs(){
        //config.criarConfig("template");
    }
    public void salvarConfig(){
        //config.saveConfig("template");
    }
    public void reloadAll(){
        salvarConfig();
        //config.reloadConfig("template");
    }

}
