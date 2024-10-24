package net.etum.shattered.players.Ability;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Ability {

    private String name;
    private String description;
    private Boolean enabled;
    private Long cooldown; // en millisecondes
    private Timer timer;

    public Ability(String name, String description, Boolean enabled, Long cooldown) {
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.cooldown = cooldown;
        this.timer = new Timer();
    }

    public Ability(String name, String description, Boolean enabled) {
        this(name, description, enabled, 0L); // Cooldown par défaut à 0
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Long getCooldown() {
        return cooldown;
    }

    public void setCooldown(Long cooldown) {
        this.cooldown = cooldown;
    }


    // Méthode à appeler quand l'ability est utilisée
    public void useAbility() {
        if (!enabled) {
            System.out.println("Ability is on cooldown, wait for " + cooldown / 1000 + " seconds.");
            return;
        }

        // Lancer l'ability
        System.out.println("Using ability: " + name);
        startCooldown();
    }

    // Méthode pour lancer le cooldown
    private void startCooldown() {
        enabled = false; // Désactive l'ability
        timer.schedule(new CooldownTask(), cooldown); // Planifie la réactivation
    }

    // Tâche pour réactiver l'ability après le cooldown
    private class CooldownTask extends TimerTask {
        @Override
        public void run() {
            enabled = true; // Réactive l'ability
            System.out.println(name + " is ready to use again!");
        }
    }

    // Méthode pour annuler le cooldown si nécessaire
    public void cancelCooldown() {
        timer.cancel();
        timer = new Timer(); // Recrée un nouveau Timer pour la réutilisation future
        enabled = true; // Réactive manuellement l'ability si annulé
    }
}
