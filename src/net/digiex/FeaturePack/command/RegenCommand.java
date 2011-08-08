package net.digiex.FeaturePack.command;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.FeaturePack.FPPermissions;
import net.digiex.FeaturePack.FPSettings;
import net.digiex.FeaturePack.FeaturePack;

public class RegenCommand implements CommandExecutor {
	@SuppressWarnings("unused")
	private FeaturePack parent;

	public static HashMap<Player, World> Regen = new HashMap<Player, World>();

	public RegenCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.regen")) {
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp regen");
					player.sendMessage(ChatColor.YELLOW + "Required: on, off");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp regen on " + ChatColor.AQUA
							+ "(Enables regen mode)");
				} else if (args[1].equals("on")) {
					player.sendMessage(FPSettings.regenon);
					Regen.put(player, player.getWorld());
				} else if (args[1].equals("off")) {
					player.sendMessage(FPSettings.regenoff);
					Regen.remove(player);
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