package net.etum.shattered.player;

public class Knight {

    private int ad; // dégât physique
    private int ap; // dégât magique
    private int armor; // armure qui réduit les dégâts physiques
    private int resistance; // bouclier qui réduit les dégâts magiques
    private int health; // points de santé
    private int mana; // points pour utiliser des compétences
    private int experience; // points pour déterminer le niveau du knight

    // Constructeur par défaut
    public Knight() {
        this.ad = 0;
        this.ap = 0;
        this.armor = 0;
        this.resistance = 0;
        this.health = 20; // Valeur par défaut pour la santé
        this.mana = 50; // Valeur par défaut pour le mana
        this.experience = 0;
    }

    // Constructeur avec paramètres
    public Knight(int ad, int ap, int armor, int resistance, int health, int mana, int experience) {
        this.ad = ad;
        this.ap = ap;
        this.armor = armor;
        this.resistance = resistance;
        this.health = health;
        this.mana = mana;
        this.experience = experience;
    }

    // Getters et Setters
    public int getAd() {
        return ad;
    }

    public void setAd(int ad) {
        this.ad = ad;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    // Méthode pour afficher les statistiques du knight
    public void displayStats() {
        System.out.println("Statistiques du Knight :");
        System.out.println("AD (dégât physique) : " + ad);
        System.out.println("AP (dégât magique) : " + ap);
        System.out.println("Armure : " + armor);
        System.out.println("Résistance : " + resistance);
        System.out.println("Vie : " + health);
        System.out.println("Mana : " + mana);
        System.out.println("Expérience : " + experience);
    }


}
