package com.magnesify.magneticcore.commands.island;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.core.SettingHandler;
import com.magnesify.magneticcore.files.Locale;
import com.magnesify.magneticcore.files.Settings;
import com.magnesify.magneticcore.modules.MagnesifyPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.magnesify.magneticcore.modules.StringFunctionReader.RunFunction;

public class Kit implements CommandExecutor {
    public Kit(MagneticCore magneticCore) {}

    void help(CommandSender sender) {
        Locale locale = new Locale();
        for(String a : locale.get().getStringList("magnetic-core.yardim.ada-kit")) {
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
                SettingHandler settingHandler = new SettingHandler(settings);
                MagnesifyPlayer magnesifyPlayer = new MagnesifyPlayer(player);
                if(!magnesifyPlayer.getAdaKit()) {
                    if(settingHandler.DEFAULT_KIT != null) {
                        for(String content : settingHandler.DEFAULT_KIT_CONTENT) {
                            RunFunction(player, content);
                        }
                        magnesifyPlayer.setAdaKit(true);
                    } else {
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.hata.mgc0")));
                    }
                } else {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-kit.zaten-alinmis")));
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
