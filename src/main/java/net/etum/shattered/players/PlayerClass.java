package net.etum.shattered.players;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerClass {


    protected final Player player;
    protected final Experience experience;
    protected final Money money;
    protected ClassType classType;

    public enum ClassType {
        MAGE,
        KNIGHT,
        ROGUE,
        OBSCURUS,
        NONE;

        public static ClassType fromString(String classType) {
            try {
                return ClassType.valueOf(classType.toUpperCase());
            } catch (IllegalArgumentException e) {
                return NONE;
            }
        }
    }


    public PlayerClass(Player player, int initialExp, int initialMoney, ClassType classType) {
        this.player = player;
        this.experience = new Experience(initialExp);
        this.money = new Money(initialMoney);
        this.classType = classType;
    }



    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    // Méthodes communes pour l'expérience
    public int getExp() {
        return experience.getAmount();
    }

    public void setExp(int exp) {
        experience.setAmount(exp);
    }

    public void addExp(int exp) {
        experience.add(exp);
    }

    public int getLevel() {
        return experience.getLevel();
    }

    public void setLevel(int level) {
        experience.setLevel(level);
    }

    public int getExpToNextLevel() {
        return experience.getExpToNextLevel();
    }

    // Méthodes communes pour l'argent
    public int getMoney() {
        return money.getAmount();
    }

    public void setMoney(int amount) {
        money.setAmount(amount);
    }

    public void addMoney(int amount) {
        money.add(amount);
    }

    public void removeMoney(int amount) {
        money.remove(amount);
    }


    // Méthodes de chargement et sauvegarde des données de la classe
    public void loadSubclassData(@NotNull YamlConfiguration config) {
        setExp(config.getInt("exp", 0)); // Charge l'expérience
        setMoney(config.getInt("money", 0)); // Charge l'argent
        setClassType(ClassType.NONE); // Charge le type de classe
    }

    public void saveSubclassData(@NotNull YamlConfiguration config) {
        config.set("exp", getExp()); // Sauvegarde l'expérience
        config.set("money", getMoney()); // Sauvegarde l'argent
        config.set("classe", getClassType().toString()); // Sauvegarde le type de classe
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

        public void setAmount(int exp) {
            this.exp = exp;
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

        public void setLevel(int level) {
            this.exp = (int) Math.pow(level, 2) * 100;
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

        public void setAmount(int amount) {
            this.amount = amount;
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
