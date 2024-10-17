package net.etum.shattered;

import net.etum.shattered.listeners.Commands;
import net.etum.shattered.listeners.Events;
import net.etum.shattered.players.Obscurus;
import net.etum.shattered.players.PlayerClass;
import net.etum.shattered.players.classe.Knight;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Main extends JavaPlugin {


    private static Main plugin;


    @Override
    public void onEnable() {

        plugin = this;

        new Commands(this);
        new Events(this);

        saveDefaultConfig();
        getLogger().info("Shattered has been enabled!");

        Knight knight = new Knight(Bukkit.getPlayer(UUID.randomUUID()), null, 0, 10);

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
