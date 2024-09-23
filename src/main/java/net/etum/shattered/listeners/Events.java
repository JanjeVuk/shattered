package net.etum.shattered.listeners;

import net.etum.shattered.Main;
import org.bukkit.plugin.PluginManager;

public class Events {


    PluginManager pm;

    public Events(Main plugin) {
        pm = plugin.getServer().getPluginManager();
    }
}
