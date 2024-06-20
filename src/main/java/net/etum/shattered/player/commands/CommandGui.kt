package net.etum.shattered.player.commands;

import net.etum.shattered.gui.MainGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CommandGui implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(!(sender instanceof Player)) {
            Bukkit.getLogger().warning("This command can only be executed by a player!");
            return false;
        }

        Player player = (Player) sender;

        MainGui.open(player);


        return false;
    }
}
