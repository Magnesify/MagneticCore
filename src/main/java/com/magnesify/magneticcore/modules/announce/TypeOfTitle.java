package com.magnesify.magneticcore.modules.announce;

import com.magnesify.magneticcore.files.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TypeOfTitle {

    public void broadcast() {
        Settings settings = new Settings();
        Random random = new Random();
        List<String> keys = new ArrayList<>(settings.get().getConfigurationSection("magnetic-core.duyuru.title.mesajlar").getKeys(false));
        String randomKey = keys.get(random.nextInt(keys.size()));
        String baslik = settings.get().getString("magnetic-core.duyuru.title.mesajlar." + randomKey + ".baslik");
        String alt_baslik = settings.get().getString("magnetic-core.duyuru.title.mesajlar." + randomKey + ".alt-baslik");
        for(Player player : Bukkit.getOnlinePlayers()) {
            if (Bukkit.getOnlinePlayers().size() > 0) {
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', baslik), ChatColor.translateAlternateColorCodes('&', alt_baslik), 20, 40, 20);
            }
        }
    }

}
