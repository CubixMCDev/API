package eu.cubixmc.com.managers.friendsAndParty;

import eu.cubixmc.com.CubixAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	
	public boolean hasAllowedMessages(UUID playerUUID) {
		try {
            PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT msg_allow FROM utils WHERE uuid = ?");
            q.setString(1, playerUUID.toString());
 
            int awnser = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
            	awnser = rs.getInt("msg_allow");
            }
            
            q.close();
           
            if(awnser == 1) {
            	return true;
            } else {
            	return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	
	public void setAllowMessages(UUID playerUUID, int nbr) {
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE utils SET msg_allow = ? WHERE uuid = ?");
			q.setInt(1, nbr);
			q.setString(2, playerUUID.toString());
			q.executeUpdate();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
