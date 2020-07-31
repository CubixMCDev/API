package eu.cubixmc.com.events;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventsListener implements Listener {

	private CubixAPI plugin;
	
	public EventsListener(CubixAPI plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		plugin.getUserManager().fetchUserFromDataBase(player.getUniqueId());

		User user = plugin.getUserManager().getUser(player);
		String rankId = user.getRankID();

		if(rankId.equalsIgnoreCase("admin")) {
			plugin.getS().getTeam("aadmin").addPlayer(player);
			player.setPlayerListName("§cAdmin §8|§c " + player.getName());
		} else if(rankId.equalsIgnoreCase("developer")) {
			plugin.getS().getTeam("bdeveloper").addPlayer(player);
			player.setPlayerListName("§9Developer §8|§9 " + player.getName());
		} else if(rankId.equalsIgnoreCase("resp_mod")) {
			plugin.getS().getTeam("cresp_mod").addPlayer(player);
			player.setPlayerListName("§cR. Mod §8|§c " + player.getName());
		} else if(rankId.equalsIgnoreCase("moderator")) {
			plugin.getS().getTeam("dmoderator").addPlayer(player);
			player.setPlayerListName("§3Mod §8|§3 " + player.getName());
		} else if(rankId.equalsIgnoreCase("helper")) {
			plugin.getS().getTeam("ehelper").addPlayer(player);
			player.setPlayerListName("§bHelper §8|§b " + player.getName());
		} else if(rankId.equalsIgnoreCase("builder")) {
			plugin.getS().getTeam("fbuilder").addPlayer(player);
			player.setPlayerListName("§2Builder §8|§2 " + player.getName());
		} else if(rankId.equalsIgnoreCase("partner")) {
			plugin.getS().getTeam("gpartner").addPlayer(player);
			player.setPlayerListName("§6Partner §8|§6 " + player.getName());
		} else if(rankId.equalsIgnoreCase("friend")) {
			plugin.getS().getTeam("hfriend").addPlayer(player);
			player.setPlayerListName("§fFriend §8|§f " + player.getName());
		} else if(rankId.equalsIgnoreCase("youtuber")) {
			plugin.getS().getTeam("iyoutuber").addPlayer(player);
			player.setPlayerListName("§6Ytb §8|§6 " + player.getName());
		} else if(rankId.equalsIgnoreCase("vip+")) {
			plugin.getS().getTeam("jvip+").addPlayer(player);
			player.setPlayerListName("§6VIP+ §8|§6 " + player.getName());
		} else if(rankId.equalsIgnoreCase("vip")) {
			plugin.getS().getTeam("kvip").addPlayer(player);
			player.setPlayerListName("§eVIP §8|§e " + player.getName());
		} else if(rankId.equalsIgnoreCase("player")) {
			plugin.getS().getTeam("lplayer").addPlayer(player);
			player.setPlayerListName("§7" + player.getName());
		}

	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		User user = plugin.getUserManager().getUser(e.getPlayer().getUniqueId());
		plugin.getUserManager().saveUser(user);
		plugin.getUserManager().removeUser(e.getPlayer().getUniqueId());
	}


	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		
		if(plugin.getMuted().containsKey(player.getUniqueId())) {
			if(plugin.getMuteManager().isMuted(player.getUniqueId())) {
				player.sendMessage("§cVous êtes mute !");
				e.setCancelled(true);
				return;
			} else {
				plugin.getMuted().remove(player.getUniqueId());
			}
		}

	}
	
}
