package net.etum.shattered.player;

public class Knight {


    private int ad; // Physical damage
    private int ap; // Magical damage
    private int armor; // Armor that reduces physical damage
    private int resistance; // Shield that reduces magical damage
    private int health; // Health points
    private int mana; // Points to use skills
    private int experience; // Points to determine the level of the knight

    // Default constructor
    public Knight() {
        this(0, 0, 0, 0, 20, 50, 0); // Calls the parameterized constructor with default values
    }

    // Parameterized constructor
    public Knight(int ad, int ap, int armor, int resistance, int health, int mana, int experience) {
        this.ad = ad;
        this.ap = ap;
        this.armor = armor;
        this.resistance = resistance;
        this.health = health;
        this.mana = mana;
        this.experience = experience;
    }



    // Getter and Setter for ad (physical damage)
    public int getAd() {
        return ad;
    }

    public void setAd(int ad) {
        this.ad = ad;
    }

    // Getter and Setter for ap (magical damage)
    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    // Getter and Setter for armor
    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    // Getter and Setter for resistance
    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    // Getter and Setter for health
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    // Getter and Setter for mana
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    // Getter and Setter for experience
    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    // Method to display the knight's statistics
    public void displayStats() {
        System.out.println("Knight Statistics:");
        System.out.println("Physical Damage (AD): " + ad);
        System.out.println("Magical Damage (AP): " + ap);
        System.out.println("Armor: " + armor);
        System.out.println("Resistance: " + resistance);
        System.out.println("Health: " + health);
        System.out.println("Mana: " + mana);
        System.out.println("Experience: " + experience);
    }
}
