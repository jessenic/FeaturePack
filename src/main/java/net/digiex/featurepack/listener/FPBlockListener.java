package net.digiex.FeaturePack.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import net.digiex.FeaturePack.FPSettings;
import net.digiex.FeaturePack.FeaturePack;
import net.digiex.FeaturePack.command.SecureCommand;

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