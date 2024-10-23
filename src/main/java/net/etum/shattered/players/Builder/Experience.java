package net.etum.shattered.players.Builder;

import org.bukkit.Bukkit;

// Class Experience
public class Experience {
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
        int temp = this.exp;

        try {
            temp = Math.max(0, temp - amount);
        }catch (Exception e) {
            System.out.println("Failed to remove experience amount");
        }

        this.exp = temp;
    }

    public int getLevel() {
        return (int) Math.floor(Math.sqrt(this.exp / 100.0));
    }

    public void setLevel(int level) {
        int temp = this.exp;

        try {
            temp = (int) Math.pow(level, 2) * 100;
        }catch (Exception e) {
            System.out.println("Failed to set experience level");
        }

        this.exp = temp;
    }

    public int getExpToNextLevel() {
        int currentLevel = getLevel();
        int nextLevelExp = (int) Math.pow(currentLevel + 1, 2) * 100;
        return nextLevelExp - this.exp;
    }
}
