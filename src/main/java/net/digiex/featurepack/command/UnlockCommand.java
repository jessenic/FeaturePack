package net.digiex.featurepack.command;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPPermissions;
import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FPUtils;
import net.digiex.featurepack.FeaturePack;

public class UnlockCommand implements CommandExecutor {

    private FeaturePack parent;
    private FPUtils utils = new FPUtils();

    public UnlockCommand(FeaturePack parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (FPPermissions.has(player, "featurepack.unlock")) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("?")) {
                    player.sendMessage(ChatColor.AQUA + "Help: /fp unlock");
                    player.sendMessage(ChatColor.YELLOW
                            + "Optional: time, weather");
                    player.sendMessage(ChatColor.GREEN
                            + "Example usage: /fp unlock time "
                            + ChatColor.AQUA
                            + "(Unlocks time for the current world)");
                } else if (args[1].equalsIgnoreCase("weather")) {
                    removeWeatherLock(player);
                } else if (args[1].equalsIgnoreCase("time")) {
                    removeTimeLock(player);
                } else {
                    player.sendMessage(FPSettings.incorrectusage);
                }
            } else if (args.length == 1) {
                removeTimeLock(player);
                removeWeatherLock(player);
            } else {
                player.sendMessage(FPSettings.incorrectusage);
            }
        } else {
            player.sendMessage(FPSettings.nopermission);
        }
        return true;
    }

    public void removeTimeLock(Player player) {
        World world = player.getWorld();
        if (LockCommand.TimeLocked.containsKey(world)) {
            int id = LockCommand.TimeLocked.get(world);
            parent.getServer().getScheduler().cancelTask(id);
            LockCommand.TimeLocked.remove(world);
            utils.removeTimeLock(world);
            player.sendMessage(FPSettings.timeunlock.replace("@w", player.getWorld().getName()));
        } else {
            player.sendMessage(FPSettings.timenotlocked.replace("@w", player.getWorld().getName()));
        }
    }

    public void removeWeatherLock(Player player) {
        World world = player.getWorld();
        if (LockCommand.WeatherLocked.containsKey(world)) {
            int id = LockCommand.WeatherLocked.get(world);
            parent.getServer().getScheduler().cancelTask(id);
            LockCommand.WeatherLocked.remove(world);
            utils.removeWeatherLock(world);
            player.sendMessage(FPSettings.weatherunlock.replace("@w",
                    world.getName()));
        } else {
            player.sendMessage(FPSettings.weathernotlocked.replace("@w", player.getWorld().getName()));
        }
    }
}