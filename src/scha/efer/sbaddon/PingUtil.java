
package scha.efer.sbaddon;

import org.bukkit.Bukkit;
import java.util.regex.Pattern;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;

public class PingUtil
{
    public static int getPlayerPing(final Player player) {
        try {
            final int ping = 0;
            final Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + getServerVersion() + ".entity.CraftPlayer");
            final Object converted = craftPlayer.cast(player);
            final Method handle = converted.getClass().getMethod("getHandle", (Class<?>[])new Class[0]);
            final Object entityPlayer = handle.invoke(converted, new Object[0]);
            final Field pingField = entityPlayer.getClass().getField("ping");
            return pingField.getInt(entityPlayer);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static String getServerVersion() {
        final Pattern brand = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");
        final String pkg = Bukkit.getServer().getClass().getPackage().getName();
        String version = pkg.substring(pkg.lastIndexOf(46) + 1);
        if (!brand.matcher(version).matches()) {
            version = "";
        }
        return version;
    }
}
