package fox.ryukkun_.hideplayer;

import fox.ryukkun_.hideplayer.commands.Hide;
import fox.ryukkun_.hideplayer.commands.HidePlayer;
import fox.ryukkun_.hideplayer.commands.Show;
import fox.ryukkun_.hideplayer.events.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {
    private static JavaPlugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        main.plugin = this;
        Config.setConfig();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryMoveItem(), this);
        pluginManager.registerEvents(new InteractItemFrame(), this);
        pluginManager.registerEvents(new DropItem(), this);
        pluginManager.registerEvents(new PlayerJoin(), this);
        pluginManager.registerEvents(new PlayerLeft(), this);
        pluginManager.registerEvents(new UsedItem(), this);

        getCommand("hide").setExecutor(new Hide());
        getCommand("show").setExecutor(new Show());
        getCommand("hideplayer").setExecutor(new HidePlayer());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin(){
        return plugin;
    }

}
