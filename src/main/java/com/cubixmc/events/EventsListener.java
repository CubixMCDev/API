package com.cubixmc.events;

import com.cubixmc.CubixAPI;
import com.cubixmc.data.User;
import org.bukkit.ChatColor;
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
				player.sendMessage(plugin.prefixError+ChatColor.RED+"Vous êtes mute !");
				e.setCancelled(true);
				return;
			} else {
				plugin.getMuted().remove(player.getUniqueId());
			}
		}
	}
}
