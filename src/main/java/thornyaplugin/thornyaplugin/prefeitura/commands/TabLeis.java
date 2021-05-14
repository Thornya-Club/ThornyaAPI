package thornyaplugin.thornyaplugin.prefeitura.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.util.ArrayList;
import java.util.List;

public class TabLeis implements TabCompleter {
    private ThornyaPlugin pl;
    public TabLeis(ThornyaPlugin main){
        this.pl = main;
    }
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender snd, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        List<String> options = new ArrayList<String>();
        if(args.length > 0){
            if(args.length == 1) {
                if (snd.hasPermission("prefeitura.add")) {
                    options.add("adicionar");
                }
                if (snd.hasPermission("prefeitura.edit")) {
                    options.add("editar");
                }
                if (snd.hasPermission("prefeitura.remove")) {
                    options.add("remover");
                }
                if (snd.hasPermission("prefeitura.punish")) {
                    options.add("punir");
                }
                if (snd.hasPermission("prefeitura.penality")) {
                    options.add("multa");
                }
            }else if(args.length == 2){
                if((snd.hasPermission("prefeitura.edit") ||
                        snd.hasPermission("prefeitura.remove") ||
                        snd.hasPermission("prefeitura.punish") ||
                        snd.hasPermission("prefeitura.penality")) &&
                        (args[0].equalsIgnoreCase("editar") ||
                        args[0].equalsIgnoreCase("remover") ||
                        args[0].equalsIgnoreCase("punir") ||
                        args[0].equalsIgnoreCase("multa"))){
                    //foreach leis#1
                    pl.leisVar.id.forEach(integer -> options.add("Lei#" + integer));
                    }else if(snd.hasPermission("prefeitura.add") && args[0].equalsIgnoreCase("adicionar")){
                    options.add("<nome>");

                }
            }else if(args.length == 3){
                if(snd.hasPermission("prefeitura.edit") &&
                        args[0].equalsIgnoreCase("editar")){
                    options.add("nome");
                    options.add("descrição");
                }else if(snd.hasPermission("prefeitura.punish") && args[0].equalsIgnoreCase("punir")){
                    options.add("adicionar");
                    options.add("remover");
                }
                else if(snd.hasPermission("prefeitura.penality") && args[0].equalsIgnoreCase("multa")){
                    options.add("adicionar");
                    options.add("remover");
                }
                else if(snd.hasPermission("prefeitura.add") && args[0].equalsIgnoreCase("adicionar")){
                    options.add("<descrição>");
                }
            }else if(args.length == 4){
                if(snd.hasPermission("prefeitura.penality") && args[2].equalsIgnoreCase("adicionar") &&
                args[0].equalsIgnoreCase("multa")){
                    options.add("<valor_da_multa>");
                }else if(snd.hasPermission("prefeitura.punish") && args[2].equalsIgnoreCase("adicionar") &&
                        args[0].equalsIgnoreCase("punir")){
                    options.add("<comando_da_punição>");
                }
            }
            //leis adicionar Lei#1 nome Anti-Jogo
        }

        return options;
    }
}
