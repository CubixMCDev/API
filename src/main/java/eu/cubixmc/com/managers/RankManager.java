package eu.cubixmc.com.managers;

import java.util.UUID;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.PlayerData;
import eu.cubixmc.com.ranks.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RankManager {

	private CubixAPI main;
	
	public RankManager(CubixAPI api) {
		this.main = api;
	}
	
	public void setRank(Player player, Ranks rank) {
		
		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			pData.setRank(rank);
		}
		
	}
	
	public Ranks getRank(Player player) {
		
		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			return pData.getRank();
		}
		
		return Ranks.PLAYER;
	}
	
	public Ranks getRank(UUID playerUUID) {
		
		Player player = Bukkit.getPlayer(playerUUID);
		
		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			return pData.getRank();
		}
		
		return Ranks.PLAYER;
	}
	
}
