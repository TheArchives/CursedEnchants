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

    public int getMaxCurses() {
        String r = this.config.getString("max_curses");

        if (r.equalsIgnoreCase("same")) {
            return -1;
        }

        try {
            return Integer.parseInt(r);
        } catch (NumberFormatException e) {
            this.plugin.getLogger().warning(String.format("%s is not a valid number!", r));
            return -1;
        }
    }

    public int getTickInterval() {
        return this.config.getInt("tickrate");
    }
}
