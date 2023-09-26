package fox.ryukkun_.hideplayer;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static FileConfiguration config;

    public static void setConfig() {
        Config.config = main.getPlugin().getConfig();
    }
    public static void reload() {
        main.getPlugin().reloadConfig();
        setConfig();
    }

    public static void set(PATH path, Object value) {
        config.set(path.getPath(), value);
        main.getPlugin().saveConfig();
    }

    public static boolean getBoolean(PATH path, boolean Default) {
        return config.getBoolean(path.getPath(), Default);
    }
    public static boolean getBoolean(PATH path) {
        return config.getBoolean(path.getPath());
    }
    public static double getDouble(PATH path, double Default) {
        return config.getDouble(path.getPath(), Default);
    }
    public static double getDouble(PATH path) {
        return config.getDouble(path.getPath());
    }
    public static String getString(PATH path, String Default) {
        String res = config.getString(path.getPath());
        if (res == null) {
            set(path, Default);
            res = Default;
        }
        return res;
    }
    public static String getString(PATH path) {
        return config.getString(path.getPath());
    }
    public static Material getMaterial(PATH path) {
        return Material.matchMaterial(config.getString(path.getPath()));
    }


    public enum PATH {
        change_item("item.change_item"),
        item_hide_id("item.hide.id"),
        item_hide_name("item.hide.name"),
        item_hide_lore("item.hide.lore"),
        item_show_id("item.show.id"),
        item_show_name("item.show.name"),
        item_show_lore("item.show.lore"),
        interval("interval"),
        message_hide("message.hide"),
        message_show("message.show"),
        message_alreadyHide("message.already_hide"),
        message_alreadyShow("message.already_show"),
        message_interval("message.interval"),
        prefix_success("message.prefix.success"),
        prefix_warning("message.prefix.warning"),
        prefix_error("message.prefix.error");


        private final String path;
        PATH(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}