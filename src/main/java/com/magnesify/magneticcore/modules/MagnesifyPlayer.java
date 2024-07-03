package com.magnesify.magneticcore.modules;

import com.magnesify.magneticcore.core.SettingHandler;
import com.magnesify.magneticcore.files.Settings;
import com.magnesify.magneticcore.files.Storage;
import org.bukkit.entity.Player;

public class MagnesifyPlayer {

    private Player player;

    public MagnesifyPlayer(Player player) {
        this.player = player;
    }

    public void create() {
        Storage storage = new Storage();
        Settings settings = new Settings();
        SettingHandler settingHandler = new SettingHandler(settings);
        if(storage.get().getString("oyuncular." + player.getUniqueId().toString()) == null) {
            if(settingHandler.ADA_KIT) {
                storage.get().set("oyuncular." + player.getUniqueId().toString() + ".ada-kit", false);
            }

            storage.save();
        }
    }

    public boolean getAdaKit() {
        Storage storage = new Storage();
        return storage.get().getBoolean("oyuncular." + player.getUniqueId().toString() + ".ada-kit");
    }

    public void setAdaKit(boolean status) {
        Storage storage = new Storage();
        storage.get().set("oyuncular." + player.getUniqueId().toString() + ".ada-kit", status);
        storage.save();
    }

}
