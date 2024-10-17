package net.etum.shattered.players;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.attribute.Attribute;

import java.util.Objects;

public abstract class PlayerClass {

    protected final Player player;
    protected final Experience experience;
    protected final Money money;


    public PlayerClass(Player player, int initialExp, int initialMoney) {
        this.player = player;
        this.experience = new Experience(initialExp);
        this.money = new Money(initialMoney);
    }

    // Common methods for experience
    public int getExp() {
        return experience.getAmount();
    }

    public void addExp(int exp) {
        experience.add(exp);
    }

    public int getLevel() {
        return experience.getLevel();
    }

    public int getExpToNextLevel() {
        return experience.getExpToNextLevel();
    }

    // Common methods for money
    public int getMoney() {
        return money.getAmount();
    }

    public void addMoney(int amount) {
        money.add(amount);
    }

    public void removeMoney(int amount) {
        money.remove(amount);
    }

    // Common methods for health
    public double getHealth() {
        return player.getHealth();
    }

    public void setHealth(double health) {
        double maxHealth = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
        player.setHealth(Math.min(health, maxHealth));
    }

    public void increaseMaxHealth(double amount) {
        double currentMaxHealth = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(currentMaxHealth + amount);
    }

    // Common methods for armor
    public double getArmor() {
        return Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_ARMOR)).getValue();
    }

    public void setArmor(int armorPoints) {
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_ARMOR)).setBaseValue(Math.min(armorPoints, 20));
    }


    // Classe Experience
    public static class Experience {
        private int exp;

        public Experience(int initialExp) {
            this.exp = initialExp;
        }

        public int getAmount() {
            return exp;
        }

        public void add(int amount) {
            this.exp += amount;
        }

        public void remove(int amount) {
            this.exp = Math.max(0, this.exp - amount);
        }

        public int getLevel() {
            return (int) Math.floor(Math.sqrt(this.exp / 100.0));
        }

        public int getExpToNextLevel() {
            int currentLevel = getLevel();
            int nextLevelExp = (int) Math.pow(currentLevel + 1, 2) * 100;
            return nextLevelExp - this.exp;
        }
    }

    // Classe Money
    public static class Money {
        private int amount;

        public Money(int initialAmount) {
            this.amount = initialAmount;
        }

        public int getAmount() {
            return amount;
        }

        public void add(int amount) {
            this.amount += amount;
        }

        public void remove(int amount) {
            this.amount = Math.max(0, this.amount - amount);
        }

        public boolean hasEnough(int amount) {
            return this.amount >= amount;
        }

        public boolean isBankrupt() {
            return this.amount == 0;
        }
    }
}
