package com.archivesmc.cursedenchants;

import com.archivesmc.cursedenchants.curses.Curse;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CursedEnchantsPlugin extends JavaPlugin {
    private Config config;
    private List<Curse> curses;

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        if(!this.getServer().getName().equalsIgnoreCase("spigot")) {
            this.getLogger().severe("This plugin only supports Spigot!");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.curses = new ArrayList<>();
        this.config = new Config(this);
    }
}
