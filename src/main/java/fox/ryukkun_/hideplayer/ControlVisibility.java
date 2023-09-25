package fox.ryukkun_.hideplayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class ControlVisibility {
    private static final HashMap<UUID, HidingPlayer> hidePlayer = new HashMap<>();


    public static void hide(Player player) {
        UUID uuid = player.getUniqueId();
        if (isHidingPlayer(uuid)) {
            MCLogger.sendMessage(player, MCLogger.Level.Warning, "already hidden all players =)");
            return;
        }

        if (!setEnable(uuid, true)) {
            intervalMessage(player);
            return;
        }
        String message = main.getFileConfig().getString("message.hide");
        if (!message.isEmpty()) {
            MCLogger.sendMessage(player, MCLogger.Level.Success, message);
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getUniqueId() == uuid) continue;
            player.hidePlayer(main.getPlugin(), onlinePlayer);
        }

    }

    public static void show(Player player) {
        UUID uuid = player.getUniqueId();
        if (!isHidingPlayer(uuid)) {
            MCLogger.sendMessage(player, MCLogger.Level.Warning, "already shown all players =)");
            return;
        }

        if (!setEnable(uuid, false)) {
            intervalMessage(player);
            return;
        }
        String message = main.getFileConfig().getString("message.show");
        if (!message.isEmpty()) {
            MCLogger.sendMessage(player, MCLogger.Level.Success, message);
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getUniqueId() == uuid) continue;
            player.showPlayer(main.getPlugin(), onlinePlayer);
        }
    }

    public static boolean isHidingPlayer(Player player){
        return isHidingPlayer(player.getUniqueId());
    }
    public static boolean isHidingPlayer(UUID uuid){
        if (!hidePlayer.containsKey( uuid)) return false;
        return hidePlayer.get( uuid).isEnable();
    }

    public static boolean setEnable(UUID uuid, boolean enable) {
        if (!hidePlayer.containsKey(uuid)) {
            hidePlayer.put(uuid, new HidingPlayer(uuid, enable));
            return true;
        }
        else {
            HidingPlayer hp = hidePlayer.get(uuid);
            if (hp.canAction()) {
                hp.setEnable(enable);
                return true;
            }
            return false;
        }
    }

    public static Collection<Player> getHidingPlayer() {
        Collection<Player> players = new ArrayList<>();
        for (HidingPlayer p : hidePlayer.values()) {
            if (p.isEnable()) players.add( Bukkit.getPlayer(p.getUUID()));
        }
        return players;
    }

    public static void reset(Player player) {
        hidePlayer.remove(player.getUniqueId());
    }


    private static void intervalMessage(Player player) {
        MCLogger.sendMessage(player, MCLogger.Level.Warning, "Please wait "+main.getFileConfig().getDouble("interval")+" seconds.");
    }


    private static class HidingPlayer {
        private final UUID uuid;
        private boolean enable;
        private long lastActionTime = 0;

        public HidingPlayer(UUID uuid, boolean enable) {
            this.uuid = uuid;
            this.enable = enable;
        }

        public Player getPlayer() {
            return Bukkit.getPlayer(uuid);
        }

        public UUID getUUID(){
            return uuid;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean b) {
            enable = b;
            updateLastAction();
        }

        public void updateLastAction() {
            this.lastActionTime = System.currentTimeMillis();
        }

        public boolean canAction() {
            return main.getFileConfig().getDouble("interval")*1000 <= System.currentTimeMillis()-lastActionTime;
        }
    }
}
