package fr.cubixmc.api.managers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.cubixmc.api.CubixAPI;
import fr.cubixmc.api.data.PlayerData;

public class EcoManager {

	private CubixAPI main;
	
	public EcoManager(CubixAPI cubixapi) {
		this.main = cubixapi;
	}
	
	public int getBalanceCoins(Player player) {
		
		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			return pData.getCoins();
		}
		
        return 0;
		
	}
	
	public int getBalanceCoins(UUID playerUUID) {
		
		Player player = Bukkit.getPlayer(playerUUID);

		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			return pData.getCoins();
		}
		
        return 0;
		
	}
	
	public void addCoins(Player player, int amount) {
		
		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			int coins = pData.getCoins() + amount;
			pData.setCoins(coins);
			main.dataPlayers.put(player, pData);
		}
		
	}
	
	public void removeCoins(Player player, int amount) {
		
		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			int coins = pData.getCoins() - amount;
			
			if(coins <= 0) {
				return;
			}
			
			pData.setCoins(coins);
			main.dataPlayers.put(player, pData);
		}
		
	}
	
	public int getBalanceCredits(Player player) {
		
		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			return pData.getCredits();
		}
		
        return 0;
		
	}
	
	public int getBalanceCredits(UUID playerUUID) {
		
		Player player = Bukkit.getPlayer(playerUUID);

		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			return pData.getCredits();
		}
		
        return 0;
		
	}
	
	
	public void addCredits(Player player, int amount) {
		
		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			int credits = pData.getCredits() + amount;
			pData.setCredits(credits);
			main.dataPlayers.put(player, pData);
		}
		
	}
	
	public void removeCredits(Player player, int amount) {
		
		if(main.dataPlayers.containsKey(player)) {
			PlayerData pData = main.dataPlayers.get(player);
			int credits = pData.getCredits() - amount;
			
			if(credits <= 0) {
				return;
			}
			
			pData.setCredits(credits);
			main.dataPlayers.put(player, pData);
		}
		
	}
	
}
