package com.magnesify.magneticcore.events;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.core.SettingHandler;
import com.magnesify.magneticcore.files.Settings;
import com.magnesify.magneticcore.files.Storage;
import com.magnesify.magneticcore.modules.MagnesifyPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CreatePlayerData implements Listener {
    public CreatePlayerData(MagneticCore magneticCore) {}
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MagnesifyPlayer player = new MagnesifyPlayer(event.getPlayer());
        player.create();
    }

}
