
package scha.efer.sbaddon.boards;

import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import e.Game;
import scha.efer.sbaddon.Main;

import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.HashMap;

public class Board2
{
    private static HashMap<UUID, Board2> players;
    private Player player;
    private Scoreboard scoreboard;
    private Objective sidebar;
    public static int sayi;
    String game_time;
    String state;
    int playing;
    String datef;
    String timef;
    public static HashMap<String, Integer> tribute;
    
    static {
        Board2.players = new HashMap<UUID, Board2>();
        Board2.tribute = new HashMap<String, Integer>();
    }
    
    public static Board2 create(final Player player, final boolean healthName, final boolean healthTab) {
        return new Board2(player, healthName, healthTab);
    }
    
    public static Board2 get(final Player player) {
        return Board2.players.get(player.getUniqueId());
    }
    
    public static void remove(final Player player) {
        Board2.players.remove(player.getUniqueId());
    }
    
    private Board2(final Player player, final boolean healthName, final boolean healthTab) {
        this.datef = Main.plugin.getConfig().getString("DateFormat");
        this.timef = Main.plugin.getConfig().getString("TimeFormat");
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        (this.sidebar = this.scoreboard.registerNewObjective("sidebar", "dummy")).setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(this.scoreboard);
        for (int i = 1; i <= 15; ++i) {
            final Team team = this.scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(this.genEntry(i));
        }
        Board2.players.put(player.getUniqueId(), this);
    }
    
    public void setTitle(String title) {
        title = title.replace("%stage%", Main.anan.get("Stage")).replace("%game_time%", "").replace('&', '§');
        if (title.length() > 32) {
            title = title.substring(0, 32);
        }
        if (!this.sidebar.getDisplayName().equals(title)) {
            this.sidebar.setDisplayName(title);
        }
    }
    
    public void setSlot(final int slot, String text) {
        final Team team = this.scoreboard.getTeam("SLOT_" + slot);
        final String entry = this.genEntry(slot);
        if (!this.scoreboard.getEntries().contains(entry)) {
            this.sidebar.getScore(entry).setScore(slot);
        }
        if (Main.anan.containsKey("Lobby")) {
            if (Game.getAlivePlayers() == null) {
                this.playing = Bukkit.getOnlinePlayers().size();
                Board2.tribute.put("Playing", Bukkit.getOnlinePlayers().size());
            }
            else {
                this.playing = Game.getAlivePlayers().size();
                Board2.tribute.put("Playing", Game.getAlivePlayers().size());
            }
        }
        else {
            this.playing = Game.getAlivePlayers().size();
            Board2.tribute.put("Playing", Game.getAlivePlayers().size());
        }
        final Date date = new Date();
        final String saat = new SimpleDateFormat(this.timef).format(date);
        final Date date2 = new Date();
        final String tarih = new SimpleDateFormat(this.datef).format(date2);
        final Calendar cal = Calendar.getInstance();
        final int hours = cal.get(11);
        final int minutes = cal.get(12);
        final int seconds = cal.get(13);
        final int ckm = 1;
        final int online = Bukkit.getOnlinePlayers().size();
        final int ping = 0;
        final int st = hours - 12;
        final int st2 = minutes / 2;
        final String stt = String.valueOf(st) + ":" + minutes;
        final String stt2 = String.valueOf(hours) + ":" + st2 + "ZT";
        String ap = " AM EET";
        if (hours > 12) {
            ap = " PM EET";
        }
        String time;
        if (minutes < 10) {
            time = String.valueOf(String.valueOf(String.valueOf(hours))) + ":0" + String.valueOf(minutes);
        }
        else if (hours < 10) {
            time = "0" + String.valueOf(hours) + ":" + String.valueOf(minutes);
        }
        else {
            time = String.valueOf(String.valueOf(String.valueOf(hours))) + ":" + String.valueOf(minutes);
        }
        String rank = "Unknown";
        if (Bukkit.getPluginManager().isPluginEnabled("GroupManager") && Bukkit.getPluginManager().isPluginEnabled("PermissionsEx")) {
            rank = Main.getGroup(this.player);
        }
        else if (Bukkit.getPluginManager().isPluginEnabled("GroupManager")) {
            rank = Main.getGroup(this.player);
        }
        else if (Bukkit.getPluginManager().isPluginEnabled("PermissionsEx")) {
            rank = Main.getPexGroup(this.player);
        }
        else {
            rank = "Unknown";
        }
        final int kills = this.player.getStatistic(Statistic.PLAYER_KILLS);
        text = text.replace("%stage%", Game.getStageName()).replace('&', '§').replace("%arrow%", "»").replace("%time%", String.valueOf(time) + ap).replace("%date%", tarih.replace("January", "Ocak").replace("February", "Subat").replace("March", "Mart").replace("April", "Nisan").replace("May", "Mayis").replace("June", "Haziran").replace("July", "Temmuz").replace("August", "Agustos").replace("September", "Eylul").replace("October", "Ekim").replace("November", "Kasim").replace("December", "Aralik")).replace("%rank%", rank).replace("%kills%", new StringBuilder().append(kills).toString()).replace("%world%", this.player.getWorld().getName()).replace("%time2%", String.valueOf(String.valueOf(stt)) + " " + stt2).replace("%playing%", new StringBuilder().append(Board2.tribute.get("Playing")).toString()).replace("%watching%", new StringBuilder().append(Game.getSpectators().size()).toString()).replace("%player_name%", this.player.getName()).replace("%player_displayname%", this.player.getDisplayName()).replace("%ping%", new StringBuilder().append(0).toString()).replace("%online%", new StringBuilder().append(online).toString());
        final String pre = this.getFirstSplit(text);
        final String suf = this.getFirstSplit(String.valueOf(String.valueOf(ChatColor.getLastColors(pre))) + this.getSecondSplit(text));
        if (!team.getPrefix().equals(pre)) {
            team.setPrefix(pre);
        }
        if (!team.getSuffix().equals(suf)) {
            team.setSuffix(suf);
        }
    }
    
    public void removeSlot(final int slot) {
        final String entry = this.genEntry(slot);
        if (this.scoreboard.getEntries().contains(entry)) {
            this.scoreboard.resetScores(entry);
        }
    }
    
    public void setSlotsFromList(final List<String> list) {
        int slot = list.size();
        if (slot < 15) {
            for (int i = slot + 1; i <= 15; ++i) {
                this.removeSlot(i);
            }
        }
        for (final String line : list) {
            this.setSlot(slot, line);
            --slot;
        }
    }
    
    private String genEntry(final int slot) {
        return ChatColor.values()[slot].toString();
    }
    
    private String getFirstSplit(final String s) {
        return (s.length() > 16) ? s.substring(0, 16) : s;
    }
    
    private String getSecondSplit(String s) {
        if (s.length() > 32) {
            s = s.substring(0, 32);
        }
        return (s.length() > 16) ? s.substring(16, s.length()) : "";
    }
}
