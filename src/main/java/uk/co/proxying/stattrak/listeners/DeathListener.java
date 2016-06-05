package uk.co.proxying.stattrak.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import uk.co.proxying.stattrak.utils.Config;
import uk.co.proxying.stattrak.utils.NBTItem;
import uk.co.proxying.stattrak.utils.StatUtils;

/**
 * Created by Kieran Quigley (Proxying) on 01-Jun-16.
 */
public class DeathListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMobKill(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            if (new Config<Boolean>("stattrak.monster-kills-counted").getValue()) {
                Player killer = event.getEntity().getKiller();
                if (killer.getItemInHand() != null && killer.getItemInHand().getType() != Material.AIR) {
                    if (StatUtils.isItemWhitelistedForStatTrak(killer.getItemInHand())) {
                        ItemStack killerItem = killer.getItemInHand();
                        if (StatUtils.isStatTrakItem(killerItem)) {
                            killer.setItemInHand(new ItemStack(Material.AIR));
                            ItemStack newStack = StatUtils.incrementKills(killerItem);
                            killer.setItemInHand(newStack);
                            if (new Config<Boolean>("stattrak.inform-player-kill-increase").getValue()) {
                                int newKills = new NBTItem(newStack).getInteger("stattrak");
                                int oldKills = newKills - 1;
                                if (!new Config<String>("stattrak.player-kill-increase-message").getValue().equalsIgnoreCase("null")) {
                                    killer.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("stattrak.player-kill-increase-message").getValue().replace("%oldkills%", String.valueOf(oldKills)).replace("%newkills%", String.valueOf(newKills))));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerKill(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            if (new Config<Boolean>("stattrak.player-kills-counted").getValue()) {
                Player killer = event.getEntity().getKiller();
                if (killer.getItemInHand() != null && killer.getItemInHand().getType() != Material.AIR) {
                    if (StatUtils.isItemWhitelistedForStatTrak(killer.getItemInHand())) {
                        ItemStack killerItem = killer.getItemInHand();
                        if (StatUtils.isStatTrakItem(killerItem)) {
                            killer.setItemInHand(new ItemStack(Material.AIR));
                            ItemStack newStack = StatUtils.incrementKills(killerItem);
                            killer.setItemInHand(newStack);
                            if (new Config<Boolean>("stattrak.inform-player-kill-increase").getValue()) {
                                int newKills = new NBTItem(newStack).getInteger("stattrak");
                                int oldKills = newKills - 1;
                                if (!new Config<String>("stattrak.player-kill-increase-message").getValue().equalsIgnoreCase("null")) {
                                    killer.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("stattrak.player-kill-increase-message").getValue().replace("%oldkills%", String.valueOf(oldKills)).replace("%newkills%", String.valueOf(newKills))));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
