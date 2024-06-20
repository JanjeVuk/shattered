package net.etum.shattered.player

import net.etum.shattered.Main.Companion.instance
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException
import java.util.*

class PlayerDataManager(private val player: Player) {
    private val file = File("plugins//" + instance!!.name + "//data//players//" + player.uniqueId + ".yml")
    private val config: FileConfiguration

    init {
        if (!file.exists()) {
            try {
                file.parentFile.mkdirs()
                file.createNewFile()
                LOGGER.info("Created new data file for player " + player.name + " (" + player.uniqueId + ")")
            } catch (e: IOException) {
                LOGGER.warning("Could not create data file for player " + player.name + " (" + player.uniqueId + "): " + e.message)
            }
        } else {
            LOGGER.info("Loading data file for player " + player.name + " (" + player.uniqueId + ")")
        }
        this.config = YamlConfiguration.loadConfiguration(file)
    }


    fun saveConfig() {
        try {
            config.save(file)
            LOGGER.info("Saved data for player " + player.name + " (" + player.uniqueId + ")")
        } catch (e: IOException) {
            LOGGER.warning("Could not save data for player " + player.name + " (" + player.uniqueId + "): " + e.message)
        }
    }

    fun loadConfig() {
        val knight: Knight
        if (!config.contains(KEY_KNIGHT)) {
            knight = createDefaultKnight()
            saveKnightInConfig(knight)
            LOGGER.info("Created default knight for player " + player.name + " (" + player.uniqueId + ")")
        } else {
            knight = readKnightFromConfig()
            LOGGER.info("Loaded knight data for player " + player.name + " (" + player.uniqueId + ")")
        }
        knightMap[player.uniqueId] = knight
    }

    private fun createDefaultKnight(): Knight {
        return Knight()
    }

    private fun saveKnightInConfig(knight: Knight) {
        config[KEY_KNIGHT + KEY_AD] =
            knight.ad
        config[KEY_KNIGHT + KEY_AP] =
            knight.ap
        config[KEY_KNIGHT + KEY_ARMOR] =
            knight.armor
        config[KEY_KNIGHT + KEY_RESISTANCE] =
            knight.resistance
        config[KEY_KNIGHT + KEY_HEALTH] =
            knight.health
        config[KEY_KNIGHT + KEY_MANA] =
            knight.mana
        config[KEY_KNIGHT + KEY_EXPERIENCE] =
            knight.experience
        saveConfig()
    }

    private fun readKnightFromConfig(): Knight {
        val ad = config.getInt(KEY_KNIGHT + KEY_AD)
        val ap = config.getInt(KEY_KNIGHT + KEY_AP)
        val armor = config.getInt(KEY_KNIGHT + KEY_ARMOR)
        val resistance = config.getInt(KEY_KNIGHT + KEY_RESISTANCE)
        val health = config.getInt(KEY_KNIGHT + KEY_HEALTH)
        val mana = config.getInt(KEY_KNIGHT + KEY_MANA)
        val experience = config.getInt(KEY_KNIGHT + KEY_EXPERIENCE)
        return Knight(ad, ap, armor, resistance, health, mana, experience)
    }

    companion object {
        private val LOGGER = Bukkit.getLogger()

        private const val KEY_KNIGHT = "knight"
        private const val KEY_AD = ".ad"
        private const val KEY_AP = ".ap"
        private const val KEY_ARMOR = ".armor"
        private const val KEY_RESISTANCE = ".resistance"
        private const val KEY_HEALTH = ".health"
        private const val KEY_MANA = ".mana"
        private const val KEY_EXPERIENCE = ".experience"

        val knightMap: MutableMap<UUID, Knight> = HashMap()

        // Gestion de la HashMap
        fun getKnight(player: Player): Knight? {
            return knightMap[player.uniqueId]
        }

        val allKnights: Map<UUID, Knight>
            get() = knightMap

        fun saveKnight(player: Player) {
            val knight = knightMap[player.uniqueId]
            if (knight != null) {
                val manager = PlayerDataManager(player)
                manager.saveKnightInConfig(knight)
                LOGGER.info("Saved knight data for player " + player.name + " (" + player.uniqueId + ")")
            } else {
                LOGGER.warning("Could not find knight data for player " + player.name + " (" + player.uniqueId + ")")
            }
        }

        fun saveAllKnights() {
            for (uuid in knightMap.keys) {
                val player = Bukkit.getPlayer(uuid)
                if (player != null) {
                    saveKnight(player)
                }
            }
            LOGGER.info("Saved all knights data.")
        }
    }
}