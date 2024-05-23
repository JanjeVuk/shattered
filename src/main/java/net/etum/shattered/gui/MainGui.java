package net.etum.shattered.gui;

import net.etum.shattered.gui.builder.InventoryBuilder;
import net.etum.shattered.gui.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class MainGui implements Listener {

    private static final String title = "Shattered";


    public static void open(Player player) {
        InventoryBuilder inv = new InventoryBuilder(title, 9*6);
        inv.setTemplate();

        ItemBuilder profil = new ItemBuilder(Material.PLAYER_HEAD);
        profil.setName("Profil : " + player.getName());
        profil.setSkullOwner(player.getName());


        player.openInventory(inv.build());
    }
}
