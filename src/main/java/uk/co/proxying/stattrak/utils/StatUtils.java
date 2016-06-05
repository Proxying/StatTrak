package uk.co.proxying.stattrak.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.co.proxying.stattrak.StatTrak;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kieran Quigley (Proxying) on 01-Jun-16.
 */
public class StatUtils {

    public static boolean isStatTrakCreator(ItemStack itemStack) {
        if (itemStack.getType().getId() == new Config<Integer>("stattrak.creation-item-id").getValue()) {
            if (itemStack.getItemMeta().hasDisplayName()) {
                NBTItem nbtItem = new NBTItem(itemStack);
                if (nbtItem.hasKey(new Config<String>("stattrak.creation-item-nbt-identifier").getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isItemWhitelistedForStatTrak(ItemStack itemStack) {
        for (int whitelistedItems : new Config<List<Integer>>("stattrak.item-whitelist").getValue()) {
            if (itemStack.getType().getId() == whitelistedItems) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStatTrakItem(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("stattrak");
    }

    public static ItemStack incrementKills(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        int kills = nbtItem.getInteger("stattrak");
        kills++;
        nbtItem.setInteger("stattrak", kills);
        ItemStack newItem = nbtItem.getItem();
        ItemMeta meta = newItem.getItemMeta();
        List<String> configLore = retrieveConfigLoreStatTrak();
        for (String cLore : configLore) {
            if (cLore.contains("%kills%")) {
                int index = configLore.indexOf(cLore);
                cLore = cLore.replace("%kills%", String.valueOf(kills));
                configLore.set(index, cLore);
            }
        }
        if (isBlockTrakItem(itemStack)) {
            int breaks = new NBTItem(itemStack).getInteger("blocktrak");
            List<String> configBlockLore = retrieveConfigLoreBlockTrak();
            for (String cBLore : configBlockLore) {
                if (cBLore.contains("%blocks%")) {
                    int index = configBlockLore.indexOf(cBLore);
                    cBLore = cBLore.replace("%blocks%", String.valueOf(breaks));
                    configBlockLore.set(index, cBLore);
                }
            }
            configLore.addAll(configBlockLore);
        }
        meta.setLore(configLore);
        newItem.setItemMeta(meta);
        return newItem;
    }

    private static List<String> retrieveConfigLoreStatTrak() {
        List<String> loreToReturn = new ArrayList<>();
        for (String lore : new Config<List<String>>("stattrak.applied-item-lore").getValue()) {
            loreToReturn.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        return loreToReturn;
    }

    public static ItemStack createStatTrakItem() {
        ItemStack itemStack;
        int itemMeta;
        if (new Config<Integer>("stattrak.creation-item-metadata").getValue() != 0) {
            itemMeta = new Config<Integer>("stattrak.creation-item-metadata").getValue();
            itemStack = new ItemStack(Material.getMaterial(new Config<Integer>("stattrak.creation-item-id").getValue()), 1, (short) itemMeta);
        } else {
            itemStack = new ItemStack(Material.getMaterial(new Config<Integer>("stattrak.creation-item-id").getValue()), 1);
        }
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        for (String parsedLore : new Config<List<String>>("stattrak.creation-item-lore").getValue()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', parsedLore));
        }
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', new Config<String>("stattrak.creation-item-name").getValue()));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean(new Config<String>("stattrak.creation-item-nbt-identifier").getValue(), true);
        return nbtItem.getItem();
    }

    public static ItemStack applyStatTrak(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> configLore = retrieveConfigLoreStatTrak();
        for (String cLore : configLore) {
            if (cLore.contains("%kills%")) {
                int index = configLore.indexOf(cLore);
                cLore = cLore.replace("%kills%", "0");
                configLore.set(index, cLore);
            }
        }
        if (isBlockTrakItem(itemStack)) {
            int breaks = new NBTItem(itemStack).getInteger("blocktrak");
            List<String> configBlockLore = retrieveConfigLoreBlockTrak();
            for (String cBLore : configBlockLore) {
                if (cBLore.contains("%blocks%")) {
                    int index = configBlockLore.indexOf(cBLore);
                    cBLore = cBLore.replace("%blocks%", String.valueOf(breaks));
                    configBlockLore.set(index, cBLore);
                }
            }
            configLore.addAll(configBlockLore);
        }
        meta.setLore(configLore);
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("stattrak", 0);
        return nbtItem.getItem();
    }

    public static boolean isBlockTrakCreator(ItemStack itemStack) {
        if (itemStack.getType().getId() == new Config<Integer>("blocktrak.creation-item-id").getValue()) {
            if (itemStack.getItemMeta().hasDisplayName()) {
                NBTItem nbtItem = new NBTItem(itemStack);
                if (nbtItem.hasKey(new Config<String>("blocktrak.creation-item-nbt-identifier").getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isItemWhitelistedForBlockTrak(ItemStack itemStack) {
        for (int whitelistedItems : new Config<List<Integer>>("blocktrak.item-whitelist").getValue()) {
            if (itemStack.getType().getId() == whitelistedItems) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlockTrakItem(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasKey("blocktrak");
    }

    public static ItemStack incrementBlocks(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        int breaks = nbtItem.getInteger("blocktrak");
        breaks++;
        nbtItem.setInteger("blocktrak", breaks);
        ItemStack newItem = nbtItem.getItem();
        ItemMeta meta = newItem.getItemMeta();
        List<String> configLore = new ArrayList<>();
        if (isStatTrakItem(itemStack)) {
            int kills = new NBTItem(itemStack).getInteger("stattrak");
            List<String> configStatLore = retrieveConfigLoreStatTrak();
            for (String cSLore : configStatLore) {
                if (cSLore.contains("%kills%")) {
                    int index = configStatLore.indexOf(cSLore);
                    cSLore = cSLore.replace("%kills%", String.valueOf(kills));
                    configStatLore.set(index, cSLore);
                }
            }
            configLore.addAll(configStatLore);
        }
        configLore.addAll(retrieveConfigLoreBlockTrak());
        for (String cLore : configLore) {
            if (cLore.contains("%blocks%")) {
                int index = configLore.indexOf(cLore);
                cLore = cLore.replace("%blocks%", String.valueOf(breaks));
                configLore.set(index, cLore);
            }
        }
        meta.setLore(configLore);
        newItem.setItemMeta(meta);
        return newItem;
    }

    private static List<String> retrieveConfigLoreBlockTrak() {
        List<String> loreToReturn = new ArrayList<>();
        for (String lore : new Config<List<String>>("blocktrak.applied-item-lore").getValue()) {
            loreToReturn.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        return loreToReturn;
    }

    public static ItemStack createBlockTrakItem() {
        ItemStack itemStack;
        int itemMeta;
        if (new Config<Integer>("blocktrak.creation-item-metadata").getValue() != 0) {
            itemMeta = new Config<Integer>("blocktrak.creation-item-metadata").getValue();
            itemStack = new ItemStack(Material.getMaterial(new Config<Integer>("blocktrak.creation-item-id").getValue()), 1, (short) itemMeta);
        } else {
            itemStack = new ItemStack(Material.getMaterial(new Config<Integer>("blocktrak.creation-item-id").getValue()), 1);
        }
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        for (String parsedLore : new Config<List<String>>("blocktrak.creation-item-lore").getValue()) {
            lore.add(ChatColor.translateAlternateColorCodes('&', parsedLore));
        }
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', new Config<String>("blocktrak.creation-item-name").getValue()));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean(new Config<String>("blocktrak.creation-item-nbt-identifier").getValue(), true);
        return nbtItem.getItem();
    }

    public static ItemStack applyBlockTrack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> configLore = new ArrayList<>();
        if (isStatTrakItem(itemStack)) {
            int kills = new NBTItem(itemStack).getInteger("stattrak");
            List<String> configStatLore = retrieveConfigLoreStatTrak();
            for (String cSLore : configStatLore) {
                if (cSLore.contains("%kills%")) {
                    int index = configStatLore.indexOf(cSLore);
                    cSLore = cSLore.replace("%kills%", String.valueOf(kills));
                    configStatLore.set(index, cSLore);
                }
            }
            configLore.addAll(configStatLore);
        }
        configLore.addAll(retrieveConfigLoreBlockTrak());
        for (String cLore : configLore) {
            if (cLore.contains("%blocks%")) {
                int index = configLore.indexOf(cLore);
                cLore = cLore.replace("%blocks%", "0");
                configLore.set(index, cLore);
            }
        }
        meta.setLore(configLore);
        itemStack.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setInteger("blocktrak", 0);
        return nbtItem.getItem();
    }

    public static boolean isOnLeftSide(int slot) {
        for (int leftSlot : StatTrak.getInstance().getLeftMenuSlots()) {
            if (leftSlot == slot) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOnRightSide(int slot) {
        for (int rightSlot : StatTrak.getInstance().getRightMenuSlots()) {
            if (rightSlot == slot) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInMiddle(int slot) {
        for (int middleSlot : StatTrak.getInstance().getMiddleMenuSlots()) {
            if (middleSlot == slot) {
                return true;
            }
        }
        return false;
    }
}