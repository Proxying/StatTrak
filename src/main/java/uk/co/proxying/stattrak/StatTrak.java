package uk.co.proxying.stattrak;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.proxying.stattrak.commands.BlockGiveCommand;
import uk.co.proxying.stattrak.commands.StatGiveCommand;
import uk.co.proxying.stattrak.listeners.ApplicationListener;
import uk.co.proxying.stattrak.listeners.BreakListener;
import uk.co.proxying.stattrak.listeners.DeathListener;
import uk.co.proxying.stattrak.utils.BlockTrakUpgrade;
import uk.co.proxying.stattrak.utils.Menu;
import uk.co.proxying.stattrak.utils.StatTrakUpgrade;

import java.util.HashMap;
import java.util.Map;

public final class StatTrak extends JavaPlugin {

    public static StatTrak getInstance() {
        return instance;
    }

    private static StatTrak instance;

    public Menu getStatTrakConfirmationMenu() {
        return statTrakConfirmationMenu;
    }

    public void setStatTrakConfirmationMenu(Menu confirmationMenu) {
        this.statTrakConfirmationMenu = confirmationMenu;
    }

    private Menu statTrakConfirmationMenu;

    public Menu getBlockTrakConfirmationMenu() {
        return blockTrakConfirmationMenu;
    }

    public void setBlockTrakConfirmationMenu(Menu blockTrakConfirmationMenu) {
        this.blockTrakConfirmationMenu = blockTrakConfirmationMenu;
    }

    private Menu blockTrakConfirmationMenu;

    public Map<String, StatTrakUpgrade> getCurrentlyConfirmingStat() {
        return currentlyConfirmingStat;
    }

    private Map<String, StatTrakUpgrade> currentlyConfirmingStat = new HashMap<>();

    public Map<String, BlockTrakUpgrade> getCurrentlyConfirmingBlock() {
        return currentlyConfirmingBlock;
    }

    private Map<String, BlockTrakUpgrade> currentlyConfirmingBlock = new HashMap<>();

    public int[] getLeftMenuSlots() {
        return leftMenuSlots;
    }

    private int[] leftMenuSlots = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30, 36, 37, 38, 39, 45, 46, 47, 48};

    public int[] getRightMenuSlots() {
        return rightMenuSlots;
    }

    private int[] rightMenuSlots = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35, 41, 42, 43, 44, 50, 51, 52, 53};

    public int[] getMiddleMenuSlots() {
        return middleMenuSlots;
    }

    private int[] middleMenuSlots = new int[]{4, 13, 22, 31, 40, 49};

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ApplicationListener(), this);
        Bukkit.getPluginManager().registerEvents(new BreakListener(), this);
        getCommand("sgive").setExecutor(new StatGiveCommand());
        getCommand("bgive").setExecutor(new BlockGiveCommand());
    }

    @Override
    public void onDisable() {
    }
}
