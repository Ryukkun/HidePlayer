package fox.ryukkun_.hideplayer.events;

import fox.ryukkun_.hideplayer.ControlVisibility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeft implements Listener {
    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        ControlVisibility.reset(event.getPlayer());
    }
}
