package net.etum.shattered.players.classe;

import net.etum.shattered.Main;
import net.etum.shattered.players.Hero;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Rogue extends Hero {

    public Rogue(Player player, int initialExp, int initialMoney, ClassType classType) {
        super(player, initialExp, initialMoney, classType);
    }

    @Override
    public void loadSubclassData(@NotNull YamlConfiguration config) {
        super.loadSubclassData(config);

        // Charge les attributs de la classe
        Main.getPlugin().getLogger().info("Charging rogue data for player " + player.getName());

        setClassType(ClassType.ROGUE);
    }

    @Override
    public void saveSubclassData(@NotNull YamlConfiguration config) {
        super.saveSubclassData(config);

        // Sauvegarde les attributs de la classe
        Main.getPlugin().getLogger().info("Saving rogue data for player " + player.getName());
    }

}
