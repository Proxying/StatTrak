package uk.co.proxying.stattrak.utils;

import org.bukkit.inventory.ItemStack;

/**
 * Created by Kieran Quigley (Proxying) on 05-Jun-16.
 */
public class BlockTrakUpgrade {

    private String playerName;

    public ItemStack getToUpgrade() {
        return toUpgrade;
    }

    public void setToUpgrade(ItemStack toUpgrade) {
        this.toUpgrade = toUpgrade;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    private ItemStack toUpgrade;
}
