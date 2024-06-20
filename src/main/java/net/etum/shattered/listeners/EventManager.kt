package net.etum.shattered.listeners

import net.etum.shattered.Main
import net.etum.shattered.gui.MainGui
import net.etum.shattered.player.events.PlayerConnectionListener
import org.bukkit.plugin.PluginManager

class EventManager(main: Main) {
    init {
        val pm = main.server.pluginManager

        eventsPlayer(main, pm)
        eventsGui(main, pm)
    }


    private fun eventsPlayer(main: Main, pm: PluginManager) {
        pm.registerEvents(PlayerConnectionListener(), main)
    }

    private fun eventsGui(main: Main, pm: PluginManager) {
        pm.registerEvents(MainGui(), main)
    }
}