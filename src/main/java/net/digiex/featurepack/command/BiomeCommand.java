package net.digiex.featurepack.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;

public class BiomeCommand implements CommandExecutor {

    @SuppressWarnings("unused")
    private FeaturePack parent;

    public BiomeCommand(FeaturePack parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (FPPermissions.has(player, "featurepack.biome")) {
            if (args.length == 1) {
                player.sendMessage(FPSettings.inbiome.replace("@b", player.getLocation().getBlock().getBiome().name().toLowerCase().replace("_", " ")));
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("?")) {
                    player.sendMessage(ChatColor.AQUA + "Help: /fp biome");
                    player.sendMessage(ChatColor.GREEN
                            + "Example usage: /fp biome " + ChatColor.AQUA
                            + "(Shows what biome you're in)");
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