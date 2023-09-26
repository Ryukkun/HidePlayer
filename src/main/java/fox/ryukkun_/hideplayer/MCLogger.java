package fox.ryukkun_.hideplayer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MCLogger {
    private static final String n1 = "H", n2 = "ide", n3 = "P", n4 = "layer ";
    private static final String WhitePrefix = n1+n2+n3+n4+" >> ";


    public static void sendMessage(Player player, Level level, String text) {
        player.sendMessage( getMessage( text, level));
    }

    public static void sendMessage(CommandSender sender, Level level ,String text) {
        sender.sendMessage( getMessage( text, level));
    }

    public static void syncSendMessage(CommandSender sender, Level level ,String text) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, level, text);
            }
        }.runTask( main.getPlugin());
    }

    public static void syncSendMessage(Player player, Level level ,String text) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, level, text);
            }
        }.runTask( main.getPlugin());
    }

    private static String getMessage(String text, Level level) {
        if (level.equals( Level.Error)) {
            return Config.getString(Config.PATH.prefix_error, "")+text;
        } else if (level.equals( Level.Warning)) {
            return Config.getString(Config.PATH.prefix_warning, "")+text;
        } else if (level.equals( Level.Success)) {
            return Config.getString(Config.PATH.prefix_success, "")+text;
        } else {
            return WhitePrefix+text;
        }
    }

    public enum Level {
        Error, Warning, Success, White
    }
}

