package eu.cubixmc.com.managers;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.User;
import org.bukkit.entity.Player;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class UserManager {

    private final CubixAPI plugin;
    private final HashMap<UUID, User> users;

    public UserManager(CubixAPI plugin) {
        this.plugin = plugin;
        this.users = new HashMap<>();
    }

    public User getUser(Player player) {
        return getUser(player.getUniqueId());
    }

    public User getUser(UUID uuid) {
        if (users.containsKey(uuid)) {
            return users.get(uuid);
        } else {
            return null;
        }
    }

    private void createNewUser(UUID uuid) {
        User user = new User(uuid);
        users.put(uuid, user);
        plugin.getDatabaseManager().performAsyncUpdate("INSERT INTO `players`(`uuid`) VALUES(?);", user.getUuid().toString());
    }

    public void fetchUserFromDataBase(UUID playerUUID) {

        if(!hasAccount(playerUUID)){
            createNewUser(playerUUID);
            return;
        }

        try {
            CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT uuid, coins, credits, primary_rank, secondary_rank, perms, exp, level FROM players WHERE uuid = ?", playerUUID.toString());
            if (set.next()) {
                UUID uuid = UUID.fromString(set.getString("uuid"));
                User user = new User(uuid);
                user.setCoins(set.getInt("coins"));
                user.setCredits(set.getInt("credits"));
                user.setPrimaryRank(plugin.getIdToRank().get(set.getString("primary_rank").toLowerCase()));
                if(!(set.getString("secondary_rank").equalsIgnoreCase("NONE"))) {
                    user.setSecondaryRank(plugin.getIdToRank().get(set.getString("secondary_rank").toLowerCase()));
                }
                user.loadPerms(set.getString("perms"), plugin);
                user.setLevel(plugin.get().getLvlfromExp(set.getInt("exp")));
                user.setExp(set.getInt("exp"));
                users.put(uuid, user);
            }
        } catch (SQLException e) {
            System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
            e.printStackTrace();
        }
    }

    public void saveUsers() {
        for (User user : users.values()) {
            saveUser(user);
        }
    }

    public void saveUser(User user) {
        if(user.getSecondaryRank() != null) {
            plugin.getDatabaseManager().performAsyncUpdate("UPDATE players SET coins = ?, credits = ?, primary_rank = ?, secondary_rank = ?, perms = ?, exp = ?, level = ? WHERE uuid = ?",
                    user.getCoins(), user.getCredits(), user.getPrimaryRank().getId(), user.getSecondaryRank().getId(), user.permsToStr(), user.getExp(), user.getLevel(), user.getUuid().toString());
        } else {
            plugin.getDatabaseManager().performAsyncUpdate("UPDATE players SET coins = ?, credits = ?, primary_rank = ?, perms = ?, exp = ?, level = ? WHERE uuid = ?",
                    user.getCoins(), user.getCredits(), user.getPrimaryRank().getId(), user.permsToStr(), user.getExp(), user.getLevel(), user.getUuid().toString());
        }
    }

    public void fetchUsers() {
        plugin.getDatabaseManager().performAsyncQuery("SELECT * FROM players", set -> {
            while (set.next()) {
                UUID uuid = UUID.fromString(set.getString("uuid"));
                User user = new User(uuid);
                user.setCoins(set.getInt("coins"));
                user.setCredits(set.getInt("credits"));
                user.setPrimaryRank(plugin.getIdToRank().get(set.getString("primary_rank").toLowerCase()));
                if(!(set.getString("secondary_rank").equalsIgnoreCase("NONE"))) {
                    user.setSecondaryRank(plugin.getIdToRank().get(set.getString("secondary_rank").toLowerCase()));
                }
                user.loadPerms(set.getString("perms"), plugin);
                user.setLevel(set.getInt("level"));
                user.setExp(set.getInt("exp"));
                users.put(uuid, user);
            }
        });
    }

    public void removeUser(UUID playerUUID) {
        if(users.containsKey(playerUUID)) users.remove(playerUUID);
    }

    public boolean hasAccount(UUID playerUUID) {
        try {
            CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT uuid FROM players WHERE uuid = ?", playerUUID.toString());
            if (set.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
            e.printStackTrace();
        }
        return false;
    }
}
