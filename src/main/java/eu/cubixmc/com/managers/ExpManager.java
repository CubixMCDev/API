package eu.cubixmc.com.managers;

import eu.cubixmc.com.CubixAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ExpManager {
	
	private CubixAPI main;
	protected String table = "players";
	
	public ExpManager(CubixAPI main) {
		this.main = main;
	}
	
	public void setLevel(int level, UUID playeruuid) {
		try {
			PreparedStatement ps = main.getDataBase().getConnection().prepareStatement("UPDATE " + table + " SET level = ? WHERE uuid = ?");
			ps.setInt(1, level);
			ps.setString(2, playeruuid.toString());
			ps.executeUpdate();
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getLevel(UUID playeruuid) {
		int exp = 0;
		try {
			PreparedStatement ps = main.getDataBase().getConnection().prepareStatement("SELECT level FROM " + table + " WHERE uuid = ?");
			ps.setString(1, playeruuid.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				exp = rs.getInt("level");
			}
			ps.close();
			return exp;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return exp;
	}
	
	public void addExp(int amount, UUID playeruuid) {
		try {
			PreparedStatement ps = main.getDataBase().getConnection().prepareStatement("UPDATE " + table + " SET exp = ? WHERE uuid = ?");
			ps.setInt(1, getExp(playeruuid) + amount);
			ps.setString(2, playeruuid.toString());
			ps.executeUpdate();
			ps.close();
			checkLvlUp(playeruuid);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void checkLvlUp(UUID playeruuid) {
		if(getExp(playeruuid) == getXPfromLevel(getLevel(playeruuid) + 1)) setLevel(getLevel(playeruuid) + 1, playeruuid);
	}

	public int getExp(UUID playeruuid) {
		int exp = 0;
		try {
			PreparedStatement ps = main.getDataBase().getConnection().prepareStatement("SELECT exp FROM " + table + " WHERE uuid = ?");
			ps.setString(1, playeruuid.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				exp = rs.getInt("exp");
			}
			ps.close();
			return exp;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return exp;
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
}
