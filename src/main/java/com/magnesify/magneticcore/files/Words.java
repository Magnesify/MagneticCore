package com.magnesify.magneticcore.files;

import com.magnesify.magneticcore.MagneticCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Words {
    private FileConfiguration localeConfig;
    private File localeFile;

    public void reload() {
        if (localeFile == null) {
            localeFile = new File(MagneticCore.get().getDataFolder(), "veri/kelimeler.yml");
        }

        if (!localeFile.exists()) {
            MagneticCore.get().saveResource("veri/kelimeler.yml", false);
        }

        localeConfig = YamlConfiguration.loadConfiguration(localeFile);
    }

    public void save() {
        try {
            localeConfig.save(localeFile);
        } catch (IOException e) {
            MagneticCore.get().getLogger().warning("Could not save veri/kelimeler.yml!");
        }
    }

    public FileConfiguration get() {
        if (localeConfig == null) {
            reload();
        }
        return localeConfig;
    }
}
