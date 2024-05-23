package net.etum.shattered.gui.builder;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {
    private final Inventory inventory;

    public InventoryBuilder(String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, Component.text(title));

    }

    public InventoryBuilder setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
        return this;
    }

    public InventoryBuilder addItem(ItemStack item) {
        inventory.addItem(item);
        return this;
    }

    public void setTemplate() {
        ItemStack grayPane = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build();
        ItemStack blackPane = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build();

        // Set the gray panes (background)
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, grayPane);
        }

        // Set the black panes (border)
        int rows = inventory.getSize() / 9;
        for (int i = 0; i < rows; i++) {
            inventory.setItem(i * 9, blackPane); // left border
            inventory.setItem(i * 9 + 8, blackPane); // right border
        }
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, blackPane); // top border
            inventory.setItem((rows - 1) * 9 + i, blackPane); // bottom border
        }

    }

    public Inventory build() {
        return inventory;
    }
}
