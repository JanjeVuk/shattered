package net.etum.shattered.player.commands

import net.etum.shattered.gui.MainGui
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandGui : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, s: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            Bukkit.getLogger().warning("This command can only be executed by a player!")
            return false
        }

        MainGui.open(sender)


        return false
    }
}