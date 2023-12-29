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
        main.getPlugin().saveConfig();
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
        return Config.getString(path, "");
    }
    public static Material getMaterial(PATH path) {
        return Material.matchMaterial(config.getString(path.getPath()));
    }


    public enum PATH {
        change_item("item.change_item", TYPE.Boolean),
        item_hide_id("item.hide.id", TYPE.Material),
        item_hide_name("item.hide.name", TYPE.String),
        item_hide_lore("item.hide.lore", TYPE.String),
        item_show_id("item.show.id", TYPE.Material),
        item_show_name("item.show.name", TYPE.String),
        item_show_lore("item.show.lore", TYPE.String),
        interval("interval", TYPE.Double),
        give_item("give_item", TYPE.Boolean),
        message_hide("message.hide", TYPE.String),
        message_show("message.show", TYPE.String),
        message_alreadyHide("message.already_hide", TYPE.String),
        message_alreadyShow("message.already_show", TYPE.String),
        message_interval("message.interval", TYPE.String),
        prefix_success("message.prefix.success", TYPE.String),
        prefix_warning("message.prefix.warning", TYPE.String),
        prefix_error("message.prefix.error", TYPE.String);


        private final String path;
        private final TYPE type;
        PATH(String path, TYPE type) {
            this.path = path;
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public TYPE getType() {
            return type;
        }

        public static PATH getValue(String text) {
            for (PATH p : PATH.values()) {
                if (p.getPath().trim().equals(text)) return p;
            }
            return null;
        }
    }

    public enum TYPE {
        String,
        Boolean,
        Double,
        Material
    }
}
