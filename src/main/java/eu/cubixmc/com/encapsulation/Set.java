package eu.cubixmc.com.encapsulation;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.User;
import eu.cubixmc.com.ranks.Rank;
import org.bukkit.Bukkit;

import java.util.UUID;

public class Set {

    private CubixAPI plugin;

    public Set(CubixAPI plugin) { this.plugin = plugin; }

    public void addCoins(UUID playerUUID, int amount) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            user.addCoins(amount);
        }
    }

    public void removeCoins(UUID playerUUID, int amount) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            user.removeCoins(amount);
        }
    }

    public void addCredits(UUID playerUUID, int amount) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            user.addCredits(amount);
        }
    }

    public void removeCredits(UUID playerUUID, int amount) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            user.removeCredits(amount);
        }
    }

    public void setRank(UUID playerUUID, String rank, boolean isPrimary) {
        if(isPrimary) {
            plugin.getDatabaseManager().performAsyncUpdate("UPDATE players SET primary_rank = ? WHERE uuid = ?", rank, playerUUID.toString());
        } else {
            plugin.getDatabaseManager().performAsyncUpdate("UPDATE players SET secondary_rank = ? WHERE uuid = ?", rank, playerUUID.toString());
        }
    }

    public void setExp(UUID playerUUID, int amount) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            user.addExp(amount);
        }
    }

    public void setLevel(UUID playerUUID, int level) {
        if(Bukkit.getPlayer(playerUUID).isOnline()) {
            User user = plugin.getUserManager().getUser(playerUUID);
            user.setLevel(level);
        }
    }
}
