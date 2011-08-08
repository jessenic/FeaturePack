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
import net.digiex.FeaturePack.task.WeatherVoteTask;

public class WeatherVoteCommand implements CommandExecutor {
	public FeaturePack parent;
	public static VoteTypes VoteType = VoteTypes.Unknown;

	public final static HashMap<World, Boolean> inProgress = new HashMap<World, Boolean>();
	public final static HashMap<World, String> Type = new HashMap<World, String>();
	public final static HashMap<Player, World> Yes = new HashMap<Player, World>();
	public final static HashMap<Player, World> No = new HashMap<Player, World>();
	public final static HashMap<World, Integer> ID = new HashMap<World, Integer>();

	public WeatherVoteCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.weather")) {
			if (args.length == 2) {
				if (!inProgress.containsKey(player.getWorld())) {
					if (player.getWorld().getEnvironment()
							.equals(Environment.NETHER)
							|| player.getWorld().getEnvironment()
									.equals(Environment.SKYLANDS)) {
						player.sendMessage(FPSettings.cannotvotehere.replace(
								"@t", "weather").replace("@w",
								player.getWorld().getName()));
					} else {
						if (args[1].equalsIgnoreCase("?")) {
							player.sendMessage(ChatColor.AQUA
									+ "Help: /fp weather");
							player.sendMessage(ChatColor.YELLOW
									+ "Required: sun, rain, thunder");
							player.sendMessage(ChatColor.GREEN
									+ "Example usage: /fp weather rain "
									+ ChatColor.AQUA
									+ "(Starts a vote for rain)");
						} else if (args[1].equals("sun")) {
							return createVote(player, VoteTypes.Sun);
						} else if (args[1].equals("rain")) {
							return createVote(player, VoteTypes.Rain);
						} else if (args[1].equals("thunder")) {
							return createVote(player, VoteTypes.Thunder);
						} else {
							player.sendMessage(FPSettings.incorrectusage);
						}
					}
				} else {
					if (!inProgress.get(player.getWorld())) {
						if (FPPermissions.has(player,
								"featurepack.weather.bypass")) {
							return bypassVote(player, args);
						} else {
							player.sendMessage(FPSettings.weathercooldown);
						}
					} else {
						player.sendMessage(FPSettings.weathervoteinprogress
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

	public boolean createVote(Player player, VoteTypes weathertype) {
		WeatherVoteTask task = new WeatherVoteTask(parent, player.getWorld());
		VoteType = weathertype;
		int weatherID;
		float timeVotesLeft = player.getWorld().getPlayers().size();
		if (timeVotesLeft <= 1) {
			player.sendMessage(FPSettings.freeweather.replace("@t",
					VoteType.toString()));
			setWeather(player, weathertype);
			FeaturePack.log.info(player.getDisplayName()
					+ " was given a free weather change to "
					+ VoteType.toString() + " for "
					+ player.getWorld().getName() + ".");
		} else {
			FeaturePack.log.info(player.getDisplayName()
					+ " created a weather vote for changing weather to "
					+ VoteType.toString() + " for "
					+ player.getWorld().getName() + ".");
			for (Player p : player.getWorld().getPlayers()) {
				p.sendMessage(FPSettings.WeatherVote.replace("@p",
						player.getName()).replace("@t", VoteType.toString()));
			}
			weatherID = parent
					.getServer()
					.getScheduler()
					.scheduleAsyncDelayedTask(parent, task,
							FPSettings.WeatherVotingTime * 60 * 20);
			inProgress.put(player.getWorld(), true);
			ID.put(player.getWorld(), weatherID);
			Type.put(player.getWorld(), VoteType.toString());
			Yes.put(player, player.getWorld());
		}
		return true;
	}

	public boolean bypassVote(Player player, String[] args) {
		cancelVote(player);
		if (args[1].equals("sun")) {
			return createVote(player, VoteTypes.Sun);
		} else if (args[1].equals("rain")) {
			return createVote(player, VoteTypes.Rain);
		} else if (args[1].equals("thunder")) {
			return createVote(player, VoteTypes.Thunder);
		} else {
			player.sendMessage(FPSettings.incorrectusage);
		}
		return true;
	}

	public boolean cancelVote(Player player) {
		ArrayList<String[]> Remove = new ArrayList<String[]>();
		int id = WeatherVoteCommand.ID.get(player.getWorld());
		parent.getServer().getScheduler().cancelTask(id);
		for (Entry<Player, World> e : WeatherVoteCommand.Yes.entrySet()) {
			if (e.getValue().equals(player.getWorld())) {
				Remove.add(new String[] { player.getName(), "YES" });
			}
		}
		for (Entry<Player, World> e : KillVoteCommand.No.entrySet()) {
			if (e.getValue().equals(player.getWorld())) {
				Remove.add(new String[] { player.getName(), "NO" });
			}
		}
		for (String[] s : Remove) {
			if (s[1].equals("YES")) {
				Player p = parent.getServer().getPlayer(s[0]);
				WeatherVoteCommand.Yes.remove(p);
			} else if (s[1].equals("NO")) {
				Player p = parent.getServer().getPlayer(s[0]);
				WeatherVoteCommand.No.remove(p);
			}
		}
		WeatherVoteCommand.inProgress.remove(player.getWorld());
		WeatherVoteCommand.ID.remove(player.getWorld());
		return true;
	}

	public boolean setWeather(Player player, VoteTypes weathertype) {
		if (weathertype == VoteTypes.Sun) {
			player.getWorld().setStorm(false);
			player.getWorld().setThundering(false);
		} else if (weathertype == VoteTypes.Rain) {
			player.getWorld().setStorm(true);
			player.getWorld().setThundering(false);
		} else if (weathertype == VoteTypes.Thunder) {
			player.getWorld().setStorm(true);
			player.getWorld().setThundering(true);
		}
		return true;
	}

	public static enum VoteTypes {
		Sun, Rain, Thunder, Unknown
	}
}