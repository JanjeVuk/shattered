package net.etum.shattered.listeners;

import net.etum.shattered.Main;
import net.etum.shattered.player.events.PlayerConnectionListener;
import org.bukkit.plugin.PluginManager;

public class EventManager {
    public EventManager(Main main) {

        PluginManager pm = main.getServer().getPluginManager();


    }


    private void eventsPlayer(Main main, PluginManager pm){
        pm.registerEvents(new PlayerConnectionListener(), main);
    }
}
