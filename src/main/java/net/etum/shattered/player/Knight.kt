package net.etum.shattered.player

import org.bukkit.entity.Player

class Knight @JvmOverloads constructor(
    private var ad: Int = 3,          // Physical damage
    private var ap: Int = 0,          // Magical damage
    private var armor: Int = 0,       // Armor that reduces physical damage
    private var resistance: Int = 0,  // Shield that reduces magical damage
    private var health: Int = 20,     // Health points
    private var mana: Int = 50,       // Points to use skills
    private var experience: Int = 0   // Points to determine the level of the knight
) {

    // Getters and Setters
    fun getAD(): Int {
        return ad
    }

    fun setAD(value: Int) {
        ad = value
    }

    fun getAP(): Int {
        return ap
    }

    fun setAP(value: Int) {
        ap = value
    }

    fun getArmor(): Int {
        return armor
    }

    fun setArmor(value: Int) {
        armor = value
    }

    fun getResistance(): Int {
        return resistance
    }

    fun setResistance(value: Int) {
        resistance = value
    }

    fun getHealth(): Int {
        return health
    }

    fun setHealth(value: Int) {
        health = value
    }

    fun getMana(): Int {
        return mana
    }

    fun setMana(value: Int) {
        mana = value
    }

    fun getExperience(): Int {
        return experience
    }

    fun setExperience(value: Int) {
        experience = value
    }

    // Method to display the knight's statistics
    fun displayStats(player: Player) {
        player.sendMessage("🛡️ Knight Statistics 🛡️:")
        player.sendMessage("⚔️ Physical Damage (AD): $ad")
        player.sendMessage("🔮 Magical Damage (AP): $ap")
        player.sendMessage("🔰 Armor: $armor")
        player.sendMessage("🔱 Resistance: $resistance")
        player.sendMessage("❤️ Health: $health")
        player.sendMessage("💙 Mana: $mana")
        player.sendMessage("⭐ Experience: $experience")
    }

    // Method to increase Physical Damage (AD)
    fun increaseAD(amount: Int) {
        ad += amount
    }

    // Method to increase Magical Damage (AP)
    fun increaseAP(amount: Int) {
        ap += amount
    }

    // Method to use mana
    fun useMana(amount: Int) {
        mana -= amount
        if (mana < 0) {
            mana = 0
        }
    }

    // Method to gain experience
    fun gainExperience(amount: Int) {
        experience += amount
    }
}
