package net.digiex.featurepack.command;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;

public class VoteCommand implements CommandExecutor {
	private static FeaturePack parent;

	public VoteCommand(FeaturePack parent) {
		VoteCommand.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.vote")) {
			if (args.length == 3) {
				if (args[1].equals("yes")) {
					if (args[1].equalsIgnoreCase("?")) {
						player.sendMessage(ChatColor.AQUA + "Help: /fp vote");
						player.sendMessage(ChatColor.YELLOW + "Optional: yes "
								+ ChatColor.AQUA + "(time, weather, kill)"
								+ ChatColor.YELLOW + ", no " + ChatColor.AQUA
								+ "(time, weather, kill)" + ChatColor.YELLOW
								+ ", cancel");
						player.sendMessage(ChatColor.GREEN
								+ "Example usage: /fp vote yes time "
								+ ChatColor.AQUA + "(Votes yes to a time vote)");
					} else if (args[2].equals("time")) {
						if (TimeVoteCommand.inProgress.containsKey(player
								.getWorld())) {
							TimeVoteYes(player);
						}
					} else if (args[2].equals("weather")) {
						if (WeatherVoteCommand.inProgress.containsKey(player
								.getWorld())) {
							WeatherVoteYes(player);
						}
					} else if (args[2].equals("kill")) {
						if (KillVoteCommand.inProgress.containsKey(player
								.getWorld())) {
							KillVoteYes(player);
						}
					} else {
						if (TimeVoteCommand.inProgress.containsKey(player
								.getWorld())) {
							TimeVoteYes(player);
						} else if (WeatherVoteCommand.inProgress
								.containsKey(player.getWorld())) {
							WeatherVoteYes(player);
						} else if (KillVoteCommand.inProgress
								.containsKey(player.getWorld())) {
							KillVoteYes(player);
						}
					}
				} else if (args[1].equals("no")) {
					if (args[2].equals("time")) {
						if (TimeVoteCommand.inProgress.containsKey(player
								.getWorld())) {
							TimeVoteNo(player);
						}
					} else if (args[2].equals("weather")) {
						if (WeatherVoteCommand.inProgress.containsKey(player
								.getWorld())) {
							WeatherVoteNo(player);
						}
					} else if (args[2].equals("kill")) {
						if (KillVoteCommand.inProgress.containsKey(player
								.getWorld())) {
							KillVoteNo(player);
						}
					} else {
						if (TimeVoteCommand.inProgress.containsKey(player
								.getWorld())) {
							TimeVoteNo(player);
						} else if (WeatherVoteCommand.inProgress
								.containsKey(player.getWorld())) {
							WeatherVoteNo(player);
						} else if (KillVoteCommand.inProgress
								.containsKey(player.getWorld())) {
							KillVoteNo(player);
						}
					}
				} else if (args[1].equals("cancel")) {
					if (FPPermissions.has(player, "featurepack.vote.cancel")) {
						if (args[2].equalsIgnoreCase("time")) {
							return cancelTime(player);
						} else if (args[2].equalsIgnoreCase("weather")) {
							return cancelWeather(player);
						} else if (args[2].equalsIgnoreCase("kill")) {

						} else {
							return cancelVote(player, args);
						}
					}
				} else {
					player.sendMessage(FPSettings.incorrectusage);
				}
			} else if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp vote");
					player.sendMessage(ChatColor.YELLOW
							+ "Optional: yes, no, cancel");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp vote yes " + ChatColor.AQUA
							+ "(Votes yes to a vote in progress)");
				} else if (args[1].equals("yes")) {
					if (TimeVoteCommand.inProgress.containsKey(player
							.getWorld())) {
						TimeVoteYes(player);
					}
					if (WeatherVoteCommand.inProgress.containsKey(player
							.getWorld())) {
						WeatherVoteYes(player);
					}
					if (KillVoteCommand.inProgress.containsKey(player
							.getWorld())) {
						KillVoteYes(player);
					}
				} else if (args[1].equals("no")) {
					if (TimeVoteCommand.inProgress.containsKey(player
							.getWorld())) {
						TimeVoteNo(player);
					}
					if (WeatherVoteCommand.inProgress.containsKey(player
							.getWorld())) {
						WeatherVoteNo(player);
					}
					if (KillVoteCommand.inProgress.containsKey(player
							.getWorld())) {
						KillVoteNo(player);
					}
				} else if (args[1].equals("cancel")) {
					if (FPPermissions.has(player, "featurepack.vote.cancel")) {
						return cancelVote(player, args);
					}
				} else {
					player.sendMessage(FPSettings.incorrectusage);
				}
			} else if (args.length == 1) {
				return getCurrent(player);
			} else {
				player.sendMessage(FPSettings.incorrectusage);
			}
		} else {
			player.sendMessage(FPSettings.nopermission);
		}
		return true;
	}

	public static void TimeVoteYes(Player player) {
		if (FPPermissions.has(player, "featurepack.time.vote")) {
			if (TimeVoteCommand.inProgress.get(player.getWorld())) {
				if (TimeVoteCommand.Yes.containsKey(player)) {
					player.sendMessage(FPSettings.alreadyvotedtime.replace(
							"@t", TimeVoteCommand.Type.get(player.getWorld())));
				} else if (TimeVoteCommand.No.containsKey(player)) {
					TimeVoteCommand.No.remove(player);
					TimeVoteCommand.Yes.put(player, player.getWorld());
					player.sendMessage(FPSettings.adjustvotenoyes);
				} else {
					TimeVoteCommand.Yes.put(player, player.getWorld());
					player.sendMessage(FPSettings.timevotesyes.replace("@t",
							TimeVoteCommand.Type.get(player.getWorld())));
				}
			} else {
				player.sendMessage(FPSettings.timecooldown);
			}
		}
	}

	public static void TimeVoteNo(Player player) {
		if (FPPermissions.has(player, "featurepack.time.vote")) {
			if (TimeVoteCommand.inProgress.get(player.getWorld())) {
				if (TimeVoteCommand.No.containsKey(player)) {
					player.sendMessage(FPSettings.alreadyvotedtime.replace(
							"@t", TimeVoteCommand.Type.get(player.getWorld())));
				} else if (TimeVoteCommand.Yes.containsKey(player)) {
					TimeVoteCommand.Yes.remove(player);
					TimeVoteCommand.No.put(player, player.getWorld());
					player.sendMessage(FPSettings.adjustvoteyesno);
				} else {
					TimeVoteCommand.No.put(player, player.getWorld());
					player.sendMessage(FPSettings.timevotesno.replace("@t",
							TimeVoteCommand.Type.get(player.getWorld())));
				}
			} else {
				player.sendMessage(FPSettings.timecooldown);
			}
		}
	}

	public static void WeatherVoteYes(Player player) {
		if (FPPermissions.has(player, "featurepack.weather.vote")) {
			if (WeatherVoteCommand.inProgress.get(player.getWorld())) {
				if (WeatherVoteCommand.Yes.containsKey(player)) {
					player.sendMessage(FPSettings.alreadyvotedweather.replace(
							"@t",
							WeatherVoteCommand.Type.get(player.getWorld())));
				} else if (WeatherVoteCommand.No.containsKey(player)) {
					WeatherVoteCommand.No.remove(player);
					WeatherVoteCommand.Yes.put(player, player.getWorld());
					player.sendMessage(FPSettings.adjustvotenoyes);
				} else {
					WeatherVoteCommand.Yes.put(player, player.getWorld());
					player.sendMessage(FPSettings.weathervotesyes.replace("@t",
							WeatherVoteCommand.Type.get(player.getWorld())));
				}
			} else {
				player.sendMessage(FPSettings.weathercooldown);
			}
		}
	}

	public static void WeatherVoteNo(Player player) {
		if (FPPermissions.has(player, "featurepack.weather.vote")) {
			if (WeatherVoteCommand.inProgress.get(player.getWorld())) {
				if (WeatherVoteCommand.No.containsKey(player)) {
					player.sendMessage(FPSettings.alreadyvotedweather.replace(
							"@t",
							WeatherVoteCommand.Type.get(player.getWorld())));
				} else if (WeatherVoteCommand.Yes.containsKey(player)) {
					WeatherVoteCommand.Yes.remove(player);
					WeatherVoteCommand.No.put(player, player.getWorld());
					player.sendMessage(FPSettings.adjustvoteyesno);
				} else {
					WeatherVoteCommand.No.put(player, player.getWorld());
					player.sendMessage(FPSettings.weathervotesno.replace("@t",
							WeatherVoteCommand.Type.get(player.getWorld())));
				}
			} else {
				player.sendMessage(FPSettings.weathercooldown);
			}
		}
	}

	public static void KillVoteYes(Player player) {
		if (FPPermissions.has(player, "featurepack.kill.vote")) {
			if (KillVoteCommand.inProgress.get(player.getWorld())) {
				if (KillVoteCommand.Yes.containsKey(player)) {
					player.sendMessage(FPSettings.alreadyvotedkill.replace(
							"@t", KillVoteCommand.Type.get(player.getWorld())));
				} else if (KillVoteCommand.No.containsKey(player)) {
					KillVoteCommand.No.remove(player);
					KillVoteCommand.Yes.put(player, player.getWorld());
					player.sendMessage(FPSettings.adjustvotenoyes);
				} else {
					KillVoteCommand.Yes.put(player, player.getWorld());
					player.sendMessage(FPSettings.killvotesyes.replace("@t",
							KillVoteCommand.Type.get(player.getWorld())));
				}
			} else {
				player.sendMessage(FPSettings.killcooldown);
			}
		}
	}

	public static void KillVoteNo(Player player) {
		if (FPPermissions.has(player, "featurepack.kill.vote")) {
			if (KillVoteCommand.inProgress.get(player.getWorld())) {
				if (KillVoteCommand.No.containsKey(player)) {
					player.sendMessage(FPSettings.alreadyvotedkill.replace(
							"@t", KillVoteCommand.Type.get(player.getWorld())));
				} else if (KillVoteCommand.Yes.containsKey(player)) {
					KillVoteCommand.Yes.remove(player);
					KillVoteCommand.No.put(player, player.getWorld());
					player.sendMessage(FPSettings.adjustvoteyesno);
				} else {
					KillVoteCommand.No.put(player, player.getWorld());
					player.sendMessage(FPSettings.killvotesno.replace("@t",
							KillVoteCommand.Type.get(player.getWorld())));
				}
			} else {
				player.sendMessage(FPSettings.killcooldown);
			}
		}
	}

	public boolean getCurrent(Player player) {
		if (FPPermissions.has(player, "featurepack.time.vote")) {
			if (TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
				int yes = 0;
				int no = 0;
				for (Entry<Player, World> e : TimeVoteCommand.Yes.entrySet()) {
					if (e.getValue().equals(player.getWorld())) {
						yes++;
					}
				}
				for (Entry<Player, World> e : TimeVoteCommand.No.entrySet()) {
					if (e.getValue().equals(player.getWorld())) {
						no++;
					}
				}
				player.sendMessage("Time Vote in progress, "
						+ String.valueOf(yes) + " Yes and "
						+ String.valueOf(no) + " No votes.");
			}
		}
		if (FPPermissions.has(player, "featurepack.weather.vote")) {
			if (WeatherVoteCommand.inProgress.containsKey(player.getWorld())) {
				int yes = 0;
				int no = 0;
				for (Entry<Player, World> e : WeatherVoteCommand.Yes.entrySet()) {
					if (e.getValue().equals(player.getWorld())) {
						yes++;
					}
				}
				for (Entry<Player, World> e : WeatherVoteCommand.No.entrySet()) {
					if (e.getValue().equals(player.getWorld())) {
						no++;
					}
				}
				player.sendMessage("Weather Vote in progress, "
						+ String.valueOf(yes) + " Yes and "
						+ String.valueOf(no) + " No votes.");
			}
		}
		if (FPPermissions.has(player, "featurepack.kill.vote")) {
			if (KillVoteCommand.inProgress.containsKey(player.getWorld())) {
				int yes = 0;
				int no = 0;
				for (Entry<Player, World> e : KillVoteCommand.Yes.entrySet()) {
					if (e.getValue().equals(player.getWorld())) {
						yes++;
					}
				}
				for (Entry<Player, World> e : KillVoteCommand.No.entrySet()) {
					if (e.getValue().equals(player.getWorld())) {
						no++;
					}
				}
				player.sendMessage("Kill Vote in progress, "
						+ String.valueOf(yes) + " Yes and "
						+ String.valueOf(no) + " No votes.");
			}
		}
		return true;
	}

	public static boolean cancelVote(Player player, String[] args) {
		if (args.length == 3) {
			if (args[2].equals("time")) {
				if (TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
					cancelTime(player);
				}
			} else if (args[2].equals("weather")) {
				if (WeatherVoteCommand.inProgress
						.containsKey(player.getWorld())) {
					cancelWeather(player);
				}
			} else if (args[2].equals("kill")) {
				if (KillVoteCommand.inProgress.containsKey(player.getWorld())) {
					cancelKill(player);
				}
			}
		} else {
			if (TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
				cancelTime(player);
			}
			if (WeatherVoteCommand.inProgress.containsKey(player.getWorld())) {
				cancelWeather(player);
			}
			if (KillVoteCommand.inProgress.containsKey(player.getWorld())) {
				cancelKill(player);
			}
		}
		return true;
	}

	public static boolean cancelTime(Player player) {
		ArrayList<String[]> Remove = new ArrayList<String[]>();
		int id = TimeVoteCommand.ID.get(player.getWorld());
		String type = TimeVoteCommand.Type.get(player.getWorld());
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
		for (Player p : player.getWorld().getPlayers()) {
			p.sendMessage(FPSettings.VoteCancelled.replace("@t", type));
		}
		return true;
	}

	public static boolean cancelWeather(Player player) {
		ArrayList<String[]> Remove = new ArrayList<String[]>();
		int id = WeatherVoteCommand.ID.get(player.getWorld());
		String type = WeatherVoteCommand.Type.get(player.getWorld());
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
		for (Player p : player.getWorld().getPlayers()) {
			p.sendMessage(FPSettings.VoteCancelled.replace("@t", type));
		}
		return true;
	}

	public static boolean cancelKill(Player player) {
		ArrayList<String[]> Remove = new ArrayList<String[]>();
		int id = KillVoteCommand.ID.get(player.getWorld());
		String type = KillVoteCommand.Type.get(player.getWorld());
		parent.getServer().getScheduler().cancelTask(id);
		for (Entry<Player, World> e : KillVoteCommand.Yes.entrySet()) {
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
				KillVoteCommand.Yes.remove(p);
			} else if (s[1].equals("NO")) {
				Player p = parent.getServer().getPlayer(s[0]);
				KillVoteCommand.No.remove(p);
			}
		}
		KillVoteCommand.inProgress.remove(player.getWorld());
		KillVoteCommand.ID.remove(player.getWorld());
		for (Player p : player.getWorld().getPlayers()) {
			p.sendMessage(FPSettings.VoteCancelled.replace("@t", type));
		}
		return true;
	}
}