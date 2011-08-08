package net.digiex.FeaturePack.thread;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import net.digiex.FeaturePack.FPPermissions;
import net.digiex.FeaturePack.FPPermissions.HandlerType;
import net.digiex.FeaturePack.FPSettings;
import net.digiex.FeaturePack.FeaturePack;

public class FPIPv6 implements Runnable {
	public boolean interrupted = false;
	private FeaturePack parent;

	public FPIPv6(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public void run() {
		while (true) {
			Player player = FeaturePack.IPv6.poll();
			if (player != null) {
				if (player.getAddress().getAddress().getHostAddress()
						.contains(":")) {
					giveIPv6(player);
				} else {
					takeIPv6(player);
				}
			}
			if (Thread.interrupted() || interrupted) {
				break;
			}

		}
	}

	public void giveIPv6(Player player) {
		player.sendMessage(FPSettings.hasipv6);
		FeaturePack.log.info(player.getDisplayName() + " has IPv6!");
		if (FPPermissions.handlerType.equals(HandlerType.GROUPMANAGER)) {
			Plugin p = parent.getServer().getPluginManager()
					.getPlugin("GroupManager");
			GroupManager gm = (GroupManager) p;
			OverloadedWorldHolder perm = gm.getWorldsHolder().getWorldData(
					player);
			Group group = perm.getGroup("IPv6");
			if (group == null) {
				group = perm.createGroup("IPv6");
			}
			if (!perm.getUser(player.getName()).containsSubGroup(group)) {
				perm.getUser(player.getName()).addSubGroup(group);
			}
		} else if (FPPermissions.handlerType.equals(HandlerType.PERMISSIONS)) {
			Plugin perm_plugin = parent.getServer().getPluginManager()
					.getPlugin("Permissions");
			PermissionHandler p = ((Permissions) perm_plugin).getHandler();
			for (World world : parent.getServer().getWorlds()) {
				for (Object permission : FPSettings.IPv6Permissions) {
					p.addUserPermission(world.getName(), player.getName(),
							permission.toString());
				}
				p.addUserPermission(world.getName(), player.getName(),
						"featurepack.ipv6.automated");
				p.reload();
			}
		}
	}

	public void takeIPv6(Player player) {
		if (FPPermissions.handlerType.equals(HandlerType.GROUPMANAGER)) {
			Plugin p = parent.getServer().getPluginManager()
					.getPlugin("GroupManager");
			GroupManager gm = (GroupManager) p;
			OverloadedWorldHolder perm = gm.getWorldsHolder().getWorldData(
					player);
			Group group = perm.getGroup("IPv6");
			if (group == null) {
				group = perm.createGroup("IPv6");
			}
			if (perm.getUser(player.getName()).containsSubGroup(group)) {
				perm.getUser(player.getName()).removeSubGroup(group);
			}
		} else if (FPPermissions.handlerType.equals(HandlerType.PERMISSIONS)) {
			Plugin perm_plugin = parent.getServer().getPluginManager()
					.getPlugin("Permissions");
			PermissionHandler p = ((Permissions) perm_plugin).getHandler();
			if (FPPermissions.has(player, "featurepack.ipv6.automated")) {
				for (World world : parent.getServer().getWorlds()) {
					for (Object permission : FPSettings.IPv6Permissions) {
						p.removeUserPermission(world.getName(),
								player.getName(), permission.toString());
					}
					p.removeUserPermission(world.getName(), player.getName(),
							"featurepack.ipv6.automated");
					p.reload();
				}
			}
		}
	}
}