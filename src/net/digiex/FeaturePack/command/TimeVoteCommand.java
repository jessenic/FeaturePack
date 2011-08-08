package net.digiex.FeaturePack.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.FeaturePack.FPPermissions;
import net.digiex.FeaturePack.FPSettings;
import net.digiex.FeaturePack.FeaturePack;
import net.digiex.FeaturePack.task.TimeVoteTask;

public class TimeVoteCommand implements CommandExecutor {
	public static FeaturePack parent;
	public static int timeValue;
	public static VoteTypes VoteType = VoteTypes.Unknown;

	public final static HashMap<World, Boolean> inProgress = new HashMap<World, Boolean>();
	public final static HashMap<World, String> Type = new HashMap<World, String>();
	public final static HashMap<Player, World> Yes = new HashMap<Player, World>();
	public final static HashMap<Player, World> No = new HashMap<Player, World>();
	public final static HashMap<World, Integer> ID = new HashMap<World, Integer>();

	public TimeVoteCommand(FeaturePack parent) {
		TimeVoteCommand.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.time")) {
			if (args.length == 2) {
				if (!inProgress.containsKey(player.getWorld())) {
					if (player.getWorld().getEnvironment()
							.equals(Environment.NETHER)
							|| player.getWorld().getEnvironment()
									.equals(Environment.SKYLANDS)) {
						player.sendMessage(FPSettings.cannotvotehere.replace(
								"@t", "time").replace("@w",
								player.getWorld().getName()));
					} else {
						if (args[1].equalsIgnoreCase("?")) {
							player.sendMessage(ChatColor.AQUA
									+ "Help: /fp time");
							player.sendMessage(ChatColor.YELLOW
									+ "Required: day, night, dawn, dusk");
							player.sendMessage(ChatColor.GREEN
									+ "Example usage: /fp time day "
									+ ChatColor.AQUA
									+ "(Starts a vote for day)");
						} else if (args[1].equals("day")) {
							timeValue = 0;
							return createVote(player, VoteTypes.Day);
						} else if (args[1].equals("night")) {
							timeValue = 14500;
							return createVote(player, VoteTypes.Night);
						} else if (args[1].equals("dawn")) {
							timeValue = 22000;
							return createVote(player, VoteTypes.Dawn);
						} else if (args[1].equals("dusk")) {
							timeValue = 12500;
							return createVote(player, VoteTypes.Dusk);
						} else {
							player.sendMessage(FPSettings.incorrectusage);
						}
					}
				} else {
					if (!inProgress.get(player.getWorld())) {
						if (FPPermissions
								.has(player, "featurepack.time.bypass")) {
							return bypassVote(player, args);
						} else {
							player.sendMessage(FPSettings.timecooldown);
						}
					} else {
						player.sendMessage(FPSettings.timevoteinprogress
								.replace("@t", Type.get(player.getWorld())));
					}
				}
			} else {
				player.sendMessage(FPSettings.incorrectusage);
			}
		} else {
			player.sendMessage(FPSettings.nopermission);
		}
		return true;
	}

	public static boolean createVote(Player player, VoteTypes timetype) {
		TimeVoteTask task = new TimeVoteTask(parent, player.getWorld());
		VoteType = timetype;
		int timeID;
		float players = player.getWorld().getPlayers().size();
		if (players <= 1) {
			player.sendMessage(FPSettings.freetime.replace("@t",
					VoteType.toString()));
			player.getWorld().setTime(timeValue);
			FeaturePack.log.info(player.getDisplayName()
					+ " was given a free time change to " + VoteType.toString()
					+ " for " + player.getWorld().getName() + ".");
		} else {
			FeaturePack.log.info(player.getDisplayName()
					+ " created a time vote for changing time to "
					+ VoteType.toString() + " for "
					+ player.getWorld().getName() + ".");
			for (Player p : player.getWorld().getPlayers()) {
				p.sendMessage(FPSettings.TimeVote.replace("@p",
						player.getName()).replace("@t", VoteType.toString()));
			}
			timeID = parent
					.getServer()
					.getScheduler()
					.scheduleAsyncDelayedTask(parent, task,
							FPSettings.TimeVotingTime * 60 * 20);
			inProgress.put(player.getWorld(), true);
			ID.put(player.getWorld(), timeID);
			Type.put(player.getWorld(), VoteType.toString());
			Yes.put(player, player.getWorld());
		}
		return true;
	}

	public boolean bypassVote(Player player, String[] args) {
		cancelVote(player);
		if (args[1].equals("day")) {
			timeValue = 0;
			return createVote(player, VoteTypes.Day);
		} else if (args[1].equals("night")) {
			timeValue = 14500;
			return createVote(player, VoteTypes.Night);
		} else if (args[1].equals("dawn")) {
			timeValue = 22000;
			return createVote(player, VoteTypes.Dawn);
		} else if (args[1].equals("dusk")) {
			timeValue = 12500;
			return createVote(player, VoteTypes.Dusk);
		} else {
			player.sendMessage(FPSettings.incorrectusage);
		}
		return true;
	}

	public boolean cancelVote(Player player) {
		ArrayList<String[]> Remove = new ArrayList<String[]>();
		int id = TimeVoteCommand.ID.get(player.getWorld());
		parent.getServer().getScheduler().cancelTask(id);
		for (Entry<Player, World> e : TimeVoteCommand.Yes.entrySet()) {
			if (e.getValue().equals(player.getWorld())) {
				Remove.add(new String[] { player.getName(), "YES" });
			}
		}
		for (Entry<Player, World> e : TimeVoteCommand.No.entrySet()) {
			if (e.getValue().equals(player.getWorld())) {
				Remove.add(new String[] { player.getName(), "NO" });
			}
		}
		for (String[] s : Remove) {
			if (s[1].equals("YES")) {
				Player p = parent.getServer().getPlayer(s[0]);
				TimeVoteCommand.Yes.remove(p);
			} else if (s[1].equals("NO")) {
				Player p = parent.getServer().getPlayer(s[0]);
				TimeVoteCommand.No.remove(p);
			}
		}
		TimeVoteCommand.inProgress.remove(player.getWorld());
		TimeVoteCommand.ID.remove(player.getWorld());
		return true;
	}

	public enum VoteTypes {
		Day, Night, Dawn, Dusk, Unknown
	}
}