package net.digiex.featurepack.command;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FPUtils;
import net.digiex.featurepack.FeaturePack;

public class SecureCommand implements CommandExecutor {
	private FeaturePack parent;

	private FPUtils utils = new FPUtils();

	public final static HashMap<Player, Boolean> Secure = new HashMap<Player, Boolean>();

	public SecureCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.secure")) {
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp secure");
					player.sendMessage(ChatColor.YELLOW + "Required: activate "
							+ ChatColor.AQUA + "(password)" + ChatColor.YELLOW
							+ ", deactivate " + ChatColor.AQUA + "(password)"
							+ ChatColor.YELLOW + ", pass " + ChatColor.AQUA
							+ "(pasword)" + ChatColor.YELLOW + ", override "
							+ ChatColor.AQUA + "(ONLINE PLAYER)");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp secure activate FEATURES "
							+ ChatColor.AQUA
							+ "(Activates secure mode using password FEATURES)");
				} else {
					player.sendMessage(FPSettings.incorrectusage);
				}
			} else if (args.length == 3) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp secure");
					player.sendMessage(ChatColor.YELLOW + "Required: activate "
							+ ChatColor.AQUA + "(password)" + ChatColor.YELLOW
							+ ", deactivate " + ChatColor.AQUA + "(password)"
							+ ChatColor.YELLOW + ", pass, override "
							+ ChatColor.AQUA + "(ONLINE PLAYER)");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp secure activate FEATURES "
							+ ChatColor.AQUA
							+ "(Activates secure mode using password FEATURES)");
				} else if (args[1].equalsIgnoreCase("pass")) {
					if (Secure.containsKey(player)) {
						if (args[2]
								.equals(utils.get(utils.savedsettings())
										.getString(
												"Secure." + player.getName()
														+ ".pass"))) {
							utils.setProperty(utils.savedsettings(), "Secure."
									+ player.getName() + ".ip", player
									.getAddress().getAddress().getHostAddress());
							utils.setProperty(utils.savedsettings(), "Secure."
									+ player.getName() + ".flagged", false);
							Secure.remove(player);
							player.sendMessage(FPSettings.securepass);
						} else {
							player.sendMessage(FPSettings.secureincorrectpass);
						}
					} else {
						player.sendMessage(FPSettings.notflagged);
					}
				} else if (args[1].equalsIgnoreCase("override")) {
					if (FPPermissions
							.has(player, "featurepack.secure.override")) {
						for (Player who : parent.getServer().getOnlinePlayers()) {
							if (who.getName().contains(args[2])) {
								utils.setProperty(utils.savedsettings(),
										"Secure." + who.getName() + ".ip", who
												.getAddress().getAddress()
												.getHostAddress());
								utils.setProperty(utils.savedsettings(),
										"Secure." + who.getName() + ".flagged",
										false);
								Secure.remove(who);
								who.sendMessage(FPSettings.securepass);
							}
						}
					} else {
						player.sendMessage(FPSettings.nopermission);
					}
				} else if (args[1].equalsIgnoreCase("activate")) {
					if (utils.get(utils.savedsettings()).getString(
							"Secure." + player.getName()) == null) {
						utils.setProperty(utils.savedsettings(), "Secure."
								+ player.getName() + ".ip", player.getAddress()
								.getAddress().getHostAddress());
						utils.setProperty(utils.savedsettings(), "Secure."
								+ player.getName() + ".pass", args[2]);
						utils.setProperty(utils.savedsettings(), "Secure."
								+ player.getName() + ".flagged", false);
						player.sendMessage(FPSettings.secureactivated);
					} else {
						player.sendMessage(FPSettings.securealreadyactivated);
					}
				} else if (args[1].equalsIgnoreCase("deactivate")) {
					if (utils.get(utils.savedsettings()).getString(
							"Secure." + player.getName()) != null) {
						if (utils
								.get(utils.savedsettings())
								.getString(
										"Secure." + player.getName() + ".pass")
								.equals(args[2])) {
							utils.removeProperty(utils.savedsettings(),
									"Secure." + player.getName());
							player.sendMessage(FPSettings.securedeactivated);
						} else {
							player.sendMessage(FPSettings.secureincorrectpass);
						}
					} else {
						player.sendMessage(FPSettings.securealreadydeactivated);
					}
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