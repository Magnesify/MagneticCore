package com.magnesify.magneticcore.modules.announce;

import com.magnesify.magneticcore.files.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TypeOfChat {

    public void broadcast() {
        Settings settings = new Settings();
        Random random = new Random();
        List<String> keys = new ArrayList<>(settings.get().getConfigurationSection("magnetic-core.duyuru.chat.mesajlar").getKeys(false));
        String randomKey = keys.get(random.nextInt(keys.size()));
        List<String> dataList = settings.get().getStringList("magnetic-core.duyuru.chat.mesajlar." + randomKey);
        for(String a : dataList) {
            if(Bukkit.getOnlinePlayers().size() > 0) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', a));
            }
        }
    }

}
