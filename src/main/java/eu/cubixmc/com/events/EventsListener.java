package eu.cubixmc.com.events;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.ranks.Ranks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
			player.setPlayerListName("§cAdmin §8❘ §c" + player.getName());
		} else if(rank == Ranks.DEV) {
			main.s.getTeam("bDev").addPlayer(player);
			player.setPlayerListName("§9Developer §8❘ §9" + player.getName());
		} else if(rank == Ranks.RESPMOD) {
			main.s.getTeam("cRespMod").addPlayer(player);
			player.setPlayerListName("§cResp. Mod §8❘ §c" + player.getName());
		} else if(rank == Ranks.MOD) {
			main.s.getTeam("dMod").addPlayer(player);
			player.setPlayerListName("§3Moderator §8❘ §3" + player.getName());
		} else if(rank == Ranks.HELPER) {
			main.s.getTeam("eHelp").addPlayer(player);
			player.setPlayerListName("§bHelper §8❘ §b" + player.getName());
		} else if(rank == Ranks.BUILDER) {
			main.s.getTeam("fBuilder").addPlayer(player);
			player.setPlayerListName("§2Builder §8❘ §2" + player.getName());
		} else if(rank == Ranks.PARTNER) {
			main.s.getTeam("gPartner").addPlayer(player);
			player.setPlayerListName("§6Partner §8❘ §6" + player.getName());
		} else if(rank == Ranks.FRIEND) {
			main.s.getTeam("hFriend").addPlayer(player);
			player.setPlayerListName("§fFriend §8❘ §f" + player.getName());
		} else if(rank == Ranks.YOUTUBE) {
			main.s.getTeam("iYoutube").addPlayer(player);
			player.setPlayerListName("§6Youtube §8❘ §6" + player.getName());
		} else if(rank == Ranks.VIPPLUS) {
			main.s.getTeam("jVip+").addPlayer(player);
			player.setPlayerListName("§6Vip+ §8❘ §6" + player.getName());
		} else if(rank == Ranks.VIP) {
			main.s.getTeam("kVip").addPlayer(player);
			player.setPlayerListName("§eVip §8❘ §e" + player.getName());
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
		
		if(rank == Ranks.PLAYER) {
			e.setFormat(rank.getTagColor() + player.getName() + rank.getChatColorTag() +" » " + e.getMessage());
		} else if(rank == Ranks.VIP){
			e.setFormat("§eVip §8\u2758 §e" + player.getName() + " §8»§f " + e.getMessage());
		} else if(rank == Ranks.VIPPLUS){
			e.setFormat("§6Vip+ §8\u2758 §6" + player.getName() + " §8»§f " + e.getMessage());
		} else if(rank == Ranks.PARTNER){
			e.setFormat("§6Partner §8\u2758 §6" + player.getName() + " §8»§f " + e.getMessage());
		} else if(rank == Ranks.YOUTUBE){
			e.setFormat("§6Youtube §8\u2758 §6" + player.getName() + " §8»§f " + e.getMessage());
		} else if(rank == Ranks.FRIEND){
			e.setFormat("§fFriend §8\u2758 §f" + player.getName() + " §8»§f " + e.getMessage());
		} else if(rank == Ranks.BUILDER){
			e.setFormat("§2Builder §8\u2758 §2" + player.getName() + " §8»§f " + e.getMessage());
		} else if(rank == Ranks.HELPER){
			e.setFormat("§bHelper §8\u2758 §b" + player.getName() + " §8»§f " + e.getMessage());
		} else if(rank == Ranks.MOD){
			e.setFormat("§3Mod §8\u2758 §3" + player.getName() + " §8»§f " + e.getMessage());
		} else if(rank == Ranks.RESPMOD){
			e.setFormat("§cResp. Mod §8\u2758 §c" + player.getName() + " §8»§f " + e.getMessage());
		} else if(rank == Ranks.DEV){
			e.setFormat("§9Developer §8\u2758 §9" + player.getName() + " §8»§f " + e.getMessage());
		} else if(rank == Ranks.ADMIN){
			e.setFormat("§4§lAdmin §8\u2758 §c" + player.getName() + " §8»§f " + e.getMessage());
		}
		
	}
	
}
