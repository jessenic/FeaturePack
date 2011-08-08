package net.digiex.FeaturePack.command;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.FeaturePack.FPPermissions;
import net.digiex.FeaturePack.FPSettings;
import net.digiex.FeaturePack.FPUtils;
import net.digiex.FeaturePack.FeaturePack;
import net.digiex.FeaturePack.payment.Method.MethodAccount;

public class LottoCommand implements CommandExecutor {
	private FeaturePack parent;

	public static int code;
	public static int amount;

	private FPUtils utils = new FPUtils();

	public LottoCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.lotto")) {
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp lotto");
					player.sendMessage(ChatColor.YELLOW
							+ "Optional: guess (1-1000), code");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp lotto 435 " + ChatColor.AQUA
							+ "(Plays lotto using code 435)");
				} else if (args[1].equalsIgnoreCase("code")) {
					if (FPPermissions.has(player, "featurepack.lotto.code")) {
						player.sendMessage(ChatColor.AQUA + "Lotto Number: "
								+ ChatColor.GREEN + code);
					}
				} else {
					try {
						MethodAccount account = this.parent.Method
								.getAccount(player.getName());
						if (account.hasEnough(FPSettings.LotteryCost)) {
							if (Integer.parseInt(args[1]) == code) {
								Random randomGenerator = new Random();
								int newRandom = randomGenerator.nextInt(1000);
								account.add(amount);
								utils.saveLotto(newRandom,
										FPSettings.LotteryStart);
								code = newRandom;
								amount = FPSettings.LotteryStart;
								player.sendMessage(FPSettings.LotteryWin
										.replace("@b", String.valueOf(account
												.balance())));
								parent.getServer().broadcastMessage(
										FPSettings.LotteryWinAll.replace("@p",
												player.getDisplayName()));
								FeaturePack.log.info(player.getDisplayName()
										+ " has won the lottery");
							} else {
								account.subtract(FPSettings.LotteryCost);
								utils.saveLotto(code, amount
										+ FPSettings.LotteryCost);
								amount = amount + FPSettings.LotteryCost;
								player.sendMessage(FPSettings.LotteryLoss
										.replace("@b", String.valueOf(account
												.balance())));
							}
						} else {
							player.sendMessage(FPSettings.nomoney);
						}
					} catch (NumberFormatException e) {
						player.sendMessage(FPSettings.incorrectusage);
					}
				}
			} else {
				player.sendMessage(ChatColor.AQUA + "Current lottery: "
						+ ChatColor.GREEN + amount);
			}
		} else {
			player.sendMessage(FPSettings.nopermission);
		}
		return true;
	}
}