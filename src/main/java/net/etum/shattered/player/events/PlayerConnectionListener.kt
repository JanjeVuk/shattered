package net.etum.shattered.player.events

import net.etum.shattered.player.PlayerDataManager
import net.etum.shattered.player.PlayerDataManager.Companion.allKnights
import net.etum.shattered.player.PlayerDataManager.Companion.saveKnight
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerConnectionListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val playerDataManager = PlayerDataManager(player)
        playerDataManager.loadConfig()
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        saveKnight(player)
        PlayerDataManager.knightMap.remove(player.uniqueId)
    }
}