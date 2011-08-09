package net.digiex.featurepack.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;

public class UngodCommand implements CommandExecutor {

    private FeaturePack parent;

    public UngodCommand(FeaturePack parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (FPPermissions.has(player, "featurepack.ungod")) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("?")) {
                    player.sendMessage(ChatColor.AQUA + "Help: /fp ungod");
                    player.sendMessage(ChatColor.YELLOW
                            + "Optional: ONLINE PLAYER");
                    player.sendMessage(ChatColor.GREEN
                            + "Example usage: /fp ungod " + ChatColor.AQUA
                            + "(Removes god from you");
                } else {
                    for (Player p : parent.getServer().getOnlinePlayers()) {
                        if (p.getDisplayName().toLowerCase().contains(args[1].toString().toLowerCase())) {
                            if (GodCommand.Gods.containsKey(p.getEntityId())) {
                                GodCommand.Gods.remove(p.getEntityId());
                                player.sendMessage(FPSettings.ungoduser.replace("@p", p.getDisplayName()));
                                p.sendMessage(FPSettings.UserUnGod.replace(
                                        "@p", player.getDisplayName()));
                                break;
                            } else {
                                player.sendMessage(FPSettings.notgoduser.replace("@p", p.getName()));
                                break;
                            }
                        }
                    }
                }
            } else if (args.length == 1) {
                if (GodCommand.Gods.containsKey(player.getEntityId())) {
                    GodCommand.Gods.remove(player.getEntityId());
                    player.sendMessage(FPSettings.ungod);
                } else {
                    player.sendMessage(FPSettings.notgod);
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