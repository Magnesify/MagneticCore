package com.magnesify.magneticcore.modules;

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
        if(storage.get().getString("oyuncular." + player.getUniqueId().toString()) == null) {
            if(settings.get().getBoolean("magnetic-core.ayarlar.ada-kit")) {
                storage.get().set("oyuncular." + player.getUniqueId().toString() + ".ada-kit", false);
            }
            if(settings.get().getBoolean("magnetic-core.ayarlar.bahsetme")) {
                storage.get().set("oyuncular." + player.getUniqueId().toString() + ".bahsetme", true);
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

    public boolean getBahsetme() {
        Storage storage = new Storage();
        return storage.get().getBoolean("oyuncular." + player.getUniqueId().toString() + ".bahsetme");
    }

    public void setBahsetme(boolean status) {
        Storage storage = new Storage();
        storage.get().set("oyuncular." + player.getUniqueId().toString() + ".bahsetme", status);
        storage.save();
    }

}
