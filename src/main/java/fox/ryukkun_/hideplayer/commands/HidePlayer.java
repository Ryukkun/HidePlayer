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
        subCommands.put("set-item", new SetItem());
        subCommands.put("set-message", new SetMessage());
    }

    private static void configSet(CommandSender sender, String path, String[] args) {
        configSet(sender, path, (0 == args.length) ? "" : args[0]);
    }
    private static void configSet(CommandSender sender, String path, String arg) {
        main.getFileConfig().set(path, arg);
        main.getPlugin().saveConfig();
        MCLogger.sendMessage(sender, MCLogger.Level.Success, path+"を \""+arg+"\" に設定しました。");
    }


    private static class SetItem extends SubCommandTPL {
        public SetItem() {
            subCommands.put("change_item", new ChangeItem());
            subCommands.put("hide", new SetItemTPL("hide"));
            subCommands.put("show", new SetItemTPL("show"));
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

                configSet(sender, "item.change_item", Boolean.toString(res));
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
            public SetItemTPL(String path) {
                path = "item."+path+".";

                subCommands.put("id", new SetId(path+"id"));
                subCommands.put("name", new SetStringTPL(path+"name"));
                subCommands.put("lour", new SetStringTPL(path+"lour"));
            }

            private static class SetId implements CMD {
                private final String path;
                public SetId(String path) {
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
            subCommands.put("hide", new SetStringTPL("message.hide"));
            subCommands.put("show", new SetStringTPL("message.show"));
            subCommands.put("prefix", new Prefix());
        }


        private static class Prefix extends SubCommandTPL {
            public Prefix() {
                String path = "message.prefix.";
                subCommands.put("success", new SetStringTPL(path+"success"));
                subCommands.put("warning", new SetStringTPL(path+"warning"));
                subCommands.put("error", new SetStringTPL(path+"error"));
            }
        }
    }


    private static class SetStringTPL implements CMD {
        private final String path;
        public SetStringTPL(String path) {
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
            configSet(sender, "interval", Double.toString(arg));
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
