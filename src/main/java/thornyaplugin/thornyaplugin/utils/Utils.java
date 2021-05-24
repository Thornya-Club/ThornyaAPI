package thornyaplugin.thornyaplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import thornyaplugin.thornyaplugin.taxas.Economy;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

public class Utils {

    public static void send(Player player, String message){

        player.sendMessage(message);

    }

    public static void send(String message){

        Bukkit.getConsoleSender().sendMessage(message);

    }

    public String generateString(int length){

        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars .length())));
        }
        return sb.toString();

    }
    public String generateInt(int length){

        String candidateChars = "1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars .length())));
        }
        return sb.toString();

    }

    public static String formatarMoney(BigDecimal amount){

        String formatado = Economy.formato.format(amount);

        return "§cT" + formatado;

    }

    public static String formatarMoney(Float amount){

        String formatado = Economy.formato.format(amount);

        return "§cT" + formatado;

    }

}
