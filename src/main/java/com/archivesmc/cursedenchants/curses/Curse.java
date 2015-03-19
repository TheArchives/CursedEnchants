package com.archivesmc.cursedenchants.curses;

import com.archivesmc.cursedenchants.utils.Reason;
import com.archivesmc.cursedenchants.utils.Target;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class Curse {
    private double overallChance = 0D;
    private String loreName = null;
    private HashMap<Integer, Double> levelChances = null;

    public Curse() {

    }

    public Curse(double overallChance, String loreName, boolean hasLevels) {
        this.setOverallChance(overallChance);
        this.setLoreName(loreName);
        this.setHasLevels(hasLevels);
    }

    public abstract int apply(Player player, ItemStack item, Map<Enchantment,Integer> enchantments, int levelCost,
                              int whichButton, Block enchantingBlock
    );

    public abstract boolean hasEnchantment(ItemStack item);

    public abstract void doAction(Player player, ItemStack item, Target target, int level, Reason reason);

    public abstract void undoAction(Player player, ItemStack item, Target target, int level, Reason reason);

    public void setLoreName(String name) {
        this.loreName = name;
    }

    public void setOverallChance(double chance) {
        this.overallChance = chance;
    }

    public void setHasLevels(boolean hasLevels) {
        if (hasLevels) {
            this.levelChances = new HashMap<>();
        } else {
            this.levelChances = null;
        }
    }

    public void setLevelChance(int level, double chance) {
        if (this.hasLevels()) {
            this.levelChances.put(level, chance);
        }
    }

    public String getLoreName() {
        return this.loreName;
    }

    public double getOverallChance() {
        return this.overallChance;
    }

    public boolean hasLevels() {
        return this.levelChances != null;
    }

    public double getLevelChance(int level) {
        if (this.hasLevels()) {
            return this.levelChances.getOrDefault(level, 0D);
        }

        return 0D;
    }

    public HashMap<Integer, Double> getLevelChances() {
        return this.levelChances;
    }

}
