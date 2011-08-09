package net.digiex.featurepack.command;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FPUtils;
import net.digiex.featurepack.FeaturePack;
import net.digiex.featurepack.task.TimeLockTask;
import net.digiex.featurepack.task.WeatherLockTask;

public class LockCommand implements CommandExecutor {
	private FeaturePack parent;

	private FPUtils utils = new FPUtils();

	public static HashMap<World, Integer> TimeLocked = new HashMap<World, Integer>();
	public static HashMap<World, Integer> WeatherLocked = new HashMap<World, Integer>();

	public LockCommand(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (FPPermissions.has(player, "featurepack.lock")) {
			if (args.length == 2) {
				if (args[1].equalsIgnoreCase("?")) {
					player.sendMessage(ChatColor.AQUA + "Help: /fp lock");
					player.sendMessage(ChatColor.YELLOW
							+ "Optional: weather, time");
					player.sendMessage(ChatColor.GREEN
							+ "Example usage: /fp lock time " + ChatColor.AQUA
							+ "(Locks time for the current world)");
				} else if (args[1].equalsIgnoreCase("weather")) {
					createWeatherLock(player);
				} else if (args[1].equalsIgnoreCase("time")) {
					createTimeLock(player);
				}
			} else if (args.length == 1) {
				createTimeLock(player);
				createWeatherLock(player);
			} else {
				player.sendMessage(FPSettings.incorrectusage);
			}
		} else {
			player.sendMessage(FPSettings.nopermission);
		}
		return true;
	}

	public static String checkWeather(Player player) {
		String type;
		if (player.getWorld().isThundering()) {
			type = "thundering";
		} else if (player.getWorld().hasStorm()) {
			type = "raining";
		} else {
			type = "sunny";
		}
		return type;
	}

	public void createTimeLock(Player player) {
		World world = player.getWorld();
		if (!TimeLocked.containsKey(world)) {
			long time = player.getWorld().getFullTime();
			int id = parent
					.getServer()
					.getScheduler()
					.scheduleSyncRepeatingTask(parent,
							new TimeLockTask(world, time), 20L,
							FPSettings.LockedTime * 60 * 20);
			TimeLocked.put(world, id);
			utils.saveTimeLock(world, time);
			player.sendMessage(FPSettings.timelocked.replace("@w",
					world.getName()));
		} else {
			player.sendMessage(FPSettings.timealreadylocked.replace("@w",
					world.getName()));
		}
	}

	public void createWeatherLock(Player player) {
		World world = player.getWorld();
		if (!WeatherLocked.containsKey(world)) {
			String weather = checkWeather(player);
			int id = parent
					.getServer()
					.getScheduler()
					.scheduleSyncRepeatingTask(parent,
							new WeatherLockTask(world, weather), 20L,
							FPSettings.LockedWeather * 60 * 20);
			WeatherLocked.put(player.getWorld(), id);
			utils.saveWeatherLock(world, weather);
			player.sendMessage(FPSettings.weatherlocked.replace("@w",
					world.getName()));
		} else {
			player.sendMessage(FPSettings.weatheralreadylocked.replace("@w",
					world.getName()));
		}
	}
}