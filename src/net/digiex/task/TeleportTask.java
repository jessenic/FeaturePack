package net.digiex.FeaturePack.task;

import org.bukkit.entity.Player;

import net.digiex.FeaturePack.FeaturePack;
import net.digiex.FeaturePack.command.GodCommand;

public class TeleportTask implements Runnable {
	private Player player;
	private FeaturePack parent;

	public TeleportTask(FeaturePack parent, Player player) {
		this.parent = parent;
		this.player = player;
	}

	@Override
	public void run() {
		if (GodCommand.tempGods.containsKey(player.getEntityId())) {
			int id = GodCommand.tempGods.get(player.getEntityId());
			GodCommand.tempGods.remove(player.getEntityId());
			parent.getServer().getScheduler().cancelTask(id);
		}
	}
}