package net.digiex.featurepack.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FPSpout;
import net.digiex.featurepack.FeaturePack;

public class ReloadCommand implements CommandExecutor {
	private FeaturePack parent;

	public ReloadCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "FeaturePack.reload")) {
			if (args.length == 1) {
				parent.getServer().getScheduler().cancelTasks(parent);
				TimeVoteCommand.inProgress.clear();
				TimeVoteCommand.ID.clear();
				TimeVoteCommand.Type.clear();
				TimeVoteCommand.Yes.clear();
				TimeVoteCommand.No.clear();
				WeatherVoteCommand.inProgress.clear();
				WeatherVoteCommand.ID.clear();
				WeatherVoteCommand.Type.clear();
				WeatherVoteCommand.Yes.clear();
				WeatherVoteCommand.No.clear();
				KillVoteCommand.inProgress.clear();
				KillVoteCommand.ID.clear();
				KillVoteCommand.Type.clear();
				KillVoteCommand.Yes.clear();
				KillVoteCommand.No.clear();
				GodCommand.Gods.clear();
				LockCommand.TimeLocked.clear();
				LockCommand.WeatherLocked.clear();
				InfoCommand.Info.clear();
				RegenCommand.Regen.clear();
				SecureCommand.Secure.clear();
				this.parent.Help.PageOne.clear();
				this.parent.Help.PageTwo.clear();
				this.parent.Help.PageThree.clear();
				this.parent.Command.clearCommands();
				this.parent.Settings.load();
				this.parent.Command.addCommands();
				this.parent.Help.load();
				this.parent.loadSaved();
				if (FPSettings.TipsEnabled) {
					parent.loadTips();
				}
				if (FPSpout.plugin != null && FPSpout.hasSpout(player)) {
					FPSpout.sendNotification(FPSettings.reloadtitle,
							FPSettings.reloadmessage, Material.CAKE, player);
				} else {
					player.sendMessage(FPSettings.reload);
				}
			} else if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp reload");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp reload " + ChatColor.AQUA
							+ "(Reloads FeaturePack)");
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