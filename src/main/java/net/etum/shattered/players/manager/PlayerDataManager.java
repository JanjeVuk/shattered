package net.etum.shattered.players.manager;

import net.etum.shattered.Main;
import net.etum.shattered.players.PlayerClass;
import net.etum.shattered.players.PlayerClass.ClassType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private static final HashMap<UUID, PlayerClass> dataPlayers = new HashMap<>();
    private static final String path = "plugins/Shattered/data/players/";

    public static void createData(Player player) {
        // Créer l'objet dossier
        File directory = new File(path);

        // Vérifier si le dossier existe, sinon le créer
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                Main.getPlugin().getLogger().info("Directory structure created: " + directory.getPath());
            } else {
                Main.getPlugin().getLogger().warning("Failed to create directory structure: " + directory.getPath());
                return;
            }
        }

        // Créer l'objet fichier pour le joueur
        File file = new File(directory, player.getUniqueId() + ".yml");

        // Vérifier si le fichier de données du joueur existe, sinon le créer
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Main.getPlugin().getLogger().info("File created for player " + player.getName());

                    // Initialiser la classe par défaut "NONE"
                    PlayerClass defaultClass = new PlayerClass(player, 0, 50, ClassType.NONE); // Valeurs par défaut
                    dataPlayers.put(player.getUniqueId(), defaultClass); // Ajouter l'objet à la mémoire

                    // Sauvegarder les données dans le fichier
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                    defaultClass.saveSubclassData(config); // Sauvegarder les données de la sous-classe
                    config.save(file); // Sauvegarder le fichier

                    Main.getPlugin().getLogger().info("Default class created for player " + player.getName());
                }
            } catch (IOException e) {
                Main.getPlugin().getLogger().warning("Failed to create file for player " + player.getName() + ": " + e.getMessage());
            }
        } else {
            Main.getPlugin().getLogger().info("Data file already exists for player " + player.getName());
        }
    }



    public static void loadData(Player player) {
        File file = new File(path + player.getUniqueId() + ".yml");
        PlayerClass playerClass;

        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            // Récupérer le type de classe du joueur ou utiliser NONE par défaut
            String playerClassTypeString = config.getString("classe", "NONE");
            ClassType playerClassType = ClassType.fromString(playerClassTypeString);

            if (playerClassType == null) {
                Main.getPlugin().getLogger().warning("Invalid class type for player " + player.getName() + ": " + playerClassTypeString);
                return;
            }

            // Initialiser l'objet PlayerClass
            playerClass = new PlayerClass(player, 0, 50, playerClassType);

            try {
                // Charger les données spécifiques à la sous-classe
                playerClass.loadSubclassData(config);

                // Ajouter les données du joueur en mémoire
                dataPlayers.put(player.getUniqueId(), playerClass);
                Main.getPlugin().getLogger().info("Data loaded for player " + player.getName());
            } catch (Exception e) {
                Main.getPlugin().getLogger().warning("Failed to load data for player " + player.getName() + ": " + e.getMessage());
            }
        } else {
            Main.getPlugin().getLogger().warning("Data file does not exist for player " + player.getName());

            // Créer le fichier si nécessaire
            createData(player);

            // Initialiser une nouvelle instance de PlayerClass avec la classe par défaut (NONE)
            playerClass = new PlayerClass(player, 0, 50, ClassType.NONE);

            // Ajouter l'objet PlayerClass à la mémoire
            dataPlayers.put(player.getUniqueId(), playerClass);

            // Sauvegarder immédiatement cette classe par défaut dans un fichier
            saveData(player);

            Main.getPlugin().getLogger().info("Default class created and loaded for player " + player.getName());
        }
    }



    public static void saveData(Player player) {
        File file = new File(path + player.getUniqueId() + ".yml");
        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            PlayerClass playerData = getPlayerData(player);
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
        } else {
            Main.getPlugin().getLogger().warning("Data file does not exist for player " + player.getName());
        }
    }

    public static PlayerClass getPlayerData(Player player) {
        return dataPlayers.get(player.getUniqueId());
    }

    public static void deleteDataFromHash(Player player) {
        if (dataPlayers.containsKey(player.getUniqueId())) {
            dataPlayers.remove(player.getUniqueId());
            Main.getPlugin().getLogger().info("Data removed from memory for player " + player.getName());
        } else {
            Main.getPlugin().getLogger().warning("Player " + player.getName() + " has no data to delete");
        }
    }
}
