package fr.jesuistrolls.antiSpawnerDeMerde;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SpawnerOffhandListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        InventoryAction action = event.getAction();

        if (event.getClickedInventory() instanceof PlayerInventory && event.getSlot() == 40) {
            ItemStack cursor = event.getCursor();
            if (action == InventoryAction.HOTBAR_SWAP) {
                ItemStack hotbarItem = player.getInventory().getItem(event.getHotbarButton());
                if (isSpawner(hotbarItem)) {
                    event.setCancelled(true);
                    return;
                }
            }
            if (isSpawner(cursor)) {
                event.setCancelled(true);
                return;
            }
        }

        if (action == InventoryAction.HOTBAR_SWAP && event.getHotbarButton() == 40) {
            if (isSpawner(event.getCurrentItem())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (!isSpawner(event.getOldCursor())) return;
        if (event.getRawSlots().contains(45)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        if (isSpawner(event.getMainHandItem()) || isSpawner(event.getOffHandItem())) {
            event.setCancelled(true);
        }
    }

    private boolean isSpawner(ItemStack item) {
        return item != null && item.getType() == Material.SPAWNER;
    }
}
