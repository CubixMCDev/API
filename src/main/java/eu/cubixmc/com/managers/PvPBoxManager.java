package eu.cubixmc.com.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.cubixmc.com.CubixAPI;
import org.bukkit.entity.Player;

public class PvPBoxManager {

	private CubixAPI main;
	
	public PvPBoxManager(CubixAPI api) {
		this.main = api;
	}
	
	public boolean hasPvPBoxAccount(Player player) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT uuid FROM pvpbox WHERE uuid = ?");
			q.setString(1,  player.getUniqueId().toString());
			ResultSet result = q.executeQuery();
			boolean hasAccount = result.next();
			q.execute();
			q.close();
			return hasAccount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public void createPvPBoxAccount(Player player) {
		if(!hasPvPBoxAccount(player)) {
			try {
				PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("INSERT INTO pvpbox(uuid,kills,deaths) VALUES (?,?,?)");
				q.setString(1, player.getUniqueId().toString());
				q.setInt(2, 0);
				q.setInt(3, 0);
				q.execute();
				q.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getKills(Player player) {
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT kills FROM pvpbox WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());
			int kills = 0;
			ResultSet rs = q.executeQuery();
			while(rs.next()) {
				kills = rs.getInt("kills");
			}
			q.close();
			return kills;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getDeaths(Player player) {
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT deaths FROM pvpbox WHERE uuid = ?");
			q.setString(1, player.getUniqueId().toString());
			int deaths = 0;
			ResultSet rs = q.executeQuery();
			while(rs.next()) {
				deaths = rs.getInt("deaths");
			}
			q.close();
			return deaths;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
