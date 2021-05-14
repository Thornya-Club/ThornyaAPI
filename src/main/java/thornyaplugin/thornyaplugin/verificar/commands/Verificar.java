package thornyaplugin.thornyaplugin.verificar.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Verificar implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender snd, Command cmd, String s, String[] args) {

        Player p = (Player)snd;
        if (cmd.getName().equalsIgnoreCase("verificar")) {
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("aceitar")){

                }else if(args[0].equalsIgnoreCase("negar")){

                }else if(args[0].equalsIgnoreCase("conta")){

                }
            }else{
                p.sendMessage("§cUse /verificar conta");
            }
        }










        return false;
    }
}
