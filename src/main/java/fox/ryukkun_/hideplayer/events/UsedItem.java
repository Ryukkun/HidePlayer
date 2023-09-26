package fox.ryukkun_.hideplayer.events;

import fox.ryukkun_.hideplayer.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class UsedItem implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK) || !event.hasItem()) return;

        Player player = event.getPlayer();
        Material hideMaterial = Config.getMaterial(Config.PATH.item_hide_id);
        Material showMaterial = Config.getMaterial(Config.PATH.item_show_id);
        if (hideMaterial == null) {
            MCLogger.sendMessage(player, MCLogger.Level.Error, Config.PATH.item_hide_id.getPath()+"を認識できません。");
            return;
        }
        if (showMaterial == null) {
            MCLogger.sendMessage(player, MCLogger.Level.Error, Config.PATH.item_show_id.getPath()+"を認識できません。");
            return;
        }

        ItemStack item = event.getItem();
        boolean isHiding = ControlVisibility.isHidingPlayer(player);
        String hideName = Config.getString(Config.PATH.item_hide_name, "");
        String showName = Config.getString(Config.PATH.item_show_name, "");

        if (!HideItemUtil.equalsItem(item, hideMaterial, hideName) && !HideItemUtil.equalsItem(item, showMaterial, showName)) return;

        if (isHiding) {
            ControlVisibility.show(player, item);

        } else {
            ControlVisibility.hide(player, item);
        }
    }
}
