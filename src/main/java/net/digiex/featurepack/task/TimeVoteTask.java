package net.digiex.featurepack.task;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.digiex.featurepack.FPSettings;
import net.digiex.featurepack.FPSpout;
import net.digiex.featurepack.FeaturePack;
import net.digiex.featurepack.command.TimeVoteCommand;
import net.digiex.featurepack.payment.Method.MethodAccount;

public class TimeVoteTask implements Runnable {

    private FeaturePack parent;
    private World world;
    private String type;
    private int id;
    private int VotesYes;
    private int VotesNo;
    private ArrayList<String[]> Remove = new ArrayList<String[]>();

    public TimeVoteTask(FeaturePack parent, World world) {
        this.parent = parent;
        this.world = world;
    }

    @Override
    public void run() {
        for (Entry<Player, World> e : TimeVoteCommand.Yes.entrySet()) {
            if (e.getValue().equals(world)) {
                VotesYes++;
                Player player = parent.getServer().getPlayer(
                        e.getKey().getName());
                giveMoney(player);
                Remove.add(new String[]{player.getName(), "YES"});
            }
        }
        for (Entry<Player, World> e : TimeVoteCommand.No.entrySet()) {
            if (e.getValue().equals(world)) {
                VotesNo++;
                Player player = parent.getServer().getPlayer(
                        e.getKey().getName());
                giveMoney(player);
                Remove.add(new String[]{player.getName(), "NO"});
            }
        }
        type = TimeVoteCommand.Type.get(world);
        id = TimeVoteCommand.ID.get(world);
        if (VotesYes >= FPSettings.TimeVotesRequired
                || VotesNo >= FPSettings.TimeVotesRequired) {
            if (VotesYes > VotesNo) {
                Cancel(FPSettings.timevotepassed.replace("@t", type));
                if (type.equals("Day")) {
                    world.setTime(0);
                    Sleep();
                } else if (type.equals("Night")) {
                    world.setTime(14500);
                    Sleep();
                } else if (type.equals("Dawn")) {
                    world.setTime(22000);
                    Sleep();
                } else if (type.equals("Dusk")) {
                    world.setTime(12500);
                    Sleep();
                }
            } else if (VotesYes == VotesNo && VotesYes > 0 && VotesNo > 0) {
                Cancel(FPSettings.timevotetied.replace("@t", type));
                Sleep();
            } else if (VotesYes < VotesNo) {
                Cancel(FPSettings.timevotefailed.replace("@t", type));
                Sleep();
            }
        } else {
            Cancel(FPSettings.timevotefailed.replace("@t", type));
            Sleep();
        }
    }

    private void Cancel(String msg) {
        for (Player p : world.getPlayers()) {
            p.sendMessage(msg);
        }
    }

    private void Sleep() {
        try {
            TimeVoteCommand.inProgress.remove(world);
            TimeVoteCommand.inProgress.put(world, false);
            Thread.sleep(FPSettings.TimeCooldown * 60 * 1000);
        } catch (InterruptedException e) {
            this.parent.getServer().getScheduler().cancelTask(id);
        }
        Stop();
    }

    private void Stop() {
        for (String[] s : Remove) {
            if (s[1].equals("YES")) {
                Player p = parent.getServer().getPlayer(s[0]);
                TimeVoteCommand.Yes.remove(p);
            } else if (s[1].equals("NO")) {
                Player p = parent.getServer().getPlayer(s[0]);
                TimeVoteCommand.No.remove(p);
            }
        }
        TimeVoteCommand.inProgress.remove(world);
        TimeVoteCommand.ID.remove(world);
        for (Player player : world.getPlayers()) {
            player.sendMessage(FPSettings.timehascooleddown);
        }
        this.parent.getServer().getScheduler().cancelTask(id);
    }

    private void giveMoney(Player player) {
        if (this.parent.Method != null) {
            if (FPSettings.VotingMoney > 0) {
                MethodAccount account = this.parent.Method.getAccount(player.getName());
                account.add(FPSettings.VotingMoney);
                if (FPSpout.plugin != null && FPSpout.hasSpout(player)) {
                    FPSpout.sendNotification(
                            "Thanks For Voting",
                            FPSettings.VotingMoneyContrib.replace("@a",
                            String.valueOf(FPSettings.VotingMoney)),
                            Material.WATCH, player);
                } else {
                    player.sendMessage(FPSettings.VotingMoneyMessage.replace(
                            "@a", String.valueOf(FPSettings.VotingMoney)));
                }
            }
        }
    }
}