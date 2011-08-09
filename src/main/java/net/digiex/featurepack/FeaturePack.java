package net.digiex.featurepack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.digiex.featurepack.command.LockCommand;
import net.digiex.featurepack.command.LottoCommand;
import net.digiex.featurepack.payment.Method;
import net.digiex.featurepack.task.TimeLockTask;
import net.digiex.featurepack.task.TipSetTask;
import net.digiex.featurepack.task.WeatherLockTask;
import net.digiex.featurepack.thread.FPIPv6;

public class FeaturePack extends JavaPlugin {
	private Random rng;
	private Thread thread;
	public Method Method = null;
	private ArrayList<TipSetTask> tipSets;

	private FPUtils Utils = new FPUtils();
	public FPHelp Help = new FPHelp(this);
	public FPCommand Command = new FPCommand(this);
	public FPEconomy Economy = new FPEconomy(this);
	public FPSettings Settings = new FPSettings(this);

	public static final FPLogging log = new FPLogging();
	public static Queue<Player> IPv6 = new LinkedList<Player>();

	private File dataDirectory = new File("plugins" + File.separator
			+ "FeaturePack" + File.separator);

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
		if (FPSettings.IPv6) {
			try {
				thread.interrupt();
				thread.join();
				log.debug("IPv6 thread successfully joined.");
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		log.info("Plugin disabled. version " + getDescription().getVersion());
		getServer().getPluginManager().disablePlugin(this);
	}

	@Override
	public void onEnable() {
		this.rng = new Random(System.currentTimeMillis());
		dataDirectory.mkdirs();
		createFile(Utils.config());
		createFile(Utils.messages());
		Settings.load();
		if (FPSettings.Overloaded) {
			log.setFilter();
		}
		if (FPSettings.DisablePlugin) {
			getServer().getPluginManager().disablePlugin(this);
		} else {
			FPSpout.load(this);
			FPPermissions.load(this);
			FPListeners.load(this);
			Economy.load();
			Command.addCommands();
			Help.load();
			if (FPSettings.TipsEnabled) {
				loadTips();
			}
			loadSaved();
			if (FPSettings.IPv6) {
				thread = new Thread(new FPIPv6(this), "FeaturePack IPv6 Thread");
				thread.start();
				log.debug("IPv6 thread initiated");
			}
			log.info("Plugin enabled! version " + getDescription().getVersion());
		}
	}

	public void createFile(File file) {
		if (!file.exists()) {
			log.info(file.getName() + " not found, creating new...");
			try {
				Utils.copy(
						getClassLoader().getResourceAsStream(file.getName()),
						file);
			} catch (IOException ex) {
				log.severe("Unable to create " + file.getName() + ".");
			}
		}
	}

	public void loadSaved() {
		if (Utils.savedsettings().exists()) {
			if (Method != null && FPSettings.LotteryEnabled) {
				int code = -1;
				int amount = -1;
				code = Utils.get(Utils.savedsettings()).getInt("Lotto.Code",
						code);
				amount = Utils.get(Utils.savedsettings()).getInt(
						"Lotto.Amount", amount);
				if (code != -1 && amount != -1) {
					LottoCommand.code = code;
					LottoCommand.amount = amount;
				} else {
					Random randomGenerator = new Random();
					int newRandom = randomGenerator.nextInt(1000);
					LottoCommand.code = newRandom;
					LottoCommand.amount = FPSettings.LotteryStart;
				}
			}
			loadTimeLocks();
			loadWeatherLocks();
		} else {
			if (Method != null && FPSettings.LotteryEnabled) {
				Random randomGenerator = new Random();
				int newRandom = randomGenerator.nextInt(1000);
				LottoCommand.code = newRandom;
				LottoCommand.amount = FPSettings.LotteryStart;
			}
		}
	}

	public void loadTimeLocks() {
		for (World world : getServer().getWorlds()) {
			int time = -1;
			time = Utils.get(Utils.savedsettings()).getInt(
					"TimeLocked." + world.getName(), time);
			if (time != -1) {
				log.info("Loading time lock settings for " + world.getName());
				int id = getServer().getScheduler().scheduleSyncRepeatingTask(
						this, new TimeLockTask(world, time), 20L,
						FPSettings.LockedTime * 60 * 20);
				LockCommand.TimeLocked.put(world, id);
			}
		}
	}

	public void loadWeatherLocks() {
		for (World world : getServer().getWorlds()) {
			String weather = null;
			weather = Utils.get(Utils.savedsettings()).getString(
					"WeatherLocked." + world.getName());
			if (weather != null) {
				log.info("Loading weather lock settings for " + world.getName());
				int id = getServer().getScheduler().scheduleSyncRepeatingTask(
						this, new WeatherLockTask(world, weather), 20L,
						FPSettings.LockedWeather * 60 * 20);
				LockCommand.WeatherLocked.put(world, id);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<TipSetTask> tipSetsForWorld(World w) {
		try {
			List tipList = getConfiguration().getList(
					w == null ? "FeaturePack.Tips.Global" : "FeaturePack.Tips."
							+ w.getName());
			if (tipList == null)
				return null;
			List tipSets = new ArrayList(tipList.size());

			for (Iterator i = tipList.iterator(); i.hasNext();) {
				HashMap t = (HashMap) i.next();
				int period = ((Number) t.get("period")).intValue();
				int delay = t.containsKey("delay") ? ((Number) t.get("delay"))
						.intValue() : period;
				boolean isRandom = (!t.containsKey("random"))
						|| (((Boolean) t.get("random")).booleanValue());
				tipSets.add(new TipSetTask(getServer(), w, delay, period,
						(List) t.get("tips"), isRandom ? this.rng.nextLong()
								: 0L));
			}
			return tipSets;
		} catch (Exception e) {
			FeaturePack.log
					.severe("FeaturePack Tips is not configured correctly, please fix or disable them");
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void loadTips() {
		this.tipSets = new ArrayList();

		getConfiguration().load();

		List worldTips = tipSetsForWorld(null);
		if (worldTips != null)
			this.tipSets.addAll(worldTips);

		for (Iterator i = getServer().getWorlds().iterator(); i.hasNext();) {
			World w = (World) i.next();
			worldTips = tipSetsForWorld(w);
			if (worldTips != null)
				this.tipSets.addAll(worldTips);
		}

		for (Iterator i = this.tipSets.iterator(); i.hasNext();) {
			TipSetTask tipSet = (TipSetTask) i.next();
			getServer().getScheduler().scheduleSyncRepeatingTask(this, tipSet,
					tipSet.getDelay() * 60 * 20, tipSet.getPeriod() * 60 * 20);
		}
	}
}