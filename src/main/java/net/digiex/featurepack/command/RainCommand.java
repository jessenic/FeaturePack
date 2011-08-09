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

public class RainCommand implements CommandExecutor {

    private FeaturePack parent;

    public RainCommand(FeaturePack parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (FPPermissions.has(player, "featurepack.rain")) {
            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("?")) {
                    player.sendMessage(ChatColor.AQUA + "Help: /fp rain");
                    player.sendMessage(ChatColor.YELLOW + "Optional: all");
                    player.sendMessage(ChatColor.GREEN
                            + "Example usage: /fp rain all " + ChatColor.AQUA
                            + "(Sets all worlds to rain)");
                } else if (args[1].toString().equalsIgnoreCase("all")) {
                    for (World world : parent.getServer().getWorlds()) {
                        world.setStorm(true);
                        world.setThundering(false);
                    }
                    player.sendMessage(FPSettings.rainall);
                } else {
                    player.sendMessage(FPSettings.incorrectusage);
                }
            } else if (args.length == 1) {
                player.getWorld().setStorm(true);
                player.sendMessage(FPSettings.rain);
            } else {
                player.sendMessage(FPSettings.incorrectusage);
            }
        } else {
            player.sendMessage(FPSettings.nopermission);
        }
        return true;
    }
}