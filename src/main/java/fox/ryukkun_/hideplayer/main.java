package fox.ryukkun_.hideplayer;

import fox.ryukkun_.hideplayer.commands.Hide;
import fox.ryukkun_.hideplayer.commands.Show;
import fox.ryukkun_.hideplayer.events.PlayerJoin;
import fox.ryukkun_.hideplayer.events.PlayerLeft;
import fox.ryukkun_.hideplayer.events.UsedItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {
    private static JavaPlugin plugin;
    private static FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        main.plugin = this;
        main.config = getConfig();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoin(), this);
        pluginManager.registerEvents(new PlayerLeft(), this);
        pluginManager.registerEvents(new UsedItem(), this);

        getCommand("hide").setExecutor(new Hide());
        getCommand("show").setExecutor(new Show());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin(){
        return plugin;
    }

    public static FileConfiguration getFileConfig() {
        return config;
    }
}
