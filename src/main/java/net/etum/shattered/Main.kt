package net.etum.shattered

import net.etum.shattered.listeners.CommandManager
import net.etum.shattered.listeners.EventManager
import org.bukkit.plugin.java.JavaPlugin


class Main : JavaPlugin() {
    override fun onEnable() {
        instance = this

        // Initialize command manager
        CommandManager(this)

        // Initialize event manager
        EventManager(this)

        logger.info(instance!!.name + " has been enabled!")
    }


    override fun onDisable() {
        logger.info(instance!!.name + " has been disabled!")
    }

    companion object {
        // Singleton instance of the plugin
        @JvmStatic
        var instance: Main? = null
            private set
    }
}