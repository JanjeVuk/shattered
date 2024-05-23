package net.etum.shattered.gui.builder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder addLore(String... lines) {
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        for (String line : lines) {
            lore.add(line);
        }
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        if (enchantment == null) {
            throw new IllegalArgumentException("Enchantment cannot be null");
        }
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder addEnchants(Map<Enchantment, Integer> enchantments) {
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        if (flag == null) {
            throw new IllegalArgumentException("ItemFlag cannot be null");
        }
        meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {
        for (ItemFlag flag : flags) {
            if (flag == null) {
                throw new IllegalArgumentException("ItemFlag cannot be null");
            }
            meta.addItemFlags(flag);
        }
        return this;
    }

    public ItemBuilder hideAllFlags() {
        meta.addItemFlags(ItemFlag.values());
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        item.setDurability((short) durability);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        item.setAmount(amount);
        return this;
    }

    public void setSkullOwner(String owner) {
        if (item.getType() != Material.PLAYER_HEAD) {
            throw new IllegalStateException("Item is not a player head");
        }
        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwner(owner);
    }

    public ItemBuilder setCustomModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    public ItemBuilder clone() {
        ItemBuilder clone = new ItemBuilder(item.getType(), item.getAmount());
        clone.meta.setDisplayName(meta.getDisplayName());
        clone.meta.setLore(meta.getLore());
        clone.meta.setUnbreakable(meta.isUnbreakable());
        for (ItemFlag flag : meta.getItemFlags()) {
            clone.meta.addItemFlags(flag);
        }
        clone.meta.setCustomModelData(meta.getCustomModelData());
        return clone;
    }

    public static ItemBuilder fromItemStack(ItemStack itemStack) {
        if (itemStack == null) {
            throw new IllegalArgumentException("ItemStack cannot be null");
        }
        ItemBuilder builder = new ItemBuilder(itemStack.getType(), itemStack.getAmount());
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            builder.meta.setDisplayName(meta.getDisplayName());
            builder.meta.setLore(meta.getLore());
            builder.meta.setUnbreakable(meta.isUnbreakable());
            for (ItemFlag flag : meta.getItemFlags()) {
                builder.meta.addItemFlags(flag);
            }
            builder.meta.setCustomModelData(meta.getCustomModelData());
        }
        return builder;
    }
}
