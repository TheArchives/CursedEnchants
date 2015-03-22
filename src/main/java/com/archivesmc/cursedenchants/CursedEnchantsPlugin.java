package com.archivesmc.cursedenchants;

import com.archivesmc.cursedenchants.curses.Curse;
import com.archivesmc.cursedenchants.curses.registry.CurseRegistry;
import com.archivesmc.cursedenchants.runnables.TimedChecker;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class CursedEnchantsPlugin extends JavaPlugin {
    private Config config;
    private CurseRegistry curseRegistry;
    private TimedChecker timedChecker;
    private BukkitTask timedTask = null;

    @Override
    public void onEnable() {
        if(!this.getServer().getName().equalsIgnoreCase("spigot")) {
            this.getLogger().severe("This plugin only supports Spigot!");
            this.getLogger().info(String.format("Current server name: %s", this.getServer().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.config = new Config(this);
        this.curseRegistry = new CurseRegistry(this);
        this.timedChecker = new TimedChecker(this);

        this.timedTask = this.getServer().getScheduler().runTaskTimer(
                this, this.timedChecker, this.config.getTickInterval(), this.config.getTickInterval()
        );
    }

    @Override
    public void onDisable() {
        if (this.timedTask != null) {
            this.timedTask.cancel();
        }
    }

    public int getMaxEnchants(ItemStack item) {
        if (this.config.getMaxCurses() < 0) {
            return item.getEnchantments().size();
        } else {
            return this.config.getMaxCurses();
        }
    }

    public CurseRegistry getCurseRegistry() {
        return this.curseRegistry;
    }
}
