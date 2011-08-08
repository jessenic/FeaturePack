package net.digiex.FeaturePack;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.digiex.FeaturePack.command.BiomeCommand;
import net.digiex.FeaturePack.command.DawnCommand;
import net.digiex.FeaturePack.command.DayCommand;
import net.digiex.FeaturePack.command.DuskCommand;
import net.digiex.FeaturePack.command.GodCommand;
import net.digiex.FeaturePack.command.HelpCommand;
import net.digiex.FeaturePack.command.IPv6Command;
import net.digiex.FeaturePack.command.InfoCommand;
import net.digiex.FeaturePack.command.KillVoteCommand;
import net.digiex.FeaturePack.command.LightningCommand;
import net.digiex.FeaturePack.command.LockCommand;
import net.digiex.FeaturePack.command.LottoCommand;
import net.digiex.FeaturePack.command.MyTimeCommand;
import net.digiex.FeaturePack.command.NightCommand;
import net.digiex.FeaturePack.command.RainCommand;
import net.digiex.FeaturePack.command.RegenCommand;
import net.digiex.FeaturePack.command.ReloadCommand;
import net.digiex.FeaturePack.command.SecureCommand;
import net.digiex.FeaturePack.command.SeedCommand;
import net.digiex.FeaturePack.command.SunCommand;
import net.digiex.FeaturePack.command.ThunderCommand;
import net.digiex.FeaturePack.command.TimeVoteCommand;
import net.digiex.FeaturePack.command.UngodCommand;
import net.digiex.FeaturePack.command.UnlockCommand;
import net.digiex.FeaturePack.command.VersionCommand;
import net.digiex.FeaturePack.command.VoteCommand;
import net.digiex.FeaturePack.command.WeatherVoteCommand;

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