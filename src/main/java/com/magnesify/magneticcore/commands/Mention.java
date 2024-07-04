package com.magnesify.magneticcore.commands;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.files.Locale;
import com.magnesify.magneticcore.files.Settings;
import com.magnesify.magneticcore.modules.MagnesifyPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.magnesify.magneticcore.maps.ChatMaps.optional_player_chat_view;

public class Mention implements CommandExecutor {
    public Mention(MagneticCore magneticCore) {}

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            MagnesifyPlayer magnesifyPlayer = new MagnesifyPlayer(player);
            Settings settings = new Settings();
            magnesifyPlayer.setBahsetme(!magnesifyPlayer.getBahsetme());
            Locale locale = new Locale();
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.bahsetme-degistirildi").replace("<durum>", magnesifyPlayer.getBahsetme() ? settings.get().getString("magnetic-core.degerler.aktif") : settings.get().getString("magnetic-core.degerler.de-aktif") )));
        } else {
            Locale locale = new Locale();
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.oyun-komutu")));

        }
        return false;
    }
}
