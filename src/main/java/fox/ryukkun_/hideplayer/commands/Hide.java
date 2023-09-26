package fox.ryukkun_.hideplayer.commands;

import fox.ryukkun_.hideplayer.ControlVisibility;
import fox.ryukkun_.hideplayer.MCLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hide implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            MCLogger.sendMessage(sender, MCLogger.Level.Error, "playerで実行しよう！");
            return true;
        }

        ControlVisibility.hide((Player) sender, null);
        return true;
    }
}
