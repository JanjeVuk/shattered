package net.etum.shattered;


import net.etum.shattered.listeners.CommandManager;
import net.etum.shattered.listeners.EventManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {



    // Singleton instance of the plugin
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize command manager
        new CommandManager(this);

        // Initialize event manager
        new EventManager(this);

        getLogger().info(getInstance().getName() + " has been enabled!");
    }


    @Override
    public void onDisable() {
        getLogger().info(getInstance().getName() + " has been disabled!");
    }

    public static Main getInstance() {
        return instance;
    }
}