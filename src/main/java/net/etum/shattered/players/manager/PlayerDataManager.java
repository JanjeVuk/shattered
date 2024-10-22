package net.etum.shattered.players.manager;

import net.etum.shattered.Main;
import net.etum.shattered.players.Hero;
import net.etum.shattered.players.Hero.ClassType;
import net.etum.shattered.players.classe.Knight.Knight;
import net.etum.shattered.players.classe.Mage;
import net.etum.shattered.players.classe.Obscurus;
import net.etum.shattered.players.classe.Rogue;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private static final HashMap<UUID, Hero> dataPlayers = new HashMap<>();
    private static final String path = "plugins/Shattered/data/players/";

    public static void createData(Player player) {
        File directory = new File(path);
        if (!directory.exists() && !directory.mkdirs()) {
            Main.getPlugin().getLogger().warning("Failed to create directory structure: " + directory.getPath());
            return;
        }

        File file = new File(directory, player.getUniqueId() + ".yml");
        if (file.exists()) {
            Main.getPlugin().getLogger().info("Data file already exists for player " + player.getName());
            return;
        }

        try {
            if (file.createNewFile()) {
                Main.getPlugin().getLogger().info("File created for player " + player.getName());
                initializePlayerClass(player, file); // Initialize with default class
            }
        } catch (IOException e) {
            Main.getPlugin().getLogger().warning("Failed to create file for player " + player.getName() + ": " + e.getMessage());
        }
    }

    //
    public static void loadData(Player player) {
        File file = new File(path + player.getUniqueId() + ".yml");
        if (!file.exists()) {
            Main.getPlugin().getLogger().warning("Data file does not exist for player " + player.getName());
            createData(player); // Create data if it doesn't exist
            return;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ClassType playerClassType = ClassType.fromString(config.getString("classe", "NONE"));

        if (playerClassType == null) {
            Main.getPlugin().getLogger().warning("Invalid class type for player " + player.getName());
            return;
        }

        Hero playerClass = createPlayerClassInstance(player, playerClassType);
        try {
            playerClass.loadSubclassData(config);
            dataPlayers.put(player.getUniqueId(), playerClass);
            Main.getPlugin().getLogger().info("Data loaded for player " + player.getName());
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Failed to load data for player " + player.getName() + ": " + e.getMessage());
        }
    }

    private static Hero createPlayerClassInstance(Player player, ClassType classType) {
        switch (classType) {
            case KNIGHT:
                return new Knight(player, 0, 50, classType);
            case MAGE:
                return new Mage(player, 0, 50, classType);
            case OBSCURUS:
                return new Obscurus(player, 0, 50, classType);
            case ROGUE:
                return new Rogue(player, 0, 50, classType);
            default:
                return new Hero(player, 0, 50, classType);
        }
    }

    public static void saveData(Player player) {
        File file = new File(path + player.getUniqueId() + ".yml");
        if (!file.exists()) {
            Main.getPlugin().getLogger().warning("Data file does not exist for player " + player.getName());
            return;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Hero playerData = getPlayerData(player);
        if (playerData != null) {
            try {
                playerData.saveSubclassData(config);
                config.save(file);
                Main.getPlugin().getLogger().info("Data saved for player " + player.getName());
            } catch (IOException e) {
                Main.getPlugin().getLogger().warning("Failed to save data for player " + player.getName() + ": " + e.getMessage());
            }
        } else {
            Main.getPlugin().getLogger().warning("No data found for player " + player.getName());
        }
    }

    public static Hero getPlayerData(Player player) {
        return dataPlayers.get(player.getUniqueId());
    }

    public static String getpath(){
        return path;
    }

    public static void deleteDataFromHash(Player player) {
        if (dataPlayers.remove(player.getUniqueId()) != null) {
            Main.getPlugin().getLogger().info("Data removed from memory for player " + player.getName());
        } else {
            Main.getPlugin().getLogger().warning("Player " + player.getName() + " has no data to delete");
        }
    }

    private static void initializePlayerClass(Player player, File file) throws IOException {
        Hero playerClass = new Hero(player, 0, 50, ClassType.NONE);
        dataPlayers.put(player.getUniqueId(), playerClass);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        playerClass.saveSubclassData(config); // Save default data
        config.save(file); // Save file
        Main.getPlugin().getLogger().info("Default class created for player " + player.getName());
    }
}
