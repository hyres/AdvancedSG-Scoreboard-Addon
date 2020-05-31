
package scha.efer.sbaddon.boards;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;
import java.util.Iterator;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreBoardBuilder
{
    private boolean reset;
    private Scoreboard scoreboard;
    private Objective scoreObjective;
    private Objective nameHealthObj;
    private Objective tablistHealthObj;
    private Team spectator;
    
    public ScoreBoardBuilder(final String score_name, final boolean health, final boolean spect) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        (this.scoreObjective = this.scoreboard.registerNewObjective(score_name, "dummy")).setDisplaySlot(DisplaySlot.SIDEBAR);
        if (health) {
            (this.tablistHealthObj = this.scoreboard.registerNewObjective("tablife", "dummy")).setDisplaySlot(DisplaySlot.PLAYER_LIST);
            (this.nameHealthObj = this.scoreboard.registerNewObjective("namelife", "health")).setDisplaySlot(DisplaySlot.BELOW_NAME);
            this.nameHealthObj.setDisplayName(ChatColor.DARK_RED + "\u2764");
        }
        if (spect) {
            (this.spectator = this.scoreboard.registerNewTeam("spec")).setAllowFriendlyFire(false);
            this.spectator.setCanSeeFriendlyInvisibles(true);
        }
    }
    
    public void updateSpectPlayer(final Player p) {
        if (!this.spectator.hasPlayer((OfflinePlayer)p)) {
            this.spectator.addPlayer((OfflinePlayer)p);
        }
        this.spectator.setPrefix("�7");
        this.spectator.setCanSeeFriendlyInvisibles(true);
    }
    
    public void updatelife() {
        for (final Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            final Player player4;
            final Player player3 = player4 = onlinePlayers;
            this.nameHealthObj.getScore(player3.getName()).setScore((int)player4.getHealth() + this.absorpHearts(player4));
            this.tablistHealthObj.getScore(player3.getName()).setScore((int)player4.getHealth() + this.absorpHearts(player4));
        }
    }
    
    private int absorpHearts(final Player pl) {
        for (final PotionEffect pe : pl.getActivePotionEffects()) {
            if (pe.getType().equals((Object)PotionEffectType.ABSORPTION)) {
                final int amt = pe.getAmplifier() * 2 + 2;
                return amt;
            }
        }
        return 0;
    }
    
    public String color(final String s) {
        return s.replaceAll("&", "�");
    }
    
    public void setName(String substring) {
        if (substring.length() > 32) {
            substring = substring.substring(0, 32);
        }
        this.scoreObjective.setDisplayName(this.color(substring));
    }
    
    public void lines(final Integer n, String substring) {
        final Team team = this.scoreboard.getTeam("SCT_" + n);
        if (substring.length() > 32) {
            substring = substring.substring(0, 32);
        }
        final String[] splitStringLine = this.splitStringLine(substring);
        if (team == null) {
            final Team registerNewTeam = this.scoreboard.registerNewTeam("SCT_" + n);
            registerNewTeam.addEntry(this.getEntry(n));
            this.setPrefix(registerNewTeam, splitStringLine[0]);
            this.setSuffix(registerNewTeam, splitStringLine[1]);
            this.scoreObjective.getScore(this.getEntry(n)).setScore((int)n);
        }
        else {
            this.setPrefix(team, splitStringLine[0]);
            this.setSuffix(team, splitStringLine[1]);
        }
    }
    
    public void setPrefix(final Team team, final String prefix) {
        if (prefix.length() > 16) {
            team.setPrefix(prefix.substring(0, 16));
            return;
        }
        team.setPrefix(prefix);
    }
    
    public void setSuffix(final Team team, final String s) {
        if (s.length() > 16) {
            team.setSuffix(this.maxChars(16, s));
        }
        else {
            team.setSuffix(s.substring(0, s.length()));
        }
    }
    
    public String maxChars(final int n, final String s) {
        if (ChatColor.translateAlternateColorCodes('&', s).length() > n) {
            return s.substring(0, n);
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    
    public String getEntry(final Integer n) {
        if (n == 0) {
            return "�0";
        }
        if (n == 1) {
            return "�1";
        }
        if (n == 2) {
            return "�2";
        }
        if (n == 3) {
            return "�3";
        }
        if (n == 4) {
            return "�4";
        }
        if (n == 5) {
            return "�5";
        }
        if (n == 6) {
            return "�6";
        }
        if (n == 7) {
            return "�7";
        }
        if (n == 8) {
            return "�8";
        }
        if (n == 9) {
            return "�9";
        }
        if (n == 10) {
            return "�a";
        }
        if (n == 11) {
            return "�b";
        }
        if (n == 12) {
            return "�c";
        }
        if (n == 13) {
            return "�d";
        }
        if (n == 14) {
            return "�e";
        }
        if (n == 15) {
            return "�f";
        }
        return "";
    }
    
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
    
    public boolean isReset() {
        return this.reset;
    }
    
    public void setReset(final boolean reset) {
        this.reset = reset;
    }
    
    public void build(final Player player) {
        player.setScoreboard(this.scoreboard);
    }
    
    private String[] splitStringLine(final String s) {
        final StringBuilder sb = new StringBuilder(s.substring(0, (s.length() >= 16) ? 16 : s.length()));
        final StringBuilder sb2 = new StringBuilder((s.length() > 16) ? s.substring(16) : "");
        if (sb.toString().length() > 1 && sb.charAt(sb.length() - 1) == '�') {
            sb.deleteCharAt(sb.length() - 1);
            sb2.insert(0, '�');
        }
        String string = "";
        for (int i = 0; i < sb.toString().length(); ++i) {
            if (sb.toString().charAt(i) == '�' && i < sb.toString().length() - 1) {
                string = String.valueOf(String.valueOf(String.valueOf(string))) + "�" + sb.toString().charAt(i + 1);
            }
        }
        String string2 = new StringBuilder().append((Object)sb2).toString();
        if (sb.length() > 14) {
            string2 = (string.isEmpty() ? ("�" + string2) : (String.valueOf(String.valueOf(String.valueOf(String.valueOf(string)))) + string2));
        }
        return new String[] { (sb.toString().length() > 16) ? sb.toString().substring(0, 16) : sb.toString(), (string2.toString().length() > 16) ? string2.toString().substring(0, 16) : string2.toString() };
    }
}
