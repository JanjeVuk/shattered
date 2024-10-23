package net.etum.shattered.players.Ability;

public class Ability {

    private String name;
    private String description;
    private Boolean enabled;


    public Ability(String name, String description, Boolean enabled) {
        this.name = name;
        this.description = description;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
