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

public class SeedCommand implements CommandExecutor {
	private FeaturePack parent;

	public SeedCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.seed")) {
			if (args.length == 1) {
				for (World world : parent.getServer().getWorlds()) {
					player.sendMessage(ChatColor.AQUA + world.getName()
							+ ChatColor.GRAY + " = " + ChatColor.GREEN
							+ world.getSeed());
				}
			} else if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp seed");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp seed " + ChatColor.AQUA
							+ "(Shows all seeds for all worlds)");
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