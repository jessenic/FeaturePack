package net.digiex.featurepack;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class FPPermissions {
	private static PermissionHandler Permissions;
	private static GroupManager GroupManager;
	public static HandlerType handlerType = HandlerType.VANILLA;

	public static enum HandlerType {
		PERMISSIONS, GROUPMANAGER, VANILLA
	}

	public static void load(FeaturePack parent) {
		FeaturePack.log.debug("Loading Permissions handler");
		Plugin p = parent.getServer().getPluginManager()
				.getPlugin("Permissions");
		Plugin g = parent.getServer().getPluginManager()
				.getPlugin("GroupManager");
		if (Permissions == null && GroupManager == null) {
			if (g != null) {
				GroupManager = ((GroupManager) g);
				handlerType = HandlerType.GROUPMANAGER;
				FeaturePack.log.info("Using GroupManager version "
						+ g.getDescription().getVersion());
			} else if (p != null) {
				Permissions = ((Permissions) p).getHandler();
				handlerType = HandlerType.PERMISSIONS;
				FeaturePack.log.info("Using Permissions version "
						+ p.getDescription().getVersion());
			} else {
				handlerType = HandlerType.VANILLA;
			}
		}
	}

	public static boolean has(Player player, String permission) {
		switch (handlerType) {
		case PERMISSIONS:
			return Permissions.has(player, permission);
		case GROUPMANAGER:
			return GroupManager.getWorldsHolder().getWorldPermissions(player)
					.has(player, permission);
		default:
			if (player.hasPermission("featurepack.*")) {
				return true;
			} else {
				return player.hasPermission(permission);
			}
		}
	}
}