package net.digiex.featurepack.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FeaturePack;
import net.digiex.featurepack.command.SecureCommand;

public class FPBlockListener extends BlockListener {
	@SuppressWarnings("unused")
	private FeaturePack parent;

	public FPBlockListener(FeaturePack parent) {
		this.parent = parent;
	}

        @Override
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (FPSettings.Secure) {
			if (SecureCommand.Secure.containsKey(player)) {
				event.setCancelled(true);
			}
		}
	}

        @Override
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (FPSettings.Secure) {
			if (SecureCommand.Secure.containsKey(player)) {
				event.setCancelled(true);
			}
		}
	}
}