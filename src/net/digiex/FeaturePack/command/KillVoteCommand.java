package net.digiex.FeaturePack.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import net.digiex.FeaturePack.FPPermissions;
import net.digiex.FeaturePack.FPSettings;
import net.digiex.FeaturePack.FeaturePack;
import net.digiex.FeaturePack.task.KillVoteTask;

public class KillVoteCommand implements CommandExecutor {
	public FeaturePack parent;
	public static VoteTypes VoteType = VoteTypes.Unknown;

	public final static HashMap<World, Boolean> inProgress = new HashMap<World, Boolean>();
	public final static HashMap<World, String> Type = new HashMap<World, String>();
	public final static HashMap<Player, World> Yes = new HashMap<Player, World>();
	public final static HashMap<Player, World> No = new HashMap<Player, World>();
	public final static HashMap<World, Integer> ID = new HashMap<World, Integer>();

	public KillVoteCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.kill")) {
			if (args.length == 2) {
				if (!inProgress.containsKey(player.getWorld())) {
					if (args[1].equalsIgnoreCase("?")) {
						player.sendMessage(ChatColor.AQUA + "Help: /fp kill");
						player.sendMessage(ChatColor.YELLOW
								+ "Required: animals, monsters, all");
						player.sendMessage(ChatColor.GREEN
								+ "Example usage: /fp kill animals "
								+ ChatColor.AQUA
								+ "(Starts a vote to kill all animals)");
					} else if (args[1].equals("animals")) {
						return createVote(player, VoteTypes.Animals);
					} else if (args[1].equals("monsters")) {
						return createVote(player, VoteTypes.Monsters);
					} else if (args[1].equals("all")) {
						return createVote(player,
								VoteTypes.Animals_and_Monsters);
					} else {
						player.sendMessage(FPSettings.incorrectusage);
					}
				} else {
					if (!inProgress.get(player.getWorld())) {
						if (FPPermissions
								.has(player, "featurepack.kill.bypass")) {
							return bypassVote(player, args);
						} else {
							player.sendMessage(FPSettings.killcooldown);
						}
					} else {
						player.sendMessage(FPSettings.killvoteinprogress
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

	public boolean createVote(Player player, VoteTypes killtype) {
		KillVoteTask task = new KillVoteTask(parent, player.getWorld());
		VoteType = killtype;
		int killID;
		float timeVotesLeft = player.getWorld().getPlayers().size();
		if (timeVotesLeft <= 1) {
			killEntities(player, killtype);
		} else {
			FeaturePack.log.info(player.getDisplayName()
					+ " created a kill vote for killing "
					+ VoteType.toString().replace("_", " "));
			for (Player p : player.getWorld().getPlayers()) {
				p.sendMessage(FPSettings.KillVote.replace("@p",
						player.getName()).replace("@t",
						String.valueOf(killtype).replace("_", " ")));
			}
			killID = parent
					.getServer()
					.getScheduler()
					.scheduleAsyncDelayedTask(parent, task,
							FPSettings.KillVotingTime * 60 * 20);
			inProgress.put(player.getWorld(), true);
			ID.put(player.getWorld(), killID);
			Type.put(player.getWorld(),
					String.valueOf(killtype).replace("_", " "));
			Yes.put(player, player.getWorld());
		}
		return true;
	}

	public boolean bypassVote(Player player, String[] args) {
		cancelVote(player);
		if (args[1].equals("animals")) {
			return createVote(player, VoteTypes.Animals);
		} else if (args[1].equals("monsters")) {
			return createVote(player, VoteTypes.Monsters);
		} else if (args[1].equals("all")) {
			return createVote(player, VoteTypes.Animals_and_Monsters);
		} else {
			player.sendMessage(FPSettings.incorrectusage);
		}
		return true;
	}

	public boolean cancelVote(Player player) {
		ArrayList<String[]> Remove = new ArrayList<String[]>();
		int id = KillVoteCommand.ID.get(player.getWorld());
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
		return true;
	}

	public boolean killEntities(Player player, VoteTypes type) {
		if (type == VoteTypes.Animals) {
			Integer total = 0;
			List<LivingEntity> mobs = player.getWorld().getLivingEntities();
			for (LivingEntity m : mobs) {
				if (!isWolf(m)) {
					m.remove();
					total++;
				}
			}
			player.sendMessage(FPSettings.freekill.replace("@a",
					total.toString()).replace("@t", type.toString()));
			FeaturePack.log.info(player.getDisplayName() + " killed "
					+ type.toString() + ".");
		} else if (type == VoteTypes.Monsters) {
			Integer total = 0;
			List<LivingEntity> mobs = player.getWorld().getLivingEntities();
			for (LivingEntity m : mobs) {
				if (isMonster(m)) {
					m.remove();
					total++;
				}
			}
			player.sendMessage(FPSettings.freekill.replace("@a",
					total.toString()).replace("@t", type.toString()));
			FeaturePack.log.info(player.getDisplayName() + " killed "
					+ type.toString() + ".");
		} else if (type == VoteTypes.Animals_and_Monsters) {
			Integer total = 0;
			List<LivingEntity> mobs = player.getWorld().getLivingEntities();
			for (LivingEntity m : mobs) {
				if (isMonster(m) || isAnimal(m)) {
					if (!isWolf(m)) {
						m.remove();
						total++;
					}
				}
			}
			player.sendMessage(FPSettings.freekill.replace("@a",
					total.toString()).replace("@t",
					type.toString().replace("_", " ")));
			FeaturePack.log.info(player.getDisplayName() + " killed "
					+ type.toString().replace("_", " ") + ".");
		}
		return true;
	}

	public boolean isMonster(LivingEntity e) {
		return (e instanceof Monster);
	}

	public boolean isAnimal(LivingEntity e) {
		return (e instanceof Animals);
	}

	public boolean isWolf(LivingEntity e) {
		return (e instanceof Wolf);
	}

	public static enum VoteTypes {
		Animals, Monsters, Animals_and_Monsters, Unknown
	}
}