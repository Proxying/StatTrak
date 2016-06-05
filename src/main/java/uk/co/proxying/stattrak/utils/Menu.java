package uk.co.proxying.stattrak.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import uk.co.proxying.stattrak.StatTrak;

/**
 * Created by Kieran Quigley (Proxying) on 05-Jun-16.
 */
public abstract class Menu implements Listener {
    private String name;
    private int size;
    private Inventory inventory;

    public Menu(String name, int size) {
        this.name = name;
        this.size = size;
        this.inventory = Bukkit.createInventory(null, size, name);
        Bukkit.getServer().getPluginManager().registerEvents(this, StatTrak.getInstance());
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Menu setItem(int slot, ItemStack itemStack) {
        this.getInventory().setItem(slot, itemStack);
        return this;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getInventory().getTitle().equals(this.getName())) {
            if(event.getWhoClicked() instanceof Player) {
                if(event.getRawSlot() <= event.getInventory().getSize()) {
                    event.setCancelled(true);
                    this.onClick(event, event.getRawSlot());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getInventory().getTitle().equals(this.getName())) {
            this.onClose(event);
        }
    }

    @EventHandler
    public abstract void onClick(InventoryClickEvent var1, int var2);

    @EventHandler
    public abstract void onClose(InventoryCloseEvent var1);

    public void openFor(Player player) {
        player.openInventory(this.getInventory());
    }
}
