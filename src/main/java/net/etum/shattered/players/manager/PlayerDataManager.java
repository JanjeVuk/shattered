package net.etum.shattered.players.manager;

import net.etum.shattered.players.PlayerClass;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    // HashMap contenant les données des joueurs, peut contenir n'importe quelle sous-classe de PlayerClass
    private final HashMap<UUID, PlayerClass> dataPlayers = new HashMap<>();



}
