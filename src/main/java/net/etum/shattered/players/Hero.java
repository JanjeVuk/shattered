package net.etum.shattered.players;

import net.etum.shattered.players.Ability.Ability;
import net.etum.shattered.players.manager.PlayerDataManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Hero {

    private static final Logger LOGGER = Logger.getLogger(Hero.class.getName()); // Utilisation du logger

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
                return NONE; // Retourne NONE si la classe n'existe pas
            }
        }
    }

    protected final Player player;
    protected int exp;
    protected int money;
    protected ClassType classType;
    protected List<Ability> abilities = new ArrayList<>(); // Initialisation pour éviter NullPointerException

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

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void addAbility(Ability ability, Player player) {
        List<Ability> abilities = getAbilities();

        if (ability == null) {
            LOGGER.warning("Impossible d'ajouter une capacité nulle.");
            return;
        }

        if (abilities.size() >= 3) { // Limite à 3 capacités
            LOGGER.warning("Le joueur ne peut pas avoir plus de 3 capacités.");
            return;
        }

        if (abilities.contains(ability)) {
            LOGGER.warning("Le joueur possède déjà cette capacité.");
            return;
        }

        abilities.add(ability);
    }

    public void removeAbility(String abilityName, Player player) {
        List<Ability> abilities = getAbilities();

        Optional<Ability> abilityToRemove = abilities.stream()
                .filter(ability -> ability.getName().equals(abilityName))
                .findFirst();

        if (abilityToRemove.isPresent()) {
            abilities.remove(abilityToRemove.get());
            LOGGER.info("La capacité " + abilityName + " a été supprimée.");
        } else {
            LOGGER.warning("Aucune capacité avec le nom " + abilityName + " trouvée.");
        }
    }

    public boolean hasAbility(Ability ability, Player player) {
        return PlayerDataManager.getPlayerData(player)
                .getAbilities()
                .stream()
                .anyMatch(target -> ability.getName().equals(target.getName()));
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    // Méthodes de chargement des données générales du joueur, y compris les abilities
    public void loadSubclassData(@NotNull YamlConfiguration config) {
        setExp(config.getInt("exp", 0)); // Charge l'expérience
        setMoney(config.getInt("money", 0)); // Charge l'argent
        setClassType(ClassType.fromString(config.getString("classe", "NONE"))); // Charge le type de classe

        // Charger les capacités (si elles sont présentes dans la configuration)

    }

    // Méthodes de sauvegarde des données générales du joueur, y compris les abilities
    public void saveSubclassData(@NotNull YamlConfiguration config) {
        config.set("exp", getExp()); // Sauvegarde l'expérience
        config.set("money", getMoney()); // Sauvegarde l'argent
        config.set("classe", getClassType().toString()); // Sauvegarde le type de classe

        // Sauvegarder les capacités sous forme de liste de noms
        List<String> abilityNames = abilities.stream()
                .map(Ability::getName)
                .collect(Collectors.toList());

        config.set("abilities", abilityNames); // Sauvegarde les capacités
    }
}
