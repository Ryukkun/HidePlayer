package fox.ryukkun_.hideplayer.events;

import fox.ryukkun_.hideplayer.ControlVisibility;
import fox.ryukkun_.hideplayer.main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (Player hidingPlayer : ControlVisibility.getHidingPlayer()) {
            hidingPlayer.hidePlayer(main.getPlugin(), player);
        }

        FileConfiguration config = main.getFileConfig();
        if (config.getBoolean("item.change_item")) {
            Material showMaterial = Material.matchMaterial(config.getString("item.show.id"));
            Material hideMaterial = Material.matchMaterial(config.getString("item.hide.id"));
            String showName = config.getString("item.show.name");
            String hideName = config.getString("item.hide.name");

            for (ItemStack is : player.getInventory()) {
                if (!is.getType().equals(showMaterial)) continue;
                ItemMeta im = is.getItemMeta();
                if (im.hasDisplayName() || !showName.isEmpty()) {
                    if (!im.getDisplayName().equals( showName)) continue;
                }

                is.setType(hideMaterial);
                im.setDisplayName(hideName);
                im.setLore(Arrays.asList(config.getString("item.hide.lure").split("\n")));
                is.setItemMeta(im);
            }
        }


    }
}
