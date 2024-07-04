package com.magnesify.magneticcore.commands.island;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.files.Locale;
import com.magnesify.magneticcore.files.Settings;
import com.magnesify.magneticcore.modules.MagnesifyPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.magnesify.magneticcore.modules.StringFunctionReader.RunFunction;

public class Kit implements CommandExecutor, TabCompleter {
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
                help(commandSender);
            } else if (strings.length == 1){
                Locale locale = new Locale();
                Player player = (Player) commandSender;
                Settings settings = new Settings();
                MagnesifyPlayer magnesifyPlayer = new MagnesifyPlayer(player);
                String kitName = strings[0];
                String perm = settings.get().getString("magnetic-core.ada-kit.kitler." + kitName + ".yetki");
                String name = settings.get().getString("magnetic-core.ada-kit.kitler." + kitName + ".isim");
                if(name != null) {
                    if(perm == null) {
                        if(kitName.equalsIgnoreCase(settings.get().getString("magnetic-core.ada-kit.oyuncu-adakiti"))) {
                            if(!magnesifyPlayer.getAdaKit()) {
                                if(settings.get().getString("magnetic-core.ada-kit.oyuncu-adakiti") != null) {
                                    for(String content : settings.get().getStringList("magnetic-core.ada-kit.kitler." + settings.get().getString("magnetic-core.ada-kit.oyuncu-adakiti") + ".icerik")) {
                                        RunFunction(player, content);
                                    }
                                    magnesifyPlayer.setAdaKit(true);
                                } else {
                                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.hata.adakit-bulunamadi")));
                                }
                            } else {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-kit.zaten-alinmis")));
                            }
                        } else {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-kit.bu-kiti-alamazsin")));
                        }
                    } else {
                        if(player.hasPermission(perm)) {
                            if(!magnesifyPlayer.getAdaKit()) {
                                for(String content : settings.get().getStringList("magnetic-core.ada-kit.kitler." + kitName + ".icerik")) {
                                    RunFunction(player, content);
                                }
                                magnesifyPlayer.setAdaKit(true);
                            } else {
                                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-kit.zaten-alinmis")));
                            }
                        } else {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-kit.bu-kiti-alamazsin")));
                        }
                    }
                } else {
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.ada-kit.boyle-bir-kit-yok")));
                }
            }
        } else {
            Locale locale = new Locale();
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.oyun-komutu")));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            Settings settings = new Settings();
            for(String kits : settings.get().getConfigurationSection("magnetic-core.ada-kit.kitler").getKeys(false)) {
                String kit = settings.get().getString("magnetic-core.ada-kit.kitler." + kits + ".isim");
                commands.add(kit);
            }
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
