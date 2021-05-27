package thornyaplugin.thornyaplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;
import thornyaplugin.thornyaplugin.ThornyaAPI;


public class Thornya implements CommandExecutor {
    private final ThornyaAPI pl;

    //Map<String, Long> cooldowns = new HashMap<String, Long>();

    public Thornya(ThornyaAPI pl) {
        this.pl = pl;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender snd, @NotNull Command cmd, @NotNull String s, String[] args) {
       // if(cooldowns.containsKey(p.getName())){
       //     if(cooldowns.get(p.getName()) > System.currentTimeMillis()){
       //         long timeleft = (cooldowns.get(p.getName()) - System.currentTimeMillis()) / 1000;

        //        p.sendMessage("§cUse novamente em §f" + String.valueOf(cooldowns.get(p.getName()) - System.currentTimeMillis()));
        //        return true;
       //     }
        //}

        return false;
    }
}
