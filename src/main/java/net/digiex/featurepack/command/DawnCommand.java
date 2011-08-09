package net.digiex.featurepack.command;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;

public class DawnCommand implements CommandExecutor {

    private FeaturePack parent;

    public DawnCommand(FeaturePack parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (FPPermissions.has(player, "featurepack.dawn")) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("?")) {
                    player.sendMessage(ChatColor.AQUA + "Help: /fp dawn");
                    player.sendMessage(ChatColor.YELLOW + "Optional: all");
                    player.sendMessage(ChatColor.GREEN
                            + "Example usage: /fp dawn all " + ChatColor.AQUA
                            + "(Sets all worlds to dawn)");
                } else if (args[1].equalsIgnoreCase("all")) {
                    for (World world : parent.getServer().getWorlds()) {
                        world.setTime(22000);
                    }
                    player.sendMessage(FPSettings.dawnall);
                } else {
                    player.sendMessage(FPSettings.incorrectusage);
                }
            } else if (args.length == 1) {
                player.getWorld().setTime(22000);
                player.sendMessage(FPSettings.dawn);
            } else {
                player.sendMessage(FPSettings.incorrectusage);
            }
        } else {
            player.sendMessage(FPSettings.nopermission);
        }
        return true;
    }
}