package net.etum.shattered;

import net.etum.shattered.listeners.Commands;
import net.etum.shattered.listeners.Events;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    private static Main plugin;


    @Override
    public void onEnable() {

        plugin = this;

        new Commands(this);
        new Events(this);

        saveDefaultConfig();
        getLogger().info("Shattered has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Shattered has been disabled!");
    }

    @Override
    public void onLoad() {
        // TODO: Implement your plugin's onLoad logic here
    }

    public static Main getPlugin() {
        return plugin;
    }


}
