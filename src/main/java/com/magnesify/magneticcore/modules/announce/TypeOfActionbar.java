package com.magnesify.magneticcore.modules.announce;

import com.magnesify.magneticcore.files.Settings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

public class TypeOfActionbar {

    public void broadcast() {
        Settings settings = new Settings();
        Random random = new Random();
        int i = settings.get().getStringList("magnetic-core.duyuru.actionbar.mesajlar").size();
        int randomNumber = random.nextInt(i);
        String message = settings.get().getStringList("magnetic-core.duyuru.actionbar.mesajlar").get(randomNumber);
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(Bukkit.getOnlinePlayers().size() > 0) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
            }
        }
    }

}
