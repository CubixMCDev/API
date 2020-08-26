package eu.cubixmc.com.managers;

import eu.cubixmc.com.CubixAPI;
import org.bukkit.Bukkit;

import javax.sql.rowset.CachedRowSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ModManager {

	private CubixAPI plugin;
	
	public ModManager(CubixAPI plugin) {
		this.plugin = plugin;
	}
	
	public void setInMod(UUID playerUUID) {
		plugin.getDatabaseManager().performUpdate("UPDATE utils SET modmode = 1 WHERE uuid = ?", playerUUID.toString());
	}
	
	public void removeFromMod(UUID playerUUID) {
		plugin.getDatabaseManager().performUpdate("UPDATE utils SET modmode = 0 WHERE uuid = ?", playerUUID.toString());
	}
	
	public boolean isInMod(UUID playerUUID) {
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT modmode FROM utils WHERE uuid = ?", playerUUID.toString());
			if (set.next()) {
				if(set.getInt("modmode") == 1) {
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
}
