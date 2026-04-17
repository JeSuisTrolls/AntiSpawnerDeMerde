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

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        InventoryAction action = event.getAction();
        int hotbarButton = event.getHotbarButton();


        boolean clickedOffhand = event.getRawSlot() == 45
                || (event.getClickedInventory() instanceof PlayerInventory && event.getSlot() == 40);

        if (clickedOffhand) {
            if (action == InventoryAction.HOTBAR_SWAP) {
                ItemStack hotbarItem = hotbarButton >= 0 ? player.getInventory().getItem(hotbarButton) : null;
                if (isSpawner(hotbarItem)) {
                    event.setCancelled(true);
                    return;
                }
            }
            if (isSpawner(event.getCursor())) {
                event.setCancelled(true);
                return;
            }
        }

        if (action == InventoryAction.HOTBAR_SWAP && (hotbarButton == 40 || hotbarButton == -1)) {
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