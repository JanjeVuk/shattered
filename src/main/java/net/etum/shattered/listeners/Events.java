package net.etum.shattered.listeners;

import net.etum.shattered.Main;
import net.etum.shattered.manager.ConnexionManager;
import net.etum.shattered.players.classe.Knight.Knight;
import net.etum.shattered.players.classe.Knight.KnightAbility;
import org.bukkit.plugin.PluginManager;

public class Events {

    public Events(Main plugin) {
        PluginManager pm = plugin.getServer().getPluginManager();

        connectionEvents(pm);
    }


    private void connectionEvents(PluginManager pm) {
        pm.registerEvents(new ConnexionManager(), Main.getPlugin());

    }

    private void ClassEvents(PluginManager pm) {
        pm.registerEvents(new KnightAbility(), Main.getPlugin());
    }

}
