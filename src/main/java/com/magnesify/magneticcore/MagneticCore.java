package com.magnesify.magneticcore;

import com.magnesify.magneticcore.api.Vault;
import com.magnesify.magneticcore.commands.Mention;
import com.magnesify.magneticcore.commands.PlayerChat;
import com.magnesify.magneticcore.commands.admin.ManageCore;
import com.magnesify.magneticcore.commands.island.Ads;
import com.magnesify.magneticcore.commands.island.Fly;
import com.magnesify.magneticcore.commands.island.Kit;
import com.magnesify.magneticcore.events.ChatMessageFilter;
import com.magnesify.magneticcore.events.CreatePlayerData;
import com.magnesify.magneticcore.events.LuckyBlocks;
import com.magnesify.magneticcore.events.PlayerMention;
import com.magnesify.magneticcore.events.island.FlyHandler;
import com.magnesify.magneticcore.files.Locale;
import com.magnesify.magneticcore.files.Settings;
import com.magnesify.magneticcore.files.Storage;
import com.magnesify.magneticcore.files.Words;
import com.magnesify.magneticcore.modules.announce.TypeOfActionbar;
import com.magnesify.magneticcore.modules.announce.TypeOfChat;
import com.magnesify.magneticcore.modules.announce.TypeOfTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static com.magnesify.magneticcore.modules.StringFunctionReader.RunFunctionFromConsole;

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

        Vault vault = new Vault();
        if (!vault.setupEconomy() ) {
            getLogger().severe(String.format("[%s] Vault ekonomi modülasyonu bulunamadı, eklenti kapatılıyor.", "Core"));
            getServer().getPluginManager().disablePlugin(this);
        }

        getCommand("Mgc").setExecutor(new ManageCore(this));
        pluginManager.registerEvents(new CreatePlayerData(this), this);

        if(settings.get().getBoolean("magnetic-core.ayarlar.oyuncu-sohbeti")) {
            pluginManager.registerEvents(new ChatMessageFilter(this), this);
            getCommand("Sohbetim").setExecutor(new PlayerChat(this));
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Oyuncu Sohbeti"));
        }

        if(settings.get().getBoolean("magnetic-core.ayarlar.ada-fly")) {
            pluginManager.registerEvents(new FlyHandler(this), this);
            getCommand("Adafly").setExecutor(new Fly(this));
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Ada Fly"));
        }

        if(settings.get().getBoolean("magnetic-core.ayarlar.ada-kit")) {
            getCommand("Adakit").setExecutor(new Kit(this));
            getCommand("Adakit").setTabCompleter(new Kit(this));
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Ada Kit"));
        }

        if(settings.get().getBoolean("magnetic-core.ayarlar.ada-reklam")) {
            getCommand("Adareklam").setExecutor(new Ads(this));
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Ada Reklam"));
        }

        if(settings.get().getBoolean("magnetic-core.ayarlar.bahsetme")) {
            pluginManager.registerEvents(new PlayerMention(this), this);
            getCommand("Bahsetme").setExecutor(new Mention(this));
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Bahsetme"));
        }

        if(settings.get().getBoolean("magnetic-core.ayarlar.sans-bloklari")) {
            pluginManager.registerEvents(new LuckyBlocks(this), this);
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Şans Blokları"));
        }

        if(settings.get().getBoolean("magnetic-core.ayarlar.otomatik-sohbet-silme")) {
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Otomatik Sohbet silme"));
            new BukkitRunnable() {
                @Override
                public void run() {

                    for(int i = 0; i<100;i++) {
                        Bukkit.broadcastMessage(" ");
                    }

                    Settings __settings = new Settings();
                    for(String s : __settings.get().getStringList("magnetic-core.otomatik-sohbet-silme.mesaj")) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', s));
                    }

                }
            }.runTaskTimer(this, 0, settings.get().getInt("magnetic-core.otomatik-sohbet-silme.tekrarlanma-suresi")* 20L);
        }

        if(settings.get().getBoolean("magnetic-core.ayarlar.zamanlayici")) {
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Zamanlayıcı"));

            for (String key : settings.get().getConfigurationSection("magnetic-core.zamanlayici").getKeys(false)) {
                int interval = settings.get().getInt("magnetic-core.zamanlayici." + key + ".tekrarlama-suresi");
                List<String> message = settings.get().getStringList("magnetic-core.zamanlayici." + key + ".yapilacak-islemin-komudu");

                new BukkitRunnable() {
                    @Override
                    public void run() {

                        for(String a : message) {
                            RunFunctionFromConsole(a);
                        }

                    }
                }.runTaskTimer(this, 0, interval * 20L); // Zamanlayıcıyı başlat (20L = 1 saniye)
            }
        }

        if(settings.get().getBoolean("magnetic-core.ayarlar.duyuru")) {
            getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Duyuru"));

            if(settings.get().getBoolean("magnetic-core.duyuru.title.aktif")) {
                getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Duyuru : Title"));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        TypeOfTitle typeOfTitle = new TypeOfTitle();
                        typeOfTitle.broadcast();
                    }
                }.runTaskTimer(this, 0, settings.get().getInt("magnetic-core.duyuru.title.tekrarlanma-suresi")* 20L);
            }
            if(settings.get().getBoolean("magnetic-core.duyuru.chat.aktif")) {
                getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Duyuru : Chat"));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        TypeOfChat typeOfChat = new TypeOfChat();
                        typeOfChat.broadcast();
                    }
                }.runTaskTimer(this, 0, settings.get().getInt("magnetic-core.duyuru.chat.tekrarlanma-suresi")* 20L);
            }
            if(settings.get().getBoolean("magnetic-core.duyuru.actionbar.aktif")) {
                getLogger().severe(String.format("[%s] Modül %s gereksinimleri yüklendi.", "Core", "Duyuru : Actionbar"));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        TypeOfActionbar typeOfActionbar = new TypeOfActionbar();
                        typeOfActionbar.broadcast();
                    }
                }.runTaskTimer(this, 0, settings.get().getInt("magnetic-core.duyuru.actionbar.tekrarlanma-suresi")* 20L);
            }
        }

    }
}
