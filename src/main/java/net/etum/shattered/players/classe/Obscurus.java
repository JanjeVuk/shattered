package net.etum.shattered.players.classe;

import net.etum.shattered.Main;
import net.etum.shattered.players.PlayerClass;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Obscurus extends PlayerClass {

    /*
     * Obscurus :
     *
     * Les Obscurus sont êtres qui on passé un pacte avec une entité du néant.
     *
     * Contrairement aux autres classes, les Obscurus on a set de skills représentés par des items fixe.
     *
     * Leurs capacités dépende de leur affinité à l'entité du néant.
     *
     * Elles sont beaucoup plus puissantes que les autres classes.
     */


    public Obscurus(Player player, int initialExp, int initialMoney, ClassType classType) {
        super(player, initialExp, initialMoney, classType);
    }

    @Override
    public void loadSubclassData(@NotNull YamlConfiguration config) {
        super.loadSubclassData(config);

        // Charge les attributs de la classe
        Main.getPlugin().getLogger().info("Charging obscurus data for player " + player.getName());

        setClassType(ClassType.OBSCURUS);
    }

    @Override
    public void saveSubclassData(@NotNull YamlConfiguration config) {
        super.saveSubclassData(config);

        // Sauvegarde les attributs de la classe
        Main.getPlugin().getLogger().info("Saving obscurus data for player " + player.getName());
    }


}
