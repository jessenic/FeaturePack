package net.digiex.featurepack.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;

public class HelpCommand implements CommandExecutor {
	private FeaturePack parent;

	public HelpCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.help")) {
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp help");
					player.sendMessage(ChatColor.YELLOW + "Optional: 1, 2, 3");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp help 1 " + ChatColor.AQUA
							+ "(Shows page 1 of help)");
				} else if (args[1].toString().equalsIgnoreCase("1")) {
					this.parent.Help.getHelpOne(player);
				} else if (args[1].toString().equalsIgnoreCase("2")) {
					this.parent.Help.getHelpTwo(player);
				} else if (args[1].toString().equalsIgnoreCase("3")) {
					this.parent.Help.getHelpThree(player);
				}
			} else if (args.length == 1) {
				this.parent.Help.getHelpOne(player);
			} else {
				player.sendMessage(FPSettings.incorrectusage);
			}
		} else {
			player.sendMessage(FPSettings.nopermission);
		}
		return true;
	}
}