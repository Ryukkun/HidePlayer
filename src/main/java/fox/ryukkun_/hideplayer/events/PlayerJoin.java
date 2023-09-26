package fox.ryukkun_.hideplayer.events;

import fox.ryukkun_.hideplayer.Config;
import fox.ryukkun_.hideplayer.ControlVisibility;
import fox.ryukkun_.hideplayer.HideItemUtil;
import fox.ryukkun_.hideplayer.main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (Player hidingPlayer : ControlVisibility.getHidingPlayer()) {
            hidingPlayer.hidePlayer(main.getPlugin(), player);
        }

        if (Config.getBoolean(Config.PATH.change_item)) {
            HideItemUtil.toHideItem(player.getInventory());
        }


    }
}
