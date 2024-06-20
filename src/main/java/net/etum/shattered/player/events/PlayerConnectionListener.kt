package net.etum.shattered.player.events;

import net.etum.shattered.builder.DamageSource;
import net.etum.shattered.player.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerDataManager playerDataManager = new PlayerDataManager(player);
        playerDataManager.loadConfig();

    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerDataManager.saveKnight(player);
        PlayerDataManager.getAllKnights().remove(player.getUniqueId());
    }

}
