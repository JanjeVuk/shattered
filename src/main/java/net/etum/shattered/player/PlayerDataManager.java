package net.etum.shattered.player;

import net.etum.shattered.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class PlayerDataManager {

    private static final Logger LOGGER = Bukkit.getLogger();

    private static final String KEY_KNIGHT = "knight";
    private static final String KEY_AD = ".ad";
    private static final String KEY_AP = ".ap";
    private static final String KEY_ARMOR = ".armor";
    private static final String KEY_RESISTANCE = ".resistance";
    private static final String KEY_HEALTH = ".health";
    private static final String KEY_MANA = ".mana";
    private static final String KEY_EXPERIENCE = ".experience";

    private final Player player;
    private final File file;
    private final FileConfiguration config;

    private static final Map<UUID, Knight> knightMap = new HashMap<>();

    public PlayerDataManager(Player player) {

        this.player = player;
        this.file = new File("plugins//" + Main.getInstance().getName() + "//data//players//" + player.getUniqueId() + ".yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                LOGGER.info("Created new data file for player " + player.getName() + " (" + player.getUniqueId() + ")");
            } catch (IOException e) {
                LOGGER.warning("Could not create data file for player " + player.getName() + " (" + player.getUniqueId() + "): " + e.getMessage());
            }
        } else {
            LOGGER.info("Loading data file for player " + player.getName() + " (" + player.getUniqueId() + ")");
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }



    public void saveConfig() {
        try {
            config.save(file);
            LOGGER.info("Saved data for player " + player.getName() + " (" + player.getUniqueId() + ")");
        } catch (IOException e) {
            LOGGER.warning("Could not save data for player " + player.getName() + " (" + player.getUniqueId() + "): " + e.getMessage());
        }
    }

    public void loadConfig() {
        Knight knight;
        if (!config.contains(KEY_KNIGHT)) {
            knight = createDefaultKnight();
            saveKnightInConfig(knight);
            LOGGER.info("Created default knight for player " + player.getName() + " (" + player.getUniqueId() + ")");
        } else {
            knight = readKnightFromConfig();
            LOGGER.info("Loaded knight data for player " + player.getName() + " (" + player.getUniqueId() + ")");
        }
        knightMap.put(player.getUniqueId(), knight);
    }

    private Knight createDefaultKnight() {
        return new Knight();
    }

    private void saveKnightInConfig(Knight knight) {
        config.set(KEY_KNIGHT + KEY_AD, knight.getAd());
        config.set(KEY_KNIGHT + KEY_AP, knight.getAp());
        config.set(KEY_KNIGHT + KEY_ARMOR, knight.getArmor());
        config.set(KEY_KNIGHT + KEY_RESISTANCE, knight.getResistance());
        config.set(KEY_KNIGHT + KEY_HEALTH, knight.getHealth());
        config.set(KEY_KNIGHT + KEY_MANA, knight.getMana());
        config.set(KEY_KNIGHT + KEY_EXPERIENCE, knight.getExperience());
        saveConfig();
    }

    private Knight readKnightFromConfig() {
        int ad = config.getInt(KEY_KNIGHT + KEY_AD);
        int ap = config.getInt(KEY_KNIGHT + KEY_AP);
        int armor = config.getInt(KEY_KNIGHT + KEY_ARMOR);
        int resistance = config.getInt(KEY_KNIGHT + KEY_RESISTANCE);
        int health = config.getInt(KEY_KNIGHT + KEY_HEALTH);
        int mana = config.getInt(KEY_KNIGHT + KEY_MANA);
        int experience = config.getInt(KEY_KNIGHT + KEY_EXPERIENCE);
        return new Knight(ad, ap, armor, resistance, health, mana, experience);
    }

    // Gestion de la HashMap

    public static Knight getKnight(Player player) {
        return knightMap.get(player.getUniqueId());
    }

    public static Map<UUID, Knight> getAllKnights() {
        return knightMap;
    }

    public static void saveKnight(Player player) {
        Knight knight = knightMap.get(player.getUniqueId());
        if (knight != null) {
            PlayerDataManager manager = new PlayerDataManager(player);
            manager.saveKnightInConfig(knight);
            LOGGER.info("Saved knight data for player " + player.getName() + " (" + player.getUniqueId() + ")");
        } else {
            LOGGER.warning("Could not find knight data for player " + player.getName() + " (" + player.getUniqueId() + ")");
        }
    }

    public static void saveAllKnights() {
        for (UUID uuid : knightMap.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                saveKnight(player);
            }
        }
        LOGGER.info("Saved all knights data.");
    }
}
