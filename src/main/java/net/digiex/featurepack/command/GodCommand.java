package net.digiex.featurepack.command;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;

public class GodCommand implements CommandExecutor {
	private FeaturePack parent;

	public final static HashMap<Integer, Boolean> Gods = new HashMap<Integer, Boolean>();
	public final static HashMap<Integer, Integer> tempGods = new HashMap<Integer, Integer>();

	public GodCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.god")) {
			if (args.length >= 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp god");
					player.sendMessage(ChatColor.YELLOW
							+ "Optional: ONLINE PLAYER");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp god " + ChatColor.AQUA
							+ "(Gives god to you)");
				} else {
					for (Player p : parent.getServer().getOnlinePlayers()) {
						if (p.getDisplayName().toLowerCase()
								.contains(args[1].toString().toLowerCase())) {
							if (!Gods.containsKey(p.getEntityId())) {
								Gods.put(p.getEntityId(), true);
								player.sendMessage(FPSettings.goduser.replace(
										"@p", p.getName()));
								p.sendMessage(FPSettings.UserGod.replace("@p",
										player.getDisplayName()));
								break;
							} else {
								player.sendMessage(FPSettings.alreadygoduser
										.replace("@p", p.getName()));
								break;
							}
						}
					}
				}
			} else if (args.length == 1) {
				if (!Gods.containsKey(player.getEntityId())) {
					Gods.put(player.getEntityId(), true);
					player.sendMessage(FPSettings.god);
				} else {
					player.sendMessage(FPSettings.alreadygod);
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