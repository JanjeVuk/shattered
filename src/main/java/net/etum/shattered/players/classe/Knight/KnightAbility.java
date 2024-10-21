package net.etum.shattered.players.classe.Knight;

import net.etum.shattered.players.PlayerClass;
import net.etum.shattered.players.manager.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class KnightAbility implements Listener {


    //passif
    @EventHandler
    public void onRegenPassifPlayer(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        // Vérifie si le joueur est de classe Knight
        PlayerClass playerData = PlayerDataManager.getPlayerData(player);
        if (playerData.getClassType() != PlayerClass.ClassType.KNIGHT) {
            return;
        }

        // Base de la régénération (20% du montant original)
        double regenAmount = event.getAmount() * 0.2;


        // Applique la nouvelle quantité de régénération
        event.setAmount(regenAmount);
    }

}
