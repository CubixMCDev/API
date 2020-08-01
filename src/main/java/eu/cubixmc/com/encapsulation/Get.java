package eu.cubixmc.com.encapsulation;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.User;
import eu.cubixmc.com.sql.MysqlManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class Get {

    private CubixAPI plugin;

    public Get(CubixAPI plugin) { this.plugin = plugin; }

    //database
    public MysqlManager getDataBase() {
        return plugin.getDatabaseManager();
    }

    //mod
    public boolean isInMod(UUID playerUUID) {
        try {
            CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT modmode FROM utils WHERE uuid = ?", playerUUID.toString());
            if (set.next()) {
                if(set.getInt("modmode") == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
            e.printStackTrace();
        }
        return false;
    }

    // economie
    public int getCoins(UUID playerUUID) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            return user.getCoins();
        }
        return 0;
    }

    public int getCredits(UUID playerUUID) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            return user.getCredits();
        }
        return 0;
    }

    // rank
    public String getRankID(UUID playerUUID) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            return user.getRankID();
        }
        return "player";
    }

    public String getRankWithColors(UUID playerUUID) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            return user.getRankToStringWithColor();
        }
        return "§7Player";
    }

    public ChatColor getRankColor(UUID playerUUID) {
        return ChatColor.valueOf(plugin.getConfig().getString("ranks." + getRankID(playerUUID) + ".rankColor"));
    }

    //exp
    public int getExp(UUID playerUUID) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            return user.getExp();
        }
        return 0;
    }

    public int getLevel(UUID playerUUID) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            return getLvlfromExp(user.getExp());
        }
        return 0;
    }

    public int getXPfromLevel(int level) {
        if(level >= 1 && level <= 16) {
            return (int)(Math.pow(level, 2) + 6 * level);
        }
        else if(level >= 17 && level <= 31) {
            return (int)( 2.5 * Math.pow(level, 2) - 40.5 * level + 360);
        }
        else if(level >= 32) {
            return (int)(4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
        }else {
            return 0;
        }
    }

    public int getLvlfromExp(int exp) {
        if(exp >= 0 && exp <= 352) {
            int level = polynome(1, 6, -exp);
            return level;
        }else if(exp >= 353 && exp <= 1624) {
            int level = polynome(2.5, -40.5, 360-exp);
            return level;
        }else if(exp >= 1625) {
            int level = polynome(4.5, -162.5, 2220-exp);
            return level;
        }else {
            return 0;
        }
    }

    public static int polynome(double a, double b, double c) {
        ArrayList<Integer> solutions = new ArrayList<Integer>();
        int delta = (int) (Math.pow(b, 2) - 4 * a * c);
        if (delta > 0) {
            solutions.add((int) Math.ceil(((-b + Math.sqrt(delta)) / (2*a))));
            // solutions.add((int) ((-b + Math.sqrt(delta)) / 2*a));
        }else if(delta == 0) {
            solutions.add((int) Math.ceil((- b / (2*a))));
        }else if (delta < 0) {
            solutions.add(null);
        }
        return solutions.get(0);
    }

    //ban
    public boolean isBanned(UUID playerUUID) {
        return plugin.getBanManager().isBanned(playerUUID);
    }

    //mute
    public boolean isMuted(UUID playerUUID) {
        return plugin.getMuteManager().isMuted(playerUUID);
    }
}
