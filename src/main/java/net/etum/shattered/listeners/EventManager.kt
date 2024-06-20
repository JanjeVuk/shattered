package net.etum.shattered.listeners;

import net.etum.shattered.Main;
import net.etum.shattered.gui.MainGui;
import net.etum.shattered.player.events.PlayerConnectionListener;
import org.bukkit.plugin.PluginManager;

public class EventManager {
    public EventManager(Main main) {

        PluginManager pm = main.getServer().getPluginManager();

        eventsPlayer(main, pm);
        eventsGui(main, pm);

    }


    private void eventsPlayer(Main main, PluginManager pm){
        pm.registerEvents(new PlayerConnectionListener(), main);
    }

    private void eventsGui(Main main, PluginManager pm){
        pm.registerEvents(new MainGui(), main);
    }
}
