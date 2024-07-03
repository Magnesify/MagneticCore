package com.magnesify.magneticcore.core;

import com.magnesify.magneticcore.files.Settings;

import java.util.List;

public class SettingHandler {

    private Settings settings;

    public SettingHandler(Settings settings) {
        this.settings = settings;
    }

    public boolean OYUNCU_SOHBET = settings.get().getBoolean("magnetic-core.ayarlar.oyuncu-sohbeti");
    public boolean COIN = settings.get().getBoolean("magnetic-core.ayarlar.coin");
    public boolean DUYURU = settings.get().getBoolean("magnetic-core.ayarlar.duyuru");
    public boolean SANS_BLOKLARI = settings.get().getBoolean("magnetic-core.ayarlar.sans-bloklari");
    public boolean OYLAMA = settings.get().getBoolean("magnetic-core.ayarlar.oylama");
    public boolean YAZ_KAZAN = settings.get().getBoolean("magnetic-core.ayarlar.yaz-kazan");
    public boolean EVENT = settings.get().getBoolean("magnetic-core.ayarlar.event");
    public boolean KOMUT_KISALTMA = settings.get().getBoolean("magnetic-core.ayarlar.komut-kisaltma");
    public boolean ADA_KIT = settings.get().getBoolean("magnetic-core.ayarlar.ada-kit");
    public boolean ADA_FLY = settings.get().getBoolean("magnetic-core.ayarlar.ada-fly");
    public boolean ADA_REKLAM = settings.get().getBoolean("magnetic-core.ayarlar.ada-reklam");
    public boolean ADA_REKLAM_DISCORD = settings.get().getBoolean("magnetic-core.ayarlar.ada-reklam.discord.reklam-mesaji-discorda-gonderilsin");
    public boolean ZAMANLAMA = settings.get().getBoolean("magnetic-core.ayarlar.zamanlama");

    public String DEFAULT_KIT = settings.get().getString("magnetic-core.ada-kit.oyuncu-adakiti");
    public int ADS_PRICE = settings.get().getInt("magnetic-core.ada-reklam.reklam-ucreti");
    public List<String> DEFAULT_KIT_CONTENT = settings.get().getStringList("magnetic-core.ada-kit.kitler." + DEFAULT_KIT + ".icerik");

}
