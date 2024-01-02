package fox.ryukkun_.hideplayer.events;

import fox.ryukkun_.hideplayer.HideItemUtil;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InteractItemFrame implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame) {
            if (HideItemUtil.equalsItem(event.getPlayer().getInventory().getItemInMainHand()) ||
                    HideItemUtil.equalsItem(event.getPlayer().getInventory().getItemInOffHand())) {
                event.setCancelled(true);
            }
        }
    }
}
