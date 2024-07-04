package com.magnesify.magneticcore.commands.island;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.files.Locale;
import com.magnesify.magneticcore.files.Settings;
import com.magnesify.magneticcore.files.Words;
import com.magnesify.magneticcore.modules.MagnesifyPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.magnesify.magneticcore.api.Vault.getEconomy;
import static org.bukkit.Bukkit.getLogger;

public class Ads implements CommandExecutor {
    public Ads(MagneticCore magneticCore) {}

    void help(CommandSender sender) {
        Locale locale = new Locale();
        for(String a : locale.get().getStringList("magnetic-core.yardim.ada-reklam")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', a));
        }
    }

    private void sendToDiscord(List<String> message, String webhookUrl) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            String combinedMessage = String.join("\n", message);
            String json = "{ \"content\": \"" + combinedMessage + "\" }";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 204) {
                getLogger().warning("[MagneticCore] Discord webhook'a mesaj gönderilemedi, response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            getLogger().severe("[MagneticCore] Discord webhook'a mesaj gönderilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if(strings.length > 0) {
                Locale locale = new Locale();
                Player player = (Player) commandSender;
                Settings settings = new Settings();
                Words words = new Words();
                MagnesifyPlayer magnesifyPlayer = new MagnesifyPlayer(player);
                if (getEconomy().getBalance(player) >= settings.get().getInt("magnetic-core.ada-reklam.reklam-ucreti")) {
                    String message = "";
                    for (String part : strings) {
                        if (message != "") message += " ";
                        message += part;
                    }
                    for (String w : words.get().getStringList("yasakli-kelimeler.ada-reklam")) {
                        if (message.contains(w)) {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-reklam.yasakli-kelime")));
                            return false;
                        }
                    }

                    getEconomy().withdrawPlayer(player, settings.get().getInt("magnetic-core.ada-reklam.reklam-ucreti"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-reklam.reklam-gerceklesti").replace("<ucret>", String.valueOf((int) (settings.get().getInt("magnetic-core.ada-reklam.reklam-ucreti"))))));
                    for (String m : settings.get().getStringList("magnetic-core.ada-reklam.mesaj")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', m.replace("<oyuncu>", player.getName()).replace("<mesaj>", message)));
                    }
                    if (settings.get().getBoolean("magnetic-core.ayarlar.ada-reklam.discord.reklam-mesaji-discorda-gonderilsin")) {
                        sendToDiscord(settings.get().getStringList("magnetic-core.ada-reklam.discord.mesaj"), settings.get().getString("magnetic-core.ada-reklam.discord.webhook"));

                    }

                } else {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-reklam.yetersiz-bakiye").replace("<ihtiyaci_olan_miktar>", String.valueOf((int) (settings.get().getInt("magnetic-core.ada-reklam.reklam-ucreti") - getEconomy().getBalance(player))))));
                }
            } else if (strings.length == 0) {
                help(commandSender);
            } else {
                help(commandSender);
            }
        } else {
            Locale locale = new Locale();
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.oyun-komutu")));
        }
        return false;
    }
}
