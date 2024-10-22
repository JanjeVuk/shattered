package net.etum.shattered.players;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Hero {



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

    protected final Player player;
    protected int exp;
    protected int money;
    protected ClassType classType;

    public Hero(Player player, int exp, int money, ClassType classType) {
        this.player = player;
        this.exp = exp;
        this.money = money;
        this.classType = classType;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }


    public int getExp() {
        return exp;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    // Méthodes de chargement et sauvegarde des données général du joueur
    public void loadSubclassData(@NotNull YamlConfiguration config) {
        setExp(config.getInt("exp", 0)); // Charge l'expérience
        setMoney(config.getInt("money", 0)); // Charge l'argent
        setClassType(ClassType.NONE); // Charge le type de classe
    }

    // Méthodes de chargement et sauvegarde des données général du joueur
    public void saveSubclassData(@NotNull YamlConfiguration config) {
        config.set("exp", getExp()); // Sauvegarde l'expérience
        config.set("money", getMoney()); // Sauvegarde l'argent
        config.set("classe", getClassType().toString()); // Sauvegarde le type de classe
    }


}
