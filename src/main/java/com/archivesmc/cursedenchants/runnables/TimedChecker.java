package com.archivesmc.cursedenchants.runnables;


import com.archivesmc.cursedenchants.CursedEnchantsPlugin;
import com.archivesmc.cursedenchants.utils.Reason;
import com.archivesmc.cursedenchants.utils.Target;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TimedChecker implements Runnable {
    private CursedEnchantsPlugin plugin;

    public TimedChecker(CursedEnchantsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            for (ItemStack item : player.getInventory().getArmorContents()) {
                this.plugin.getCurseRegistry().doActions(player, item, new Target(null), Reason.EQUIPPED);
            }

            this.plugin.getCurseRegistry().doActions(player, player.getItemInHand(), new Target(null), Reason.EQUIPPED);
        }
    }
}
