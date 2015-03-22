package com.archivesmc.cursedenchants.curses.registry;

import com.archivesmc.cursedenchants.CursedEnchantsPlugin;
import com.archivesmc.cursedenchants.curses.Curse;
import com.archivesmc.cursedenchants.utils.Reason;
import com.archivesmc.cursedenchants.utils.Target;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurseRegistry {
    private CursedEnchantsPlugin plugin;
    private Set<Curse> curses;

    private final Pattern loreMatcher = Pattern.compile("([a-zA-Z0-9\\s]+) ([IVX]+)");

    public CurseRegistry(CursedEnchantsPlugin plugin) {
        this.plugin = plugin;
        this.curses = new HashSet<>();
    }

    public void registerCurse(Curse curse) {
        this.curses.add(curse);
    }

    public void registerCurses(Curse ... curses) {
        for (Curse c : curses) {
            this.registerCurse(c);
        }
    }

    public void unregisterCurse(Curse curse) {
        this.curses.remove(curse);
    }

    public void unregisterCurses(Curse ... curses) {
        for (Curse c : curses) {
            this.unregisterCurse(c);
        }
    }

    public void doActions(Player player, ItemStack item, Target target, Reason reason) {
        for (Map.Entry<Curse, Integer> entry : this.getCurseLevels(item).entrySet()) {
            try {
                entry.getKey().doAction(player, item, target, entry.getValue(), reason);
            } catch (Exception e) {
                this.plugin.getLogger().warning(String.format(
                        "Unable to activate curse: %s",
                        entry.getKey().getLoreName()
                ));

                e.printStackTrace();
            }
        }
    }

    public void undoActions(Player player, ItemStack item, Target target, Reason reason) {
        for (Map.Entry<Curse, Integer> entry : this.getCurseLevels(item).entrySet()) {
            try {
                entry.getKey().undoAction(player, item, target, entry.getValue(), reason);
            } catch (Exception e) {
                this.plugin.getLogger().warning(String.format(
                        "Unable to activate curse: %s",
                        entry.getKey().getLoreName()
                ));

                e.printStackTrace();
            }
        }
    }

    public void applyCurses(Player player, ItemStack item, Map<Enchantment,Integer> enchantments, int levelCost,
                            int whichButton, Block enchantingBlock) {
        int maxCurses = this.plugin.getMaxEnchants(item);
        int numCurses = 0;
        int level;

        for (Curse curse : this.curses) {
            if (numCurses > maxCurses) {
                break;
            }

            level = curse.apply(player, item, enchantments, levelCost, whichButton, enchantingBlock);

            if (level > 0) {
                numCurses += 1;

                this.applyCurseLore(curse, level, item);
            }
        }
    }

    public void applyCurseLore(Curse curse, int level, ItemStack item) {
        item.getItemMeta().getLore().add(String.format(
                "%s%s %s",
                ChatColor.RED,
                curse.getLoreName(),
                this.levelToRoman(level)
        ));
    }

    public Map<Curse, Integer> getCurseLevels(ItemStack item) {
        Map<Curse, Integer> levels = new HashMap<>();
        Matcher matcher;

        String rawLine;
        Curse curse;

        for (String line : item.getItemMeta().getLore()) {
            rawLine = ChatColor.stripColor(line);
            matcher = this.loreMatcher.matcher(rawLine);

            if (matcher.matches()) {
                curse = this.getCurseFromName(matcher.group(0));

                if (curse != null) {
                    levels.put(curse, this.romanToLevel(matcher.group(1)));
                }
            }
        }

        return levels;
    }

    public String levelToRoman(int level) {
        switch (level) {
            case 1: return  "I";
            case 2: return  "II";
            case 3: return  "III";
            case 4: return  "IV";
            case 5: return  "V";
            case 6: return  "VI";
            case 7: return  "VII";
            case 8: return  "VIII";
            case 9: return  "IX";
            case 10: return "X";
            default: return "???";
        }
    }

    public int romanToLevel(String level) {
        switch (level) {
            case "I": return    1;
            case "II": return   2;
            case "III": return  3;
            case "IV": return   4;
            case "V": return    5;
            case "VI": return   6;
            case "VII": return  7;
            case "VIII": return 8;
            case "IX": return   9;
            case "X": return    10;
            default: return     0;
        }
    }

    public Curse getCurseFromName(String name) {
        for (Curse curse : this.curses) {
            if (curse.getLoreName().equals(name)) {
                return curse;
            }
        }

        return null;
    }
}
