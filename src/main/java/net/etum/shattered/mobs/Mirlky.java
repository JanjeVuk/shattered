package net.etum.shattered.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

/**
 * Mirlky is a class that represents a custom spider entity with additional functionality.
 * It implements the Listener interface to handle events related to the spider.
 */
public class Mirlky implements Listener {

    private int groupId;
    private int health;
    private int level;
    private Location spawnLocation;
    private int zoneRadius;
    private Spider spider;
    private final Random random;

    /**
     * Mirlky class represents a game character that spawns a Spider at a given location.
     * It has properties such as groupId, health, level, spawnLocation, and zoneRadius.
     * Mirlky can check if a given location is within its zone.
     */
    public Mirlky(int groupId, int health, int level, Location spawnLocation, int zoneRadius) {
        this.groupId = groupId;
        this.health = health;
        this.level = level;
        this.spawnLocation = spawnLocation;
        this.zoneRadius = zoneRadius;
        this.random = new Random();
        spawnSpider();
        startWaterCheckTask();
    }

    /**
     * Spawns a Spider entity at the specified spawn location.
     * The health, custom name, and custom name visibility of the Spider are set according to the provided level.
     */
    private void spawnSpider() {
        spider = (Spider) Objects.requireNonNull(spawnLocation.getWorld()).spawnEntity(spawnLocation, EntityType.SPIDER);
        Objects.requireNonNull(spider.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
        spider.setHealth(health);
        spider.setCustomName("CustomSpider Lvl " + level);
        spider.setCustomNameVisible(true);

        // Adding resistance based on level
        AttributeInstance armorAttribute = spider.getAttribute(Attribute.GENERIC_ARMOR);
        if (armorAttribute != null) {
            armorAttribute.setBaseValue(getArmorValueForLevel(level));
        }

        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getProvidingPlugin(getClass()));
    }

    /**
     * Starts a repeating task to check if the spider is in water.
     */
    private void startWaterCheckTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (spider == null || spider.isDead()) {
                    cancel();
                    return;
                }

                if (spider.getLocation().getBlock().isLiquid()) {
                    spider.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 1, true, false));
                } else {
                    spider.removePotionEffect(PotionEffectType.INVISIBILITY);
                }
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(getClass()), 0L, 20L); // Checks every second
    }

    /**
     * Calculates the armor value based on the spider's level.
     *
     * @param level the level of the spider
     * @return the calculated armor value
     */
    private double getArmorValueForLevel(int level) {
        return 2.0 + (level * 0.5); // Example calculation, you can adjust this formula
    }

    /**
     * Moves the spider to the specified location.
     * If the spider is not null and is not dead, it will be teleported to the given location.
     *
     * @param location the location to move the spider to
     */
    public void moveTo(Location location) {
        if (spider != null && !spider.isDead()) {
            spider.teleport(location);
        }
    }

    /**
     * Removes the spider entity if it exists.
     */
    public void remove() {
        if (spider != null) {
            spider.remove();
        }
    }

    /**
     * Determines whether a given location is within the zone.
     *
     * @param location the location to check
     * @return true if the location is within the zone, false otherwise
     */
    public boolean isInZone(Location location) {
        return location.distance(spawnLocation) <= zoneRadius;
    }

    /**
     * This method handles the EntityDeathEvent.
     * It checks if the entity that died is the custom Spider, and performs custom behavior on death.
     * Custom behavior can include dropping special items, respawning, etc.
     *
     * @param event The EntityDeathEvent that occurred.
     */
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().equals(spider)) {
            // Annuler les loots originaux
            event.getDrops().clear();

            // Définir les pourcentages de chance pour chaque item de loot
            double venomGlandChance = 0.5; // 50% de chance de loot
            double caveSilkChance = 0.8; // 80% de chance de loot

            // Ajouter les loots personnalisés avec probabilité
            if (random.nextDouble() < venomGlandChance) {
                ItemStack venomGland = new ItemStack(Material.SPIDER_EYE, 1); // Utiliser SPIDER_EYE comme exemple, personnaliser selon vos besoins
                venomGland.getItemMeta().setDisplayName("🧪 Glandes de Venin");
                event.getDrops().add(venomGland);
            }

            if (random.nextDouble() < caveSilkChance) {
                ItemStack caveSilk = new ItemStack(Material.STRING, 1); // Utiliser STRING comme exemple, personnaliser selon vos besoins
                caveSilk.getItemMeta().setDisplayName("🕸️ Soie de Grottes");
                event.getDrops().add(caveSilk);
            }

            System.out.println("CustomSpider has died and dropped custom loot.");
        }
    }

    /**
     * This method is triggered when a player moves.
     * It checks if the player has entered the zone and performs custom behavior if they have.
     *
     * @param event The PlayerMoveEvent object representing the player's movement.
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (isInZone(Objects.requireNonNull(event.getTo()))) {
            // Custom behavior when a player enters the zone
            System.out.println("Player has entered the zone of a CustomSpider.");
        }
    }

    /**
     * This method is triggered when a spider attacks a player.
     * It applies a poison effect to the player with a chance based on the spider's level.
     *
     * @param event The EntityDamageByEntityEvent that occurred.
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().equals(spider) && event.getEntity() instanceof org.bukkit.entity.Player) {
            if (random.nextDouble() < getVenomActivationChance(level)) {
                org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getEntity();
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, getVenomDuration(level), 1)); // Apply slowness
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, getVenomDuration(level), 1)); // Apply poison
            }
        }
    }

    /**
     * Calculates the venom activation chance based on the spider's level.
     *
     * @param level the level of the spider
     * @return the calculated venom activation chance
     */
    private double getVenomActivationChance(int level) {
        return 0.1 + (level * 0.02); // Example calculation, you can adjust this formula
    }

    /**
     * Calculates the venom effect duration based on the spider's level.
     *
     * @param level the level of the spider
     * @return the calculated venom effect duration in ticks
     */
    private int getVenomDuration(int level) {
        return 40 + (level * 2); // Example calculation, you can adjust this formula
    }


    // Getters and setters for the fields

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (spider != null) {
            spider.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
            spider.setHealth(health);
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        if (spider != null) {
            spider.setCustomName("CustomSpider Lvl " + level);
            spider.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(getArmorValueForLevel(level));
        }
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public int getZoneRadius() {
        return zoneRadius;
    }

    public void setZoneRadius(int zoneRadius) {
        this.zoneRadius = zoneRadius;
    }
}
