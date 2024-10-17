package net.etum.shattered.listeners;

import net.etum.shattered.Main;
import net.etum.shattered.manager.ConnexionManager;
import org.bukkit.plugin.PluginManager;

public class Events {

    public Events(Main plugin) {
        PluginManager pm = plugin.getServer().getPluginManager();

        connectionEvents(pm);
    }


    private void connectionEvents(PluginManager pm) {
        pm.registerEvents(new ConnexionManager(), Main.getPlugin());

    }

}
