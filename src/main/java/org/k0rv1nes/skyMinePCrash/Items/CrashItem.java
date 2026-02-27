package org.k0rv1nes.skyMinePCrash.Items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.k0rv1nes.skyMinePCrash.SkyMinePCrash;
import java.util.List;
import java.util.UUID;

public class CrashItem {

    public String giveCrashStick(Player player) {
        ItemStack itemstack = new ItemStack(Material.STICK);

        ItemMeta itemMeta = itemstack.getItemMeta();
        String displayName = SkyMinePCrash.getInstance().getConfig().getString("item.itemName");
        itemMeta.setDisplayName(displayName);
        List<String> lore = SkyMinePCrash.getInstance().getConfig().getStringList("item.itemLore");
        itemMeta.setLore(lore);



        if (SkyMinePCrash.getInstance().getConfig().getBoolean("item.Stick_enchantment")) {
            Enchantment enchantment = Enchantment.getByName(SkyMinePCrash.getInstance().getConfig().getString("item.enchantment_type"));
            int level = SkyMinePCrash.getInstance().getConfig().getInt("item.enchantment_level");


            int maxLevel = enchantment.getMaxLevel();
            if (level > maxLevel) {
                level = maxLevel;
            }
            itemstack.addEnchantment(enchantment, level);
        }

        NamespacedKey key = new NamespacedKey(SkyMinePCrash.getInstance(), "unique_id");
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        String uniqueID = UUID.randomUUID().toString(); // Генерация уникального ID
        dataContainer.set(key, PersistentDataType.STRING, uniqueID);

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setCustomModelData((int) (Math.random() * Integer.MAX_VALUE));

        itemstack.setItemMeta(itemMeta);
        itemstack.setAmount(1);
        itemstack.setItemMeta(itemMeta);
        player.getInventory().addItem(itemstack);

        return uniqueID; // Возвращаем уникальный ID
    }


    public String getUniqueIDFromStick(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return null;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        NamespacedKey key = new NamespacedKey(SkyMinePCrash.getInstance(), "unique_id");
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();

        return dataContainer.get(key, PersistentDataType.STRING);
    }


}
