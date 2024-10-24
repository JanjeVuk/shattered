package net.etum.shattered.players.Ability;

import net.etum.shattered.players.Hero;
import net.etum.shattered.players.manager.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class HealingBoost extends Ability implements Listener {


    public HealingBoost() {
        super("name", "description", true);
    }

    /*
     * Le Chevalier bénéficie d'une augmentation passive de l'efficacité des soins,
     * qui varie en fonction de son niveau ou d'améliorations spécifiques.
     * Cette capacité peut être adaptée pour offrir des bonus plus importants à mesure que le joueur progresse.
     */
    @EventHandler
    public void onRegenPassifPlayer(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        if(!PlayerDataManager.getPlayerData(player).hasAbility(this, player)){
            return;
        }



        // Base de la régénération (20% du montant original)
        double regenAmount = event.getAmount() * 0.2;


        // Applique la nouvelle quantité de régénération
        event.setAmount(regenAmount);
    }

}
