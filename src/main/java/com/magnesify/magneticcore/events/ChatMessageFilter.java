package com.magnesify.magneticcore.events;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.files.Locale;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static com.magnesify.magneticcore.maps.ChatMaps.optional_player_chat_view;

public class ChatMessageFilter implements Listener {
    public ChatMessageFilter(MagneticCore magneticCore) {}

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        if(optional_player_chat_view.contains(sender.getUniqueId().toString())) {
            event.setCancelled(true);
            Locale locale = new Locale();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', locale.get().getString("magnetic-core.mesajlar.sohbetin-kapali-iken-mesaj-gonderemezsin")));
        }
        event.getRecipients().removeIf(player -> optional_player_chat_view.contains(player.getUniqueId().toString()));
    }

}
