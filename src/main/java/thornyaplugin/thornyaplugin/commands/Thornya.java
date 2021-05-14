package thornyaplugin.thornyaplugin.commands;

import me.mattstudios.mfmsg.base.internal.MessageComponent;
import me.mattstudios.mfmsg.bukkit.BukkitMessage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import thornyaplugin.thornyaplugin.ThornyaPlugin;

import java.util.HashMap;
import java.util.Map;


public class Thornya implements CommandExecutor {
    private final ThornyaPlugin pl;

    Map<String, Long> cooldowns = new HashMap<String, Long>();

    public Thornya(ThornyaPlugin pl) {
        this.pl = pl;
    }


    @Override
    public boolean onCommand(CommandSender snd, Command cmd, String s, String[] args) {


       // if(cooldowns.containsKey(p.getName())){
       //     if(cooldowns.get(p.getName()) > System.currentTimeMillis()){
       //         long timeleft = (cooldowns.get(p.getName()) - System.currentTimeMillis()) / 1000;

        //        p.sendMessage("§cUse novamente em §f" + String.valueOf(cooldowns.get(p.getName()) - System.currentTimeMillis()));
        //        return true;
       //     }
        //}

        if (cmd.getName().equalsIgnoreCase("thornya")) {
            if (snd instanceof Player) {
                Player p = (Player) snd;
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        pl.reloadConfig("configuration.yml");
                        pl.reloadConfigP();
                        pl.staff.staffDetect();
                        pl.leisVar.updateLeis();
                        pl.sqlLeis.updateLeis();
                        pl.candidatosVar.updateCandidatos();
                        p.sendMessage(pl.getFile("configuration.yml").getString("message_reload").replace("&", "§"));
                    } else if (args[0].equalsIgnoreCase("limparchat")) {
                        int i = 0;
                        while (i < 100) {
                            Bukkit.getServer().broadcastMessage(" ");
                            i++;
                        }
                        Bukkit.broadcastMessage(pl.getFile("configuration.yml").getString("message_clearchat").replace("%player%", p.getName()).replace("&", "§"));
                    } else if (args[0].equalsIgnoreCase("enviarplayerdatabase")) {
                        p.sendMessage("§4COMANDO SOMENTE PELO CONSOLE!");

                    } else if (args[0].equalsIgnoreCase("aceitar")) {
                        if (pl.sqliv.hasNick(p.getName())) {
                            if (!pl.sqliv.hasVerifyNick(p.getName())) {
                                String idDiscord = pl.sqliv.getIdDiscord(p.getName());
                                pl.sqliv.updatePlayerVerify(p.getName());
                                pl.sendEmbedVerificado(p.getName());
                                pl.sendMessageInformando(pl.getUserName(), p.getName(), true);
                            } else {
                                p.sendMessage("§cVocê já é verificado!");
                            }
                        } else {
                            p.sendMessage("§cVocê não nenhuma solicitação!");
                        }

                    } else if (args[0].equalsIgnoreCase("negar")) {
                        if (pl.sqliv.hasNick(p.getName())) {
                            if (!pl.sqliv.hasVerifyNick(p.getName())) {
                                pl.sendEmbedNegado(p.getName());
                                pl.sendMessageInformando(pl.getUserName(), p.getName(), false);
                                pl.sqliv.deletePlayerNick(p.getName());
                            } else {
                                p.sendMessage("§cVocê já é verificado!");
                            }
                        } else {
                            p.sendMessage("§cVocê não nenhuma solicitação!");
                        }
                    } else {
                        p.sendMessage("§cEsse comando não existe!");
                    }

                } else if (args.length > 1) {
                    if (args[0].equalsIgnoreCase("mf")) {
                        int i = 1;
                        StringBuilder megafone = new StringBuilder();
                        while (i < args.length) {
                            megafone.append(" " + args[i]);
                            i++;
                        }
                        final BukkitMessage message = BukkitMessage.create();
                        final MessageComponent component = message.parse("<g:#009FFF:#ec2F4B>**[" + p.getName() + "]**<r:0.5:1.0>" + megafone.toString());
                        p.sendMessage(" ");
                        component.sendMessage(p);
                        p.sendMessage(" ");
                    } else {
                        p.sendMessage("§cEsse comando não existe!");
                    }
                } else {
                    p.sendMessage("§cEsse comando não existe!");
                }

            }else{
                if (args[0].equalsIgnoreCase("enviarplayerdatabase")) {
                    if (Bukkit.getServer().getOnlinePlayers().size() > 0) {
                        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                            int GameMode = 0;
                            if (player.getGameMode() == org.bukkit.GameMode.CREATIVE) {
                                GameMode = 1;
                            } else if (player.getGameMode() == org.bukkit.GameMode.SPECTATOR) {
                                GameMode = 3;
                            } else if (player.getGameMode() == org.bukkit.GameMode.ADVENTURE) {
                                GameMode = 2;
                            }
                            if(pl.sc.getClanManager().getClanPlayer(player) != null) {
                                pl.mysqltaxas.updatePlayer(player.getUniqueId().toString(), player.getName(),
                                        pl.ess.getUser(player).getGroup(),
                                        pl.sc.getClanManager().getClanPlayer(player).getTag(),
                                        pl.ess.getUser(player).getMoney().doubleValue(),
                                        pl.sc.getClanManager().getClanPlayer(player).getKDR(),
                                        pl.sc.getClanManager().getClanPlayer(player).getDeaths(),
                                        pl.sc.getClanManager().getClanPlayer(player).getCivilianKills(),
                                        pl.sc.getClanManager().getClanPlayer(player).getNeutralKills(),
                                        pl.sc.getClanManager().getClanPlayer(player).getRivalKills(),
                                        pl.sc.getClanManager().getClanPlayer(player).isLeader(),
                                        pl.sc.getClanManager().getClanPlayer(player).isTrusted(),
                                        pl.sc.getClanManager().getClanPlayer(player).isFriendlyFire(),
                                        player.getExp(), player.getTotalExperience(), player.isOp(), GameMode);
                            }else{
                                pl.mysqltaxas.updateNoClanPlayer(player.getUniqueId().toString(), player.getName(),
                                        pl.ess.getUser(player).getGroup(), pl.ess.getUser(player).getMoney().doubleValue(),
                                        player.getExp(), player.getTotalExperience(), player.isOp(), GameMode);
                            }


                        });
                    }
                }

            }

        }
        return false;
    }
}