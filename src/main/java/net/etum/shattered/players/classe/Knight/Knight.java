package net.etum.shattered.players.classe.Knight;

import net.etum.shattered.Main;
import net.etum.shattered.players.PlayerClass;
import net.etum.shattered.players.manager.PlayerDataManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;

public class Knight extends PlayerClass {



    public Knight(Player player, int initialExp, int initialMoney, ClassType classType) {
        super(player, initialExp, initialMoney, classType);
    }






    @Override
    public void loadSubclassData(@NotNull YamlConfiguration config) {
        super.loadSubclassData(config);
        Main.getPlugin().getLogger().info("Charging knight data for player " + player.getName());
        setClassType(ClassType.KNIGHT);
    }

    @Override
    public void saveSubclassData(@NotNull YamlConfiguration config) {
        super.saveSubclassData(config);
        Main.getPlugin().getLogger().info("Saving knight data for player " + player.getName());
    }
}
