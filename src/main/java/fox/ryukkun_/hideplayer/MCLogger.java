package fox.ryukkun_.hideplayer;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MCLogger {
    private static final String n1 = "H", n2 = "ide", n3 = "P", n4 = "layer ";
    private static final String ErrorPrefix = ChatColor.DARK_RED + n1 + ChatColor.RED + n2 +
            ChatColor.DARK_RED + n3 + ChatColor.RED + n4 +
            ChatColor.DARK_RED + ">> " + ChatColor.RED;

    private static final String WarningPrefix = ChatColor.GOLD + n1 + ChatColor.YELLOW + n2 +
            ChatColor.GOLD + n3 + ChatColor.YELLOW + n4 +
            ChatColor.GOLD + ">> " + ChatColor.YELLOW;

    private static final String SuccessPrefix = ChatColor.DARK_GREEN + n1 + ChatColor.GREEN + n2 +
            ChatColor.DARK_GREEN + n3 + ChatColor.GREEN + n4 +
            ChatColor.DARK_GREEN + ">> " + ChatColor.GREEN;

    private static final String WhitePrefix = n1+n2+n3+n4+">> ";


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
            return ErrorPrefix+text;
        } else if (level.equals( Level.Warning)) {
            return WarningPrefix+text;
        } else if (level.equals( Level.Success)) {
            return SuccessPrefix+text;
        } else {
            return WhitePrefix+text;
        }
    }

    public enum Level {
        Error, Warning, Success, White
    }
}

