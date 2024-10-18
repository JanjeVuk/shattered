package net.etum.shattered.manager;

import net.etum.shattered.Main;
import net.etum.shattered.players.manager.PlayerDataManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnexionManager implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        // récupération du joueur
        Player player = event.getPlayer();

        // chargement des données du joueur
        PlayerDataManager.loadData(player);

        // message pour indiquer qu'un joueur a rejoint le serveur
        event.joinMessage(Component.text("&7[&a+&7] &f" + player.getName()));

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();


        PlayerDataManager.saveData(player);

        // suppression des données du joueur de la Hashmap
        PlayerDataManager.deleteDataFromHash(player);

        // message pour indiquer qu'un joueur a rejoint le serveur
        event.quitMessage(Component.text("&7[&c-&7] &f" + player.getName()));
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
    }

}
