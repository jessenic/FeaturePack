package net.digiex.featurepack.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;

public class IPv6Command implements CommandExecutor {

    @SuppressWarnings("unused")
    private FeaturePack parent;

    public IPv6Command(FeaturePack parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (FPPermissions.has(player, "featurepack.ipv6")) {
            if (args.length == 1) {
                if (player.getAddress().getAddress().getHostAddress().contains(":")) {
                    player.sendMessage(FPSettings.hasipv6);
                } else {
                    player.sendMessage(FPSettings.noipv6);
                }
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("?")) {
                    player.sendMessage(ChatColor.AQUA + "Help: /fp ipv6");
                    player.sendMessage(ChatColor.GREEN
                            + "Example usage: /fp ipv6 " + ChatColor.AQUA
                            + "(Shows if you have IPv6)");
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
}