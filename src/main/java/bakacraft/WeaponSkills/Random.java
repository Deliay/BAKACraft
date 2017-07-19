package bakacraft.WeaponSkills;

import org.bukkit.ChatColor;

/**
 * Created by admin on 2017/7/7.
 */
public final class Random {
    private final static java.util.Random rd = new java.util.Random();

    public final static double GetRandom() {
        return rd.nextDouble() * 100;
    }

    public final static boolean TestRandom(double percent) {
        return GetRandom() < percent;
    }

    public final static int RandomRange(int end) {
        return rd.nextInt(end);
    }

    public final static String Colorilize(String raw) {
        return ChatColor.translateAlternateColorCodes('&', raw);
    }
}
