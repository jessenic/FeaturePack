package net.digiex.featurepack;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FPHelp {

    public ArrayList<String[]> PageOne = new ArrayList<String[]>();
    public ArrayList<String[]> PageTwo = new ArrayList<String[]>();
    public ArrayList<String[]> PageThree = new ArrayList<String[]>();
    private FeaturePack parent;

    public FPHelp(FeaturePack parent) {
        this.parent = parent;
    }

    public void load() {
        FeaturePack.log.debug("Loading help messages");
        PageOne.add(new String[]{"fp help",
                    "Shows help messages, Page 1 of 3", "featurepack.help"});
        PageOne.add(new String[]{"fp biome", "Shows what biome you're in",
                    "featurepack.biome"});
        if (FPSettings.IPv6) {
            PageOne.add(new String[]{"fp ipv6", "Check if you have IPv6",
                        "featurepack.ipv6"});
        }
        if (FPSettings.LotteryEnabled && parent.Method != null) {
            PageOne.add(new String[]{"fp lotto",
                        "Play the lottery. Costs: " + FPSettings.LotteryCost,
                        "featurepack.lottery"});
        }
        if (FPSettings.KillVotes && FPSettings.VotingEnabled) {
            PageOne.add(new String[]{"fp kill", "Starts a vote to kill",
                        "featurepack.kill"});
        }
        if (FPSettings.TimeVotes && FPSettings.VotingEnabled) {
            PageOne.add(new String[]{"fp time", "Starts a vote for time",
                        "featurepack.time"});
        }
        if (FPSettings.MyTime) {
            if (parent.Method != null) {
                PageOne.add(new String[]{"fp mytime",
                            "Set you're time. Costs: " + FPSettings.MyTimeCost,
                            "featurepack.mytime"});
            } else {
                PageOne.add(new String[]{"fp mytime", "Set you're time",
                            "featurepack.mytime"});
            }
        }
        if (FPSettings.WeatherVotes && FPSettings.VotingEnabled) {
            PageOne.add(new String[]{"fp weather",
                        "Starts a vote for weather", "featurepack.weather"});
        }
        if (FPSettings.TimeVotes || FPSettings.WeatherVotes
                || FPSettings.KillVotes && FPSettings.VotingEnabled) {
            PageOne.add(new String[]{"fp vote",
                        "Vote for time, weather or kill votes", "featurepack.vote"});
        }
        if (FPSettings.Secure) {
            PageOne.add(new String[]{"fp secure", "Secure you're account",
                        "featurepack.secure"});
        }
        PageOne.add(new String[]{"Use any command with",
                    "for more information or examples", "featurepack.help"});
        PageTwo.add(new String[]{"fp help",
                    "Shows help messages, Page 2 of 3", "featurepack.help"});
        PageTwo.add(new String[]{"fp day", "Changes time to day",
                    "featurepack.day"});
        PageTwo.add(new String[]{"fp night", "Changes time to night",
                    "featurepack.night"});
        PageTwo.add(new String[]{"fp dawn", "Changes time to dawn",
                    "featurepack.dawn"});
        PageTwo.add(new String[]{"fp dusk", "Changes time to dusk",
                    "featurepack.dusk"});
        PageTwo.add(new String[]{"fp sun", "Changes weather to sun",
                    "featurepack.sun"});
        PageTwo.add(new String[]{"fp rain", "Changes weather to rain",
                    "featurepack.rain"});
        PageTwo.add(new String[]{"fp thunder", "Changes weather to thunder",
                    "featurepack.command.thunder"});
        if (FPSettings.Regen) {
            PageTwo.add(new String[]{"fp regen",
                        "Regenerate chunks as you move", "featurepack.regen"});
        }
        PageTwo.add(new String[]{"Use any command with",
                    "for more information or examples", "featurepack.help"});
        PageThree.add(new String[]{"fp help",
                    "Shows help messages, Page 3 of 3", "featurepack.help"});
        PageThree.add(new String[]{"fp info", "Server information",
                    "featurepack.info"});
        PageThree.add(new String[]{"fp version", "FeaturePack version",
                    "featurepack.version"});
        PageThree.add(new String[]{"fp reload", "Reload FeaturePack",
                    "featurepack.reload"});
        PageThree.add(new String[]{"fp seed", "Shows seeds for worlds",
                    "featurepack.seed"});
        PageThree.add(new String[]{"fp god", "Gives god",
                    "featurepack.command.god"});
        PageThree.add(new String[]{"fp ungod", "Removes god",
                    "featurepack.ungod"});
        PageThree.add(new String[]{"fp light", "Strikes a player",
                    "featurepack.light"});
        PageThree.add(new String[]{"fp lock", "Locks time and weather",
                    "featurepack.lock"});
        PageThree.add(new String[]{"fp unlock", "Unlocks time and weather",
                    "featurepack.unlock"});
        PageThree.add(new String[]{"Use any command with",
                    "for more information or examples", "featurepack.help"});
    }

    public boolean getHelpOne(Player player) {
        for (String[] command : PageOne) {
            if (FPPermissions.has(player, command[2])) {
                if (command[0].equals("Use any command with")) {
                    player.sendMessage(ChatColor.AQUA + command[0]
                            + ChatColor.GREEN + " ? " + ChatColor.AQUA
                            + command[1]);
                } else {
                    player.sendMessage(ChatColor.AQUA + "/" + command[0]
                            + ChatColor.GRAY + " - " + ChatColor.GREEN
                            + command[1]);
                }
            }
        }
        return true;
    }

    public boolean getHelpTwo(Player player) {
        for (String[] command : PageTwo) {
            if (command[0].equals("Use any command with")) {
                player.sendMessage(ChatColor.AQUA + command[0]
                        + ChatColor.GREEN + " ? " + ChatColor.AQUA + command[1]);
            } else {
                player.sendMessage(ChatColor.AQUA + "/" + command[0]
                        + ChatColor.GRAY + " - " + ChatColor.GREEN + command[1]);
            }
        }
        return true;
    }

    public boolean getHelpThree(Player player) {
        for (String[] command : PageThree) {
            if (command[0].equals("Use any command with")) {
                player.sendMessage(ChatColor.AQUA + command[0]
                        + ChatColor.GREEN + " ? " + ChatColor.AQUA + command[1]);
            } else {
                player.sendMessage(ChatColor.AQUA + "/" + command[0]
                        + ChatColor.GRAY + " - " + ChatColor.GREEN + command[1]);
            }
        }
        return true;
    }
}