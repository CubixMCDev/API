package fr.cubixmc.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.cubixmc.api.CubixAPI;
import fr.cubixmc.api.ranks.Ranks;

public class EventsListener implements Listener {

	private CubixAPI main;
	
	public EventsListener(CubixAPI api) {
		this.main = api;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		main.getPlayerDataManager().createAccount(player);
		main.getPvPBoxManager().createPvPBoxAccount(player);
		main.getPlayerDataManager().loadPlayerData(player);
		
		Ranks rank = main.getRankManager().getRank(player);
		if(rank == Ranks.ADMIN) {
			main.s.getTeam("aAdmin").addPlayer(player);
		} else if(rank == Ranks.RESPMOD) {
			main.s.getTeam("bRespMod").addPlayer(player);
		} else if(rank == Ranks.DEV) {
			main.s.getTeam("cDev").addPlayer(player);
		} else if(rank == Ranks.MOD) {
			main.s.getTeam("dMod").addPlayer(player);
		} else if(rank == Ranks.ASSISTANT) {
			main.s.getTeam("eAss").addPlayer(player);
		} else if(rank == Ranks.BUILDER) {
			main.s.getTeam("fBuilder").addPlayer(player);
		} else if(rank == Ranks.PARTNER) {
			main.s.getTeam("gPartner").addPlayer(player);
		} else if(rank == Ranks.FRIEND) {
			main.s.getTeam("hFriend").addPlayer(player);
		} else if(rank == Ranks.YOUTUBE) {
			main.s.getTeam("iYoutube").addPlayer(player);
		} else if(rank == Ranks.VIPPLUS) {
			main.s.getTeam("jVip+").addPlayer(player);
		} else if(rank == Ranks.VIP) {
			main.s.getTeam("kVip").addPlayer(player);
		} else if(rank == Ranks.PLAYER) {
			main.s.getTeam("lPlayer").addPlayer(player);
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		main.getPlayerDataManager().savePlayerData(player);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		
		Ranks rank = main.getRankManager().getRank(player);
		
		if(main.muted.containsKey(player.getUniqueId())) {
			if(main.getMuteManager().isMuted(player.getUniqueId())) {
				player.sendMessage("§cVous êtes mute !");
				e.setCancelled(true);
				return;
			} else {
				main.muted.remove(player.getUniqueId());
			}
		}
		
		//   §8\u2758 
		/*
		if(e.getMessage().contains(player.getName())) {
			e.setMessage(e.getMessage().replaceAll(player.getName(), ChatColor.GOLD + "§l" + player.getName() + ChatColor.RESET + ""));
			e.setCancelled(true);
			Player allPlayer = (Player) Bukkit.getServer().getOnlinePlayers();
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 10);
		}
		*/
		if(rank.getPower() < 10) {
			e.setFormat(rank.getTagColor() + player.getName() + rank.getChatColorTag() + " » " + e.getMessage());
		} else {
			e.setFormat(rank.getTagColor() + rank.getNameTag() + "§8| " + rank.getTagColor() + player.getName() + rank.getChatColorTag() + " » " + e.getMessage());
		}
		
	}
	
}
