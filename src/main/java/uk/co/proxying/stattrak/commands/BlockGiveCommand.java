package uk.co.proxying.stattrak.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import uk.co.proxying.stattrak.utils.Config;
import uk.co.proxying.stattrak.utils.StatUtils;

/**
 * Created by Kieran Quigley (Proxying) on 03-Jun-16.
 */
public class BlockGiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (!sender.hasPermission(new Config<String>("blocktrak.give-command-permission-node").getValue())) {
                if (!new Config<String>("blocktrak.error-give-command-permission-fail").getValue().equalsIgnoreCase("null")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.error-give-command-permission-fail").getValue()));
                }
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                ItemStack stack = StatUtils.createBlockTrakItem();
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), stack);
                    if (!new Config<String>("blocktrak.completion-give-command-inventory-full-message").getValue().equalsIgnoreCase("null")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-message").getValue().replace("%amount%", "1")));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-inventory-full-title").getValue().equalsIgnoreCase("null") && !new Config<String>("blocktrak.completion-give-command-inventory-full-subtitle").getValue().equalsIgnoreCase("null")) {
                        player.sendTitle(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-title").getValue().replace("%amount%", "1")), ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-subtitle").getValue().replace("%amount%", "1")));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-sender-success").getValue().equalsIgnoreCase("null")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-sender-success").getValue().replace("%player%", player.getName()).replace("%amount%", "1")));
                    }
                    return true;
                } else {
                    player.getInventory().addItem(stack);
                    if (!new Config<String>("blocktrak.completion-give-command-message").getValue().equalsIgnoreCase("null")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-message").getValue().replace("%amount%", "1")));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-title").getValue().equalsIgnoreCase("null") && !new Config<String>("blocktrak.completion-give-command-subtitle").getValue().equalsIgnoreCase("null")) {
                        player.sendTitle(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-title").getValue().replace("%amount%", "1")), ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-subtitle").getValue().replace("%amount%", "1")));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-sender-success").getValue().equalsIgnoreCase("null")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-sender-success").getValue().replace("%player%", player.getName()).replace("%amount%", "1")));
                    }
                    return true;
                }
            } else if (args.length == 1) {
                int amount;
                try {
                    amount = Integer.valueOf(args[0]);
                } catch (NumberFormatException e) {
                    return true;
                }
                if (amount < 0) {
                    return true;
                }
                ItemStack stack = StatUtils.createBlockTrakItem();
                stack.setAmount(amount);
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItemNaturally(player.getLocation(), stack);
                    if (!new Config<String>("blocktrak.completion-give-command-inventory-full-message").getValue().equalsIgnoreCase("null")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-message").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-inventory-full-title").getValue().equalsIgnoreCase("null") && !new Config<String>("blocktrak.completion-give-command-inventory-full-subtitle").getValue().equalsIgnoreCase("null")) {
                        player.sendTitle(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-title").getValue().replace("%amount%", String.valueOf(amount))), ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-subtitle").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-sender-success").getValue().equalsIgnoreCase("null")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-sender-success").getValue().replace("%player%", player.getName()).replace("%amount%", String.valueOf(amount))));
                    }
                    return true;
                } else {
                    player.getInventory().addItem(stack);
                    if (!new Config<String>("blocktrak.completion-give-command-message").getValue().equalsIgnoreCase("null")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-message").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-title").getValue().equalsIgnoreCase("null") && !new Config<String>("blocktrak.completion-give-command-subtitle").getValue().equalsIgnoreCase("null")) {
                        player.sendTitle(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-title").getValue().replace("%amount%", String.valueOf(amount))), ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-subtitle").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-sender-success").getValue().equalsIgnoreCase("null")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-sender-success").getValue().replace("%player%", player.getName()).replace("%amount%", String.valueOf(amount))));
                    }
                    return true;
                }
            } else if (args.length == 2) {
                if (Bukkit.getPlayer(args[0]) == null) {
                    return true;
                }
                Player player1 = Bukkit.getPlayer(args[0]);
                int amount;
                try {
                    amount = Integer.valueOf(args[1]);
                } catch (NumberFormatException e) {
                    return true;
                }
                if (amount < 0) {
                    return true;
                }
                ItemStack stack = StatUtils.createBlockTrakItem();
                stack.setAmount(amount);
                if (player1.getInventory().firstEmpty() == -1) {
                    player1.getWorld().dropItemNaturally(player1.getLocation(), stack);
                    if (!new Config<String>("blocktrak.completion-give-command-inventory-full-message").getValue().equalsIgnoreCase("null")) {
                        player1.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-message").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-inventory-full-title").getValue().equalsIgnoreCase("null") && !new Config<String>("blocktrak.completion-give-command-inventory-full-subtitle").getValue().equalsIgnoreCase("null")) {
                        player1.sendTitle(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-title").getValue().replace("%amount%", String.valueOf(amount))), ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-subtitle").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-sender-success").getValue().equalsIgnoreCase("null")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-sender-success").getValue().replace("%player%", player1.getName()).replace("%amount%", String.valueOf(amount))));
                    }
                    return true;
                } else {
                    player1.getInventory().addItem(stack);
                    if (!new Config<String>("blocktrak.completion-give-command-message").getValue().equalsIgnoreCase("null")) {
                        player1.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-message").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-title").getValue().equalsIgnoreCase("null") && !new Config<String>("blocktrak.completion-give-command-subtitle").getValue().equalsIgnoreCase("null")) {
                        player1.sendTitle(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-title").getValue().replace("%amount%", String.valueOf(amount))), ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-subtitle").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-sender-success").getValue().equalsIgnoreCase("null")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-sender-success").getValue().replace("%player%", player1.getName()).replace("%amount%", String.valueOf(amount))));
                    }
                    return true;
                }
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length == 2) {
                if (Bukkit.getPlayer(args[0]) == null) {
                    return true;
                }
                Player player1 = Bukkit.getPlayer(args[0]);
                int amount;
                try {
                    amount = Integer.valueOf(args[1]);
                } catch (NumberFormatException e) {
                    return true;
                }
                if (amount < 0) {
                    return true;
                }
                ItemStack stack = StatUtils.createBlockTrakItem();
                stack.setAmount(amount);
                if (player1.getInventory().firstEmpty() == -1) {
                    player1.getWorld().dropItemNaturally(player1.getLocation(), stack);
                    if (!new Config<String>("blocktrak.completion-give-command-inventory-full-message").getValue().equalsIgnoreCase("null")) {
                        player1.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-message").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-inventory-full-title").getValue().equalsIgnoreCase("null") && !new Config<String>("blocktrak.completion-give-command-inventory-full-subtitle").getValue().equalsIgnoreCase("null")) {
                        player1.sendTitle(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-title").getValue().replace("%amount%", String.valueOf(amount))), ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-inventory-full-subtitle").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-sender-success").getValue().equalsIgnoreCase("null")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-sender-success").getValue().replace("%player%", player1.getName()).replace("%amount%", String.valueOf(amount))));
                    }
                    return true;
                } else {
                    player1.getInventory().addItem(stack);
                    if (!new Config<String>("blocktrak.completion-give-command-message").getValue().equalsIgnoreCase("null")) {
                        player1.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-message").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-title").getValue().equalsIgnoreCase("null") && !new Config<String>("blocktrak.completion-give-command-subtitle").getValue().equalsIgnoreCase("null")) {
                        player1.sendTitle(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-title").getValue().replace("%amount%", String.valueOf(amount))), ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-subtitle").getValue().replace("%amount%", String.valueOf(amount))));
                    }
                    if (!new Config<String>("blocktrak.completion-give-command-sender-success").getValue().equalsIgnoreCase("null")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.completion-give-command-sender-success").getValue().replace("%player%", player1.getName()).replace("%amount%", String.valueOf(amount))));
                    }
                    return true;
                }
            }
        }
        return true;
    }
}
