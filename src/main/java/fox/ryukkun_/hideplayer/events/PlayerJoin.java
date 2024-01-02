package fox.ryukkun_.hideplayer.events;

import fox.ryukkun_.hideplayer.Config;
import fox.ryukkun_.hideplayer.ControlVisibility;
import fox.ryukkun_.hideplayer.HideItemUtil;
import fox.ryukkun_.hideplayer.main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
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

        if (Config.getBoolean(Config.PATH.change_item)) {
            HideItemUtil.toHideItem(player.getInventory());
        }

        if (Config.getBoolean(Config.PATH.give_item)) {
            giveItem(player);
        }

    }
    private static void giveItem(Player player) {
        Material showMaterial = Material.matchMaterial(Config.getString(Config.PATH.item_show_id));
        String showName = Config.getString(Config.PATH.item_show_name);
        Material hideMaterial = Material.matchMaterial(Config.getString(Config.PATH.item_hide_id));
        String hideName = Config.getString(Config.PATH.item_hide_name);

        Inventory inv = player.getInventory();

        for (ItemStack is : inv) {
            if (HideItemUtil.equalsItem(is, showMaterial, showName) ||
                    HideItemUtil.equalsItem(is, hideMaterial, hideName)) return;
        }
        inv.addItem(new ItemStack(hideMaterial){
            public ItemStack run() {
                ItemMeta im = this.getItemMeta();
                im.setDisplayName(hideName);
                im.setLore( Arrays.asList(Config.getString(Config.PATH.item_hide_lore, "").split("\n")));
                this.setItemMeta(im);
                return this;
            }
        }.run());
    }
}
