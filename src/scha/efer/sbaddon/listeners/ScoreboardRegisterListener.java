
package scha.efer.sbaddon.listeners;

import java.util.List;

import e.Game;
import scha.efer.sbaddon.Main;
import scha.efer.sbaddon.Utils;
import scha.efer.sbaddon.boards.ScoreboardHelper;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import java.util.WeakHashMap;

import org.bukkit.event.Listener;

public class ScoreboardRegisterListener implements Listener
{
    private static final Main plugin;
    private static final WeakHashMap<Player, ScoreboardHelper> helper;
    static String ip;
    static String title;
    
    static {
        plugin = Main.plugin;
        helper = new WeakHashMap<Player, ScoreboardHelper>();
        ScoreboardRegisterListener.ip = "§cwww.eucw.cf";
        ScoreboardRegisterListener.title = "§c§lEUCW";
    }
    
    public static ScoreboardHelper getScoreboardFor(final Player player) {
        return ScoreboardRegisterListener.helper.get(player);
    }
    
    @EventHandler
    public void onjoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        new BukkitRunnable() {
            public void run() {
                ScoreboardRegisterListener.registerScoreboards(p);
            }
        }.runTaskLater((Plugin)ScoreboardRegisterListener.plugin, 10L);
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
    }
    
    @EventHandler
    public void onPlayerKick(final PlayerKickEvent event) {
        final Player player = event.getPlayer();
        ScoreboardRegisterListener.helper.remove(player);
    }
    
    public void unregister(final Scoreboard board, final String name) {
        final Team team = board.getTeam(name);
        if (team != null) {
            team.unregister();
        }
    }
    
    public static void registerScoreboards(final Player player) {
        final Scoreboard bukkitScoreBoard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        player.setScoreboard(bukkitScoreBoard);
        final ScoreboardHelper scoreboardHelper = new ScoreboardHelper(bukkitScoreBoard);
        ScoreboardRegisterListener.helper.put(player, scoreboardHelper);
        resendTab(player);
        setupScoreboard();
        for (final Player online : Bukkit.getOnlinePlayers()) {
            if (online != player && getScoreboardFor(online) != null) {
                getScoreboardFor(online).getScoreBoard();
            }
        }
    }
    
    public static void resendTab(final Player player) {
        if (getScoreboardFor(player) == null) {
            return;
        }
        final Scoreboard scoreboard = getScoreboardFor(player).getScoreBoard();
    }
    
    public static void setupScoreboard() {
        new BukkitRunnable() {
            public void run() {
                final List<String> lineslobby = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.LOBBY.lines");
                final List<String> linespg = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.PREGAME.lines");
                final List<String> lineslg = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.LIVEGAME.lines");
                final List<String> linespdm = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.PREDEATHMATCH.lines");
                final List<String> linesdm = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.DEATHMATCH.lines");
                final List<String> linesrs = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.RESTARTING.lines");
                if (Bukkit.getOnlinePlayers().size() > 0) {
                    for (final Player p : Bukkit.getOnlinePlayers()) {
                        if (!ScoreboardRegisterListener.helper.containsKey(p)) {
                            return;
                        }
                        final ScoreboardHelper scoreboard = ScoreboardRegisterListener.getScoreboardFor(p);
                        if (Game.getStageName().equals("Lobby")) {
                            if (!Main.plugin.getConfig().getBoolean("Scoreboard.LOBBY.enabled")) {
                                continue;
                            }
                            scoreboard.clear();
                            for (final String ln : lineslobby) {
                                scoreboard.add(Utils.replacer(ln, p));
                            }
                            scoreboard.update(p);
                        }
                        else if (Game.getStageName().equals("Pre-Game")) {
                            if (!Main.plugin.getConfig().getBoolean("Scoreboard.PREGAME.enabled")) {
                                continue;
                            }
                            scoreboard.clear();
                            for (final String ln : linespg) {
                                scoreboard.add(Utils.replacer(ln, p));
                            }
                            scoreboard.update(p);
                        }
                        else if (Game.getStageName().equals("Game")) {
                            if (!Main.plugin.getConfig().getBoolean("Scoreboard.LIVEGAME.enabled")) {
                                continue;
                            }
                            scoreboard.clear();
                            for (final String ln : lineslg) {
                                scoreboard.add(Utils.replacer(ln, p));
                            }
                            scoreboard.update(p);
                        }
                        else if (Game.getStageName().equals("Pre-Deathmatch")) {
                            if (!Main.plugin.getConfig().getBoolean("Scoreboard.PREDEATHMATCH.enabled")) {
                                continue;
                            }
                            scoreboard.clear();
                            for (final String ln : linespdm) {
                                scoreboard.add(Utils.replacer(ln, p));
                            }
                            scoreboard.update(p);
                        }
                        else if (Game.getStageName().equals("Deathmatch")) {
                            if (!Main.plugin.getConfig().getBoolean("Scoreboard.DEATHMATCH.enabled")) {
                                continue;
                            }
                            scoreboard.clear();
                            for (final String ln : linesdm) {
                                scoreboard.add(Utils.replacer(ln, p));
                            }
                            scoreboard.update(p);
                        }
                        else {
                            if (!Game.getStageName().equals("Restarting") || !Main.plugin.getConfig().getBoolean("Scoreboard.RESTARTING.enabled")) {
                                continue;
                            }
                            scoreboard.clear();
                            for (final String ln : linesrs) {
                                scoreboard.add(Utils.replacer2(ln, p));
                            }
                            scoreboard.update(p);
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)ScoreboardRegisterListener.plugin, 3L, 3L);
    }
    
    public static void loadBoard() {
        final List<String> lineslobby = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.LOBBY.lines");
        final List<String> linespg = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.PREGAME.lines");
        final List<String> lineslg = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.LIVEGAME.lines");
        final List<String> linespdm = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.PREDEATHMATCH.lines");
        final List<String> linesdm = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.DEATHMATCH.lines");
        final List<String> linesrs = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.RESTARTING.lines");
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (!ScoreboardRegisterListener.helper.containsKey(p)) {
                return;
            }
            final ScoreboardHelper scoreboard = getScoreboardFor(p);
            if (Game.getStageName().equals("Lobby")) {
                if (!Main.plugin.getConfig().getBoolean("Scoreboard.LOBBY.enabled")) {
                    continue;
                }
                scoreboard.clear();
                for (final String ln : lineslobby) {
                    scoreboard.add(Utils.replacer(ln, p));
                }
                scoreboard.update(p);
            }
            else if (Game.getStageName().equals("Pre-Game")) {
                if (!Main.plugin.getConfig().getBoolean("Scoreboard.PREGAME.enabled")) {
                    continue;
                }
                scoreboard.clear();
                for (final String ln : linespg) {
                    scoreboard.add(Utils.replacer(ln, p));
                }
                scoreboard.update(p);
            }
            else if (Game.getStageName().equals("Game")) {
                if (!Main.plugin.getConfig().getBoolean("Scoreboard.LIVEGAME.enabled")) {
                    continue;
                }
                scoreboard.clear();
                for (final String ln : lineslg) {
                    scoreboard.add(Utils.replacer(ln, p));
                }
                scoreboard.update(p);
            }
            else if (Game.getStageName().equals("Pre-Deathmatch")) {
                if (!Main.plugin.getConfig().getBoolean("Scoreboard.PREDEATHMATCH.enabled")) {
                    continue;
                }
                scoreboard.clear();
                for (final String ln : linespdm) {
                    scoreboard.add(Utils.replacer(ln, p));
                }
                scoreboard.update(p);
            }
            else if (Game.getStageName().equals("Deathmatch")) {
                if (!Main.plugin.getConfig().getBoolean("Scoreboard.DEATHMATCH.enabled")) {
                    continue;
                }
                scoreboard.clear();
                for (final String ln : linesdm) {
                    scoreboard.add(Utils.replacer(ln, p));
                }
                scoreboard.update(p);
            }
            else {
                if (!Game.getStageName().equals("Restarting") || !Main.plugin.getConfig().getBoolean("Scoreboard.RESTARTING.enabled")) {
                    continue;
                }
                scoreboard.clear();
                for (final String ln : linesrs) {
                    scoreboard.add(Utils.replacer2(ln, p));
                }
                scoreboard.update(p);
            }
        }
    }
}
