package com.archivesmc.cursedenchants;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private CursedEnchantsPlugin plugin;
    private FileConfiguration config;

    public Config(CursedEnchantsPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();

        this.plugin.saveDefaultConfig();
        this.plugin.reloadConfig();
    }
}
