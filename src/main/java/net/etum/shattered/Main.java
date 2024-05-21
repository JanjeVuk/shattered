package net.etum.shattered;


import net.etum.shattered.listeners.CommandManager;
import net.etum.shattered.listeners.EventManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the Shattered plugin.
 * This class is responsible for initializing and shutting down the plugin.
 */
public final class Main extends JavaPlugin {

    // Singleton instance of the plugin
    private static Main instance;

    /**
     * Called when the plugin is enabled.
     * Initializes command and event managers.
     */
    @Override
    public void onEnable() {
        instance = this;

        // Initialize command manager
        new CommandManager(this);

        // Initialize event manager
        new EventManager(this);

        getLogger().info("ShatteredPlugin has been enabled!");
    }

    /**
     * Called when the plugin is disabled.
     * Use this method to handle any necessary cleanup.
     */
    @Override
    public void onDisable() {
        getLogger().info("ShatteredPlugin has been disabled!");
    }

    public static Main getInstance() {
        return instance;
    }
}