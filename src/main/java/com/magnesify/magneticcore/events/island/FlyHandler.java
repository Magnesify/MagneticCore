package com.magnesify.magneticcore.events.island;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.files.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FlyHandler implements Listener {
    public FlyHandler(MagneticCore magneticCore) {}

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Settings settings = new Settings();
        if(settings.get().getBoolean("magnetic-core.ada-fly.cik-girde-ucusu-kapat")) {
            event.getPlayer().setFlying(false);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Settings settings = new Settings();
        if(settings.get().getBoolean("magnetic-core.ada-fly.cik-girde-ucusu-kapat")) {
            event.getPlayer().setFlying(false);
        }
    }

}
