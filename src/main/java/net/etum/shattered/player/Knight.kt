package net.etum.shattered.player

class Knight @JvmOverloads constructor(
    var ad: Int = 0,          // Physical damage
    var ap: Int = 0,          // Magical damage
    var armor: Int = 0,       // Armor that reduces physical damage
    var resistance: Int = 0,  // Shield that reduces magical damage
    var health: Int = 20,     // Health points
    var mana: Int = 50,       // Points to use skills
    var experience: Int = 0   // Points to determine the level of the knight
) {
    // Method to display the knight's statistics
    fun displayStats() {
        println("Knight Statistics:")
        println("Physical Damage (AD): $ad")
        println("Magical Damage (AP): $ap")
        println("Armor: $armor")
        println("Resistance: $resistance")
        println("Health: $health")
        println("Mana: $mana")
        println("Experience: $experience")
    }
}


