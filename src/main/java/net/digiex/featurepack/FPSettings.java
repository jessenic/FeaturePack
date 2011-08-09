package net.digiex.featurepack;

import java.io.File;
import java.util.List;

public class FPSettings {

    @SuppressWarnings("unused")
    private FeaturePack parent;
    private FPUtils utils = new FPUtils();
    public static boolean DebugMode;
    public static boolean IPv6;
    public static boolean TimeVotes;
    public static boolean WeatherVotes;
    public static boolean VotingEnabled;
    public static boolean LotteryEnabled;
    public static boolean MyTime;
    public static boolean DeathEnabled;
    public static boolean TeleportEnabled;
    public static boolean KillVotes;
    public static boolean BiomeMessages;
    public static boolean BedVoting;
    public static boolean TipsEnabled;
    public static boolean GodOnTeleportOrJoin;
    public static boolean WandsEnabled;
    public static boolean SignEnabled;
    public static boolean Regen;
    public static boolean DisablePlugin;
    public static boolean Secure;
    public static boolean Overloaded;
    public static int LightningWand;
    public static int VoteWand;
    public static int TimeVotingTime;
    public static int WeatherVotingTime;
    public static int godtime;
    public static int KillVotingTime;
    public static int LockedTime;
    public static int LockedWeather;
    public static int DropOnOPKill;
    public static int DropOnKill;
    public static int MyTimeCost;
    public static int LotteryStart;
    public static int TimeVotesRequired;
    public static int WeatherVotesRequired;
    public static int KillVotesRequired;
    public static int VotingMoney;
    public static int TimeCooldown;
    public static int WeatherCooldown;
    public static int KillCooldown;
    public static int LotteryCost;
    public static int FireballWand;
    public static List<Object> IPv6Permissions;
    public static String timevotepassed;
    public static String timevotetied;
    public static String timevotefailed;
    public static String timevoteexpired;
    public static String weathervotepassed;
    public static String weathervotetied;
    public static String weathervotefailed;
    public static String weathervoteexpired;
    public static String novotes;
    public static String alreadyvotedtime;
    public static String alreadyvotedweather;
    public static String timevotesyes;
    public static String timevotesno;
    public static String weathervotesyes;
    public static String weathervotesno;
    public static String adjustvoteyesno;
    public static String adjustvotenoyes;
    public static String nopermission;
    public static String freetime;
    public static String freeweather;
    public static String day;
    public static String night;
    public static String dawn;
    public static String dusk;
    public static String sun;
    public static String rain;
    public static String thunder;
    public static String noipv6;
    public static String hasipv6;
    public static String reload;
    public static String incorrectusage;
    public static String timealreadylocked;
    public static String weatheralreadylocked;
    public static String timelocked;
    public static String weatherlocked;
    public static String god;
    public static String ungod;
    public static String timevoteinprogress;
    public static String weathervoteinprogress;
    public static String timeunlock;
    public static String weatherunlock;
    public static String SignText;
    public static String weathernotlocked;
    public static String timenotlocked;
    public static String goduser;
    public static String ungoduser;
    public static String strikeuser;
    public static String enteredbiome;
    public static String inbiome;
    public static String teleworld;
    public static String killvotepassed;
    public static String killvotetied;
    public static String killvotefailed;
    public static String killvoteexpired;
    public static String killvotesyes;
    public static String killvotesno;
    public static String freekill;
    public static String killvoteinprogress;
    public static String alreadyvotedkill;
    public static String cannotvotehere;
    public static String version;
    public static String deathmessage;
    public static String mytime;
    public static String nomoney;
    public static String mytimenormal;
    public static String LotteryLoss;
    public static String LotteryWin;
    public static String LotteryWinAll;
    public static String UserGod;
    public static String UserUnGod;
    public static String VotingMoneyMessage;
    public static String VoteCancelled;
    public static String VotingMoneyContrib;
    public static String timecooldown;
    public static String weathercooldown;
    public static String killcooldown;
    public static String timehascooleddown;
    public static String weatherhascooleddown;
    public static String killhascooleddown;
    public static String TimeVote;
    public static String WeatherVote;
    public static String KillVote;
    public static String alreadygod;
    public static String alreadygoduser;
    public static String notgod;
    public static String notgoduser;
    public static String secureactivated;
    public static String securedeactivated;
    public static String securepass;
    public static String secureoverride;
    public static String securealreadyactivated;
    public static String securealreadydeactivated;
    public static String secureincorrectpass;
    public static String regenon;
    public static String regenoff;
    public static String dayall;
    public static String nightall;
    public static String dawnall;
    public static String duskall;
    public static String sunall;
    public static String rainall;
    public static String thunderall;
    public static String reloadtitle;
    public static String reloadmessage;
    public static String timevotetitle;
    public static String timeyesalreadyvotedmessage;
    public static String timenoalreadyvotedmessage;
    public static String timeyesnomessage;
    public static String timenoyesmessage;
    public static String timeyesmessage;
    public static String timenomessage;
    public static String timecooldownmessage;
    public static String weathervotetitle;
    public static String weatheryesalreadyvotedmessage;
    public static String weathernoalreadyvotedmessage;
    public static String weatheryesnomessage;
    public static String weathernoyesmessage;
    public static String weatheryesmessage;
    public static String weathernomessage;
    public static String weathercooldownmessage;
    public static String killvotetitle;
    public static String killyesalreadyvotedmessage;
    public static String killnoalreadyvotedmessage;
    public static String killyesnomessage;
    public static String killnoyesmessage;
    public static String killyesmessage;
    public static String killnomessage;
    public static String killcooldownmessage;
    public static String timevotes;
    public static String weathervotes;
    public static String killvotes;
    public static String flagged;
    public static String notflagged;

    public FPSettings(FeaturePack parent) {
        this.parent = parent;
    }

    public void load() {
        try {
            setBooleans();
            setInts();
            setLists();
            setStrings();
        } catch (NullPointerException e) {
            fixConfigs(utils.config(), utils.configold());
        }
        try {
            setMessages();
        } catch (NullPointerException e) {
            fixConfigs(utils.messages(), utils.messagesold());
        }
        FeaturePack.log.debug("Debug mode enabled!");
        FeaturePack.log.debug("Settings loaded");
    }

    private void fixConfigs(File src, File des) {
        FeaturePack.log.severe(src.getName() + " is incorrect or is outdated");
        FeaturePack.log.severe("Restart sever to fix " + src.getName());
        if (des.exists()) {
            des.delete();
        } else {
            src.renameTo(des);
        }
        DisablePlugin = true;
    }

    private void setBooleans() {
        DebugMode = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Debug");
        IPv6 = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.IPv6.Enabled");
        TimeVotes = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Time.Enabled");
        WeatherVotes = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Weather.Enabled");
        VotingEnabled = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Enabled");
        GodOnTeleportOrJoin = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.God.GodOnTeleportOrJoin");
        WandsEnabled = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Wands.Enabled");
        SignEnabled = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Sign.Enabled");
        TipsEnabled = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Tips.Enabled");
        BedVoting = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Voting.BedVoting");
        TeleportEnabled = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Teleport.Enabled");
        BiomeMessages = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.BiomeMessages");
        KillVotes = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Kill.Enabled");
        DeathEnabled = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Death.Enabled");
        MyTime = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.MyTime.Enabled");
        LotteryEnabled = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Lottery.Enabled");
        Regen = (Boolean) utils.getProperty(utils.config(), "FeaturePack.Regen");
        Secure = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Secure");
        Overloaded = (Boolean) utils.getProperty(utils.config(),
                "FeaturePack.Overloaded");
    }

    private void setInts() {
        LightningWand = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Wands.Lightning");
        VoteWand = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Wands.Vote");
        TimeVotingTime = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Time.Time");
        WeatherVotingTime = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Weather.Time");
        godtime = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.God.Time");
        KillVotingTime = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Kill.Time");
        LockedTime = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.TimeLocking.Time");
        LockedWeather = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.WeatherLocking.Time");
        DropOnOPKill = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Death.DropOnOPKill");
        DropOnKill = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Death.DropOnKill");
        MyTimeCost = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.MyTime.Cost");
        LotteryCost = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Lottery.Cost");
        TimeVotesRequired = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Time.Required");
        WeatherVotesRequired = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Weather.Required");
        KillVotesRequired = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Kill.Required");
        VotingMoney = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Money");
        TimeCooldown = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Time.Cooldown");
        WeatherCooldown = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Weather.Cooldown");
        KillCooldown = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Voting.Kill.Cooldown");
        LotteryStart = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Lottery.Start");
        FireballWand = (Integer) utils.getProperty(utils.config(),
                "FeaturePack.Wands.Fireball");
    }

    private void setLists() {
        IPv6Permissions = utils.get(utils.config()).getList(
                "FeaturePack.IPv6.Permissions");
    }

    public void setStrings() {
        teleworld = (String) utils.getProperty(utils.config(),
                "FeaturePack.Teleport.World");
        SignText = (String) utils.getProperty(utils.config(),
                "FeaturePack.Sign.Text");
    }

    private void setMessages() {
        timevotepassed = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timevotepassed"));
        timevotetied = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timevotetied"));
        timevotefailed = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timevotefailed"));
        timevotesyes = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timevotesyes"));
        timevotesno = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timevotesno"));
        freetime = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.freetime"));
        timevoteinprogress = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.timevoteinprogress"));
        alreadyvotedtime = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.alreadyvotedtime"));
        timealreadylocked = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.timealreadylocked"));
        timelocked = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timelocked"));
        timeunlock = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timeunlock"));
        timenotlocked = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timenotlocked"));
        day = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.day"));
        night = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.night"));
        dawn = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.dawn"));
        dusk = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.dusk"));
        weathervotepassed = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathervotepassed"));
        weathervotetied = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathervotetied"));
        weathervotefailed = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathervotefailed"));
        weathervotesyes = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathervotesyes"));
        weathervotesno = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.weathervotesno"));
        freeweather = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.freeweather"));
        weathervoteinprogress = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathervoteinprogress"));
        alreadyvotedweather = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.alreadyvotedweather"));
        weatheralreadylocked = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weatheralreadylocked"));
        weatherlocked = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.weatherlocked"));
        weatherunlock = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.weatherunlock"));
        weathernotlocked = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathernotlocked"));
        sun = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.sun"));
        rain = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.rain"));
        thunder = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.thunder"));
        killvotepassed = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killvotepassed"));
        killvotetied = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killvotetied"));
        killvotefailed = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killvotefailed"));
        killvotesyes = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killvotesyes"));
        killvotesno = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killvotesno"));
        freekill = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.freekill"));
        killvoteinprogress = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.killvoteinprogress"));
        alreadyvotedkill = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.alreadyvotedkill"));
        novotes = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.novotes"));
        adjustvoteyesno = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.adjustvoteyesno"));
        adjustvotenoyes = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.adjustvotenoyes"));
        nopermission = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.nopermission"));
        noipv6 = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.noipv6"));
        hasipv6 = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.hasipv6"));
        reload = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.reload"));
        incorrectusage = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.incorrectusage"));
        god = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.god"));
        ungod = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.ungod"));
        goduser = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.goduser"));
        ungoduser = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.ungoduser"));
        strikeuser = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.strikeuser"));
        enteredbiome = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.enteredbiome"));
        inbiome = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.inbiome"));
        cannotvotehere = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.cannotvotehere"));
        version = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.version"));
        deathmessage = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.deathmessage"));
        mytime = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.mytime"));
        nomoney = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.nomoney"));
        mytimenormal = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.mytimenormal"));
        LotteryLoss = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.lotteryloss"));
        LotteryWin = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.lotterywin"));
        LotteryWinAll = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.lotterywinall"));
        UserGod = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.usergod"));
        UserUnGod = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.userungod"));
        VotingMoneyMessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.votingmoney"));
        VoteCancelled = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.votecancelled"));
        VotingMoneyContrib = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.votingmoneyc"));
        timecooldown = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timecooldown"));
        weathercooldown = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathercooldown"));
        killcooldown = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killcooldown"));
        timehascooleddown = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.timehascooleddown"));
        weatherhascooleddown = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weatherhascooleddown"));
        killhascooleddown = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.killhascooleddown"));
        TimeVote = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timevote"));
        WeatherVote = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.weathervote"));
        KillVote = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killvote"));
        alreadygod = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.alreadygod"));
        alreadygoduser = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.alreadygoduser"));
        secureactivated = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.secureactivated"));
        securedeactivated = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.securedeactivated"));
        securepass = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.securepass"));
        secureoverride = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.secureoverride"));
        securealreadyactivated = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.securealreadyactivated"));
        securealreadydeactivated = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.securealreadydeactivated"));
        secureincorrectpass = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.secureincorrectpass"));
        notgod = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.notgod"));
        notgoduser = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.notgoduser"));
        regenon = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.regenon"));
        regenoff = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.regenoff"));
        reloadtitle = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.reloadtitle"));
        reloadmessage = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.reloadmessage"));
        dayall = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.dayall"));
        nightall = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.nightall"));
        dawnall = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.dawnall"));
        duskall = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.duskall"));
        sunall = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.sunall"));
        rainall = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.rainall"));
        thunderall = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.thunderall"));
        timevotetitle = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timevotetitle"));
        timeyesalreadyvotedmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.timeyesalreadyvotedmessage"));
        timenoalreadyvotedmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.timenoalreadyvotedmessage"));
        timeyesnomessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.timeyesnomessage"));
        timenoyesmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.timenoyesmessage"));
        timeyesmessage = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timeyesmessage"));
        timenomessage = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timenomessage"));
        timecooldownmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.timecooldownmessage"));
        weathervotetitle = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathervotetitle"));
        weatheryesalreadyvotedmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weatheryesalreadyvotedmessage"));
        weathernoalreadyvotedmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathernoalreadyvotedmessage"));
        weatheryesnomessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weatheryesnomessage"));
        weathernoyesmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathernoyesmessage"));
        weatheryesmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weatheryesmessage"));
        weathernomessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathernomessage"));
        weathercooldownmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.weathercooldownmessage"));
        killvotetitle = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killvotetitle"));
        killyesalreadyvotedmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.killyesalreadyvotedmessage"));
        killnoalreadyvotedmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.killnoalreadyvotedmessage"));
        killyesnomessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.killyesnomessage"));
        killnoyesmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.killnoyesmessage"));
        killyesmessage = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killyesmessage"));
        killnomessage = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killnomessage"));
        killcooldownmessage = trimColor((String) utils.getProperty(
                utils.messages(), "Messages.killcooldownmessage"));
        timevotes = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.timevotes"));
        weathervotes = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.weathervotes"));
        killvotes = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.killvotes"));
        flagged = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.flagged"));
        notflagged = trimColor((String) utils.getProperty(utils.messages(),
                "Messages.notflagged"));
    }

    private String trimColor(String from) {
        return from.replace("$", "\u00A7");
    }
}