package com.magnesify.magneticcore.events;

import com.magnesify.magneticcore.MagneticCore;
import com.magnesify.magneticcore.files.Settings;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Random;

import static com.magnesify.magneticcore.modules.StringFunctionReader.RunFunction;

public class LuckyBlocks implements Listener {
    public LuckyBlocks(MagneticCore magneticCore) {}

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Settings settings = new Settings();
        for(String s : settings.get().getConfigurationSection("magnetic-core.sans-bloklari").getKeys(false)) {
            String block_name = settings.get().getString("magnetic-core.sans-bloklari." + s + ".kirilacak-blok");
            if(block.getType() == Material.getMaterial(block_name)) {
                Random random = new Random();
                int ni = random.nextInt(100) + 1;
                for(String a : settings.get().getConfigurationSection("magnetic-core.sans-bloklari." + s + ".sansa-bagli-tetiklenecek-fonksiyonlar").getKeys(false)) {
                    int chance = settings.get().getInt("magnetic-core.sans-bloklari." + s + ".sansa-bagli-tetiklenecek-fonksiyonlar." + a + ".sans");
                    List<String> prizes = settings.get().getStringList("magnetic-core.sans-bloklari." + s + ".sansa-bagli-tetiklenecek-fonksiyonlar." + a + ".oduller");
                    if(ni == chance) {
                        for(String functions : prizes) {
                            RunFunction(player, functions);
                        }
                    }
                }
            }
        }
    }

}
