
package scha.efer.sbaddon;

import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import org.bukkit.entity.Player;
import e.Game;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import e.zb;
import java.util.HashMap;

public class Timer
{
    public static String key;
    public static String site;
    public static int lm;
    public static int ls;
    public static int game_m;
    public static int game_s;
    public static int end_m;
    public static int end_s;
    public static int restart;
    public static int pregame;
    public static int pg;
    public static int predm;
    public static int dm;
    public static int ds;
    static int secondsPG;
    static int minutesPG;
    public static int lobbytime;
    public static int minplayers;
    public static int pregametime;
    public static int gametime;
    public static int restartingtime;
    public static int dmtime;
    public static int predmtime;
    static int secondsLB;
    static int minutesLB;
    static int mnt;
    static int scd;
    static HashMap<String, Integer> oldtime;
    public static boolean timerE;
    
    static {
        Timer.key = Main.plugin.getConfig().getString("license-key");
        Timer.site = "https://crispymc.tk/license/verify.php";
        Timer.lm = 2;
        Timer.ls = 0;
        Timer.game_m = 29;
        Timer.game_s = 60;
        Timer.end_m = 6;
        Timer.end_s = 60;
        Timer.restart = 15;
        Timer.pregame = 30;
        Timer.pg = 30;
        Timer.predm = 10;
        Timer.dm = 2;
        Timer.ds = 60;
        Timer.secondsPG = Timer.pregame % 60;
        Timer.minutesPG = (Timer.pregame - Timer.secondsPG) / 60;
        Timer.lobbytime = ((zb)zb.getPlugin((Class)zb.class)).getConfig().getInt("Main-settings.lobby-time");
        Timer.minplayers = ((zb)zb.getPlugin((Class)zb.class)).getConfig().getInt("Main-settings.min-players");
        Timer.pregametime = ((zb)zb.getPlugin((Class)zb.class)).getConfig().getInt("Main-settings.starting-time");
        Timer.gametime = ((zb)zb.getPlugin((Class)zb.class)).getConfig().getInt("Main-settings.game-time");
        Timer.restartingtime = ((zb)zb.getPlugin((Class)zb.class)).getConfig().getInt("Main-settings.end-time");
        Timer.dmtime = ((zb)zb.getPlugin((Class)zb.class)).getConfig().getInt("Main-settings.dm-time");
        Timer.predmtime = ((zb)zb.getPlugin((Class)zb.class)).getConfig().getInt("Main-settings.dm-prepare-time");
        Timer.mnt = (Timer.lobbytime - Timer.secondsLB) / 60;
        Timer.scd = Timer.lobbytime % 60;
        Timer.oldtime = new HashMap<String, Integer>();
    }
    
    public static void start() {
        Bukkit.getScheduler().cancelTask(Timer.pregametime);
        Bukkit.getScheduler().cancelTask(Timer.lobbytime);
        Bukkit.getScheduler().cancelTask(Timer.pregametime);
        Timer.oldtime.put("lobi", Timer.lobbytime);
        new BukkitRunnable() {
            public void run() {
                if (Game.getStageName().equals("Game")) {
                    if (Timer.game_s == 0 && Timer.game_m == 30) {
                        Timer.game_m = 29;
                        Timer.game_s = 60;
                    }
                    else if (Timer.game_s == 0) {
                        Timer.game_s = 60;
                    }
                    String sure = "";
                    --Timer.game_s;
                    if (Timer.game_s == 60) {
                        Timer.game_s = 59;
                    }
                    else if (Timer.game_s == 50) {
                        sure = "30 seconds";
                    }
                    else if (Timer.game_s == 45) {
                        sure = "45 seconds";
                    }
                    else if (Timer.game_s == 30) {
                        sure = "30 seconds";
                    }
                    else if (Timer.game_s == 15) {
                        sure = "15 seconds";
                    }
                    else if (Timer.game_s == 10) {
                        sure = "10 seconds";
                    }
                    else if (Timer.game_s > 5 || Timer.game_s <= 1) {
                        if (Timer.game_s == 1) {
                            sure = "1 second";
                        }
                        else if (Timer.game_s == 0) {
                            Timer.game_s = 0;
                        }
                    }
                }
                else if (Game.getStageName().equals("Pre-Game")) {
                    String sure = "";
                    --Timer.pg;
                    if (Timer.minutesPG <= 0) {
                        Timer.minutesPG = 0;
                    }
                    --Timer.secondsPG;
                    --Timer.minutesPG;
                    if (Timer.pg == 1) {
                        sure = "1 second";
                    }
                    else if (Timer.pg == 0) {
                        Timer.pg = 30;
                    }
                }
                else if (Game.getStageName().equals("Pre-Deathmatch")) {
                    String sure = "";
                    --Timer.predm;
                    if (Timer.predm == 1) {
                        sure = "1 second";
                    }
                    else if (Timer.predm == 0) {
                        Timer.predm = 10;
                    }
                }
                else if (Game.getStageName().equals("Deathmatch")) {
                    if (Timer.ds == 0 && Timer.dm == 3) {
                        Timer.dm = 2;
                        Timer.ds = 60;
                    }
                    else if (Timer.ds == 0) {
                        Timer.ds = 60;
                    }
                    String sure = "";
                    --Timer.ds;
                    if (Timer.ds == 60) {
                        Timer.ds = 59;
                    }
                    else if (Timer.ds == 50) {
                        sure = "30 seconds";
                    }
                    else if (Timer.ds == 45) {
                        sure = "45 seconds";
                    }
                    else if (Timer.ds == 30) {
                        sure = "30 seconds";
                    }
                    else if (Timer.ds == 15) {
                        sure = "15 seconds";
                    }
                    else if (Timer.ds == 10) {
                        sure = "10 seconds";
                    }
                    else if (Timer.ds > 5 || Timer.ds <= 1) {
                        if (Timer.ds == 1) {
                            sure = "1 second";
                        }
                        else if (Timer.ds == 0) {
                            Timer.ds = 0;
                        }
                    }
                }
                else if (Game.getStageName().equals("Lobby")) {
                    if (Bukkit.getOnlinePlayers().size() > 0 && Bukkit.getOnlinePlayers().size() >= Timer.minplayers) {
                        for (final Player all : Bukkit.getOnlinePlayers()) {
                            Timer.lobbytime = all.getLevel();
                        }
                        --Timer.lobbytime;
                        if (Timer.lobbytime > 0) {
                            Timer.secondsLB = Timer.lobbytime % 60;
                            Timer.minutesLB = (Timer.lobbytime - Timer.secondsLB) / 60;
                        }
                        else {
                            Timer.secondsLB = Timer.oldtime.get("lobi") % 60;
                            Timer.minutesLB = (Timer.lobbytime - Timer.secondsLB) / 60;
                        }
                        if (Timer.minutesLB <= 0) {
                            Timer.minutesLB = 0;
                        }
                        if (Timer.lobbytime <= 0) {
                            Timer.lobbytime = Timer.oldtime.get("lobi");
                        }
                        --Timer.lobbytime;
                    }
                    else {
                        Timer.lobbytime = Timer.oldtime.get("lobi");
                    }
                    if (Bukkit.getOnlinePlayers().size() < Timer.minplayers) {
                        Timer.lobbytime = Timer.oldtime.get("lobi");
                    }
                }
                else if (Game.getStageName().equals("Restarting")) {
                    String sure = "";
                    --Timer.restart;
                    if (Timer.restart == 1) {
                        sure = "1 second";
                    }
                    else if (Timer.restart == 0) {
                        Timer.restart = 15;
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.plugin, 1L, 20L);
        new BukkitRunnable() {
            public void run() {
                if (Game.getStageName().equals("Game")) {
                    --Timer.game_m;
                    if (Timer.game_m == 30) {
                        Timer.game_m = 29;
                    }
                    else if (Timer.game_m != 0 && Timer.game_m < 0) {
                        Timer.game_m = 29;
                    }
                }
                else if (Game.getStageName().equals("Lobby")) {
                    Timer.minutesLB = (Timer.lobbytime - Timer.secondsLB) / 60;
                }
                else if (Game.getStageName().equals("Deathmatch")) {
                    --Timer.dm;
                    if (Timer.dm == 3) {
                        Timer.dm = 2;
                    }
                    else if (Timer.dm != 0 && Timer.dm < 0) {
                        Timer.dm = 2;
                    }
                }
                else {
                    --Timer.lm;
                    if (Timer.lm != 0 && Timer.lm < 0) {
                        Timer.lm = 1;
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.plugin, 0L, 1200L);
    }
}
