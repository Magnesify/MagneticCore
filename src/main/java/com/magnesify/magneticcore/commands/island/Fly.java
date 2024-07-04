package com.magnesify.magneticcore.commands.island;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.files.Locale;
import com.magnesify.magneticcore.files.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {
    public Fly(MagneticCore magneticCore) {}

    void help(CommandSender sender) {
        Locale locale = new Locale();
        for(String a : locale.get().getStringList("magnetic-core.yardim.ada-fly")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', a));
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            if(strings.length == 0) {
                Locale locale = new Locale();
                Player player = (Player) commandSender;
                Settings settings = new Settings();
                for(String worlds : settings.get().getStringList("magnetic-core.ada-fly.ucusun-izin-verildigi-dunyalar")) {
                    if(!player.hasPermission(settings.get().getString("magnetic-core.ada-fly.engeli-bypass-edecek-yetki"))) {
                        if(player.getWorld().getName().equalsIgnoreCase(worlds)) {
                            if(player.getAllowFlight()) {
                                player.setAllowFlight(false);
                                player.setFlying(false);
                            } else {
                                player.setAllowFlight(true);
                                player.setFlying(true);
                            }
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-fly.bildirim").replace("<durum>", player.isFlying() ? settings.get().getString("magnetic-core.degerler.aktif") : settings.get().getString("magnetic-core.degerler.de-aktif") )));
                        } else {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-fly.yasakli-dunya")));
                        }
                    } else {
                        if(player.getAllowFlight()) {
                            player.setAllowFlight(false);
                            player.setFlying(false);
                        } else {
                            player.setAllowFlight(true);
                            player.setFlying(true);
                        }
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-fly.bildirim").replace("<durum>", player.isFlying() ? settings.get().getString("magnetic-core.degerler.aktif") : settings.get().getString("magnetic-core.degerler.de-aktif") )));
                    }
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
