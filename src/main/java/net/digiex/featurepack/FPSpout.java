package net.digiex.featurepack;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class FPSpout {
	public static Plugin plugin;

	public static void load(FeaturePack parent) {
		FeaturePack.log.debug("Loading Spout");
		Plugin p = parent.getServer().getPluginManager().getPlugin("Spout");
		if (p != null) {
			plugin = p;
			FeaturePack.log.info("Using Spout version "
					+ p.getDescription().getVersion());
		} else {
			FeaturePack.log
					.info("Unable to load Spout, server doesn't have plugin");
		}
	}

	public static void sendNotification(String title, String message,
			Material item, Player player) {
		getPlayer(player).sendNotification(title, message, item);
	}

	public static SpoutPlayer getPlayer(Player player) {
		return SpoutManager.getPlayer(player);
	}

	public static boolean hasSpout(Player player) {
		return getPlayer(player).isSpoutCraftEnabled();
	}
}