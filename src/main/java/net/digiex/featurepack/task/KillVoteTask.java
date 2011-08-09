package net.digiex.featurepack.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FPSpout;
import net.digiex.featurepack.FeaturePack;
import net.digiex.featurepack.command.KillVoteCommand;
import net.digiex.featurepack.payment.Method.MethodAccount;

public class KillVoteTask implements Runnable {
	private FeaturePack parent;
	private World world;
	private int id;
	private String type;

	private int VotesYes;
	private int VotesNo;

	private ArrayList<String[]> Remove = new ArrayList<String[]>();

	public KillVoteTask(FeaturePack parent, World world) {
		this.parent = parent;
		this.world = world;
	}

	@Override
	public void run() {
		for (Entry<Player, World> e : KillVoteCommand.Yes.entrySet()) {
			if (e.getValue().equals(world)) {
				VotesYes++;
				Player player = parent.getServer().getPlayer(
						e.getKey().getName());
				giveMoney(player);
				Remove.add(new String[] { player.getName(), "YES" });
			}
		}
		for (Entry<Player, World> e : KillVoteCommand.No.entrySet()) {
			if (e.getValue().equals(world)) {
				VotesNo++;
				Player player = parent.getServer().getPlayer(
						e.getKey().getName());
				giveMoney(player);
				Remove.add(new String[] { player.getName(), "NO" });
			}
		}
		type = KillVoteCommand.Type.get(world);
		id = KillVoteCommand.ID.get(world);
		if (VotesYes >= FPSettings.KillVotesRequired
				|| VotesNo >= FPSettings.KillVotesRequired) {
			if (VotesYes > VotesNo) {
				Cancel(FPSettings.killvotepassed.replace("@t", type));
				if (type.equals("Animals")) {
					List<LivingEntity> mobs = world.getLivingEntities();
					for (LivingEntity m : mobs) {
						if (isAnimal(m)) {
							if (!isWolf(m)) {
								m.remove();
							}
						}
					}
					Sleep();
				} else if (type.equals("Monsters")) {
					List<LivingEntity> mobs = world.getLivingEntities();
					for (LivingEntity m : mobs) {
						if (isMonster(m)) {
							m.remove();
						}
					}
					Sleep();
				} else if (type.equals("Animals and Monsters")) {
					List<LivingEntity> mobs = world.getLivingEntities();
					for (LivingEntity m : mobs) {
						if (isMonster(m) || isAnimal(m)) {
							if (!isWolf(m)) {
								m.remove();
							}
						}
					}
					Sleep();
				}
			} else if (VotesYes == VotesNo && VotesYes > 0 && VotesNo > 0) {
				Cancel(FPSettings.killvotetied.replace("@t", type));
				Sleep();
			} else if (VotesYes < VotesNo) {
				Cancel(FPSettings.killvotefailed.replace("@t", type));
				Sleep();
			}
		} else {
			Cancel(FPSettings.killvotefailed.replace("@t", type));
			Sleep();
		}
	}

	private void Cancel(String msg) {
		for (Player p : world.getPlayers()) {
			p.sendMessage(msg);
		}
	}

	private void Sleep() {
		try {
			KillVoteCommand.inProgress.remove(world);
			KillVoteCommand.inProgress.put(world, false);
			Thread.sleep(FPSettings.KillCooldown * 60 * 1000);
		} catch (InterruptedException e) {
			this.parent.getServer().getScheduler().cancelTask(id);
		}
		Stop();
	}

	private void Stop() {
		for (String[] s : Remove) {
			if (s[1].equals("YES")) {
				Player p = parent.getServer().getPlayer(s[0]);
				KillVoteCommand.Yes.remove(p);
			} else if (s[1].equals("NO")) {
				Player p = parent.getServer().getPlayer(s[0]);
				KillVoteCommand.No.remove(p);
			}
		}
		KillVoteCommand.inProgress.remove(world);
		KillVoteCommand.ID.remove(world);
		for (Player player : world.getPlayers()) {
			player.sendMessage(FPSettings.killhascooleddown);
		}
		this.parent.getServer().getScheduler().cancelTask(id);
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

	private void giveMoney(Player player) {
		if (this.parent.Method != null) {
			if (FPSettings.VotingMoney > 0) {
				MethodAccount account = this.parent.Method.getAccount(player
						.getName());
				account.add(FPSettings.VotingMoney);
				if (FPSpout.plugin != null && FPSpout.hasSpout(player)) {
					FPSpout.sendNotification(
							"Thanks For Voting",
							FPSettings.VotingMoneyContrib.replace("@a",
									String.valueOf(FPSettings.VotingMoney)),
							Material.WATCH, player);
				} else {
					player.sendMessage(FPSettings.VotingMoneyMessage.replace(
							"@a", String.valueOf(FPSettings.VotingMoney)));
				}
			}
		}
	}
}