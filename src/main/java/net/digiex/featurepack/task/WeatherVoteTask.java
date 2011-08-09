package net.digiex.featurepack.task;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FPSpout;
import net.digiex.featurepack.FeaturePack;
import net.digiex.featurepack.command.WeatherVoteCommand;
import net.digiex.featurepack.payment.Method.MethodAccount;

public class WeatherVoteTask implements Runnable {
	private FeaturePack parent;
	private World world;
	private String type;
	private int id;

	private int VotesYes;
	private int VotesNo;

	private ArrayList<String[]> Remove = new ArrayList<String[]>();

	public WeatherVoteTask(FeaturePack parent, World world) {
		this.parent = parent;
		this.world = world;
	}

	@Override
	public void run() {
		for (Entry<Player, World> e : WeatherVoteCommand.Yes.entrySet()) {
			if (e.getValue().equals(world)) {
				VotesYes++;
				Player player = parent.getServer().getPlayer(
						e.getKey().getName());
				giveMoney(player);
				Remove.add(new String[] { player.getName(), "YES" });
			}
		}
		for (Entry<Player, World> e : WeatherVoteCommand.No.entrySet()) {
			if (e.getValue().equals(world)) {
				VotesNo++;
				Player player = parent.getServer().getPlayer(
						e.getKey().getName());
				giveMoney(player);
				Remove.add(new String[] { player.getName(), "NO" });
			}
		}
		type = WeatherVoteCommand.Type.get(world);
		id = WeatherVoteCommand.ID.get(world);
		if (VotesYes >= FPSettings.WeatherVotesRequired
				|| VotesNo >= FPSettings.WeatherVotesRequired) {
			if (VotesYes > VotesNo) {
				Cancel(FPSettings.weathervotepassed.replace("@t", type));
				if (type.equals("Sun")) {
					world.setStorm(false);
					world.setThundering(false);
					Sleep();
				} else if (type.equals("Rain")) {
					world.setStorm(true);
					world.setThundering(false);
					Sleep();
				} else if (type.equals("Thunder")) {
					world.setStorm(true);
					world.setThundering(true);
					Sleep();
				}
			} else if (VotesYes == VotesNo && VotesYes > 0 && VotesNo > 0) {
				Cancel(FPSettings.weathervotetied.replace("@t", type));
				Sleep();
			} else if (VotesYes < VotesNo) {
				Cancel(FPSettings.weathervotefailed.replace("@t", type));
				Sleep();
			}
		} else {
			Cancel(FPSettings.weathervotefailed.replace("@t", type));
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
			WeatherVoteCommand.inProgress.remove(world);
			WeatherVoteCommand.inProgress.put(world, false);
			Thread.sleep(FPSettings.WeatherCooldown * 60 * 1000);
		} catch (InterruptedException e) {
			this.parent.getServer().getScheduler().cancelTask(id);
		}
		Stop();
	}

	private void Stop() {
		for (String[] s : Remove) {
			if (s[1].equals("YES")) {
				Player p = parent.getServer().getPlayer(s[0]);
				WeatherVoteCommand.Yes.remove(p);
			} else if (s[1].equals("NO")) {
				Player p = parent.getServer().getPlayer(s[0]);
				WeatherVoteCommand.No.remove(p);
			}
		}
		WeatherVoteCommand.inProgress.remove(world);
		WeatherVoteCommand.ID.remove(world);
		for (Player player : world.getPlayers()) {
			player.sendMessage(FPSettings.weatherhascooleddown);
		}
		this.parent.getServer().getScheduler().cancelTask(id);
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