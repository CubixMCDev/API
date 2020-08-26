package fr.cubixmc.api.managers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.cubixmc.api.CubixAPI;
import fr.cubixmc.api.data.PlayerData;
import fr.cubixmc.api.ranks.Ranks;

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
