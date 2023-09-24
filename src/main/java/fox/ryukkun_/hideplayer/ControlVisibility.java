package fox.ryukkun_.hideplayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class ControlVisibility {
    private static final Collection<UUID> enableHide = new ArrayList<>();


    public static void hide(Player player) {
        UUID uuid = player.getUniqueId();
        if (enableHide.contains(uuid)) {
            MCLogger.sendMessage(player, MCLogger.Level.Warning, "already hidden the player =)");
            return;
        }

        enableHide.add(uuid);
        for (Player onlinePlayer : getOnlinePlayers(player)) {
            player.hidePlayer(main.getPlugin(), onlinePlayer);
        }
    }

    public static void show(Player player) {
        UUID uuid = player.getUniqueId();
        if (!enableHide.contains(uuid)) {
            MCLogger.sendMessage(player, MCLogger.Level.Warning, "already shown the player =)");
            return;
        }

        enableHide.remove(uuid);
        for (Player onlinePlayer : getOnlinePlayers(player)) {
            player.showPlayer(main.getPlugin(), onlinePlayer);
        }
    }

    public static boolean isHidingPlayer(Player player){
        return enableHide.contains( player.getUniqueId());
    }


    public static Collection<Player> getHidingPlayer() {
        Collection<Player> players = new ArrayList<>();
        for (UUID uuid : enableHide) {
            players.add( Bukkit.getPlayer(uuid));
        }
        return players;
    }

    public static void reset(Player player) {
        enableHide.remove(player.getUniqueId());
    }


    private static Collection<? extends Player> getOnlinePlayers(Player ignore) {
        Collection<? extends Player> onlineList = Bukkit.getOnlinePlayers();
        onlineList.remove(ignore);
        return onlineList;
    }
}
