package net.digiex.FeaturePack.command;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.FeaturePack.FPPermissions;
import net.digiex.FeaturePack.FPSettings;
import net.digiex.FeaturePack.FeaturePack;

public class NightCommand implements CommandExecutor {
	private FeaturePack parent;

	public NightCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.night")) {
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp night");
					player.sendMessage(ChatColor.YELLOW + "Optional: all");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp night all " + ChatColor.AQUA
							+ "(Sets all worlds to night)");
				} else if (args[1].toString().equalsIgnoreCase("all")) {
					for (World world : parent.getServer().getWorlds()) {
						world.setTime(14500);
					}
					player.sendMessage(FPSettings.nightall);
				} else {
					player.sendMessage(FPSettings.incorrectusage);
				}
			} else if (args.length == 1) {
				player.getWorld().setTime(14500);
				player.sendMessage(FPSettings.night);
			} else {
				player.sendMessage(FPSettings.incorrectusage);
			}
		} else {
			player.sendMessage(FPSettings.nopermission);
		}
		return true;
	}
}