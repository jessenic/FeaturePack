package net.digiex.featurepack.listener;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FPUtils;
import net.digiex.featurepack.FeaturePack;
import net.digiex.featurepack.command.GodCommand;
import net.digiex.featurepack.command.KillVoteCommand;
import net.digiex.featurepack.command.RegenCommand;
import net.digiex.featurepack.command.SecureCommand;
import net.digiex.featurepack.command.TimeVoteCommand;
import net.digiex.featurepack.command.TimeVoteCommand.VoteTypes;
import net.digiex.featurepack.command.VoteCommand;
import net.digiex.featurepack.command.WeatherVoteCommand;
import net.digiex.featurepack.task.TeleportTask;

public class FPPlayerListener extends PlayerListener {
	private FeaturePack parent;

	private FPUtils utils = new FPUtils();

	public FPPlayerListener(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (((event.getAction() == Action.LEFT_CLICK_BLOCK) || (event
				.getAction() == Action.LEFT_CLICK_AIR))
				&& (event.hasItem())
				&& (event.getItem().getTypeId() == FPSettings.FireballWand)
				&& (FPPermissions.has(event.getPlayer(),
						"featurepack.wand.fireball") && FPSettings.WandsEnabled)) {
			Vector direction = player.getEyeLocation().getDirection()
					.multiply(2);
			player.getWorld()
					.spawn(player.getEyeLocation().add(direction.getX(),
							direction.getY(), direction.getZ()), Fireball.class);
		}
		if (((event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event
				.getAction() == Action.RIGHT_CLICK_AIR))
				&& (event.hasItem())
				&& (event.getItem().getTypeId() == FPSettings.LightningWand)
				&& (FPPermissions.has(event.getPlayer(),
						"featurepack.wand.lightning") && FPSettings.WandsEnabled)) {
			event.getPlayer()
					.getWorld()
					.strikeLightning(
							event.getPlayer().getTargetBlock(null, 500)
									.getLocation());
		}
		if (((event.getAction() == Action.LEFT_CLICK_BLOCK) || (event
				.getAction() == Action.LEFT_CLICK_AIR))
				&& (event.hasItem())
				&& (event.getItem().getTypeId() == FPSettings.VoteWand)
				&& (FPPermissions.has(event.getPlayer(),
						"featurepack.wand.vote") && FPSettings.WandsEnabled)) {
			if (FPPermissions.has(player, "featurepack.time.vote")) {
				if (TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
					if (TimeVoteCommand.inProgress.get(player.getWorld())) {
						VoteCommand.TimeVoteYes(player);
					} else {
						player.sendMessage(FPSettings.timecooldown);
					}
				}
			}
			if (FPPermissions.has(player, "featurepack.weather.vote")) {
				if (WeatherVoteCommand.inProgress
						.containsKey(player.getWorld())) {
					if (WeatherVoteCommand.inProgress.get(player.getWorld())) {
						VoteCommand.WeatherVoteYes(player);
					} else {
						player.sendMessage(FPSettings.weathercooldown);
					}
				}
			}
			if (FPPermissions.has(player, "featurepack.kill.vote")) {
				if (KillVoteCommand.inProgress.containsKey(player.getWorld())) {
					if (KillVoteCommand.inProgress.get(player.getWorld())) {
						VoteCommand.KillVoteYes(player);
					} else {
						player.sendMessage(FPSettings.killcooldown);
					}
				}
			}
		}
		if (((event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event
				.getAction() == Action.RIGHT_CLICK_AIR))
				&& (event.hasItem())
				&& (event.getItem().getTypeId() == FPSettings.VoteWand)
				&& (FPPermissions.has(event.getPlayer(),
						"featurepack.wand.vote") && FPSettings.WandsEnabled)) {
			if (FPPermissions.has(player, "featurepack.time.vote")) {
				if (TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
					if (TimeVoteCommand.inProgress.get(player.getWorld())) {
						VoteCommand.TimeVoteNo(player);
					} else {
						player.sendMessage(FPSettings.timecooldown);
					}
				}
			}
			if (FPPermissions.has(player, "featurepack.weather.vote")) {
				if (WeatherVoteCommand.inProgress
						.containsKey(player.getWorld())) {
					if (WeatherVoteCommand.inProgress.get(player.getWorld())) {
						VoteCommand.WeatherVoteNo(player);
					} else {
						player.sendMessage(FPSettings.weathercooldown);
					}
				}
			}
			if (FPPermissions.has(player, "featurepack.kill.vote")) {
				if (KillVoteCommand.inProgress.containsKey(player.getWorld())) {
					if (KillVoteCommand.inProgress.get(player.getWorld())) {
						VoteCommand.KillVoteNo(player);
					} else {
						player.sendMessage(FPSettings.killcooldown);
					}
				}
			}
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& FPPermissions.has(event.getPlayer(), "featurepack.sign")
				&& FPSettings.SignEnabled) {
			BlockState state = event.getClickedBlock().getState();
			if (state instanceof Sign) {
				Sign sign = (Sign) state;
				if ((sign.getLines()[0].equalsIgnoreCase("["
						+ FPSettings.SignText + "]"))) {
					if (!(sign.getLines()[1].isEmpty())) {
						parent.getServer().dispatchCommand(
								event.getPlayer(),
								sign.getLines()[1].toString()
										+ sign.getLines()[2].toString()
										+ sign.getLines()[3].toString());
					} else {
						event.getPlayer()
								.sendMessage(FPSettings.incorrectusage);
					}
				}
			}
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK
				&& FPPermissions.has(event.getPlayer(), "featurepack.sign")
				&& FPSettings.SignEnabled) {
			BlockState state = event.getClickedBlock().getState();
			if (state instanceof Sign) {
				Sign sign = (Sign) state;
				if ((sign.getLines()[0].equalsIgnoreCase("["
						+ FPSettings.SignText + "]"))) {
					player.sendMessage(FPSettings.incorrectusage);
				}
			}

		}
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (FPSettings.Secure) {
			if (utils.getProperty(utils.savedsettings(),
					"Secure." + player.getName()) != null) {
				boolean flagged = (Boolean) utils.getProperty(
						utils.savedsettings(), "Secure." + player.getName()
								+ ".flagged");
				String ip = (String) utils.getProperty(utils.savedsettings(),
						"Secure." + player.getName() + ".ip");
				String trueip = player.getAddress().getAddress()
						.getHostAddress();
				if (flagged) {
					SecureCommand.Secure.put(player, true);
					player.sendMessage(FPSettings.flagged);
				} else {
					if (!ip.equalsIgnoreCase(trueip)) {
						utils.setProperty(utils.savedsettings(), "Secure."
								+ player.getName() + ".flagged", true);
						SecureCommand.Secure.put(player, true);
						player.sendMessage(FPSettings.flagged);
					}
				}
			}
		}
		if (TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
			player.sendMessage(FPSettings.timevoteinprogress.replace("@t",
					TimeVoteCommand.Type.get(player.getWorld())));
		}
		if (WeatherVoteCommand.inProgress.containsKey(player.getWorld())) {
			player.sendMessage(FPSettings.weathervoteinprogress.replace("@t",
					WeatherVoteCommand.Type.get(player.getWorld())));
		}
		if (KillVoteCommand.inProgress.containsKey(player.getWorld())) {
			player.sendMessage(FPSettings.killvoteinprogress.replace("@t",
					KillVoteCommand.Type.get(player.getWorld())));
		}
		if (FPSettings.IPv6) {
			FeaturePack.IPv6.offer(player);
		}
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (FPSettings.Regen) {
			if (RegenCommand.Regen.containsKey(player)) {
				RegenCommand.Regen.remove(player);
			}
		}
		if (TimeVoteCommand.Yes.containsKey(player)) {
			TimeVoteCommand.Yes.remove(player);
		}
		if (WeatherVoteCommand.Yes.containsKey(player)) {
			WeatherVoteCommand.Yes.remove(player);
		}
		if (KillVoteCommand.Yes.containsKey(player)) {
			KillVoteCommand.Yes.remove(player);
		}
		if (TimeVoteCommand.No.containsKey(player)) {
			TimeVoteCommand.No.remove(player);
		}
		if (WeatherVoteCommand.No.containsKey(player)) {
			WeatherVoteCommand.No.remove(player);
		}
		if (KillVoteCommand.No.containsKey(player)) {
			KillVoteCommand.No.remove(player);
		}
		if (GodCommand.Gods.containsKey(event.getPlayer().getEntityId())) {
			GodCommand.Gods.remove(event.getPlayer().getEntityId());
		}
		if (SecureCommand.Secure.containsKey(player)) {
			SecureCommand.Secure.remove(player);
		}
	}

	@Override
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		if (FPSettings.Regen) {
			if (RegenCommand.Regen.containsKey(player)) {
				RegenCommand.Regen.remove(player);
			}
		}
		if (FPSettings.GodOnTeleportOrJoin) {
			if (!GodCommand.tempGods.containsKey(player.getEntityId())) {
				int id = parent
						.getServer()
						.getScheduler()
						.scheduleSyncDelayedTask(parent,
								new TeleportTask(parent, player),
								FPSettings.godtime);
				GodCommand.tempGods.put(player.getEntityId(), id);
			}
		}
		if (TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
			if (TimeVoteCommand.Yes.containsKey(player)) {
				World Old = TimeVoteCommand.Yes.get(player);
				World New = player.getWorld();
				if (Old != New) {
					TimeVoteCommand.Yes.remove(player);
					player.sendMessage(FPSettings.timevoteinprogress.replace(
							"@t", TimeVoteCommand.Type.get(player.getWorld())));
				}
			}
			if (TimeVoteCommand.No.containsKey(player)) {
				World Old = TimeVoteCommand.No.get(player);
				World New = player.getWorld();
				if (Old != New) {
					TimeVoteCommand.No.remove(player);
					player.sendMessage(FPSettings.timevoteinprogress.replace(
							"@t", TimeVoteCommand.Type.get(player.getWorld())));
				}
			}
		}
		if (WeatherVoteCommand.inProgress.containsKey(player.getWorld())) {
			if (WeatherVoteCommand.Yes.containsKey(player)) {
				World Old = WeatherVoteCommand.Yes.get(player);
				World New = player.getWorld();
				if (Old != New) {
					WeatherVoteCommand.Yes.remove(player);
					player.sendMessage(FPSettings.weathervoteinprogress
							.replace("@t", WeatherVoteCommand.Type.get(player
									.getWorld())));
				}
			}
			if (WeatherVoteCommand.No.containsKey(player)) {
				World Old = WeatherVoteCommand.No.get(player);
				World New = player.getWorld();
				if (Old != New) {
					WeatherVoteCommand.No.remove(player);
					player.sendMessage(FPSettings.weathervoteinprogress
							.replace("@t", WeatherVoteCommand.Type.get(player
									.getWorld())));
				}
			}
		}
		if (KillVoteCommand.inProgress.containsKey(player.getWorld())) {
			if (KillVoteCommand.Yes.containsKey(player)) {
				World Old = KillVoteCommand.Yes.get(player);
				World New = player.getWorld();
				if (Old != New) {
					KillVoteCommand.Yes.remove(player);
					player.sendMessage(FPSettings.killvoteinprogress.replace(
							"@t", KillVoteCommand.Type.get(player.getWorld())));
				}
			}
			if (KillVoteCommand.No.containsKey(player)) {
				World Old = KillVoteCommand.No.get(player);
				World New = player.getWorld();
				if (Old != New) {
					KillVoteCommand.No.remove(player);
					player.sendMessage(FPSettings.killvoteinprogress.replace(
							"@t", KillVoteCommand.Type.get(player.getWorld())));
				}
			}
		}
		if (SecureCommand.Secure.containsKey(player)) {
			event.setCancelled(true);
		}
	}

	@Override
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Player player = event.getPlayer();
		if (FPSettings.BedVoting) {
			if (FPPermissions.has(event.getPlayer(), "featurepack.bed")) {
				if (!TimeVoteCommand.inProgress.containsKey(player.getWorld())) {
					TimeVoteCommand.createVote(player, VoteTypes.Day);
				} else {
					if (TimeVoteCommand.inProgress.get(event.getPlayer()
							.getWorld()) == false) {
						player.sendMessage(FPSettings.timecooldown);
					} else {
						player.sendMessage(FPSettings.timevoteinprogress
								.replace("@t", TimeVoteCommand.Type.get(player
										.getWorld())));
					}
				}
			}
		}
	}

	@Override
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (FPSettings.BiomeMessages) {
			if (!event.getTo().getBlock().getBiome()
					.equals(event.getFrom().getBlock().getBiome())) {
				event.getPlayer().sendMessage(
						FPSettings.enteredbiome.replace("@b", event.getTo()
								.getBlock().getBiome().name().toLowerCase()
								.replace("_", " ")));
			}
		}
		if (FPSettings.Regen) {
			if (RegenCommand.Regen.containsKey(player)) {
				Chunk chunk = player.getLocation().getBlock().getChunk();
				player.getWorld().regenerateChunk(chunk.getX(), chunk.getZ());
			}
		}
		if (FPSettings.Secure) {
			if (SecureCommand.Secure.containsKey(player)) {
				event.setCancelled(true);
			}
		}
	}

	@Override
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if (FPSettings.Secure) {
			if (SecureCommand.Secure.containsKey(player)) {
				event.setCancelled(true);
			}
		}
	}

	@Override
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (FPSettings.Secure) {
			if (SecureCommand.Secure.containsKey(player)) {
				event.setCancelled(true);
			}
		}
	}
}