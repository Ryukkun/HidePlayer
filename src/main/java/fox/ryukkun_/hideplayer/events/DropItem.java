package fox.ryukkun_.hideplayer.events;

import fox.ryukkun_.hideplayer.HideItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class DropItem implements Listener {
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemStack is = event.getItemDrop().getItemStack();
        if (HideItemUtil.equalsHideItem(is) || HideItemUtil.equalsShowItem(is)) {
            event.setCancelled(true);
        }
    }
}
