package uk.co.proxying.stattrak.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import uk.co.proxying.stattrak.utils.Config;
import uk.co.proxying.stattrak.utils.NBTItem;
import uk.co.proxying.stattrak.utils.StatUtils;

/**
 * Created by Kieran Quigley (Proxying) on 03-Jun-16.
 */
public class BreakListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        if (event.getPlayer() != null) {
            Player breaker = event.getPlayer();
            if (breaker.getItemInHand() != null && breaker.getItemInHand().getType() != Material.AIR) {
                if (StatUtils.isItemWhitelistedForBlockTrak(breaker.getItemInHand())) {
                    ItemStack breakerItem = breaker.getItemInHand();
                    if (StatUtils.isBlockTrakItem(breakerItem)) {
                        if (breaker.getGameMode() == GameMode.CREATIVE) {
                            if (!new Config<Boolean>("blocktrak.creative-mode-counted").getValue()) {
                                return;
                            }
                        }
                        breaker.setItemInHand(new ItemStack(Material.AIR));
                        ItemStack newStack = StatUtils.incrementBlocks(breakerItem);
                        breaker.setItemInHand(newStack);
                        if (new Config<Boolean>("blocktrak.inform-player-stat-increase").getValue()) {
                            int newBlocks = new NBTItem(newStack).getInteger("blocktrak");
                            int oldBlocks = newBlocks - 1;
                            if (!new Config<String>("blocktrak.player-stat-increase-message").getValue().equalsIgnoreCase("null")) {
                                breaker.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.player-stat-increase-message").getValue().replace("%oldblocks%", String.valueOf(oldBlocks)).replace("%newblocks%", String.valueOf(newBlocks))));
                            }
                        }
                    }
                }
            }
        }
    }

}
