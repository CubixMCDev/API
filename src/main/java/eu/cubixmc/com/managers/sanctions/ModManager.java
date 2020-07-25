package eu.cubixmc.com.managers.sanctions;

import eu.cubixmc.com.CubixAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ModManager {

	private CubixAPI main;
	
	public ModManager(CubixAPI main) {
		this.main = main;
	}
	
	public void setInMod(UUID playerUUID) {
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE utils SET modmode = 1 WHERE uuid = ?");
			q.setString(1, playerUUID.toString());
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeFromMod(UUID playerUUID) {
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE utils SET modmode = 0 WHERE uuid = ?");
			q.setString(1, playerUUID.toString());
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isInMod(UUID playerUUID) {
		try {
            PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT modmode FROM utils WHERE uuid = ?");
            q.setString(1, playerUUID.toString());
 
            int awnser = 0;
            ResultSet rs = q.executeQuery();
           
            while(rs.next()){
            	awnser = rs.getInt("modmode");
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
}
