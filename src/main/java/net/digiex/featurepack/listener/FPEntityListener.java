package net.digiex.FeaturePack.listener;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.FallingSand;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Weather;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;

import net.digiex.FeaturePack.FPSettings;
import net.digiex.FeaturePack.FeaturePack;
import net.digiex.FeaturePack.command.GodCommand;

public class FPEntityListener extends EntityListener {
	private FeaturePack parent;

	public FPEntityListener(FeaturePack parent) {
		this.parent = parent;
	}

	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			CraftPlayer player = (CraftPlayer) event.getEntity();
			if (GodCommand.Gods.containsKey(player.getEntityId())
					|| GodCommand.tempGods.containsKey(player.getEntityId())) {
				event.setCancelled(true);
				player.getHandle().fireTicks = 0;
				player.setRemainingAir(player.getMaximumAir());
			}
			if (FPSettings.TeleportEnabled) {
				if (event.getCause().equals(DamageCause.VOID)) {
					double x = player.getLocation().getX();
					double y = 200;
					double z = player.getLocation().getZ();
					float yaw = player.getLocation().getYaw();
					float pitch = player.getLocation().getPitch();
					Iterator<World> w = parent.getServer().getWorlds()
							.iterator();
					while (w.hasNext()) {
						World world = w.next();
						if (world.getName().equalsIgnoreCase(
								FPSettings.teleworld)) {
							event.setCancelled(true);
							Location l = new Location(world, x, y, z, yaw,
									pitch);
							player.teleport(l);
						}
					}
				}
			}
		}
	}

	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			if (FPSettings.DeathEnabled) {
				CraftPlayer player = (CraftPlayer) event.getEntity();
				parent.getServer().broadcastMessage(
						FPSettings.deathmessage.replace("@p", player.getName())
								.replace(
										"@r",
										replaceCause(player
												.getLastDamageCause())));
				FeaturePack.log.info("R.I.P. " + player.getName());
				ItemStack stack = new ItemStack(260, 1);
				if (player.isOp()) {
					stack = new ItemStack(FPSettings.DropOnOPKill, 1);
				} else {
					stack = new ItemStack(FPSettings.DropOnKill, 1);
				}
				player.getWorld().dropItemNaturally(
						event.getEntity().getLocation(), stack);
			}
		}
	}

	public String replaceCause(EntityDamageEvent e) {
		String cause = null;
		if (e instanceof EntityDamageByEntityEvent) {
			if (((EntityDamageByEntityEvent) e).getDamager() != null) {
				cause = getMob(((EntityDamageByEntityEvent) e).getDamager());
			}
		} else if (e instanceof EntityDamageByBlockEvent) {
			if (((EntityDamageByBlockEvent) e).getDamager() != null) {
				cause = ((EntityDamageByBlockEvent) e).getDamager().getType()
						.toString().toLowerCase().replace("_", " ");
			}
		}
		if (cause != null) {
			return cause;
		} else {
			switch (e.getCause()) {
			case BLOCK_EXPLOSION:
				return "block explosion";
			case CONTACT:
				return "block contact";
			case CUSTOM:
				return "Unknown";
			case DROWNING:
				return "drowning";
			case ENTITY_ATTACK:
				return "a mob or person";
			case ENTITY_EXPLOSION:
				return "creeper";
			case FALL:
				return "falling";
			case FIRE:
				return "fire";
			case FIRE_TICK:
				return "fire";
			case LAVA:
				return "lava";
			case LIGHTNING:
				return "lightning";
			case PROJECTILE:
				return "some sort of a projectile";
			case SUFFOCATION:
				return "not having enough air";
			case VOID:
				return "falling into the void";
			default:
				return "Unknown";
			}
		}
	}

	public String getMob(Entity e) {
		if (e instanceof Player) {
			return ((Player) e).getDisplayName();
		} else if (e instanceof Arrow) {
			return "Arrow";
		} else if (e instanceof Creeper) {
			return "Creeper";
		} else if (e instanceof Egg) {
			return "Egg";
		} else if (e instanceof FallingSand) {
			return "Falling Sand";
		} else if (e instanceof Fireball) {
			return "Ghast Fireball";
		} else if (e instanceof Ghast) {
			return "Ghast";
		} else if (e instanceof Giant) {
			return "Giant";
		} else if (e instanceof HumanEntity) {
			return ((HumanEntity) e).getName();
		} else if (e instanceof LightningStrike) {
			return "Lightning Strike";
		} else if (e instanceof PigZombie) {
			return "Pig Zombie";
		} else if (e instanceof Skeleton) {
			return "Skeleton";
		} else if (e instanceof Slime) {
			return "Slime";
		} else if (e instanceof Snowball) {
			return "Snowball";
		} else if (e instanceof Spider) {
			return "Spider";
		} else if (e instanceof Wolf) {
			return "Wolf";
		} else if (e instanceof Zombie) {
			return "Zombie";
		} else if (e instanceof Chicken) {
			return "Chicken";
		} else if (e instanceof Cow) {
			return "Cow";
		} else if (e instanceof Fish) {
			return "Fish";
		} else if (e instanceof Pig) {
			return "Pig";
		} else if (e instanceof Sheep) {
			return "Sheep";
		} else if (e instanceof Squid) {
			return "Squid";
		} else if (e instanceof Weather) {
			return "Weather!!?";
		} else if (e instanceof Monster) {
			return "Monster";
		} else if (e instanceof Explosive) {
			return "Explosives";
		} else {
			return "a mob or person";
		}
	}
}