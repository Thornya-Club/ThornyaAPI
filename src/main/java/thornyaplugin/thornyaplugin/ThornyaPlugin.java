package thornyaplugin.thornyaplugin;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.register.payment.methods.VaultEco;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import thornyaplugin.thornyaplugin.clans.Clan;
import thornyaplugin.thornyaplugin.clans.commands.ClanCommand;
import thornyaplugin.thornyaplugin.commands.Thornya;
import thornyaplugin.thornyaplugin.discord.BotManager;
import thornyaplugin.thornyaplugin.events.ThornyaE;
import thornyaplugin.thornyaplugin.prefeitura.commands.Leis;
import thornyaplugin.thornyaplugin.prefeitura.commands.Prefeitura;
import thornyaplugin.thornyaplugin.prefeitura.commands.TabLeis;
import thornyaplugin.thornyaplugin.prefeitura.commands.TabPrefeitura;
import thornyaplugin.thornyaplugin.systemCoins.database.SQLite;
import thornyaplugin.thornyaplugin.taxas.Economy;
import thornyaplugin.thornyaplugin.taxas.Tax;
import thornyaplugin.thornyaplugin.taxas.database.Mysql;
import thornyaplugin.thornyaplugin.taxas.events.QuickShop;
import thornyaplugin.thornyaplugin.vars.*;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public final class ThornyaPlugin extends JavaPlugin {
    public boolean DEVMODE = false;
    private Member m;
    public thornyaplugin.thornyaplugin.taxas.database.SQLite sqlitetaxas;
    public thornyaplugin.thornyaplugin.taxas.database.Mysql mysqltaxas;
    public Tax tax;
    public staff staff;
    public SQLite sqli;
    public thornyaplugin.thornyaplugin.verificar.SQLite.SQLite sqliv;
    private File file = null;
    private FileConfiguration fileC = null;
    private File fPrefeitura = null;
    private FileConfiguration fcPrefeitura = null;
    private File translate = null;
    private FileConfiguration fctranslate = null;
    public SimpleClans sc;
    public BotManager bot;
    public Essentials ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
    Plugin clanPL = getServer().getPluginManager().getPlugin("SimpleClans");
    public Clan clan;
    public clanschannel cc;
    public cargosDiscord cD;
    public static String PREFIX;
    public Leis pf;
    public leis leisVar;
    public candidatos candidatosVar;
    public thornyaplugin.thornyaplugin.prefeitura.database.SQLite sqlLeis;

    public void sendMessageGlobal(ArrayList<String> message){
        message.forEach(msg ->{
            Bukkit.getServer().broadcastMessage(msg);
                });
    }

    public ArrayList<String> getRoles(String guildID){
        java.util.List<String> roles = new ArrayList<>();
        bot.getJDA().getGuildById(guildID).getRoles().forEach(role -> {
                    roles.add(role.getName());
                }
        );
        return (ArrayList<String>) roles;
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§2========================================");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§ePlugin Thornya carregado com sucesso!");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§2========================================");
        carregarconfigs();
        PREFIX = getFile("configuration.yml").getString("prefix");
        this.candidatosVar = new candidatos(this);
        this.staff = new staff(this);
        if(!this.DEVMODE) {
            this.cD = new cargosDiscord(this);
        }
        registrarListener(new ThornyaE(this));
        registrarListener(new QuickShop(this));
        this.sqli = new SQLite(this);
        if (clanPL != null) { sc = ((SimpleClans) clanPL); }
        if(!this.DEVMODE){
            bot = new BotManager(this);
            this.cc = new clanschannel(this);
        }
        clan = new Clan(this);
        sqliv = new thornyaplugin.thornyaplugin.verificar.SQLite.SQLite(this);
        this.leisVar = new leis(this);
        sqlLeis = new thornyaplugin.thornyaplugin.prefeitura.database.SQLite(this);
        pf = new Leis(this);
        this.sqlitetaxas = new thornyaplugin.thornyaplugin.taxas.database.SQLite(this);
        this.mysqltaxas = new Mysql(this);
        this.tax = new Tax(this);
        registrarComandos();
    }
    public boolean createPerfilPlayer(String nick, String cargo) throws IOException {
        //staff 730x160 red
        //https://minotar.net/avatar/user/100.png
        Files.deleteIfExists(Paths.get(getDataFolder().getPath() + "\\data\\images\\perfil\\avatar.png"));
        InputStream inputStream = new URL("https://minotar.net/avatar/"+ nick +"/200.png").openStream();
        Files.copy(inputStream, Paths.get(getDataFolder().getPath() + "\\data\\images\\perfil\\avatar.png"), StandardCopyOption.REPLACE_EXISTING);
        String avatar = getDataFolder().getPath() + "\\data\\images\\perfil\\avatar.png";
        String bg = getDataFolder().getPath() + "\\data\\images\\perfil\\Background.png";
        String perfil = getDataFolder().getPath() + "\\data\\images\\perfil\\perfil.png";
        String outputNick = getDataFolder().getPath() + "\\data\\images\\perfil\\perfilName.png";
        String outputCargo = getDataFolder().getPath() + "\\data\\images\\perfil\\cargoName.png";
        String font = "\"" + getDataFolder().getPath().replace("\\", "/") + "/data/images/perfil/FiraCode-SemiBold.ttf\"";
        String isStaff = getDataFolder().getPath() + "\\data\\images\\perfil\\isSTAFF.png";
        /////////////Creation////////////////
        ProcessBuilder pbNick = new ProcessBuilder("magick","-size", "900x200", "canvas:none", "-font", font, "-pointsize","60", "-draw", "\"text 205,85 '$nick$'\"".replace("$nick$", nick), "\"$path_output$\"".replace("$path_output$", outputNick));

        pbNick.redirectErrorStream(true);

        try {
            Process p = pbNick.start();
            BufferedReader br = new BufferedReader( new InputStreamReader(p.getInputStream() ));
            String line = null;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        }catch(Exception e) {
            return false;
        }
        ProcessBuilder pbCargo = new ProcessBuilder("magick","-size", "900x200", "canvas:none", "-font", font ,"-pointsize","50", "-fill", "blue", "-draw", "\"text 205,160 '$cargo$'\"".replace("$cargo$", cargo), "\"$path_output$\"".replace("$path_output$", outputCargo));

        pbCargo.redirectErrorStream(true);

        try {
            Process p = pbCargo.start();
            BufferedReader br = new BufferedReader( new InputStreamReader(p.getInputStream() ));
            String line = null;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        }catch(Exception e) {
            return false;
        }
        ////////Composition/////////
        ProcessBuilder pbAvatarC = new ProcessBuilder("magick","composite", avatar, bg, perfil);

        pbAvatarC.redirectErrorStream(true);

        try {
            Process p = pbAvatarC.start();
            BufferedReader br = new BufferedReader( new InputStreamReader(p.getInputStream() ));
            String line = null;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        }catch(Exception e) {
            return false;
        }
        ProcessBuilder pbNickC = new ProcessBuilder("magick","composite", outputNick, perfil, perfil);

        pbNickC.redirectErrorStream(true);

        try {
            Process p = pbNickC.start();
            BufferedReader br = new BufferedReader( new InputStreamReader(p.getInputStream() ));
            String line = null;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        }catch(Exception e) {
            return false;
        }
        ProcessBuilder pbCargoC = new ProcessBuilder("magick","composite", outputCargo, perfil, perfil);

        pbCargoC.redirectErrorStream(true);

        try {
            Process p = pbCargoC.start();
            BufferedReader br = new BufferedReader( new InputStreamReader(p.getInputStream() ));
            String line = null;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        }catch(Exception e) {
            return false;
        }
        if(staff.staffs.containsKey(nick)) {
            ProcessBuilder pbIsStaffC = new ProcessBuilder("magick", "composite", isStaff, perfil, perfil);

            pbIsStaffC.redirectErrorStream(true);

            try {
                Process p = pbIsStaffC.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onDisable() {
        if(!this.DEVMODE) {
            bot.getJDA().shutdown();
            bot.getJDA().shutdownNow();
        }
    }
    public void registrarComandos(){
        registrarComando("thornya", new Thornya(this));
        registrarComando("clans", new ClanCommand(this));
        registrarComando("leis", new Leis(this));
        registrarComando("prefeitura", new Prefeitura(this));
        getCommand("leis").setTabCompleter(new TabLeis(this));
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
        criarConfig("configuration.yml");
        criarConfigPrefeitura();
        criarConfigTranslate();
        //criarConfig("staffs.yml");
    }
    public void criarConfig(String nomedoarquivo){
        File fileVerifica = new File(getDataFolder(), nomedoarquivo);
        if(!fileVerifica.exists()){ saveResource(nomedoarquivo, false);}
    }
    public FileConfiguration getFile(String nomedoarquivo){
        if(this.fileC == null){
            this.file = new File(getDataFolder(), nomedoarquivo);
            this.fileC = YamlConfiguration.loadConfiguration(this.file);
        }
        return this.fileC;

    }
    ///////////////////\\/\/\/\/\/\/\/\/\/\/Config Prefeitura///////////////////////
    public void criarConfigPrefeitura(){
        File fileVerifica = new File(getDataFolder(), "prefeitura.yml");
        if(!fileVerifica.exists()){ saveResource("prefeitura.yml", false);}
    }
    public FileConfiguration getFileP(){
        if(this.fcPrefeitura == null){
            this.fPrefeitura = new File(getDataFolder(), "prefeitura.yml");
            this.fcPrefeitura = YamlConfiguration.loadConfiguration(this.fPrefeitura);
        }
        return this.fcPrefeitura;

    }
    public void reloadConfigP(){
        if(this.fPrefeitura == null){
            this.fPrefeitura = new File(getDataFolder(), "prefeitura.yml");
        }
        this.fcPrefeitura = YamlConfiguration.loadConfiguration(this.fPrefeitura);
        if(this.fcPrefeitura != null){
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(this.fPrefeitura);
            this.fcPrefeitura.setDefaults(defaults);
        }
    }
    //////////////////////////////////////////////////////////////////////////\\/\/\/\/\//\/\
    ///////////////////\\/\/\/\/\/\/\/\/\/\/Config Translate///////////////////////
    public void criarConfigTranslate(){
        File fileVerifica = new File(getDataFolder(), "translate.yml");
        if(!fileVerifica.exists()){ saveResource("translate.yml", false);}
    }
    public FileConfiguration translate(){
        if(this.fctranslate == null){
            this.translate = new File(getDataFolder(), "translate.yml");
            this.fctranslate = YamlConfiguration.loadConfiguration(this.translate);
        }
        return this.fctranslate;

    }
    public void reloadTranslate(){
        if(this.translate == null){
            this.translate = new File(getDataFolder(), "translate.yml");
        }
        this.fctranslate = YamlConfiguration.loadConfiguration(this.translate);
        if(this.fctranslate != null){
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(this.translate);
            this.fctranslate.setDefaults(defaults);
        }
    }
    //////////////////////////////////////////////////////////////////////////\\/\/\/\/\//\/\
    public void reloadAll(){
        saveConfig();
        reloadConfigP();
        reloadConfig("configuration.yml");
        reloadTranslate();
       //reloadConfig("staffs.yml");
        //reloadConfig("jogadoresV.yml");
    }
    public void saveConfig(){

        try {
            getFile("configuration.yml").save(this.file);
            getFileP().save(this.fPrefeitura);
            translate().save(this.translate);
            //getFile("staffs.yml").save(this.file);
            //getFile("jogadoresV.yml").save(this.file);
        }catch (Exception e){

        }
    }
    public void sendEmbedNegado(String nickname) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0xFFFF0000, true))
                .setTitle("Verificação não autorizada!")
                .setDescription("Seu pedido foi negado por *{nick}*".replace("{nick}", nickname))
                .setFooter("Verificação do Servidor", "https://minotar.net/avatar/robot");
        bot.getJDA().getTextChannelById("794042606061223946").sendMessage(eb.build()).complete();
    }
    public String getUserName(){
        return m.getEffectiveName();
    }
    public void sendMessageInformando(String user, String nickname, boolean situacao){
        if(situacao){
            if(getServer().getPlayer(nickname).isOnline()){
                for(int i = 0; i <= 100; i++){
                    getServer().getPlayer(nickname).sendMessage(" ");
                }
                getServer().getPlayer(nickname).sendMessage(" ");
                getServer().getPlayer(nickname).sendMessage("§aSua conta foi vinculada com sucesso!");
                getServer().getPlayer(nickname).sendMessage(" ");
                getServer().getPlayer(nickname).sendMessage("§2Minecraft: §a" + nickname);
                getServer().getPlayer(nickname).sendMessage("§9Discord: §3" + user);
                getServer().getPlayer(nickname).sendMessage(" ");
            }
        }else{
            if(getServer().getPlayer(nickname).isOnline()){
                for(int i = 0; i <= 100; i++){
                    Bukkit.getPlayer(nickname).sendMessage(" ");
                }
                getServer().getPlayer(nickname).sendMessage("§cVocê negou o pedido de §4" + user);
                getServer().getPlayer(nickname).sendMessage(" ");
            }
        }
    }
    public void sendEmbedVerificado(String username){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("✅ Verificado com Sucesso ✅")
                .setDescription("**-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-**")
                .setColor(new Color(4886754))
                .setFooter("Verificação do Servidor", "https://minotar.net/avatar/robot")
                .addField("Minecraft", username, false)
                .addField("Discord", m.getAsMention(), false);
        m.getGuild().getTextChannelById("794042606061223946").sendMessage(eb.build()).complete();
        m.getGuild().addRoleToMember(m.getId(), m.getGuild().getRoleById("794106928145760266")).complete();
        try{
            m.modifyNickname(username).queue();
        }catch (NullPointerException e){
            System.out.println("Nick não pode ser modificado: " + e.getStackTrace());
            System.out.println("Nick não pode ser modificado: " + e.getCause());
            System.out.println("Nick não pode ser modificado: " + e.toString());
        }

    }
    public void sendServerVerify(String nickname, String nametag, String ID, Member membro){
        this.m = membro;
        Bukkit.getOnlinePlayers().forEach(online -> {
            if(online.getName().equals(nickname)) {
                ArrayList<String> messageVerify = new ArrayList<>();
                if (!getFile("configuration.yml").getStringList("discord.verifymessage").isEmpty()){
                    for (String str : getFile("configuration.yml").getStringList("discord.verifymessage")) {
                        if (str.equalsIgnoreCase("$comando")) {
                            try {
                                String cmd  = "tellraw @p [\"\",{\"text\":\"Clique em \",\"color\":\"dark_green\"},{\"text\":\"[\",\"bold\":true,\"color\":\"gray\"},{\"text\":\"Aceitar\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/thornya aceitar\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Deseja aceitar?\",\"color\":\"light_purple\"}}},{\"text\":\"]\",\"bold\":true,\"color\":\"gray\"},{\"text\":\" ou\",\"color\":\"dark_green\"},{\"text\":\" [\",\"bold\":true,\"color\":\"gray\"},{\"text\":\"Negar\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/thornya negar\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Deseja negar?\",\"color\":\"dark_red\"}}},{\"text\":\"]\",\"bold\":true,\"color\":\"gray\"}]";
                                Bukkit.getScheduler().callSyncMethod(this, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("@p", online.getName()))).get();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        } else {
                            online.sendMessage(str.replace("&", "§").
                                    replace("$nametag", nametag).
                                    replace("$id", ID).
                                    replace("$nickname", online.getName()));
                        }
                    }
                }
            }
        });
    }
    public static int getGuildUserCount(Guild guild) {
        int i = 0;
        for (Member member : guild.getMembers()) {
            if (!member.getUser().isBot()) {
                i++;
            }
        }
        return i;
    }
    public void reloadConfig(String nomedoarquivo){
        this.tax.taxValue = this.getFile("configuration.yml").getDouble("tax-prefeitura");
        this.tax.taxClan = this.getFile("configuration.yml").getDouble("tax-supremacia");
        this.tax.taxTotal = this.getFile("configuration.yml").getDouble("tax-prefeitura") + this.getFile("configuration.yml").getDouble("tax-supremacia");
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
