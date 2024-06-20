package net.etum.shattered.listeners

import net.etum.shattered.Main
import net.etum.shattered.player.commands.CommandGui
import java.util.*

class CommandManager(main: Main) {
    init {
        commandsGui(main)
    }

    companion object {
        private fun commandsGui(main: Main) {
            main.getCommand("menu")?.setExecutor(CommandGui())
        }
    }
}