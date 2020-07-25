package eu.cubixmc.com.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.PlayerData;
import eu.cubixmc.com.ranks.Ranks;
import org.bukkit.entity.Player;

public class PlayerDataManager {
	
	private CubixAPI main;
	
	public PlayerDataManager(CubixAPI cubixapi) {
		this.main = cubixapi;
	}

	public void loadPlayerData(Player player) {
		if(!main.dataPlayers.containsKey(player)) {
			PlayerData pData = createPlayerData(player);
			main.dataPlayers.put(player, pData);
		}
	}
	
	public void savePlayerData(Player player) {
		if(main.dataPlayers.containsKey(player)) {
			updatePlayerData(player);
		}
	}
	
	public void createAccount(Player player) {
		if(!hasAccount(player)) {
			try {
				PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("INSERT INTO players(uuid,coins,credits,grade) VALUES (?,?,?,?)");
				q.setString(1, player.getUniqueId().toString());
				q.setInt(2, 100);
				q.setInt(3, 0);
				q.setInt(4, Ranks.PLAYER.getPower());
				q.execute();
				q.close();
				
				PreparedStatement q1 = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("INSERT INTO pvpbox(uuid,kills,deaths) VALUES (?,?,?)");
				q1.setString(1, player.getUniqueId().toString());
				q1.setInt(2, 0);
				q1.setInt(3, 0);
				q1.execute();
				q1.close();
				
				PreparedStatement q2 = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("INSERT INTO utils(uuid) VALUES (?)");
				q2.setString(1, player.getUniqueId().toString());
				q2.execute();
				q2.close();
				
				PreparedStatement q3 = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("INSERT INTO cosmetics(uuid) VALUES (?)");
				q3.setString(1, player.getUniqueId().toString());
				q3.execute();
				q3.close();
				
				PreparedStatement q4 = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("INSERT INTO survivalgames(uuid) VALUES (?)");
				q4.setString(1, player.getUniqueId().toString());
				q4.execute();
				q4.close();
				
				PreparedStatement q5 = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("INSERT INTO hideandseek(uuid) VALUES (?)");
				q5.setString(1, player.getUniqueId().toString());
				q5.execute();
				q5.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean hasAccount(Player player) {
	
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT uuid FROM players WHERE uuid = ?");
			q.setString(1,  player.getUniqueId().toString());
			ResultSet result = q.executeQuery();
			boolean hasAccount = result.next();
			q.close();
			return hasAccount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean hasAccount(UUID playerUUID) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT uuid FROM players WHERE uuid = ?");
			q.setString(1,  playerUUID.toString());
			ResultSet result = q.executeQuery();
			boolean hasAccount = result.next();
			q.close();
			return hasAccount;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public PlayerData createPlayerData(Player player) {
		
		if(!main.dataPlayers.containsKey(player)) {
			try {
				PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT coins, credits, grade, expiredate, expirerank FROM players WHERE uuid = ?");
				q.setString(1, player.getUniqueId().toString());
				ResultSet rs = q.executeQuery();
				
				int rankvalue = 0;
				int coins = 0;
				int credits = 0;
				Ranks rank = Ranks.PLAYER;
				
				while(rs.next()) {
					coins = rs.getInt("coins");
					credits = rs.getInt("credits");
					rank = Ranks.powerToRank(rs.getInt("grade"));
					rankvalue = rs.getInt("expirerank");
					
					Timestamp now = new Timestamp(new Date().getTime());
					Timestamp expire = rs.getTimestamp("expiredate");
					boolean hasExpireRank = expire.before(now);
					
					if(hasExpireRank && rankvalue == 1) {
						rank = Ranks.PLAYER;
					}
				}
				q.close();
				PlayerData pData = new PlayerData();
				pData.setCoins(coins);
				pData.setCredits(credits);
				pData.setRank(rank);
				
				return pData;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return new PlayerData();
	}
	
	public void updatePlayerData(Player player) {
		
		if(main.dataPlayers.containsKey(player)) {
			
			PlayerData pData = main.dataPlayers.get(player);
			int coins = pData.getCoins();
			int credits = pData.getCredits();
			Ranks rank = pData.getRank();
			int power = rank.getPower();
			
			try {
				PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE players SET grade = ?, coins = ?, credits = ? WHERE uuid = ?");
				q.setInt(1, power);
				q.setInt(2, coins);
				q.setInt(3, credits);
				q.setString(4, player.getUniqueId().toString());
				q.executeUpdate();
				q.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		
	}

}
