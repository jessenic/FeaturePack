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

public class DayCommand implements CommandExecutor {

    private FeaturePack parent;

    public DayCommand(FeaturePack parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (FPPermissions.has(player, "featurepack.day")) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("?")) {
                    player.sendMessage(ChatColor.AQUA + "Help: /fp day");
                    player.sendMessage(ChatColor.YELLOW + "Optional: all");
                    player.sendMessage(ChatColor.GREEN
                            + "Example usage: /fp day all " + ChatColor.AQUA
                            + "(Sets all worlds to day)");
                } else if (args[1].equalsIgnoreCase("all")) {
                    for (World world : parent.getServer().getWorlds()) {
                        world.setTime(0);
                    }
                    player.sendMessage(FPSettings.dayall);
                } else {
                    player.sendMessage(FPSettings.incorrectusage);
                }
            } else if (args.length == 1) {
                player.getWorld().setTime(0);
                player.sendMessage(FPSettings.day);
            } else {
                player.sendMessage(FPSettings.incorrectusage);
            }
        } else {
            player.sendMessage(FPSettings.nopermission);
        }
        return true;
    }
}