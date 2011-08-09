package net.digiex.featurepack.task;

import org.bukkit.World;

public class WeatherLockTask implements Runnable {
	private String type;
	private World world;

	public WeatherLockTask(World world, String type) {
		this.type = type;
		this.world = world;
	}

	@Override
	public void run() {
		if (type == "thundering") {
			world.setStorm(true);
			world.setThundering(true);
		} else if (type == "raining") {
			world.setStorm(true);
			world.setThundering(false);
		} else if (type == "sunny") {
			world.setStorm(false);
			world.setThundering(false);
		}
	}
}