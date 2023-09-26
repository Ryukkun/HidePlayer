package fox.ryukkun_.hideplayer;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HideItemUtil {
    public static boolean equalsItem(ItemStack is, Material material, String name) {
        if (is == null) return false;
        if (!is.getType().equals(material)) return false;
        ItemMeta im = is.getItemMeta();
        return im.hasDisplayName() ? im.getDisplayName().equals(name) : name.isEmpty();
    }


    public static void toHideItem(PlayerInventory inventory) {
        Material showMaterial = Material.matchMaterial(Config.getString(Config.PATH.item_show_id));
        String showName = Config.getString(Config.PATH.item_show_name);

        for (ItemStack is : inventory) {
            if (HideItemUtil.equalsItem(is, showMaterial, showName)) HideItemUtil.toHideItem(is);
        }
    }
    public static void toHideItem(ItemStack is) {
        ItemMeta im = is.getItemMeta();
        is.setType(Config.getMaterial(Config.PATH.item_hide_id));
        im.setDisplayName(Config.getString(Config.PATH.item_hide_name));
        im.setLore(Arrays.asList(Config.getString(Config.PATH.item_hide_lure, "").split("\n")));
        is.setItemMeta(im);
    }

    public static void toShowItem(PlayerInventory inventory) {
        Material material = Material.matchMaterial(Config.getString(Config.PATH.item_hide_id));
        String name = Config.getString(Config.PATH.item_hide_name);

        for (ItemStack is : inventory) {
            if (HideItemUtil.equalsItem(is, material, name)) HideItemUtil.toShowItem(is);
        }
    }
    public static void toShowItem(ItemStack is) {
        ItemMeta im = is.getItemMeta();
        is.setType(Config.getMaterial(Config.PATH.item_show_id));
        im.setDisplayName(Config.getString(Config.PATH.item_show_name));
        im.setLore(Arrays.asList(Config.getString(Config.PATH.item_show_lure, "").split("\n")));
        is.setItemMeta(im);
    }
}
