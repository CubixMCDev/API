package eu.cubixmc.com.managers;

import eu.cubixmc.com.CubixAPI;

import javax.sql.rowset.CachedRowSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendsManager {

	private CubixAPI plugin;
	
	public FriendsManager(CubixAPI plugin) {
		this.plugin = plugin;
	}

	public int isAllowed(UUID player){
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT friend_allow FROM utils WHERE uuid = ?", player.toString());
			if (set.next()) {
				return set.getInt("friend_allow");
			}
		} catch (SQLException e) {
			System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setAllow(int allow, UUID player) {
		plugin.getDatabaseManager().performAsyncUpdate("UPDATE utils SET friend_allow = ? WHERE uuid = ?", allow, player.toString());
	}
	
	public List<UUID> getFriends(UUID player){
		List<UUID> friends = new ArrayList<UUID>();
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT friend_uuid FROM friends WHERE player_uuid = ?", player.toString());
			while (set.next()) {
				friends.add(UUID.fromString(set.getString("friend_uuid")));
			}
		} catch (SQLException e) {
			System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
			e.printStackTrace();
			return friends;
		}
		return friends;
	}
	
	public List<String> getFriendsNames(UUID player){

		List<String> friends = new ArrayList<String>();
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT friend_name FROM friends WHERE player_uuid = ?", player.toString());
			while (set.next()) {
				friends.add(set.getString("friend_name"));
			}
		} catch (SQLException e) {
			System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
			e.printStackTrace();
			return friends;
		}
		return friends;
	}
	
	public boolean isFriendWith(UUID player, UUID secondFriend) {
		if(getFriends(player).contains(secondFriend)) return true;
		return false;
	}
	
	public boolean hasAllowedMessages(UUID playerUUID) {
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT msg_allow FROM utils WHERE uuid = ?", playerUUID.toString());
			if (set.next()) {
				if(set.getInt("msg_allow") == 1) {
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
	
	public void setAllowMessages(UUID playerUUID, int nbr) {
		plugin.getDatabaseManager().performAsyncUpdate("UPDATE utils SET msg_allow = ? WHERE uuid = ?", nbr, playerUUID.toString());
	}

}
