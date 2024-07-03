package com.magnesify.magneticcore;

import com.magnesify.magneticcore.api.Vault;
import com.magnesify.magneticcore.commands.PlayerChat;
import com.magnesify.magneticcore.commands.island.Ads;
import com.magnesify.magneticcore.commands.island.Fly;
import com.magnesify.magneticcore.commands.island.Kit;
import com.magnesify.magneticcore.core.SettingHandler;
import com.magnesify.magneticcore.events.ChatMessageFilter;
import com.magnesify.magneticcore.events.CreatePlayerData;
import com.magnesify.magneticcore.events.island.FlyHandler;
import com.magnesify.magneticcore.files.Locale;
import com.magnesify.magneticcore.files.Settings;
import com.magnesify.magneticcore.files.Storage;
import com.magnesify.magneticcore.files.Words;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagneticCore extends JavaPlugin {

    private static MagneticCore magneticCore;
    public static MagneticCore get() {return magneticCore;}
    public void set(MagneticCore core) {magneticCore=core;}

    private PluginManager pluginManager = Bukkit.getPluginManager();

    @Override
    public void onEnable() {
        set(this);
        Settings settings = new Settings();
        Storage storage = new Storage();
        Words words = new Words();
        Locale locale = new Locale();
        settings.reload();
        storage.reload();
        locale.reload();
        words.reload();

        SettingHandler settingHandler = new SettingHandler(settings);

        Vault vault = new Vault();
        if (!vault.setupEconomy() ) {
            getLogger().severe(String.format("[%s] Vault ekonomi modülasyonu bulunamadı, eklenti kapatılıyor.", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }

        pluginManager.registerEvents(new CreatePlayerData(this), this);

        if(settingHandler.OYUNCU_SOHBET) {
            pluginManager.registerEvents(new ChatMessageFilter(this), this);
            getCommand("Sohbetim").setExecutor(new PlayerChat(this));
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", getDescription().getName(), "Oyuncu Sohbeti"));
        }

        if(settingHandler.ADA_FLY) {
            pluginManager.registerEvents(new FlyHandler(this), this);
            getCommand("Adafly").setExecutor(new Fly(this));
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", getDescription().getName(), "Ada Fly"));
        }

        if(settingHandler.ADA_KIT) {
            getCommand("Adakit").setExecutor(new Kit(this));
        }

        if(settingHandler.ADA_REKLAM) {
            getCommand("Adareklam").setExecutor(new Ads(this));
        }

    }
}
