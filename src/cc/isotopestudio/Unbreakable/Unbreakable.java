package cc.isotopestudio.Unbreakable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Mars on 6/5/2016.
 * Copyright ISOTOPE Studio
 */
public class Unbreakable extends JavaPlugin {
    private static final String pluginName = "Unbreakable";
    static Unbreakable plugin;
    private PluginFile config;
    static String lore;

    @Override
    public void onEnable() {
        plugin = this;

        config = new PluginFile(this, "config.yml", "config.yml");
        lore = ChatColor.translateAlternateColorCodes('&', config.getString("lore"));
        this.getCommand("naijiu").setExecutor((sender, cmd, label, args) -> {
            if (cmd.getName().equalsIgnoreCase("naijiu")) {
                if (!sender.isOp()) {
                    return false;
                }
                if (args.length > 0) {
                    lore = ChatColor.translateAlternateColorCodes('&', args[0]);
                    config.set("lore", args[0]);
                    config.save();
                    sender.sendMessage("成功设置");
                }
                return true;
            }
            return false;
        });
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    ItemStack[] items = player.getInventory().getContents();
                    for (int i = 0; i < items.length; i++) {
                        if (items[i] == null) continue;
                        if (ArmorListener.isUnbreakable(items[i])) {
                            player.getInventory().setItem(i, ArmorListener.handleUnbreakable(items[i]));
                        }
                    }
                }
            }
        }.runTaskTimer(this, 20L, 40L);

        getServer().getPluginManager().registerEvents(new ArmorListener(), this);
        getLogger().info(pluginName + "成功加载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }
}
