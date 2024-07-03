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
 * Represents a Mirlky entity.
 * Mirlkies are spiders with customizable health, level, and spawn location.
 * They have special abilities like spawning spiders and applying potion effects.
 * Mirlkies drop custom loot when killed.
 *
 * @property groupId The group ID this Mirlky belongs to.
 * @property health The current health of the Mirlky.
 * @property level The current level of the Mirlky.
 * @property spawnLocation The location where the Mirlky was spawned.
 * @property zoneRadius The radius of the zone where the Mirlky can move.
 * @constructor Creates a Mirlky with the specified properties.
 */
class Mirlky(
    var groupId: Int,
    private var health: Int,
    private var level: Int,
    var spawnLocation: Location,
    var zoneRadius: Int
) : Listener {

    /**
     * Holds the armor value for the specified level.
     *
     * @param level The level of the armor.
     * @return The armor value based on the level.
     */
    private var armor: Int = getArmorValueForLevel(level)
    /**
     * Represents a spider.
     *
     * @property spider The spider object.
     */
    private var spider: Spider? = null
    /**
     * A private variable representing an instance of the random number generator.
     */
    private val random = Random()
    /**
     * Contains the custom name based on the level.
     *
     * @property customName The custom name representing the level.
     */
    private val customName = "Mirlky Lvl $level"

    init {
        spawnSpider()
        startWaterCheckTask()
    }

    /**
     * Spawns a spider entity at the specified location.
     * The spawned spider will have the same health as the entity that calls this method.
     * The spider will have a custom name and the name will be visible.
     * The spider will also have an event listener registered to it.
     */
    private fun spawnSpider() {
        spider = (spawnLocation.world?.spawnEntity(spawnLocation, EntityType.SPIDER) as? Spider)?.apply {
            getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = health
            this.health = health
            customName = this@Mirlky.customName
            isCustomNameVisible = true

            Bukkit.getPluginManager().registerEvents(this@Mirlky, JavaPlugin.getProvidingPlugin(javaClass))
        }
    }

    /**
     * Starts a periodic task to check for water and apply effects to the spider entity.
     * The task runs every 20 ticks (1 second).
     * If the spider is dead, the task is cancelled.
     * If the spider is in water (block.isLiquid), it adds the invisibility potion effect.
     * If the spider is not in water, it removes the invisibility potion effect.
     */
    private fun startWaterCheckTask() {
        object : BukkitRunnable() {
            override fun run() {
                spider?.let {
                    if (it.isDead) {
                        cancel()
                    } else if (it.location.block.isLiquid) {
                        it.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 40, 1, true, false))
                    } else {
                        it.removePotionEffect(PotionEffectType.INVISIBILITY)
                    }
                } ?: cancel()
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(javaClass), 0L, 20L)
    }

    /**
     * Returns the armor value based on the specified level.
     *
     * @param level the level of the armor
     * @return the calculated armor value
     */
    private fun getArmorValueForLevel(level: Int): Int {
        return (2.0 + (level * 0.5)).toInt()
    }

    /**
     * Moves the spider to the specified location.
     * If the spider is not dead and the location is not null,
     * the spider will be teleported to the location.
     *
     * @param location the new location to move the spider to
     */
    fun moveTo(location: Location?) {
        location?.let {
            spider?.takeIf { !it.isDead }?.teleport(it)
        }
    }

    /**
     * Removes the spider object. If the spider object is not null,
     * it will be removed by calling its `remove()` method.
     */
    fun remove() {
        spider?.remove()
    }

    /**
     * Determines if a given location is within the zone.
     *
     * @param location The location to check.
     * @return `true` if the location is within the zone, `false` otherwise.
     */
    fun isInZone(location: Location): Boolean {
        return location.distance(spawnLocation) <= zoneRadius
    }

    /**
     * Handles the event when an entity dies.
     *
     * @param event The EntityDeathEvent triggered when an entity dies.
     */
    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entity is Spider && event.entity.customName?.contains("Mirlky") == true) {
            event.drops.clear()
            val venomGlandChance = 0.5
            val caveSilkChance = 0.8

            if (random.nextDouble() < venomGlandChance) {
                val venomGland = ItemStack(Material.SPIDER_EYE, 1).apply {
                    itemMeta?.setDisplayName("🧪 Glandes de Venin")
                }
                event.drops.add(venomGland)
            }

            if (random.nextDouble() < caveSilkChance) {
                val caveSilk = ItemStack(Material.STRING, 1).apply {
                    itemMeta?.setDisplayName("🕸️ Soie de Grottes")
                }
                event.drops.add(caveSilk)
            }

            println("Mirlky has died and dropped custom loot.")
        }
    }

    /**
     * Called when an entity is damaged by another entity.
     *
     * @param event The EntityDamageByEntityEvent representing the event.
     */
    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.damager is Spider && event.damager.customName?.contains("Mirlky") == true && event.entity is Player) {
            if (random.nextDouble() < getVenomActivationChance(level)) {
                val player = event.entity as Player
                player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, getVenomDuration(level), 1))
                player.addPotionEffect(PotionEffect(PotionEffectType.POISON, getVenomDuration(level), 1))
            }
        }
    }

    /**
     * Calculates the venom activation chance based on the given level.
     *
     * @param level the level of the venom (must be a positive integer)
     * @return the venom activation chance as a Double value
     */
    private fun getVenomActivationChance(level: Int): Double {
        return 0.1 + (level * 0.02)
    }

    /**
     * Calculates the duration of the venom effect based on the given level.
     *
     * @param level The level of the venom effect.
     * @return The duration of the venom effect in seconds.
     */
    private fun getVenomDuration(level: Int): Int {
        return 40 + (level * 2)
    }

    /**
     * Returns the current health value.
     *
     * @return the health value as an integer.
     */
    fun getHealth(): Int {
        return health
    }

    /**
     * Sets the health value of the spider.
     *
     * @param health the new health value of the spider
     */
    fun setHealth(health: Int) {
        this.health = health
        spider?.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = health.toDouble()
        spider?.health = health.toDouble()
    }

    /**
     * Returns the level.
     *
     * @return the level.
     */
    fun getLevel(): Int {
        return level
    }

    /**
     * Sets the level for the character and updates the armor value and custom name accordingly.
     *
     * @param level the new level for the character
     */
    fun setLevel(level: Int) {
        this.level = level
        this.armor = getArmorValueForLevel(level)
        spider?.customName = "Mirlky Lvl $level"
    }
}
