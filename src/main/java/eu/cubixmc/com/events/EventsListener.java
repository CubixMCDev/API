package eu.cubixmc.com.events;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.User;
import eu.cubixmc.com.file.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
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
			plugin.s.getTeam("aadmin").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.admin.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("developer")) {
			plugin.s.getTeam("bdeveloper").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.developer.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("resp_mod")) {
			plugin.s.getTeam("cresp_mod").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.resp_mod.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("moderator")) {
			plugin.s.getTeam("dmoderator").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.moderator.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("helper")) {
			plugin.s.getTeam("ehelper").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.helper.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("builder")) {
			plugin.s.getTeam("fbuilder").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.builder.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("partner")) {
			plugin.s.getTeam("gpartner").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.partner.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("friend")) {
			plugin.s.getTeam("hfriend").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.friend.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("youtuber")) {
			plugin.s.getTeam("iyoutuber").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.youtuber.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("vip+")) {
			plugin.s.getTeam("jvip+").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.vip+.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("vip")) {
			plugin.s.getTeam("kvip").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.vip.prefixAboveHead") + player.getName()));
		} else if(rankId.equalsIgnoreCase("player")) {
			plugin.s.getTeam("lplayer").addPlayer(player);
			player.setPlayerListName(Color.color(plugin.getConfig().getString("ranks.player.prefixAboveHead") + player.getName()));
		}

	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		User user = plugin.getUserManager().getUser(e.getPlayer().getUniqueId());
		plugin.getUserManager().saveUser(user);
		plugin.getUserManager().removeUser(e.getPlayer().getUniqueId());
	}

	/*
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		
		Ranks rank = main.getRankManager().getRank(player);
		
		if(plugin.muted.containsKey(player.getUniqueId())) {
			if(plugin.getMuteManager().isMuted(player.getUniqueId())) {
				player.sendMessage("§cVous êtes mute !");
				e.setCancelled(true);
				return;
			} else {
				plugin.muted.remove(player.getUniqueId());
			}
		}

	}

	 */
	
}
