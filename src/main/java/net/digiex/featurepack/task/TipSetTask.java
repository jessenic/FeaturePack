package net.digiex.featurepack.task;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TipSetTask implements Runnable {
	private final Server server;
	private final World world;
	private final int delay;
	private final int period;
	private final String[] tips;
	private final Random rng;
	private int nextTip;

	public TipSetTask(Server server, World world, int delay, int period,
			List<String> tips, long seed) {
		this.server = server;
		this.world = world;
		this.delay = delay;
		this.period = period;
		this.tips = (tips.toArray(new String[0]));
		this.rng = (seed == 0L ? null : new Random(seed));
		this.nextTip = -1;

		for (int i = 0; i < this.tips.length; i++)
			this.tips[i] = formatTip(this.tips[i]);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void run() {
		if (this.tips.length == 0)
			return;
		if (this.tips.length == 1)
			this.nextTip = 0;
		else if (this.rng != null)
			this.nextTip = this.rng.nextInt(this.tips.length);
		else {
			this.nextTip = ((this.nextTip + 1) % this.tips.length);
		}
		String tip = this.tips[this.nextTip];

		if (getWorld() == null) {
			getServer().broadcastMessage(tip);
		} else {
			Iterator i = getWorld().getPlayers().iterator();
			while (i.hasNext())
				((Player) i.next()).sendMessage(tip);
		}
	}

	private String formatTip(String tip) {
		return trimColor(tip);
	}

	public Server getServer() {
		return this.server;
	}

	public World getWorld() {
		return this.world;
	}

	public int getDelay() {
		return this.delay;
	}

	public int getPeriod() {
		return this.period;
	}

	public String[] getTips() {
		return this.tips;
	}

	public static String trimColor(String from) {
		return from.replace("$", "\u00A7");
	}
}