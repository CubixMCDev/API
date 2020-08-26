package fr.cubixmc.api.managers.friendsAndParty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.cubixmc.api.CubixAPI;

public class FriendsManager {
	
	private CubixAPI main;
	
	public FriendsManager(CubixAPI main) {
		this.main = main;
	}

	protected String table = "friends";

	public int isAllowed(UUID player){
		try {
			PreparedStatement ps = main.getDataBase().getConnection().prepareStatement("SELECT friend_allow FROM utils WHERE uuid = ?");
			ps.setString(1, player.toString());
			ResultSet rs = ps.executeQuery();
			int allow = 0;
			while(rs.next()) {
				allow = rs.getInt("friend_allow");
			}
			ps.close();
			return allow;
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void setAllow(int allow, UUID player) {
		try {
			PreparedStatement ps = main.getDataBase().getConnection().prepareStatement("UPDATE utils SET friend_allow = ? WHERE uuid = ?");
			ps.setInt(1, allow);
			ps.setString(2, player.toString());
			ps.executeUpdate();
			ps.close();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<UUID> getFriends(UUID player){
		List<UUID> friends = new ArrayList<UUID>();
		try {
			PreparedStatement ps = main.getDataBase().getConnection().prepareStatement("SELECT friend_uuid FROM " + table + " WHERE player_uuid = ?");
			ps.setString(1, player.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				friends.add(UUID.fromString(rs.getString("friend_uuid")));
			}
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
			return friends;
		}
		return friends;
	}
	
	public List<String> getFriendsNames(UUID player){
		List<String> friends = new ArrayList<String>();
		try {
			PreparedStatement ps = main.getDataBase().getConnection().prepareStatement("SELECT friend_name FROM " + table + " WHERE player_uuid = ?");
			ps.setString(1, player.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				friends.add(rs.getString("friend_name"));
			}
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
			return friends;
		}
		return friends;
	}
	
	public boolean isFriendWith(UUID player, UUID secondFriend) {
		if(getFriends(player).contains(secondFriend)) return true;
		return false;
	}

}
