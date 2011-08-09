package net.digiex.featurepack.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;
import net.digiex.featurepack.payment.Method.MethodAccount;

public class MyTimeCommand implements CommandExecutor {
	private FeaturePack parent;
	private static long timeValue;

	public MyTimeCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.mytime")) {
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp mytime");
					player.sendMessage(ChatColor.YELLOW
							+ "Required: day, night, dawn, dusk, off");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp mytime day " + ChatColor.AQUA
							+ "(Sets you're time to day)");
				} else if (args[1].equalsIgnoreCase("day")) {
					myTime(player, 0);
				} else if (args[1].equalsIgnoreCase("night")) {
					myTime(player, 14500);
				} else if (args[1].equalsIgnoreCase("dawn")) {
					myTime(player, 22000);
				} else if (args[1].equalsIgnoreCase("dusk")) {
					myTime(player, 12500);
				} else if (args[1].equalsIgnoreCase("off")) {
					if (!player.isPlayerTimeRelative()) {
						player.resetPlayerTime();
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

	public void myTime(Player player, long value) {
		if (this.parent.Method != null) {
			MethodAccount account = this.parent.Method.getAccount(player
					.getName());
			Double amount = ((double) FPSettings.MyTimeCost);
			if (account.hasEnough(amount)) {
				account.subtract(amount);
				timeValue = value;
				player.setPlayerTime(timeValue, false);
				player.sendMessage(FPSettings.mytime.replace("@b",
						account.toString()));
			} else {
				player.sendMessage(FPSettings.nomoney);
			}
		} else {
			timeValue = value;
			player.setPlayerTime(timeValue, false);
			player.sendMessage(FPSettings.mytimenormal);
		}
	}
}