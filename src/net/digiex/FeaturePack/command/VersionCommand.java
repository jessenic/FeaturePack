package net.digiex.FeaturePack.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.FeaturePack.FPPermissions;
import net.digiex.FeaturePack.FPSettings;
import net.digiex.FeaturePack.FeaturePack;

public class VersionCommand implements CommandExecutor {
	private FeaturePack parent;

	public VersionCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = ((Player) sender);
		if (FPPermissions.has(player, "featurepack.version")) {
			if (args.length == 1) {
				player.sendMessage(FPSettings.version.replace("@n",
						parent.getDescription().getName()).replace("@v",
						parent.getDescription().getVersion()));
			} else if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp version");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp version " + ChatColor.AQUA
							+ "(Shows the current FeaturePack version)");
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