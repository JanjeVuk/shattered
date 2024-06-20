package net.etum.shattered.gui.builder

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class InventoryBuilder(title: String?, size: Int) {
    private val inventory = Bukkit.createInventory(null, size, Component.text(title!!))

    fun setItem(slot: Int, item: ItemStack?): InventoryBuilder {
        inventory.setItem(slot, item)
        return this
    }

    fun addItem(item: ItemStack?): InventoryBuilder {
        inventory.addItem(item!!)
        return this
    }

    fun setTemplate() {
        val grayPane = ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build()
        val blackPane = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()

        // Set the gray panes (background)
        for (i in 0 until inventory.size) {
            inventory.setItem(i, grayPane)
        }

        // Set the black panes (border)
        val rows = inventory.size / 9
        for (i in 0 until rows) {
            inventory.setItem(i * 9, blackPane) // left border
            inventory.setItem(i * 9 + 8, blackPane) // right border
        }
        for (i in 0..8) {
            inventory.setItem(i, blackPane) // top border
            inventory.setItem((rows - 1) * 9 + i, blackPane) // bottom border
        }
    }

    fun build(): Inventory {
        return inventory
    }
}