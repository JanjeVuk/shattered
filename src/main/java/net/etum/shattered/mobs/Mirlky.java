package net.etum.shattered.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
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
    private int armor; // Nouveau champ pour gérer l'armure
    private int level;
    private Location spawnLocation;
    private int zoneRadius;
    private Spider spider;
    private final Random random;

    public Mirlky(int groupId, int health, int level, Location spawnLocation, int zoneRadius) {
        this.groupId = groupId;
        this.health = health;
        this.level = level;
        this.spawnLocation = spawnLocation;
        this.zoneRadius = zoneRadius;
        this.random = new Random();
        this.armor = getArmorValueForLevel(level); // Initialisation de l'armure
        spawnSpider();
        startWaterCheckTask();
    }

    private void spawnSpider() {
        spider = (Spider) Objects.requireNonNull(spawnLocation.getWorld()).spawnEntity(spawnLocation, EntityType.SPIDER);
        Objects.requireNonNull(spider.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
        spider.setHealth(health);
        spider.setCustomName("CustomSpider Lvl " + level);
        spider.setCustomNameVisible(true);

        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getProvidingPlugin(getClass()));
    }

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

    private int getArmorValueForLevel(int level) {
        return (int) (2.0 + (level * 0.5)); // Exemple de calcul, ajustez selon vos besoins
    }

    public void moveTo(Location location) {
        if (spider != null && !spider.isDead()) {
            spider.teleport(location);
        }
    }

    public void remove() {
        if (spider != null) {
            spider.remove();
        }
    }

    public boolean isInZone(Location location) {
        return location.distance(spawnLocation) <= zoneRadius;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().equals(spider)) {
            event.getDrops().clear();
            double venomGlandChance = 0.5;
            double caveSilkChance = 0.8;

            if (random.nextDouble() < venomGlandChance) {
                ItemStack venomGland = new ItemStack(Material.SPIDER_EYE, 1);
                venomGland.getItemMeta().setDisplayName("🧪 Glandes de Venin");
                event.getDrops().add(venomGland);
            }

            if (random.nextDouble() < caveSilkChance) {
                ItemStack caveSilk = new ItemStack(Material.STRING, 1);
                caveSilk.getItemMeta().setDisplayName("🕸️ Soie de Grottes");
                event.getDrops().add(caveSilk);
            }

            System.out.println("CustomSpider has died and dropped custom loot.");
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().equals(spider) && event.getEntity() instanceof org.bukkit.entity.Player) {
            if (random.nextDouble() < getVenomActivationChance(level)) {
                org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getEntity();
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, getVenomDuration(level), 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, getVenomDuration(level), 1));
            }
        }
    }

    private double getVenomActivationChance(int level) {
        return 0.1 + (level * 0.02); // Exemple de calcul, ajustez selon vos besoins
    }

    private int getVenomDuration(int level) {
        return 40 + (level * 2); // Exemple de calcul, ajustez selon vos besoins
    }

    // Getters and setters

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
            Objects.requireNonNull(spider.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
            spider.setHealth(health);
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.armor = getArmorValueForLevel(level); // Mettre à jour l'armure
        if (spider != null) {
            spider.setCustomName("CustomSpider Lvl " + level);
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

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
