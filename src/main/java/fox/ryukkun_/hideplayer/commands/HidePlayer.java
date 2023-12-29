package fox.ryukkun_.hideplayer.commands;

import fox.ryukkun_.hideplayer.Config;
import fox.ryukkun_.hideplayer.MCLogger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HidePlayer extends SubCommandTPL{
    public HidePlayer() {
        subCommands.put("reload_config", new Reload());
        subCommands.put("set-interval", new SetInterval());
        subCommands.put("get", new Get());
        subCommands.put("set", new Set());
    }

    private static void configSet(CommandSender sender, Config.PATH path, String arg) {
        if (path.getType().equals(Config.TYPE.Boolean)) {
            // boolean
            boolean res;
            try {
                res = Boolean.parseBoolean(arg);

            } catch (Exception e) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "true または false を入力して下さい");
                return;
            }
            Config.set(path, res);
            arg = String.valueOf(res);


        } else if (path.getType().equals(Config.TYPE.Double)) {
            // double
            if (arg.isEmpty()) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "値を入力してください。");
                return;
            }
            double res;
            try {
                res = Double.parseDouble(arg);
            } catch (Exception e) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "少数を入力してください。");
                return;
            }
            Config.set(path, res);
            arg = String.valueOf(res);


        } else if (path.getType().equals(Config.TYPE.Material)) {
            if (arg.isEmpty()) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "値を入力してください。");
                return;
            }

            if (Material.matchMaterial(arg) == null) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "アイテムを認識できませんでした。");
                return;
            }
            Config.set(path, arg);


        } else {
            // string
            Config.set(path, ChatColor.translateAlternateColorCodes('&', arg));
        }

        registerMessage(sender, path, arg);
    }


    private static void registerMessage(CommandSender sender, Config.PATH path, String arg) {
        MCLogger.sendMessage(sender, MCLogger.Level.Success, path.getPath()+"を \"§r"+arg+"§a\" に設定しました。");
    }


    private static class Get implements CMD {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            String path = args.length == 0 ? "" : String.join(".", args);
            String text = Config.config.isConfigurationSection(path) ? Config.config.getConfigurationSection(path).getValues(true).toString() : Config.config.getString(path);
            MCLogger.sendMessage(sender, MCLogger.Level.Success, path+" の値は \"§r"+text+"§a\" です。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            String path = String.join(".", Arrays.copyOf(args, args.length-1));
            if (Config.config.isConfigurationSection(path)) {
                String prefix = args[args.length-1];
                List<String> tabList = new ArrayList<>();
                for (String key : Config.config.getConfigurationSection(path).getKeys(false)) {
                    if (prefix.isEmpty() || key.startsWith(prefix)) {
                        tabList.add(key);
                    }
                }
                return tabList;
            }
            return new ArrayList<>();
        }
    }



    private static class Set implements CMD {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            Config.PATH path = null;
            String arg = "";
            boolean exist = false;
            for (int i = 0; i < args.length; i++) {
                path = Config.PATH.getValue( String.join(".", Arrays.copyOf(args, i+1)));
                if (path != null) {
                    exist = true;
                    arg = String.join(" ", Arrays.copyOfRange(args, i+1, args.length));
                    break;
                }
            }
            if (!exist) {
                MCLogger.sendMessage(sender, MCLogger.Level.Error, "存在しないPATHです");
                return true;
            }
            configSet(sender, path, arg);
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            // PATHの予測変換
            String path = String.join(".", Arrays.copyOf(args, args.length-1));
            Config.PATH path2 = Config.PATH.getValue(path);
            if (Config.config.isConfigurationSection(path)) {
                String prefix = args[args.length-1];
                List<String> tabList = new ArrayList<>();
                for (String key : Config.config.getConfigurationSection(path).getKeys(false)) {
                    if (prefix.isEmpty() || key.startsWith(prefix)) {
                        tabList.add(key);
                    }
                }
                return tabList;


            } else if (path2 != null) {
                // 値への予測変換 (boolean と materialのみ)
                if (path2.getType().equals(Config.TYPE.Boolean)) {
                    List<String> tabList = new ArrayList<>();
                    String arg = args[args.length-1];
                    for (String str : new String[]{"true", "false"}) {
                        if (arg.isEmpty() || str.startsWith(arg)) {
                            tabList.add(str);
                        }
                    }
                    return tabList;

                } else if (path2.getType().equals(Config.TYPE.Material)) {
                    List<String> tabList = new ArrayList<>();
                    String arg = args[args.length-1];
                    for (Material material : Material.values()) {
                        if (!material.isItem()) continue;
                        String name = material.name();

                        if (arg.isEmpty() || name.startsWith(arg)) {
                            tabList.add(name);
                        }
                    }
                    return tabList;
                }
            }
            return new ArrayList<>();
        }
    }




    private static class SetInterval implements CMD {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            configSet(sender, Config.PATH.interval, (args.length == 0) ? "" : args[0]);
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            return new ArrayList<>();
        }
    }


    private static class Reload implements CMD{
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            Config.reload();
            MCLogger.sendMessage(sender, MCLogger.Level.Success, "configを再読み込みしました。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            return null;
        }
    }
}
