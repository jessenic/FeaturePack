package net.digiex.featurepack;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.featurepack.command.BiomeCommand;
import net.digiex.featurepack.command.DawnCommand;
import net.digiex.featurepack.command.DayCommand;
import net.digiex.featurepack.command.DuskCommand;
import net.digiex.featurepack.command.GodCommand;
import net.digiex.featurepack.command.HelpCommand;
import net.digiex.featurepack.command.IPv6Command;
import net.digiex.featurepack.command.InfoCommand;
import net.digiex.featurepack.command.KillVoteCommand;
import net.digiex.featurepack.command.LightningCommand;
import net.digiex.featurepack.command.LockCommand;
import net.digiex.featurepack.command.LottoCommand;
import net.digiex.featurepack.command.MyTimeCommand;
import net.digiex.featurepack.command.NightCommand;
import net.digiex.featurepack.command.RainCommand;
import net.digiex.featurepack.command.RegenCommand;
import net.digiex.featurepack.command.ReloadCommand;
import net.digiex.featurepack.command.SecureCommand;
import net.digiex.featurepack.command.SeedCommand;
import net.digiex.featurepack.command.SunCommand;
import net.digiex.featurepack.command.ThunderCommand;
import net.digiex.featurepack.command.TimeVoteCommand;
import net.digiex.featurepack.command.UngodCommand;
import net.digiex.featurepack.command.UnlockCommand;
import net.digiex.featurepack.command.VersionCommand;
import net.digiex.featurepack.command.VoteCommand;
import net.digiex.featurepack.command.WeatherVoteCommand;

public class FPCommand implements CommandExecutor {
	private HashMap<String, CommandExecutor> Executors = new HashMap<String, CommandExecutor>();
	private FeaturePack parent;

	public FPCommand(FeaturePack parent) {
		this.parent = parent;
	}

	public void registerExecutor(String subcmd, CommandExecutor cmd) {
		Executors.put(subcmd.toLowerCase(), cmd);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		String commandName = command.getName().toLowerCase();
		if (((sender instanceof Player)) && (commandName.equals("fp"))) {
			if (args.length == 0) {
				sender.sendMessage(FPSettings.incorrectusage);
				return false;
			}
			String subcommandName = args[0].toLowerCase();
			if (!Executors.containsKey(subcommandName)) {
				sender.sendMessage(FPSettings.incorrectusage);
				return false;
			}
			return (Executors.get(subcommandName)).onCommand(sender, command,
					commandLabel, args);
		} else {
			FeaturePack.log.info("Commands are in-game only, sorry.");
			return false;
		}
	}

	public void addCommands() {
		registerExecutor("version", new VersionCommand(parent));
		registerExecutor("reload", new ReloadCommand(parent));
		registerExecutor("help", new HelpCommand(parent));
		registerExecutor("rain", new RainCommand(parent));
		registerExecutor("thunder", new ThunderCommand(parent));
		registerExecutor("light", new LightningCommand(parent));
		registerExecutor("sun", new SunCommand(parent));
		if (FPSettings.TimeVotes || FPSettings.WeatherVotes
				|| FPSettings.KillVotes && FPSettings.VotingEnabled) {
			registerExecutor("vote", new VoteCommand(parent));
		}
		if (FPSettings.TimeVotes && FPSettings.VotingEnabled) {
			registerExecutor("time", new TimeVoteCommand(parent));
		}
		if (FPSettings.WeatherVotes && FPSettings.VotingEnabled) {
			registerExecutor("weather", new WeatherVoteCommand(parent));
		}
		if (FPSettings.IPv6) {
			registerExecutor("ipv6", new IPv6Command(parent));
		}
		registerExecutor("day", new DayCommand(parent));
		registerExecutor("night", new NightCommand(parent));
		registerExecutor("dawn", new DawnCommand(parent));
		registerExecutor("dusk", new DuskCommand(parent));
		registerExecutor("info", new InfoCommand(parent));
		registerExecutor("lock", new LockCommand(parent));
		registerExecutor("unlock", new UnlockCommand(parent));
		registerExecutor("god", new GodCommand(parent));
		registerExecutor("ungod", new UngodCommand(parent));
		registerExecutor("seed", new SeedCommand(parent));
		registerExecutor("biome", new BiomeCommand(parent));
		if (FPSettings.KillVotes && FPSettings.VotingEnabled) {
			registerExecutor("kill", new KillVoteCommand(parent));
		}
		if (FPSettings.MyTime) {
			registerExecutor("mytime", new MyTimeCommand(parent));
		}
		if (FPSettings.LotteryEnabled && parent.Method != null) {
			registerExecutor("lotto", new LottoCommand(parent));
		}
		if (FPSettings.Regen) {
			registerExecutor("regen", new RegenCommand(parent));
		}
		if (FPSettings.Secure) {
			registerExecutor("secure", new SecureCommand(parent));
		}
		parent.getCommand("fp").setExecutor(this);
	}

	public void clearCommands() {
		Executors.clear();
	}
}