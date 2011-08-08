package net.digiex.FeaturePack.listener;

import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import net.digiex.FeaturePack.FPPermissions;
import net.digiex.FeaturePack.FPSettings;
import net.digiex.FeaturePack.FPSpout;
import net.digiex.FeaturePack.FeaturePack;
import net.digiex.FeaturePack.command.KillVoteCommand;
import net.digiex.FeaturePack.command.TimeVoteCommand;
import net.digiex.FeaturePack.command.WeatherVoteCommand;

public class FPCustomListener extends InputListener {
	@SuppressWarnings("unused")
	private FeaturePack parent;

	public FPCustomListener(FeaturePack parent) {
		this.parent = parent;
	}

	public void onKeyPressedEvent(KeyPressedEvent event) {
		SpoutPlayer player = event.getPlayer();
		if (event.getKey().equals(Keyboard.KEY_Y)) {
			if (TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
				TimeVoteYes(player);
			}
			if (WeatherVoteCommand.inProgress.containsKey(player.getWorld())) {
				WeatherVoteYes(player);
			}
			if (KillVoteCommand.inProgress.containsKey(player.getWorld())) {
				KillVoteYes(player);
			}
		}
		if (event.getKey().equals(Keyboard.KEY_N)) {
			if (TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
				TimeVoteNo(player);
			}
			if (WeatherVoteCommand.inProgress.containsKey(player.getWorld())) {
				WeatherVoteNo(player);
			}
			if (KillVoteCommand.inProgress.containsKey(player.getWorld())) {
				KillVoteNo(player);
			}
		}
		if (event.getKey().equals(Keyboard.KEY_V)) {
			Votes(player);
		}
	}

	public void TimeVoteYes(SpoutPlayer player) {
		if (FPPermissions.has(player, "featurepack.time.vote")) {
			if (TimeVoteCommand.inProgress.get(player.getWorld())) {
				if (TimeVoteCommand.Yes.containsKey(player)) {
					if (TimeVoteCommand.Yes.get(player) == player.getWorld()) {
						FPSpout.sendNotification(FPSettings.timevotetitle,
								FPSettings.timeyesalreadyvotedmessage,
								Material.WATCH, player);
					}
				} else if (TimeVoteCommand.No.containsKey(player)) {
					if (TimeVoteCommand.No.get(player) == player.getWorld()) {
						TimeVoteCommand.No.remove(player);
						TimeVoteCommand.Yes.put(player, player.getWorld());
						FPSpout.sendNotification(FPSettings.timevotetitle,
								FPSettings.timenoyesmessage, Material.WATCH,
								player);
					}
				} else {
					TimeVoteCommand.Yes.put(player, player.getWorld());
					FPSpout.sendNotification(FPSettings.timevotetitle,
							FPSettings.timeyesmessage, Material.WATCH, player);
				}
			} else {
				FPSpout.sendNotification(FPSettings.timevotetitle,
						FPSettings.timecooldownmessage, Material.WATCH, player);
			}
		}
	}

	public void TimeVoteNo(SpoutPlayer player) {
		if (FPPermissions.has(player, "featurepack.time.vote")) {
			if (TimeVoteCommand.inProgress.get(player.getWorld())) {
				if (TimeVoteCommand.No.containsKey(player)) {
					if (TimeVoteCommand.No.get(player) == player.getWorld()) {
						FPSpout.sendNotification(FPSettings.timevotetitle,
								FPSettings.timenoalreadyvotedmessage,
								Material.WATCH, player);
					}
				} else if (TimeVoteCommand.Yes.containsKey(player)) {
					if (TimeVoteCommand.Yes.get(player) == player.getWorld()) {
						TimeVoteCommand.Yes.remove(player);
						TimeVoteCommand.No.put(player, player.getWorld());
						FPSpout.sendNotification(FPSettings.timevotetitle,
								FPSettings.timeyesnomessage, Material.WATCH,
								player);
					}
				} else {
					TimeVoteCommand.No.put(player, player.getWorld());
					FPSpout.sendNotification(FPSettings.timevotetitle,
							FPSettings.timenomessage, Material.WATCH, player);
				}
			} else {
				FPSpout.sendNotification(FPSettings.timevotetitle,
						FPSettings.timecooldownmessage, Material.WATCH, player);
			}
		}
	}

	public void WeatherVoteYes(SpoutPlayer player) {
		if (FPPermissions.has(player, "featurepack.weather.vote")) {
			if (WeatherVoteCommand.inProgress.get(player.getWorld())) {
				if (WeatherVoteCommand.Yes.containsKey(player)) {
					if (WeatherVoteCommand.Yes.get(player) == player.getWorld()) {
						FPSpout.sendNotification(FPSettings.weathervotetitle,
								FPSettings.weatheryesalreadyvotedmessage,
								Material.WATCH, player);
					}
				} else if (WeatherVoteCommand.No.containsKey(player)) {
					if (WeatherVoteCommand.No.get(player) == player.getWorld()) {
						WeatherVoteCommand.No.remove(player);
						WeatherVoteCommand.Yes.put(player, player.getWorld());
						FPSpout.sendNotification(FPSettings.weathervotetitle,
								FPSettings.weathernoyesmessage, Material.WATCH,
								player);
					}
				} else {
					WeatherVoteCommand.Yes.put(player, player.getWorld());
					FPSpout.sendNotification(FPSettings.weathervotetitle,
							FPSettings.weatheryesmessage, Material.WATCH,
							player);
				}
			} else {
				FPSpout.sendNotification(FPSettings.weathervotetitle,
						FPSettings.weathercooldownmessage, Material.WATCH,
						player);
			}
		}
	}

	public void WeatherVoteNo(SpoutPlayer player) {
		if (FPPermissions.has(player, "featurepack.weather.vote")) {
			if (WeatherVoteCommand.inProgress.get(player.getWorld())) {
				if (WeatherVoteCommand.No.containsKey(player)) {
					if (WeatherVoteCommand.No.get(player) == player.getWorld()) {
						FPSpout.sendNotification(FPSettings.weathervotetitle,
								FPSettings.weathernoalreadyvotedmessage,
								Material.WATCH, player);
					}
				} else if (WeatherVoteCommand.Yes.containsKey(player)) {
					if (WeatherVoteCommand.Yes.get(player) == player.getWorld()) {
						WeatherVoteCommand.Yes.remove(player);
						WeatherVoteCommand.No.put(player, player.getWorld());
						FPSpout.sendNotification(FPSettings.weathervotetitle,
								FPSettings.weatheryesnomessage, Material.WATCH,
								player);
					}
				} else {
					WeatherVoteCommand.No.put(player, player.getWorld());
					FPSpout.sendNotification(FPSettings.weathervotetitle,
							FPSettings.weathernomessage, Material.WATCH, player);
				}
			} else {
				FPSpout.sendNotification(FPSettings.weathervotetitle,
						FPSettings.weathercooldown, Material.WATCH, player);
			}
		}
	}

	public void KillVoteYes(SpoutPlayer player) {
		if (FPPermissions.has(player, "featurepack.kill.vote")) {
			if (KillVoteCommand.inProgress.get(player.getWorld())) {
				if (KillVoteCommand.Yes.containsKey(player)) {
					if (KillVoteCommand.Yes.get(player) == player.getWorld()) {
						FPSpout.sendNotification(FPSettings.killvotetitle,
								FPSettings.killyesalreadyvotedmessage,
								Material.WATCH, player);
					}
				} else if (KillVoteCommand.No.containsKey(player)) {
					if (KillVoteCommand.No.get(player) == player.getWorld()) {
						KillVoteCommand.No.remove(player);
						KillVoteCommand.Yes.put(player, player.getWorld());
						FPSpout.sendNotification(FPSettings.killvotetitle,
								FPSettings.killnoyesmessage, Material.WATCH,
								player);
					}
				} else {
					KillVoteCommand.Yes.put(player, player.getWorld());
					FPSpout.sendNotification(FPSettings.killvotetitle,
							FPSettings.killyesmessage, Material.WATCH, player);
				}
			} else {
				FPSpout.sendNotification(FPSettings.killvotetitle,
						FPSettings.killcooldownmessage, Material.WATCH, player);
			}
		}
	}

	public void KillVoteNo(SpoutPlayer player) {
		if (FPPermissions.has(player, "featurepack.kill.vote")) {
			if (KillVoteCommand.inProgress.get(player.getWorld())) {
				if (KillVoteCommand.No.containsKey(player)) {
					if (KillVoteCommand.No.get(player) == player.getWorld()) {
						FPSpout.sendNotification(FPSettings.killvotetitle,
								FPSettings.killnoalreadyvotedmessage,
								Material.WATCH, player);
					}
				} else if (KillVoteCommand.Yes.containsKey(player)) {
					if (KillVoteCommand.Yes.get(player) == player.getWorld()) {
						KillVoteCommand.Yes.remove(player);
						KillVoteCommand.No.put(player, player.getWorld());
						FPSpout.sendNotification(FPSettings.killvotetitle,
								FPSettings.killyesnomessage, Material.WATCH,
								player);
					}
				} else {
					KillVoteCommand.No.put(player, player.getWorld());
					FPSpout.sendNotification(FPSettings.killvotetitle,
							FPSettings.killnomessage, Material.WATCH, player);
				}
			} else {
				FPSpout.sendNotification(FPSettings.killvotetitle,
						FPSettings.killcooldownmessage, Material.WATCH, player);
			}
		}
	}

	public void Votes(SpoutPlayer player) {
		if (FPPermissions.has(player, "featurepack.time.vote")) {
			if (TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
				if (TimeVoteCommand.inProgress.get(player.getWorld())) {
					int yes = 0;
					int no = 0;
					for (Entry<Player, World> e : TimeVoteCommand.Yes
							.entrySet()) {
						if (e.getValue().equals(player.getWorld())) {
							yes++;
						}
					}
					for (Entry<Player, World> e : TimeVoteCommand.No.entrySet()) {
						if (e.getValue().equals(player.getWorld())) {
							no++;
						}
					}
					FPSpout.sendNotification(
							FPSettings.timevotetitle,
							FPSettings.timevotes.replace("@y",
									String.valueOf(yes)).replace("@n",
									String.valueOf(no)), Material.WATCH, player);
				} else {
					FPSpout.sendNotification(FPSettings.timevotetitle,
							FPSettings.timecooldownmessage, Material.WATCH,
							player);
				}
			}
		}
		if (FPPermissions.has(player, "featurepack.weather.vote")) {
			if (WeatherVoteCommand.inProgress.containsKey(player.getWorld())) {
				if (WeatherVoteCommand.inProgress.get(player.getWorld())) {
					int yes = 0;
					int no = 0;
					for (Entry<Player, World> e : WeatherVoteCommand.Yes
							.entrySet()) {
						if (e.getValue().equals(player.getWorld())) {
							yes++;
						}
					}
					for (Entry<Player, World> e : WeatherVoteCommand.No
							.entrySet()) {
						if (e.getValue().equals(player.getWorld())) {
							no++;
						}
					}
					FPSpout.sendNotification(
							FPSettings.weathervotetitle,
							FPSettings.weathervotes.replace("@y",
									String.valueOf(yes)).replace("@n",
									String.valueOf(no)), Material.WATCH, player);
				} else {
					FPSpout.sendNotification(FPSettings.weathervotetitle,
							FPSettings.weathercooldownmessage, Material.WATCH,
							player);
				}
			}
		}
		if (FPPermissions.has(player, "featurepack.kill.vote")) {
			if (KillVoteCommand.inProgress.containsKey(player.getWorld())) {
				if (KillVoteCommand.inProgress.get(player.getWorld())) {
					int yes = 0;
					int no = 0;
					for (Entry<Player, World> e : KillVoteCommand.Yes
							.entrySet()) {
						if (e.getValue().equals(player.getWorld())) {
							yes++;
						}
					}
					for (Entry<Player, World> e : KillVoteCommand.No.entrySet()) {
						if (e.getValue().equals(player.getWorld())) {
							no++;
						}
					}
					FPSpout.sendNotification(
							FPSettings.killvotetitle,
							FPSettings.killvotes.replace("@y",
									String.valueOf(yes)).replace("@n",
									String.valueOf(no)), Material.WATCH, player);
				} else {
					FPSpout.sendNotification(FPSettings.killvotetitle,
							FPSettings.killcooldownmessage, Material.WATCH,
							player);
				}
			}
		}
	}
}