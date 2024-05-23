package net.etum.shattered.gui;

import net.etum.shattered.gui.builder.InventoryBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class MainGui implements Listener {


    public static void open(Player player) {
        InventoryBuilder inv = new InventoryBuilder("Main", 9*6);
        inv.setTemplate();

        player.openInventory(inv.build());
    }
}
