package fox.ryukkun_.hideplayer.commands;

import fox.ryukkun_.hideplayer.MCLogger;
import fox.ryukkun_.hideplayer.main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HidePlayer extends SubCommandTPL{
    public HidePlayer() {
        subCommands.put("reload", new Reload());
        subCommands.put("set-interval", new SetInterval());
        subCommands.put("set-itemId", new SetItemId());
        subCommands.put("check-name", new CheckName());
        subCommands.put("set-name", new SetName());
    }

    private static class SetName implements CMD {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            main.getFileConfig().set("item.name", args[0]);
            main.getPlugin().saveConfig();
            MCLogger.sendMessage(sender, MCLogger.Level.Success, "item.nameを \""+args[0]+"\" に設定しました。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            return null;
        }
    }



    private static class CheckName implements CMD {
        private static final String[] booleanList = new String[]{"true", "false"};

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            boolean res;
            try {
                res = Boolean.parseBoolean(args[0]);

            } catch (Exception e) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "true または false を入力して下さい");
                return true;
            }

            main.getFileConfig().set("item.check_name", res);
            main.getPlugin().saveConfig();
            MCLogger.sendMessage(sender, MCLogger.Level.Success, "item.check_nameを \""+res+"\" に設定しました。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            List<String> tabList = new ArrayList<>();
            for (String str : booleanList) {

                if (args[0].isEmpty() || str.startsWith(args[0])) {
                    tabList.add(str);
                }
            }
            return tabList;
        }
    }



    private static class SetItemId implements CMD {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (0 == args.length) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "値を入力してください。");
                return true;
            }

            String arg = args[0];
            if (Material.matchMaterial(arg) == null) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "アイテムを認識できませんでした。");
                return true;
            }

            main.getFileConfig().set("item.id", arg);
            main.getPlugin().saveConfig();
            MCLogger.sendMessage(sender, MCLogger.Level.Success, "Item.idを \""+arg+"\" に設定しました。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            List<String> tabList = new ArrayList<>();
            for (Material material : Material.values()) {
                if (!material.isItem()) continue;
                String name = material.name();

                if (args[0].isEmpty() || name.startsWith(args[0])) {
                    tabList.add(name);
                }
            }
            return tabList;
        }

    }


    private static class SetInterval implements CMD {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (0 == args.length) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "値を入力してください。");
                return true;
            }

            double arg;
            try {
                arg = Double.parseDouble(args[0]);
            } catch (Exception e) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "少数を入力してください。");
                return true;
            }
            main.getFileConfig().set("interval", arg);
            main.getPlugin().saveConfig();
            MCLogger.sendMessage(sender, MCLogger.Level.Success, "Intervalを \""+arg+"\" に設定しました。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            return null;
        }
    }


    private static class Reload implements CMD{
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            main.getPlugin().reloadConfig();
            MCLogger.sendMessage(sender, MCLogger.Level.Success, "configを再読み込みしました。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            return null;
        }
    }
}
