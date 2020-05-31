
package scha.efer.sbaddon;

import java.io.IOException;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Iterator;

import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import scha.efer.sbaddon.listeners.ScoreboardRegisterListener;

import org.bukkit.Bukkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import e.Game;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import java.util.Calendar;

public class Utils
{
    static Calendar cal;
    public static String[] colorcodes;
    static Scoreboard board;
    static Objective obj;
    static Team line;
    
    static {
        Utils.cal = Calendar.getInstance();
        Utils.colorcodes = new String[] { "§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9", "§a", "§b", "§c", "§d", "§e", "§f" };
    }
    
    public static String getStage() {
        final String stage = "Lobby";
        return stage;
    }
    
    public static String getTimer() {
        String game_time = "1:30";
        if (Game.getStageName().equals("Game")) {
            if (Timer.game_s < 60) {
                game_time = String.valueOf(String.valueOf(Timer.game_m)) + ":59";
            }
            if (Timer.game_s < 10) {
                game_time = String.valueOf(String.valueOf(Timer.game_m)) + ":0" + Timer.game_s;
            }
            else {
                game_time = String.valueOf(String.valueOf(Timer.game_m)) + ":" + Timer.game_s;
            }
        }
        else if (Game.getStageName().equals("Pre-Game")) {
            if (Timer.pg < 10) {
                game_time = "0:0" + Timer.pg;
            }
            else {
                game_time = "0:" + Timer.pg;
            }
        }
        else if (Game.getStageName().equals("Pre-Deathmatch")) {
            if (Timer.predm < 10) {
                game_time = "0:0" + Timer.predm;
            }
            else {
                game_time = "0:" + Timer.predm;
            }
        }
        else if (Game.getStageName().equals("Deathmatch")) {
            if (Timer.ds < 10) {
                game_time = String.valueOf(String.valueOf(Timer.dm)) + ":0" + Timer.ds;
            }
            else {
                game_time = String.valueOf(String.valueOf(Timer.dm)) + ":" + Timer.ds;
            }
        }
        else if (Game.getStageName().equals("Lobby")) {
            if (Timer.secondsLB < 10) {
                game_time = String.valueOf(String.valueOf(Timer.minutesLB)) + ":0" + Timer.secondsLB;
            }
            else {
                game_time = String.valueOf(String.valueOf(Timer.minutesLB)) + ":" + Timer.secondsLB;
            }
        }
        else if (Game.getStageName().equals("Restarting")) {
            if (Timer.restart < 10) {
                game_time = "00:0" + Timer.restart;
            }
            else {
                game_time = "00:" + Timer.restart;
            }
        }
        else if (Game.getStageName().equals("Lobby")) {
            game_time = "1:30";
        }
        else {
            game_time = "00:00";
        }
        return game_time;
    }
    
    public static String getLobbyTime2() {
        final int i = Timer.lobbytime;
        final int ms = i / 60;
        final int ss = i % 60;
        final String m = String.valueOf(String.valueOf((ms < 10) ? "0" : "")) + ms;
        final String s = String.valueOf(String.valueOf((ss < 10) ? "0" : "")) + ss;
        final String f = String.valueOf(String.valueOf(m)) + ":" + s;
        return f;
    }
    
    public static int getDay() {
        final int day = Utils.cal.get(5);
        return day;
    }
    
    public static String getDayName() {
        final Date dat = new Date();
        final SimpleDateFormat day = new SimpleDateFormat("EEEE");
        return day.format(dat);
    }
    
    public static int getHour() {
        final int hour = Utils.cal.get(11);
        final int th = getPM() ? (hour - 12) : hour;
        return th;
    }
    
    public static int getMinute() {
        final int min = Utils.cal.get(12);
        final int tm = (min < 10) ? (0 + min) : min;
        return tm;
    }
    
    public static boolean getPM() {
        final String am = (Utils.cal.get(9) == 0) ? "AM" : "PM";
        return am == "PM";
    }
    
    public static String getTime2() {
        final Date date = new Date();
        final String saat = new SimpleDateFormat("KK:mm").format(date);
        final Calendar cal = Calendar.getInstance();
        int hours = cal.get(11);
        final int minutes = cal.get(12);
        final int seconds = cal.get(13);
        final int ckm = 1;
        final int online = Bukkit.getOnlinePlayers().size();
        final int ping = 0;
        final int st = hours - 12;
        if (hours == 0) {
            hours = 12;
        }
        final int st2 = minutes / 2;
        final String stt = String.valueOf(st) + ":" + minutes;
        final String stt2 = String.valueOf(hours) + ":" + st2 + "ZS";
        String ap = " AM EET";
        if (hours > 12) {
            ap = " PM EET";
        }
        final String time = String.valueOf(String.valueOf(stt)) + " " + String.valueOf(stt2);
        return time;
    }
    
    public static void sendConsole() {
        Bukkit.getConsoleSender().sendMessage(renk("&c"));
        Bukkit.getConsoleSender().sendMessage(renk("&c"));
        Bukkit.getConsoleSender().sendMessage(renk("&c"));
        Bukkit.getConsoleSender().sendMessage(renk("&b&lScoreboardAddon &7for &3AdvancedSurvivalGames"));
        Bukkit.getConsoleSender().sendMessage(renk("&c"));
        Bukkit.getConsoleSender().sendMessage(renk("&7Author&8: &dCrispyBow"));
        Bukkit.getConsoleSender().sendMessage(renk("&7Discord&8: &aCrispyBow#0233"));
        Bukkit.getConsoleSender().sendMessage(renk("&7YouTube&8: &cCrispyBow"));
        Bukkit.getConsoleSender().sendMessage(renk("&c"));
        Bukkit.getConsoleSender().sendMessage(renk("&7All rights reserved by CrispyBow"));
        Bukkit.getConsoleSender().sendMessage(renk("&c"));
        Bukkit.getConsoleSender().sendMessage(renk("&c"));
        Bukkit.getConsoleSender().sendMessage(renk("&c"));
    }
    
    public static String renk(String text) {
        text = ChatColor.translateAlternateColorCodes('&', text);
        return text;
    }
    
    public static String getRank(final Player p) {
        String rank = "Unknown";
        if (Bukkit.getPluginManager().isPluginEnabled("PermissionsEx") && Bukkit.getPluginManager().isPluginEnabled("GroupManager")) {
            rank = Main.getGroup(p);
        }
        else if (Bukkit.getPluginManager().isPluginEnabled("PermissionsEx")) {
            rank = Main.getPexGroup(p);
        }
        else if (Bukkit.getPluginManager().isPluginEnabled("GroupManager")) {
            rank = Main.getGroup(p);
        }
        else {
            rank = "Unknown";
        }
        return rank;
    }
    
    public static void loadBoardAllPlayers() {
        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (final Player all : Bukkit.getOnlinePlayers()) {
                ScoreboardRegisterListener.registerScoreboards(all);
            }
        }
    }
    
    public static void loadBoard(final Player p) {
        showBoard(p);
    }
    
    public static void writeholders(final String mesaj, final String gidecekyer) {
        if (gidecekyer == "placeholders") {
            try {
                final File dataFolder = Main.plugin.getDataFolder();
                if (!dataFolder.exists()) {
                    dataFolder.mkdir();
                }
                final File saveTo = new File(Main.plugin.getDataFolder(), "placeholders.txt");
                if (!saveTo.exists()) {
                    saveTo.createNewFile();
                }
                final FileWriter fw = new FileWriter(saveTo, true);
                final PrintWriter pw = new PrintWriter(fw);
                pw.println(mesaj);
                pw.flush();
                pw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String replacer(String text, final Player p) {
        String pam;
        if (getPM()) {
            pam = "PM";
        }
        else {
            pam = "AM";
        }
        final Date date = new Date();
        final String saat = String.valueOf(new SimpleDateFormat(Main.plugin.getConfig().getString("Scoreboard.time-format")).format(date)) + " " + pam;
        final Date date2 = new Date();
        final String tarih = new SimpleDateFormat(Main.plugin.getConfig().getString("Scoreboard.date-format")).format(date2);
        final int online = Bukkit.getOnlinePlayers().size();
        final Date date3 = new Date();
        final Date now2 = new Date();
        final SimpleDateFormat theDate = new SimpleDateFormat("dd MMM yyyy");
        final SimpleDateFormat theTime = new SimpleDateFormat("hh:mm a z");
        final SimpleDateFormat theDateAndTime2 = new SimpleDateFormat(Main.plugin.getConfig().getString("Scoreboard.time-format2"));
        final String saat2 = String.valueOf(theDateAndTime2.format(now2)) + "ZS";
        text = text.replace("%date%", tarih).replace("%bounty%", "0").replace("%time%", saat).replace("%time2%", saat2).replace("%rank%", getRank(p)).replace("%arrow%", "»").replace("%playing%", new StringBuilder(String.valueOf(Game.getAlivePlayers().size())).toString()).replace("%watching%", new StringBuilder(String.valueOf(Game.getSpectators().size())).toString()).replace("%player%", p.getName()).replace("%map%", p.getWorld().getName()).replace("%ping%", new StringBuilder(String.valueOf(PingUtil.getPlayerPing(p))).toString()).replace("%stage%", Game.getStageName()).replace("%player_displayname%", p.getDisplayName()).replace("%game_time%", getTimer()).replace("%online%", new StringBuilder().append(online).toString());
        return text;
    }
    
    public static String replacer2(String text, final Player p) {
        String pam;
        if (getPM()) {
            pam = "PM";
        }
        else {
            pam = "AM";
        }
        final Date date = new Date();
        final String saat = String.valueOf(new SimpleDateFormat(Main.plugin.getConfig().getString("Scoreboard.time-format")).format(date)) + " " + pam;
        final Date date2 = new Date();
        final String tarih = new SimpleDateFormat(Main.plugin.getConfig().getString("Scoreboard.date-format")).format(date2);
        final int online = Bukkit.getOnlinePlayers().size();
        final Date date3 = new Date();
        final Date now2 = new Date();
        final SimpleDateFormat theDate = new SimpleDateFormat("dd MMM yyyy");
        final SimpleDateFormat theTime = new SimpleDateFormat("hh:mm a z");
        final SimpleDateFormat theDateAndTime2 = new SimpleDateFormat(Main.plugin.getConfig().getString("Scoreboard.time-format2"));
        final String saat2 = String.valueOf(theDateAndTime2.format(now2)) + "ZS";
        text = text.replace("%date%", tarih).replace("%bounty%", "0").replace("%time%", saat).replace("%time2%", saat2).replace("%rank%", getRank(p)).replace("%arrow%", "»").replace("%playing%", new StringBuilder(String.valueOf(Game.getAlivePlayers().size())).toString()).replace("%winner%", Game.getWinner().getPlayer().getName()).replace("%watching%", new StringBuilder(String.valueOf(Game.getSpectators().size())).toString()).replace("%player%", p.getName()).replace("%map%", p.getWorld().getName()).replace("%ping%", new StringBuilder(String.valueOf(PingUtil.getPlayerPing(p))).toString()).replace("%stage%", Game.getStageName()).replace("%player_displayname%", p.getDisplayName()).replace("%game_time%", getTimer()).replace("%online%", new StringBuilder().append(online).toString());
        return text;
    }
    
    public static void showBoard(final Player p) {
    }
    
    public static void update(final Player p) {
        showBoard(p);
        p.setScoreboard(Utils.board);
    }
}
