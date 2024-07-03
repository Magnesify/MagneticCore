package com.magnesify.magneticcore.files;

import com.magnesify.magneticcore.MagneticCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Settings {
    private FileConfiguration localeConfig;
    private File localeFile;

    public void reload() {
        if (localeFile == null) {
            localeFile = new File(MagneticCore.get().getDataFolder(), "ayarlar.yml");
        }

        if (!localeFile.exists()) {
            MagneticCore.get().saveResource("ayarlar.yml", false);
        }

        localeConfig = YamlConfiguration.loadConfiguration(localeFile);
    }

    public void save() {
        try {
            localeConfig.save(localeFile);
        } catch (IOException e) {
            MagneticCore.get().getLogger().warning("Could not save ayarlar.yml!");
        }
    }

    public FileConfiguration get() {
        if (localeConfig == null) {
            reload();
        }
        return localeConfig;
    }
}
