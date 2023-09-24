package fox.ryukkun_.hideplayer.events;

import fox.ryukkun_.hideplayer.ControlVisibility;
import fox.ryukkun_.hideplayer.main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class UsedItem implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        FileConfiguration config = main.getFileConfig();
        Material material = Material.matchMaterial(config.getString("item.id"));

        ItemStack item = event.getItem();
        if (!item.getType().equals(material)) return;
        if (config.getBoolean("item.check_name")){
            if (!item.getItemMeta().getDisplayName().equals( config.getString("item.name"))) return;
        }

        Player player = event.getPlayer();
        if (ControlVisibility.isHidingPlayer(player)) {
            ControlVisibility.show(player);
        } else {
            ControlVisibility.hide(player);
        }
    }
}
