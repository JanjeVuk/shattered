package net.etum.shattered.gui.builder

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta

class ItemBuilder {
    private val item: ItemStack
    private val meta: ItemMeta

    constructor(material: Material?) {
        requireNotNull(material) { "Material cannot be null" }
        this.item = ItemStack(material)
        this.meta = item.itemMeta
    }

    constructor(material: Material?, amount: Int) {
        requireNotNull(material) { "Material cannot be null" }
        require(amount > 0) { "Amount must be greater than zero" }
        this.item = ItemStack(material, amount)
        this.meta = item.itemMeta
    }

    fun setName(name: String?): ItemBuilder {
        meta.setDisplayName(name)
        return this
    }

    fun addLore(vararg lines: String): ItemBuilder {
        val lore = if (meta.hasLore()) meta.lore else ArrayList()
        for (line in lines) {
            lore!!.add(line)
        }
        meta.lore = lore
        return this
    }

    fun addEnchant(enchantment: Enchantment?, level: Int): ItemBuilder {
        requireNotNull(enchantment) { "Enchantment cannot be null" }
        meta.addEnchant(enchantment, level, true)
        return this
    }

    fun addEnchants(enchantments: Map<Enchantment?, Int?>): ItemBuilder {
        for ((key, value) in enchantments) {
            meta.addEnchant(key!!, value!!, true)
        }
        return this
    }

    fun addItemFlag(flag: ItemFlag?): ItemBuilder {
        requireNotNull(flag) { "ItemFlag cannot be null" }
        meta.addItemFlags(flag)
        return this
    }

    fun addItemFlags(vararg flags: ItemFlag?): ItemBuilder {
        for (flag in flags) {
            requireNotNull(flag) { "ItemFlag cannot be null" }
            meta.addItemFlags(flag)
        }
        return this
    }

    fun hideAllFlags(): ItemBuilder {
        meta.addItemFlags(*ItemFlag.entries.toTypedArray())
        return this
    }

    fun setUnbreakable(unbreakable: Boolean): ItemBuilder {
        meta.isUnbreakable = unbreakable
        return this
    }

    fun setDurability(durability: Int): ItemBuilder {
        item.durability = durability.toShort()
        return this
    }

    fun setAmount(amount: Int): ItemBuilder {
        require(amount > 0) { "Amount must be greater than zero" }
        item.amount = amount
        return this
    }

    fun setSkullOwner(owner: String?) {
        check(item.type == Material.PLAYER_HEAD) { "Item is not a player head" }
        val skullMeta = meta as SkullMeta
        skullMeta.setOwner(owner)
    }

    fun setCustomModelData(data: Int): ItemBuilder {
        meta.setCustomModelData(data)
        return this
    }

    fun build(): ItemStack {
        item.setItemMeta(meta)
        return item
    }

    fun clone(): ItemBuilder {
        val clone = ItemBuilder(item.type, item.amount)
        clone.meta.setDisplayName(meta.displayName)
        clone.meta.lore = meta.lore
        clone.meta.isUnbreakable = meta.isUnbreakable
        for (flag in meta.itemFlags) {
            clone.meta.addItemFlags(flag!!)
        }
        clone.meta.setCustomModelData(meta.customModelData)
        return clone
    }

    companion object {
        fun fromItemStack(itemStack: ItemStack?): ItemBuilder {
            requireNotNull(itemStack) { "ItemStack cannot be null" }
            val builder = ItemBuilder(itemStack.type, itemStack.amount)
            val meta = itemStack.itemMeta
            if (meta != null) {
                builder.meta.setDisplayName(meta.displayName)
                builder.meta.lore = meta.lore
                builder.meta.isUnbreakable = meta.isUnbreakable
                for (flag in meta.itemFlags) {
                    builder.meta.addItemFlags(flag!!)
                }
                builder.meta.setCustomModelData(meta.customModelData)
            }
            return builder
        }
    }
}