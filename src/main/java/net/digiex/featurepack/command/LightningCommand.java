package net.digiex.featurepack.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;

public class LightningCommand implements CommandExecutor {

    private FeaturePack parent;

    public LightningCommand(FeaturePack parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (FPPermissions.has(player, "featurepack.light")) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("?")) {
                    player.sendMessage(ChatColor.AQUA + "Help: /fp light");
                    player.sendMessage(ChatColor.YELLOW
                            + "Required: ONLINE PLAYER");
                    player.sendMessage(ChatColor.GREEN
                            + "Example usage: /fp light xzKinGzxBuRnzx "
                            + ChatColor.AQUA
                            + "(Stikes xzKinGzxBuRnzx with a lightning bolt)");
                } else {
                    return strikePlayer(args[1], player);
                }
            } else {
                player.sendMessage(FPSettings.incorrectusage);
            }
        } else {
            player.sendMessage(FPSettings.nopermission);
        }
        return true;
    }

    public boolean strikePlayer(String target, Player player) {
        for (Player players : parent.getServer().getOnlinePlayers()) {
            if (players.getDisplayName().toLowerCase().contains(target.toLowerCase())) {
                players.getWorld().strikeLightning(players.getLocation());
                player.sendMessage(FPSettings.strikeuser.replace("@p",
                        players.getDisplayName()));
                break;
            }
        }
        return true;
    }
}