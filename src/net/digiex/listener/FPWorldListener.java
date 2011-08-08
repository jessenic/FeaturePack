package net.digiex.FeaturePack.listener;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

import net.digiex.FeaturePack.FPUtils;
import net.digiex.FeaturePack.FeaturePack;

public class FPWorldListener extends WorldListener {
	private FeaturePack parent;

	private FPUtils utils = new FPUtils();

	public FPWorldListener(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public void onWorldLoad(WorldLoadEvent event) {
		if (utils.savedsettings().exists()) {
			this.parent.loadTimeLocks();
			this.parent.loadWeatherLocks();
		}
	}
}