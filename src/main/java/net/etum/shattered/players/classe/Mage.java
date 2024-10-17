package net.etum.shattered.players.classe;

import net.etum.shattered.Main;
import net.etum.shattered.players.PlayerClass;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Mage extends PlayerClass {

    public Mage(Player player, int initialExp, int initialMoney, ClassType classType) {
        super(player, initialExp, initialMoney, classType);
    }

    @Override
    public void loadSubclassData(@NotNull YamlConfiguration config) {
        super.loadSubclassData(config);

        // Charge les attributs de la classe
        Main.getPlugin().getLogger().info("Charging mage data for player " + player.getName());

        setClassType(ClassType.MAGE);
    }

    @Override
    public void saveSubclassData(@NotNull YamlConfiguration config) {
        super.saveSubclassData(config);

        // Sauvegarde les attributs de la classe
        Main.getPlugin().getLogger().info("Saving mage data for player " + player.getName());
    }

}
