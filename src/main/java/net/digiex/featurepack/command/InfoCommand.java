package net.digiex.featurepack.command;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;

public class InfoCommand implements CommandExecutor {

    private FeaturePack parent;
    public static ArrayList<String[]> Info = new ArrayList<String[]>();

    public InfoCommand(FeaturePack parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (FPPermissions.has(player, "featurepack.info")) {
            if (args.length == 1) {
                info(parent.getServer(), player);
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("?")) {
                    player.sendMessage(ChatColor.AQUA + "Help: /fp info");
                    player.sendMessage(ChatColor.GREEN
                            + "Example usage: /fp info " + ChatColor.AQUA
                            + "(Shows server information)");
                } else {
                    player.sendMessage(FPSettings.incorrectusage);
                }
            } else {
                player.sendMessage(FPSettings.incorrectusage);
            }
        } else {
            player.sendMessage(FPSettings.nopermission);
        }
        return true;
    }

    private void info(Server server, Player player) {
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024 * 1024;

        Info.add(new String[]{"Operating Sytem",
                    System.getProperty("os.name")});
        Info.add(new String[]{"Architecture", System.getProperty("os.arch")});
        Info.add(new String[]{"Available Processors",
                    String.valueOf(runtime.availableProcessors())});
        Info.add(new String[]{"Max RAM", runtime.maxMemory() / mb + "MB"});
        Info.add(new String[]{"Allocated RAM",
                    String.valueOf(runtime.totalMemory() / mb) + "MB"});
        Info.add(new String[]{"Free RAM", runtime.freeMemory() / mb + "MB"});
        Info.add(new String[]{"Java Version",
                    System.getProperty("java.version")});
        Info.add(new String[]{"Server Name", server.getServerName()});
        Info.add(new String[]{"Server ID", server.getServerId()});
        Info.add(new String[]{"Max Players",
                    String.valueOf(server.getMaxPlayers())});
        Info.add(new String[]{"Player Count",
                    String.valueOf(server.getOnlinePlayers().length)});
        Info.add(new String[]{"World", player.getWorld().getName()});
        Info.add(new String[]{"Time",
                    String.valueOf(player.getWorld().getTime())});
        if (!LockCommand.TimeLocked.isEmpty()) {
            String worlds = "";
            for (Entry<World, Integer> e : LockCommand.TimeLocked.entrySet()) {
                worlds = worlds + e.getKey().getName() + ", ";
            }
            Info.add(new String[]{"TimeLocked", worlds});
        }
        if (!LockCommand.WeatherLocked.isEmpty()) {
            String worlds = "";
            for (Entry<World, Integer> e : LockCommand.WeatherLocked.entrySet()) {
                worlds = worlds + e.getKey().getName() + ", ";
            }
            Info.add(new String[]{"WeatherLocked", worlds});
        }
        Info.add(new String[]{"Implementation", server.getVersion()});
        for (String[] s : Info) {
            player.sendMessage(ChatColor.AQUA + s[0] + ChatColor.GRAY + " = "
                    + ChatColor.GREEN + s[1]);
        }
        Info.clear();
    }
}