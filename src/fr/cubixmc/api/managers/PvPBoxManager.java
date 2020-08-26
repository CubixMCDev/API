package fr.cubixmc.api.managers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.mysql.jdbc.PreparedStatement;

import fr.cubixmc.api.CubixAPI;

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
	
	public boolean hasSorcierKit(Player player) {

		try {
            PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT hassorcier FROM pvpbox WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int awnser = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
            	awnser = rs.getInt("hassorcier");
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
	
	public void setSorcierKit(Player player) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE pvpbox SET hassorcier = ? WHERE uuid = ?");
			q.setInt(1, 1);
			q.setString(2, player.getUniqueId().toString());
			q.executeUpdate();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean hasTankKit(Player player) {

		try {
            PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT hastank FROM pvpbox WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int awnser = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
            	awnser = rs.getInt("hastank");
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
	
	public void setTankKit(Player player) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE pvpbox SET hastank = ? WHERE uuid = ?");
			q.setInt(1, 1);
			q.setString(2, player.getUniqueId().toString());
			q.executeUpdate();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean hasVampireKit(Player player) {

		try {
            PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT hasvampire FROM pvpbox WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());
 
            int awnser = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
            	awnser = rs.getInt("hasvampire");
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
	
	public void setVampireKit(Player player) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE pvpbox SET hasvampire = ? WHERE uuid = ?");
			q.setInt(1, 1);
			q.setString(2, player.getUniqueId().toString());
			q.executeUpdate();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
