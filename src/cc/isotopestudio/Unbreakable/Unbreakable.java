package cc.isotopestudio.Unbreakable;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Mars on 6/5/2016.
 * Copyright ISOTOPE Studio
 */
public class Unbreakable extends JavaPlugin {
    private static final String pluginName = "Unbreakable";
    public static Unbreakable plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new ArmorListener(), this);
        getLogger().info(pluginName + "�ɹ�����!");
        getLogger().info(pluginName + "��ISOTOPE Studio����!");
        getLogger().info("http://isotopestudio.cc");
    }
}
