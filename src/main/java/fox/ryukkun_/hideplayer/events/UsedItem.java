package fox.ryukkun_.hideplayer.events;

import fox.ryukkun_.hideplayer.ControlVisibility;
import fox.ryukkun_.hideplayer.MCLogger;
import fox.ryukkun_.hideplayer.main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class UsedItem implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK) || !event.hasItem()) return;
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        FileConfiguration config = main.getFileConfig();
        Material hideMaterial = Material.matchMaterial(config.getString("item.hide.id"));
        Material showMaterial = null;
        if (hideMaterial == null) {
            MCLogger.sendMessage(player, MCLogger.Level.Error, "item.hide.idを認識できません。");
            return;
        }

        boolean change_item = config.getBoolean("item.change_item");
        if (change_item) {
             showMaterial = Material.matchMaterial(config.getString("item.show.id"));
            if (showMaterial == null) {
                MCLogger.sendMessage(player, MCLogger.Level.Error, "item.show.idを認識できません。");
                return;
            }
        }

        boolean isHiding = ControlVisibility.isHidingPlayer(player);
        Material checkMaterial = (isHiding ? (change_item ? showMaterial : hideMaterial) : hideMaterial);
        String hideName = config.getString("item.hide.name");
        String showName = config.getString("item.show.name");
        String checkName = (isHiding ? (change_item ? showName : hideName) : hideName);

        if (!item.getType().equals(checkMaterial)) return;
        ItemMeta im = item.getItemMeta();
        if (im.hasDisplayName() || !checkName.isEmpty()) {
            if (!im.getDisplayName().equals( checkName)) return;
        }


        if (isHiding) {
            ControlVisibility.show(player);
            if (change_item) {
                item.setType(hideMaterial);
                im.setDisplayName(hideName);
                im.setLore(Arrays.asList(config.getString("item.hide.lure").split("\n")));
                item.setItemMeta(im);
            }

        } else {
            ControlVisibility.hide(player);
            if (change_item) {
                item.setType(showMaterial);
                im.setDisplayName(showName);
                im.setLore(Arrays.asList(config.getString("item.show.lure").split("\n")));
                item.setItemMeta(im);
            }
        }
    }
}
