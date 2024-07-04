package com.magnesify.magneticcore.events;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.files.Settings;
import com.magnesify.magneticcore.modules.MagnesifyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerMention implements Listener {
    public PlayerMention(MagneticCore magneticCore) {}

    @EventHandler
    public void onPlayerMention(AsyncPlayerChatEvent event) {
        Settings settings = new Settings();
        String message = event.getMessage();
        String startsWith = settings.get().getString("magnetic-core.bahsetme.etiket");
        Player sender = event.getPlayer();
        if (message.contains(startsWith)) {
            String[] words = message.split(" ");

            for (String word : words) {
                if (word.startsWith(startsWith)) {
                    String playerName = word.substring(1);
                    Player mentionedPlayer = Bukkit.getPlayerExact(playerName);

                    if (mentionedPlayer != null && mentionedPlayer.isOnline()) {
                        MagnesifyPlayer magnesifyPlayer = new MagnesifyPlayer(mentionedPlayer);
                        if(magnesifyPlayer.getBahsetme()) {
                            String title = settings.get().getString("magnetic-core.bahsetme.baslik").replace("<bahseden>", event.getPlayer().getName());
                            String subtitle = settings.get().getString("magnetic-core.bahsetme.alt-baslik").replace("<bahseden>", event.getPlayer().getName());
                            mentionedPlayer.sendTitle(ChatColor.translateAlternateColorCodes('&', title), ChatColor.translateAlternateColorCodes('&', subtitle), 20, 40, 20);
                        } else {
                            event.getPlayer().sendMessage(ChatColor.RED + sender.getName() + " oyuncu bahsetmelerini kapatmış!");
                        }
                    }
                }
            }
        }
    }

}
