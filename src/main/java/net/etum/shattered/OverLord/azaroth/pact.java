package net.etum.shattered.OverLord.azaroth;

/**
 * La classe Pact représente le pacte avec Azaroth, l'Overlord de la colère.
 * Ce pacte permet au chevalier de devenir un bruiser, efficace contre les tanks
 * et lors de longs combats rapprochés.
 */

public class pact {

    /**
     * Compétence 1 : Break Deff
     *
     * Mécanique :
     * - Chaque attaque automatique du chevalier ajoute une pile.
     * - À chaque 5 piles accumulées, le chevalier peut activer Break Deff.
     * - Lors de l'activation, la prochaine attaque automatique du chevalier ignore 15% de l'armure de la cible.
     * - La réduction d'armure dure 5 secondes.
     * - Si l'effet expire avant d'être réutilisé, l'accumulation des piles recommence.
     *
     * Cooldown : 8 secondes
     * Coût en mana : 50
     */

    /**
     * Compétence 2 : Blood Rage
     *
     * Mécanique :
     * - Lors de l'activation de cette capacité, le bruiser regagne des points de vie proportionnellement aux dégâts bruts infligés.
     * - Cette capacité augmente les dégâts bruts infligés de 5%.
     * - Si la cible est sous l'effet de Break Deff, l'augmentation des dégâts passe à 10%.
     *
     * Durée : 10 secondes
     * Cooldown : 20 secondes
     * Coût en mana : 80
     */

    /**
     * Compétence 3 : Unrelenting Fury
     *
     * Mécanique :
     * - Lorsqu'un ennemi atteint 5 piles de Break Deff, le chevalier peut activer Unrelenting Fury.
     * - Lors de l'activation, la prochaine attaque automatique du chevalier inflige des dégâts bruts supplémentaires égaux à 20% de l'attaque de base.
     * - Si Blood Rage est actif, les dégâts bruts supplémentaires sont augmentés à 30%.
     *
     * Cooldown : 12 secondes
     * Coût en mana : 60
     */

    /**
     * Compétence 4 : Extension du territoire, Sovereign Wrath
     *
     * Mécanique :
     * - Lorsque le domaine est activé, l'armure et la résistance magique sont réduites à néant pour le bruiser et l'ennemi.
     * - Les dégâts infligés par le bruiser sont transformés en dégâts bruts, les attaques peuvent critiquer.
     * - La compétence Blood Rage est en permanence activée.
     * - Chaque coup critique réduit le temps de recharge de Break Deff et Unrelenting Fury de 1 seconde.
     *
     * Durée : 15 secondes
     * Cooldown : 90 secondes
     * Coût en mana : 150
     */
}
