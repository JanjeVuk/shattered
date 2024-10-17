package net.etum.shattered.players.classe;

import net.etum.shattered.players.PlayerClass;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


// Classe Knight
public class Knight extends PlayerClass {


    public Knight(Player player, int initialExp, int initialMoney, ClassType classType) {
        super(player, initialExp, initialMoney, classType);
    }

    @Override
    public void loadSubclassData(@NotNull YamlConfiguration config) {
        super.loadSubclassData(config);

        // Charge les attributs de la classe
    }

}
