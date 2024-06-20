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

class Mirlky(
    var groupId: Int,
    private var health: Int,
    private var level: Int,
    var spawnLocation: Location,
    var zoneRadius: Int
) : Listener {

    private var armor: Int = getArmorValueForLevel(level)
    private var spider: Spider? = null
    private val random = Random()
    private val customName = "Mirlky Lvl $level"

    init {
        spawnSpider()
        startWaterCheckTask()
    }

    private fun spawnSpider() {
        spider = (spawnLocation.world?.spawnEntity(spawnLocation, EntityType.SPIDER) as? Spider)?.apply {
            getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = health
            this.health = health
            customName = this@Mirlky.customName
            isCustomNameVisible = true

            Bukkit.getPluginManager().registerEvents(this@Mirlky, JavaPlugin.getProvidingPlugin(javaClass))
        }
    }

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

    private fun getArmorValueForLevel(level: Int): Int {
        return (2.0 + (level * 0.5)).toInt()
    }

    fun moveTo(location: Location?) {
        location?.let {
            spider?.takeIf { !it.isDead }?.teleport(it)
        }
    }

    fun remove() {
        spider?.remove()
    }

    fun isInZone(location: Location): Boolean {
        return location.distance(spawnLocation) <= zoneRadius
    }

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

    private fun getVenomActivationChance(level: Int): Double {
        return 0.1 + (level * 0.02)
    }

    private fun getVenomDuration(level: Int): Int {
        return 40 + (level * 2)
    }

    fun getHealth(): Int {
        return health
    }

    fun setHealth(health: Int) {
        this.health = health
        spider?.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.baseValue = health.toDouble()
        spider?.health = health.toDouble()
    }

    fun getLevel(): Int {
        return level
    }

    fun setLevel(level: Int) {
        this.level = level
        this.armor = getArmorValueForLevel(level)
        spider?.customName = "Mirlky Lvl $level"
    }
}
