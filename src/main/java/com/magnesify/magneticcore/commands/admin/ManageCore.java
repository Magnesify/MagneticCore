package com.magnesify.magneticcore.commands.admin;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.files.Locale;
import com.magnesify.magneticcore.files.Settings;
import com.magnesify.magneticcore.files.Storage;
import com.magnesify.magneticcore.files.Words;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ManageCore implements CommandExecutor {
    public ManageCore(MagneticCore magneticCore) {}

    void help(CommandSender sender, int page) {
        Locale locale = new Locale();
        for(String a : locale.get().getStringList("magnetic-core.yardim.admin.sayfa-" + page)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', a));
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender.hasPermission("magneticcore.admin")) {
            // SettingHandler settingHandler = new SettingHandler(settings);
            if(strings.length == 0) {
                help(commandSender, 1);
            } else if (strings.length == 1) {
                if (strings[0].equalsIgnoreCase("yenile")) {
                    Settings settings = new Settings();
                    Storage storage = new Storage();
                    Words words = new Words();
                    Locale locale = new Locale();
                    settings.reload();
                    storage.reload();
                    locale.reload();
                    words.reload();
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.admin.eklenti-yenilendi")));
                } else {
                    help(commandSender, 1);
                }
            } else if (strings.length == 2) {
                Locale locale = new Locale();
                if(strings[0].equalsIgnoreCase("yardım")) {
                    if(locale.get().getString("magnetic-core.yardim.admin.sayfa-" + Integer.parseInt(strings[1])) != null) {
                        help(commandSender, Integer.parseInt(strings[1]));
                    } else {
                        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.admin.boyle-bir-sayfa-yok")));
                    }
                }
            } else {
                help(commandSender, 1);
            }
        }

        return false;
    }
}
