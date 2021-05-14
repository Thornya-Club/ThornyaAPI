package thornyaplugin.thornyaplugin.events;
import me.mattstudios.mfmsg.base.internal.MessageComponent;
import me.mattstudios.mfmsg.bukkit.BukkitMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.util.*;

public class ThornyaE implements Listener {

    private ThornyaPlugin pl;


    public ThornyaE(ThornyaPlugin pl) {
        this.pl = pl;
        pl.staff.staffDetect();
    }
    /*
    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        String group = pl.ess.getUser(p).getGroup();
        if(p.getName().equalsIgnoreCase("Gusttavo13")){
            final BukkitMessage message = BukkitMessage.create();
            final MessageComponent component = message.parse(pl.getFile("configuration.yml").getString("tag") + event.getMessage());
            event.setMessage(component.toString());
        }
        if(event.getMessage().contains("Gusttavo13") || event.getMessage().contains("gusttavo13")){
            String TAG = "<g:#4D16ED:#1E93FC>";
            final BukkitMessage message = BukkitMessage.create();
            if(group.equalsIgnoreCase("Aprendiz") ||
                    group.equalsIgnoreCase("Default") ||
                    group.equalsIgnoreCase("VAprendiz") ||
                    group.equalsIgnoreCase("VMestre")) {
                final MessageComponent component = message.parse(TAG + "**Gusttavo13**");
                if(event.getMessage().contains("Gusttavo13")) {
                    event.setMessage(event.getMessage().replace("Gusttavo13", component.toString() + "§b"));
                }else{
                    event.setMessage(event.getMessage().replace("gusttavo13", component.toString() + "§b"));
                }
            }else if(group.equalsIgnoreCase("VPAprendiz") ||
                    group.equalsIgnoreCase("VPMestre") ||
                    group.equalsIgnoreCase("Prefeito") ||
                    group.equalsIgnoreCase("MitoPvP")) {
                final MessageComponent component = message.parse(TAG + "**Gusttavo13**");
                if(event.getMessage().contains("Gusttavo13")) {
                    event.setMessage(event.getMessage().replace("Gusttavo13", component.toString() + "§e"));
                }else{
                    event.setMessage(event.getMessage().replace("gusttavo13", component.toString() + "§e"));
                }
            }else if(group.equalsIgnoreCase("Admin") ||
                    group.equalsIgnoreCase("Fundador") ||
                    group.equalsIgnoreCase("Builder")) {
                final MessageComponent component = message.parse(TAG + "**Gusttavo13**");
                if(event.getMessage().contains("Gusttavo13")) {
                    event.setMessage(event.getMessage().replace("Gusttavo13", component.toString() + "§d"));
                }else{
                    event.setMessage(event.getMessage().replace("gusttavo13", component.toString() + "§d"));
                }
            }else if(group.equalsIgnoreCase("Mod") || group.equalsIgnoreCase("Moderador")) {
                final MessageComponent component = message.parse(TAG + "**Gusttavo13**");
                if(event.getMessage().contains("Gusttavo13")) {
                    event.setMessage(event.getMessage().replace("Gusttavo13", component.toString() + "§a"));
                }else{
                    event.setMessage(event.getMessage().replace("gusttavo13", component.toString() + "§a"));
                }
            }
            //event.setCancelled(true);

        }
        if(pl.staff.staffs.containsKey(event.getPlayer().getName())) {
            final BukkitMessage rgbM = BukkitMessage.create();
            if(pl.ess.getUser(event.getPlayer()).getGroup().equalsIgnoreCase("Builder")){
                final MessageComponent rgb =  rgbM.parse("&d" + event.getMessage()  + "&d");
                event.setMessage(rgb.toString());
            }else if(pl.ess.getUser(event.getPlayer()).getGroup().equalsIgnoreCase("Fundador")){
                final MessageComponent rgb =  rgbM.parse("&d" + event.getMessage()+ "&d");
                event.setMessage(rgb.toString());
            }else if(pl.ess.getUser(event.getPlayer()).getGroup().equalsIgnoreCase("Ajudante")){
                final MessageComponent rgb =  rgbM.parse("&2" + event.getMessage() + "&2");
                event.setMessage(rgb.toString());
            }else if(pl.ess.getUser(event.getPlayer()).getGroup().equalsIgnoreCase("Moderador")){
                final MessageComponent rgb =  rgbM.parse("&a" + event.getMessage() + "&a");
                event.setMessage(rgb.toString());
            }else if(pl.ess.getUser(event.getPlayer()).getGroup().equalsIgnoreCase("Prefeito")){
                final MessageComponent rgb =  rgbM.parse("&e" + event.getMessage() + "&e");
                event.setMessage(rgb.toString());
            }else if(pl.ess.getUser(event.getPlayer()).getGroup().equalsIgnoreCase("Admin")){
                final MessageComponent rgb =  rgbM.parse("&d" + event.getMessage() + "&d");
                event.setMessage(rgb.toString());
            }

        }

    }
    */


}
