package fox.ryukkun_.hideplayer.events;

import fox.ryukkun_.hideplayer.Config;
import fox.ryukkun_.hideplayer.HideItemUtil;
import fox.ryukkun_.hideplayer.MCLogger;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;


public class InventoryMoveItem implements Listener {
    @EventHandler
    public void onInventoryClickItem(InventoryClickEvent event) {
        if (event.getWhoClicked().getGameMode() != GameMode.ADVENTURE) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getOpenInventory().getType().equals(InventoryType.CRAFTING)) return;


        InventoryAction action = event.getAction();
        if (action.equals(InventoryAction.PICKUP_ALL) || action.equals(InventoryAction.PICKUP_HALF) || action.equals(InventoryAction.PICKUP_ONE) || action.equals(InventoryAction.PICKUP_SOME)) {
            if (HideItemUtil.equalsItem(event.getCurrentItem())) {
                event.setCancelled(true);
                sendMessage(player);
            }

        } else if (action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
            if (HideItemUtil.equalsItem(event.getCurrentItem())) {
                event.setCancelled(true);
                sendMessage(player);
            }
        } else if (action.equals(InventoryAction.HOTBAR_SWAP) || action.equals(InventoryAction.HOTBAR_MOVE_AND_READD)) {
            if (HideItemUtil.equalsItem(player.getInventory().getItem(event.getHotbarButton()))) {
                event.setCancelled(true);
                sendMessage(player);
            }
        }
    }

    public static void sendMessage(Player player) {
        MCLogger.sendMessage(player, MCLogger.Level.Error, Config.getString(Config.PATH.inventory_change));
    }
}
