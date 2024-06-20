package net.etum.shattered.mobs

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Spider
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

/**
 * Mirlky is a class that represents a custom spider entity with additional functionality.
 * It implements the Listener interface to handle events related to the spider.
 */
class Mirlky(
// Getters and setters
    var groupId: Int, private var health: Int, private var level: Int, var spawnLocation: Location, var zoneRadius: Int
) : Listener {
    var armor: Int // Nouveau champ pour gérer l'armure
    private var spider: Spider? = null
    private val random = Random()

    init {
        this.armor = getArmorValueForLevel(level) // Initialisation de l'armure
        spawnSpider()
        startWaterCheckTask()
    }

    private fun spawnSpider() {
        spider = Objects.requireNonNull(spawnLocation.world).spawnEntity(spawnLocation, EntityType.SPIDER) as Spider
        Objects.requireNonNull(spider!!.getAttribute(Attribute.GENERIC_MAX_HEALTH))?.baseValue ?: health.toDouble()
        spider!!.health = health.toDouble()
        spider!!.customName = "CustomSpider Lvl $level"
        spider!!.isCustomNameVisible = true

        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getProvidingPlugin(javaClass))
    }

    private fun startWaterCheckTask() {
        object : BukkitRunnable() {
            override fun run() {
                if (spider == null || spider!!.isDead) {
                    cancel()
                    return
                }

                if (spider!!.location.block.isLiquid) {
                    spider!!.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 40, 1, true, false))
                } else {
                    spider!!.removePotionEffect(PotionEffectType.INVISIBILITY)
                }
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(javaClass), 0L, 20L) // Checks every second
    }

    private fun getArmorValueForLevel(level: Int): Int {
        return (2.0 + (level * 0.5)).toInt() // Exemple de calcul, ajustez selon vos besoins
    }

    fun moveTo(location: Location?) {
        if (spider != null && !spider!!.isDead) {
            spider!!.teleport(location!!)
        }
    }

    fun remove() {
        if (spider != null) {
            spider!!.remove()
        }
    }

    fun isInZone(location: Location): Boolean {
        return location.distance(spawnLocation) <= zoneRadius
    }

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entity == spider) {
            event.drops.clear()
            val venomGlandChance = 0.5
            val caveSilkChance = 0.8

            if (random.nextDouble() < venomGlandChance) {
                val venomGland = ItemStack(Material.SPIDER_EYE, 1)
                venomGland.itemMeta.setDisplayName("🧪 Glandes de Venin")
                event.drops.add(venomGland)
            }

            if (random.nextDouble() < caveSilkChance) {
                val caveSilk = ItemStack(Material.STRING, 1)
                caveSilk.itemMeta.setDisplayName("🕸️ Soie de Grottes")
                event.drops.add(caveSilk)
            }

            println("CustomSpider has died and dropped custom loot.")
        }
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.damager == spider && event.entity is Player) {
            if (random.nextDouble() < getVenomActivationChance(level)) {
                val player = event.entity as Player
                player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, getVenomDuration(level), 1))
                player.addPotionEffect(PotionEffect(PotionEffectType.POISON, getVenomDuration(level), 1))
            }
        }
    }

    private fun getVenomActivationChance(level: Int): Double {
        return 0.1 + (level * 0.02) // Exemple de calcul, ajustez selon vos besoins
    }

    private fun getVenomDuration(level: Int): Int {
        return 40 + (level * 2) // Exemple de calcul, ajustez selon vos besoins
    }

    fun getHealth(): Int {
        return health
    }

    fun setHealth(health: Int) {
        this.health = health
        if (spider != null) {
            Objects.requireNonNull(spider!!.getAttribute(Attribute.GENERIC_MAX_HEALTH))?.baseValue ?: health.toDouble()
            spider!!.health = health.toDouble()
        }
    }

    fun getLevel(): Int {
        return level
    }

    fun setLevel(level: Int) {
        this.level = level
        this.armor = getArmorValueForLevel(level) // Mettre à jour l'armure
        if (spider != null) {
            spider!!.customName = "CustomSpider Lvl $level"
        }
    }
}
