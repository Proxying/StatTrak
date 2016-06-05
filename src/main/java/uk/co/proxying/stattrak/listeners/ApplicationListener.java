package uk.co.proxying.stattrak.listeners;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import uk.co.proxying.stattrak.StatTrak;
import uk.co.proxying.stattrak.utils.*;

/**
 * Created by Kieran Quigley (Proxying) on 01-Jun-16.
 */
public class ApplicationListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerApplyStatTrak(InventoryClickEvent event) {
        if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        if (!event.getInventory().getName().equalsIgnoreCase("container.crafting")) return;
        if (event.getSlotType() == InventoryType.SlotType.ARMOR) return;
        ItemStack cursor = event.getCursor();
        if (!(StatUtils.isStatTrakCreator(cursor))) return;
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (!StatUtils.isItemWhitelistedForStatTrak(clickedItem)) {
            if (!new Config<String>("stattrak.error-attempted-non-whitelist-stattrak-message").getValue().equalsIgnoreCase("null")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("stattrak.error-attempted-non-whitelist-stattrak-message").getValue()));
            }
            return;
        }
        if (StatUtils.isStatTrakItem(clickedItem)) {
            if (!new Config<String>("stattrak.error-already-stattrak-item-message").getValue().equalsIgnoreCase("null")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("stattrak.error-already-stattrak-item-message").getValue()));
            }
            return;
        }
        event.setCancelled(true);
        if (cursor.getAmount() > 1) {
            cursor.setAmount(cursor.getAmount() - 1);
            player.getInventory().addItem(cursor);
        }
        event.setCursor(null);
        event.setCurrentItem(new ItemStack(Material.AIR));
        StatTrakUpgrade statTrakUpgrade = new StatTrakUpgrade();
        statTrakUpgrade.setPlayerName(player.getName());
        statTrakUpgrade.setToUpgrade(clickedItem.clone());
        StatTrak.getInstance().getCurrentlyConfirmingStat().put(player.getName(), statTrakUpgrade);
        if (StatTrak.getInstance().getStatTrakConfirmationMenu() == null) {
            Menu menu = new Menu("StatTrak Confirmation", 54) {
                @Override
                public void onClick(InventoryClickEvent var1, int var2) {
                    if (StatUtils.isOnLeftSide(var2)) {
                        StatTrakUpgrade sUpgrade = StatTrak.getInstance().getCurrentlyConfirmingStat().get(player.getName());
                        player.getInventory().addItem(StatUtils.applyStatTrak(sUpgrade.getToUpgrade()));
                        if (!new Config<String>("stattrak.success-applied-stattrak-message").getValue().equalsIgnoreCase("null")) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("stattrak.success-applied-stattrak-message").getValue()));
                        }
                        StatTrak.getInstance().getCurrentlyConfirmingStat().remove(player.getName());
                        player.closeInventory();
                    } else if (StatUtils.isInMiddle(var2)) {
                    } else if (StatUtils.isOnRightSide(var2)) {
                        player.getInventory().addItem(StatUtils.createStatTrakItem());
                        StatTrakUpgrade sUpgrade1 = StatTrak.getInstance().getCurrentlyConfirmingStat().get(player.getName());
                        player.getInventory().addItem(sUpgrade1.getToUpgrade());
                        StatTrak.getInstance().getCurrentlyConfirmingStat().remove(player.getName());
                        player.closeInventory();
                    }
                }
                @Override
                public void onClose(InventoryCloseEvent var1) {
                    if (StatTrak.getInstance().getCurrentlyConfirmingStat().containsKey(player.getName())) {
                        player.getInventory().addItem(StatUtils.createStatTrakItem());
                        StatTrakUpgrade sUpgrade = StatTrak.getInstance().getCurrentlyConfirmingStat().get(player.getName());
                        player.getInventory().addItem(sUpgrade.getToUpgrade());
                        StatTrak.getInstance().getCurrentlyConfirmingStat().remove(player.getName());
                    }
                }
            };
            for (int left : StatTrak.getInstance().getLeftMenuSlots()) {
                menu.setItem(left, new ItemBuilder().setItem(Material.WOOL, DyeColor.LIME.getWoolData(), ChatColor.GREEN + "Confirm").build());
            }
            for (int middle : StatTrak.getInstance().getMiddleMenuSlots()) {
                menu.setItem(middle, new ItemBuilder().setItem(Material.STAINED_GLASS_PANE, DyeColor.GRAY.getWoolData(), ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "OR").build());
            }
            for (int right : StatTrak.getInstance().getRightMenuSlots()) {
                menu.setItem(right, new ItemBuilder().setItem(Material.WOOL, DyeColor.RED.getWoolData(), ChatColor.RED + "Cancel").build());
            }
            StatTrak.getInstance().setStatTrakConfirmationMenu(menu);
            menu.openFor(player);
        } else {
            StatTrak.getInstance().getStatTrakConfirmationMenu().openFor(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerApplyBlockTrak(InventoryClickEvent event) {
        if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        if (!event.getInventory().getName().equalsIgnoreCase("container.crafting")) return;
        if (event.getSlotType() == InventoryType.SlotType.ARMOR) return;
        ItemStack cursor = event.getCursor();
        if (!(StatUtils.isBlockTrakCreator(cursor))) return;
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (!StatUtils.isItemWhitelistedForBlockTrak(clickedItem)) {
            if (!new Config<String>("blocktrak.error-attempted-non-whitelist-blocktrak-message").getValue().equalsIgnoreCase("null")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.error-attempted-non-whitelist-blocktrak-message").getValue()));
            }
            return;
        }
        if (StatUtils.isBlockTrakItem(clickedItem)) {
            if (!new Config<String>("blocktrak.error-already-blocktrak-item-message").getValue().equalsIgnoreCase("null")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.error-already-blocktrak-item-message").getValue()));
            }
            return;
        }
        event.setCancelled(true);
        if (cursor.getAmount() > 1) {
            cursor.setAmount(cursor.getAmount() - 1);
            player.getInventory().addItem(cursor);
        }
        event.setCursor(null);
        event.setCurrentItem(new ItemStack(Material.AIR));
        BlockTrakUpgrade blockTrakUpgrade = new BlockTrakUpgrade();
        blockTrakUpgrade.setPlayerName(player.getName());
        blockTrakUpgrade.setToUpgrade(clickedItem.clone());
        StatTrak.getInstance().getCurrentlyConfirmingBlock().put(player.getName(), blockTrakUpgrade);
        if (StatTrak.getInstance().getBlockTrakConfirmationMenu() == null) {
            Menu menu = new Menu("BlockTrak Confirmation", 54) {
                @Override
                public void onClick(InventoryClickEvent var1, int var2) {
                    if (StatUtils.isOnLeftSide(var2)) {
                        BlockTrakUpgrade bUpgrade = StatTrak.getInstance().getCurrentlyConfirmingBlock().get(player.getName());
                        player.getInventory().addItem(StatUtils.applyBlockTrack(bUpgrade.getToUpgrade()));
                        if (!new Config<String>("blocktrak.success-applied-blocktrak-message").getValue().equalsIgnoreCase("null")) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.success-applied-blocktrak-message").getValue()));
                        }
                        StatTrak.getInstance().getCurrentlyConfirmingBlock().remove(player.getName());
                        player.closeInventory();
                    } else if (StatUtils.isInMiddle(var2)) {
                    } else if (StatUtils.isOnRightSide(var2)) {
                        player.getInventory().addItem(StatUtils.createBlockTrakItem());
                        BlockTrakUpgrade bUpgrade1 = StatTrak.getInstance().getCurrentlyConfirmingBlock().get(player.getName());
                        player.getInventory().addItem(bUpgrade1.getToUpgrade());
                        StatTrak.getInstance().getCurrentlyConfirmingBlock().remove(player.getName());
                        player.closeInventory();
                    }
                }

                @Override
                public void onClose(InventoryCloseEvent var1) {
                    if (StatTrak.getInstance().getCurrentlyConfirmingBlock().containsKey(player.getName())) {
                        player.getInventory().addItem(StatUtils.createBlockTrakItem());
                        BlockTrakUpgrade bUpgrade1 = StatTrak.getInstance().getCurrentlyConfirmingBlock().get(player.getName());
                        player.getInventory().addItem(bUpgrade1.getToUpgrade());
                        StatTrak.getInstance().getCurrentlyConfirmingBlock().remove(player.getName());
                    }
                }
            };
            for (int left : StatTrak.getInstance().getLeftMenuSlots()) {
                menu.setItem(left, new ItemBuilder().setItem(Material.WOOL, DyeColor.LIME.getWoolData(), ChatColor.GREEN + "Confirm").build());
            }
            for (int middle : StatTrak.getInstance().getMiddleMenuSlots()) {
                menu.setItem(middle, new ItemBuilder().setItem(Material.STAINED_GLASS_PANE, DyeColor.GRAY.getWoolData(), ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "OR").build());
            }
            for (int right : StatTrak.getInstance().getRightMenuSlots()) {
                menu.setItem(right, new ItemBuilder().setItem(Material.WOOL, DyeColor.RED.getWoolData(), ChatColor.RED + "Cancel").build());
            }
            StatTrak.getInstance().setBlockTrakConfirmationMenu(menu);
            menu.openFor(player);
        } else {
            StatTrak.getInstance().getBlockTrakConfirmationMenu().openFor(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (StatTrak.getInstance().getCurrentlyConfirmingBlock().containsKey(player.getName())) {
            player.getInventory().addItem(StatUtils.createBlockTrakItem());
            BlockTrakUpgrade bUpgrade1 = StatTrak.getInstance().getCurrentlyConfirmingBlock().get(player.getName());
            player.getInventory().addItem(bUpgrade1.getToUpgrade());
            StatTrak.getInstance().getCurrentlyConfirmingBlock().remove(player.getName());
        }
        if (StatTrak.getInstance().getCurrentlyConfirmingStat().containsKey(player.getName())) {
            player.getInventory().addItem(StatUtils.createStatTrakItem());
            StatTrakUpgrade sUpgrade = StatTrak.getInstance().getCurrentlyConfirmingStat().get(player.getName());
            player.getInventory().addItem(sUpgrade.getToUpgrade());
            StatTrak.getInstance().getCurrentlyConfirmingStat().remove(player.getName());
        }
    }
}