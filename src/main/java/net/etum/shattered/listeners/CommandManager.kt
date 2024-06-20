package net.etum.shattered.listeners;

import net.etum.shattered.Main;
import net.etum.shattered.player.commands.CommandGui;

import java.util.Objects;

public class CommandManager {
    public CommandManager(Main main) {

        commandsGui(main);

    }

    private static void commandsGui(Main main){
        Objects.requireNonNull(main.getCommand("menu")).setExecutor(new CommandGui());
    }
}
