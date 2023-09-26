package fox.ryukkun_.hideplayer.commands;

import fox.ryukkun_.hideplayer.Config;
import fox.ryukkun_.hideplayer.MCLogger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HidePlayer extends SubCommandTPL{
    public HidePlayer() {
        subCommands.put("reload_config", new Reload());
        subCommands.put("set-interval", new SetInterval());
        subCommands.put("set-item", new SetItem());
        subCommands.put("set-message", new SetMessage());
        subCommands.put("get", new Get());
    }

    private static void configSet(CommandSender sender, Config.PATH path, String[] args) {
        configSet(sender, path, (0 == args.length) ? "" : String.join(" ", args));
    }
    private static void configSet(CommandSender sender, Config.PATH path, String arg) {
        Config.set(path, ChatColor.translateAlternateColorCodes('&', arg));
        registered(sender, path, arg);
    }
    private static void configSet(CommandSender sender, Config.PATH path, double arg) {
        Config.set(path, arg);
        registered(sender, path, String.valueOf(arg));
    }
    private static void configSet(CommandSender sender, Config.PATH path, boolean arg) {
        Config.set(path, arg);
        registered(sender, path, String.valueOf(arg));
    }
    private static void registered(CommandSender sender, Config.PATH path, String arg) {
        MCLogger.sendMessage(sender, MCLogger.Level.Success, path.getPath()+"を \""+arg+"\" に設定しました。");
    }


    private static class Get implements CMD {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            String arg = args.length == 0 ? "" : String.join(" ", args);
            MCLogger.sendMessage(sender, MCLogger.Level.Success, arg+" の値は \"§r"+Config.config.getString(arg)+"§r\" です。");
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            if (args.length == 1) {
                String prefix = args[0];
                List<String> tabList = new ArrayList<>();
                for (String key : Config.config.getKeys(true)) {
                    if (prefix.isEmpty() || key.startsWith(prefix)) {
                        tabList.add(key);
                    }
                }
                return tabList;
            }
            return null;
        }
    }


    private static class SetItem extends SubCommandTPL {
        public SetItem() {
            subCommands.put("change_item", new ChangeItem());
            subCommands.put("hide", new SetItemTPL(Config.PATH.item_hide_id, Config.PATH.item_hide_name, Config.PATH.item_hide_lore));
            subCommands.put("show", new SetItemTPL(Config.PATH.item_show_id, Config.PATH.item_show_name, Config.PATH.item_show_lore));
        }

        private static class ChangeItem implements CMD {
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

                configSet(sender, Config.PATH.change_item, res);
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

        private static class SetItemTPL extends SubCommandTPL {
            public SetItemTPL(Config.PATH id, Config.PATH name, Config.PATH lore) {
                subCommands.put("id", new SetId(id));
                subCommands.put("name", new SetStringTPL(name));
                subCommands.put("lore", new SetStringTPL(lore));
            }

            private static class SetId implements CMD {
                private final Config.PATH path;
                public SetId(Config.PATH path) {
                    this.path = path;
                }
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

                    configSet(sender, path, arg);
                    return true;
                }

                @Override
                public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
                    return itemMaterials(args[0]);
                }

                private static List<String> itemMaterials(String prefix) {
                    List<String> tabList = new ArrayList<>();
                    for (Material material : Material.values()) {
                        if (!material.isItem()) continue;
                        String name = material.name();

                        if (prefix.isEmpty() || name.startsWith(prefix)) {
                            tabList.add(name);
                        }
                    }
                    return tabList;
                }
            }
        }
    }



    private static class SetMessage extends SubCommandTPL {
        public SetMessage() {
            subCommands.put("hide", new SetStringTPL(Config.PATH.message_hide));
            subCommands.put("show", new SetStringTPL(Config.PATH.message_show));
            subCommands.put("already-hide", new SetStringTPL(Config.PATH.message_alreadyHide));
            subCommands.put("already-show", new SetStringTPL(Config.PATH.message_alreadyShow));
            subCommands.put("interval", new SetStringTPL(Config.PATH.message_interval));
            subCommands.put("prefix", new Prefix());
        }


        private static class Prefix extends SubCommandTPL {
            public Prefix() {
                subCommands.put("success", new SetStringTPL(Config.PATH.prefix_success));
                subCommands.put("warning", new SetStringTPL(Config.PATH.prefix_warning));
                subCommands.put("error", new SetStringTPL(Config.PATH.prefix_error));
            }
        }
    }


    private static class SetStringTPL implements CMD {
        private final Config.PATH path;
        public SetStringTPL(Config.PATH path) {
            this.path = path;
        }
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            configSet(sender, path, args);
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            return null;
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
            configSet(sender, Config.PATH.interval, arg);
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
