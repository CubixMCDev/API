package eu.cubixmc.com.managers.friendsAndParty;

import eu.cubixmc.com.CubixAPI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyManager {

	private CubixAPI main;
	
	public PartyManager(CubixAPI main) {
		this.main = main;
	}
	
	protected String table = "party";

	public boolean isInParty(String player) {
		List<String> leaders = getLeaders();
		if(leaders.contains(player)) return true;
		List<String> members = getMembers();
		for(String member : members) {
			String memberList[] = member.split(";");
			for(String p : memberList) {
				if(p.equalsIgnoreCase(player)) return true;
			}
		}
		return false;
		
	}

	public boolean isLeader(String member) {
		if(getLeaders().contains(member)) return true;
		return false;
	}

	public List<String> getMembers() {
		List<String> members = null;
		List<String> result = new ArrayList<String>();
		try {
			members = SQL_ReceiveL("SELECT members FROM " + table, "members");
			for(String member : members) {
				result.add(member.toString());
			}
			return result;
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<String> getLeaders() {
		List<String> leaders = null;
		List<String> result = new ArrayList<String>();
		try {
			leaders = SQL_ReceiveL("SELECT leader_name FROM " + table, "leader_name");
			for(String leader : leaders) {
				result.add(leader.toString());
			}
			return result;
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<String> getMembers(String player) {
		if(isLeader(player)) {
			List<String> members = null;
			ArrayList<String> players = new ArrayList<String>();
			try {
				members = SQL_ReceiveL("SELECT members FROM " + table + " WHERE leader_name = '" + player + "'", "members");
				return members;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return players;
		} else {
			return null;
		}
	}

	public int getFollow(UUID uuid) {
		int follow = 0;
		follow = (Integer) SQL_Receive("SELECT party_follow FROM utils WHERE uuid = '" + uuid.toString() + "'", "party_follow");
		return follow;
	}
	
	public void setFollow(int follow, UUID playeruuid) {
		try {
			SQL_Update("UPDATE utils SET party_follow = '" + follow + "' WHERE uuid = '" + playeruuid.toString() + "'");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int getAllow(UUID uuid) {
		int allow = 0;
		allow = (Integer) SQL_Receive("SELECT party_allow FROM utils WHERE uuid = '" + uuid.toString() + "'", "party_allow");
		return allow;
	}
	
	public void setAllow(int allow, UUID playeruuid) {
		try {
			SQL_Update("UPDATE utils SET party_allow = '" + allow + "' WHERE uuid = '" + playeruuid.toString() + "'");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Object SQL_Receive(String query, String column) {
		Object obj = null;
		try {
			main.getDataBase().getConnection();
			Statement sql = main.getDataBase().getConnection().createStatement();
			ResultSet rs = sql.executeQuery(query);
			
			while(rs.next()) {
				obj = rs.getObject(column);
			}
			sql.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	private List<String> SQL_ReceiveL(String query, String column) throws ClassNotFoundException{
		List<String> obj = new ArrayList<String>();
		try {
			main.getDataBase().getConnection();
			Statement sql = main.getDataBase().getConnection().createStatement();
			ResultSet rs = sql.executeQuery(query);
			
			while(rs.next()) {
				obj.add(rs.getObject(column).toString());
			}
			sql.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	private void SQL_Update(String query) throws ClassNotFoundException{
		try {
			main.getDataBase().getConnection();
			Statement sql = main.getDataBase().getConnection().createStatement();
			sql.executeUpdate(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
