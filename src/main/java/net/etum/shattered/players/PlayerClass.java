package net.etum.shattered.players;

import org.bukkit.entity.Player;
import java.util.Objects;

public abstract class PlayerClass {

    private final Player player; // Player ne devrait pas changer une fois défini
    private Experience experience; // Mutable pour permettre la mise à jour
    private Money money; // Mutable pour permettre la mise à jour
    private String classe; // Mutable pour permettre la mise à jour

    public PlayerClass(Player player, int initialExp, int initialMoney, String classe) {
        this.player = Objects.requireNonNull(player, "Player cannot be null");
        this.experience = new Experience(initialExp);
        this.money = new Money(initialMoney);
        this.classe = Objects.requireNonNull(classe, "Classe cannot be null");
    }

    public Player getPlayer() {
        return player;
    }

    // Gestion de l'expérience
    public int getExp() {
        return experience.getAmount();
    }

    public void addExp(int exp) {
        if (exp <= 0) {
            throw new IllegalArgumentException("Experience to add must be positive");
        }
        experience.add(exp);
    }

    public void setExperience(Experience experience) {
        this.experience = Objects.requireNonNull(experience, "Experience cannot be null");
    }

    public int getLevel() {
        return experience.getLevel();
    }

    public int getExpToNextLevel() {
        return experience.getExpToNextLevel();
    }

    // Gestion de l'argent
    public int getMoney() {
        return money.getAmount();
    }

    public void addMoney(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Money to add must be positive");
        }
        money.add(amount);
    }

    public void removeMoney(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Money to remove must be positive");
        }
        money.remove(amount);
    }

    public void setMoney(Money money) {
        this.money = Objects.requireNonNull(money, "Money cannot be null");
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = Objects.requireNonNull(classe, "Classe cannot be null");
    }

    // Gestion de l'expérience
    public static class Experience {
        private int exp;

        public Experience(int initialExp) {
            this.exp = Math.max(0, initialExp); // S'assure que l'expérience est positive
        }

        public int getAmount() {
            return exp;
        }

        public void add(int amount) {
            this.exp += Math.max(0, amount);  // Évite les ajouts négatifs
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

    // Gestion de l'argent
    public static class Money {
        private int amount;

        public Money(int initialAmount) {
            this.amount = Math.max(0, initialAmount);  // S'assure que l'argent est positif
        }

        public int getAmount() {
            return amount;
        }

        public void add(int amount) {
            this.amount += Math.max(0, amount);  // Évite les ajouts négatifs
        }

        public void remove(int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Cannot remove negative money");
            }
            this.amount = Math.max(0, this.amount - amount);
        }

        public void setAmount(int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Money cannot be negative");
            }
            this.amount = amount;
        }

        public boolean hasEnough(int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Amount cannot be negative");
            }
            return this.amount >= amount;
        }

        public boolean isBankrupt() {
            return this.amount == 0;
        }
    }
}
