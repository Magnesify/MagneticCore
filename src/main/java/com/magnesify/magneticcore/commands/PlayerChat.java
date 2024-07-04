package com.magnesify.magneticcore.commands;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.files.Locale;
import com.magnesify.magneticcore.files.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.magnesify.magneticcore.maps.ChatMaps.optional_player_chat_view;

public class PlayerChat implements CommandExecutor {
    public PlayerChat(MagneticCore magneticCore) {}

    void help(CommandSender sender) {
        Locale locale = new Locale();
        for(String a : locale.get().getStringList("magnetic-core.yardim.oyuncu-sohbeti")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', a));
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (strings.length == 0) {
                help(commandSender);
            } else if (strings.length == 1) {
                Locale locale = new Locale();
                Settings settings = new Settings();
                if(strings[0].equalsIgnoreCase("temizle")) {
                    if(settings.get().getBoolean("magnetic-core.oyuncu-sohbeti.oyuncu-kendi-sohbetini-temizleyebilir")) {
                        for(int i = 0; i<100; i++) {
                            commandSender.sendMessage(" ");
                        }
                        for(String a : locale.get().getStringList("magnetic-core.sohbetin-temizlendi")) {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', a));
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.hata.modul-kapali")));
                    }
                } else if (strings[0].equalsIgnoreCase("mesajlar")) {
                    if(settings.get().getBoolean("magnetic-core.oyuncu-sohbeti.oyuncu-sohbeti-kapatabilir")) {

                        if (optional_player_chat_view.contains(player.getUniqueId().toString())) {
                            optional_player_chat_view.remove(player.getUniqueId().toString());
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.mesajlar.sohbet-acildi")));
                        } else {
                            optional_player_chat_view.add(player.getUniqueId().toString());
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.mesajlar.sohbet-kapandi")));
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.hata.modul-kapali")));

                    }
                } else {
                    help(commandSender);
                }
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
