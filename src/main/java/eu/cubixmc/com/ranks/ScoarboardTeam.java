package eu.cubixmc.com.ranks;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.User;
import eu.cubixmc.com.file.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class ScoarboardTeam {

	private CubixAPI plugin;

	public ScoarboardTeam(CubixAPI plugin) {
		this.plugin = plugin;

	}

	public void registerTeamTag(boolean trueOrFalse) {
		if(trueOrFalse) {
			plugin.teamTagOn = true;

			if(plugin.s.getTeam("aadmin") != null) {
				plugin.s.getTeam("aadmin").unregister();
			}

			if(plugin.s.getTeam("bdeveloper") != null) {
				plugin.s.getTeam("bdeveloper").unregister();
			}

			if(plugin.s.getTeam("cresp_mod") != null) {
				plugin.s.getTeam("cresp_mod").unregister();
			}

			if(plugin.s.getTeam("dmoderator") != null) {
				plugin.s.getTeam("dmoderator").unregister();
			}

			if(plugin.s.getTeam("ehelper") != null) {
				plugin.s.getTeam("ehelper").unregister();
			}

			if(plugin.s.getTeam("fbuilder") != null) {
				plugin.s.getTeam("fbuilder").unregister();
			}

			if(plugin.s.getTeam("gpartner") != null) {
				plugin.s.getTeam("gpartner").unregister();
			}

			if(plugin.s.getTeam("hfriend") != null) {
				plugin.s.getTeam("hfriend").unregister();
			}

			if(plugin.s.getTeam("iyoutuber") != null) {
				plugin.s.getTeam("iyoutuber").unregister();
			}

			if(plugin.s.getTeam("jvip+") != null) {
				plugin.s.getTeam("jvip+").unregister();
			}

			if(plugin.s.getTeam("kvip") != null) {
				plugin.s.getTeam("kvip").unregister();
			}

			if(plugin.s.getTeam("lplayer") != null) {
				plugin.s.getTeam("lplayer").unregister();
			}

			//good
			Team tAdmin = plugin.s.registerNewTeam("aadmin");
			String prefixAdmin = plugin.getConfig().getString("ranks.admin.prefixTab");
			tAdmin.setPrefix(prefixAdmin);

			//good
			Team tDev = plugin.s.registerNewTeam("bdeveloper");
			String prefixDev = plugin.getConfig().getString("ranks.developer.prefixTab");
			tDev.setPrefix(prefixDev);

			//good
			Team tRespMod = plugin.s.registerNewTeam("cresp_mod");
			String prefixRMod = plugin.getConfig().getString("ranks.resp_mod.prefixTab");
			tRespMod.setPrefix(prefixRMod);

			//good
			Team tMod = plugin.s.registerNewTeam("dmoderator");
			String prefixMod = plugin.getConfig().getString("ranks.moderator.prefixTab");
			tMod.setPrefix(prefixMod);

			//good
			Team tHelp = plugin.s.registerNewTeam("ehelper");
			String prefixHelp = plugin.getConfig().getString("ranks.helper.prefixTab");
			tHelp.setPrefix(prefixHelp);

			//good
			Team tBuilder = plugin.s.registerNewTeam("fbuilder");
			String prefixBuilder = plugin.getConfig().getString("ranks.builder.prefixTab");
			tBuilder.setPrefix(prefixBuilder);

			//good
			Team tPartner = plugin.s.registerNewTeam("gpartner");
			String prefixPartner = plugin.getConfig().getString("ranks.partner.prefixTab");
			tPartner.setPrefix(prefixPartner);

			//good
			Team tFriend = plugin.s.registerNewTeam("hfriend");
			String prefixFriend = plugin.getConfig().getString("ranks.friend.prefixTab");
			tFriend.setPrefix(prefixFriend);

			//good
			Team tYoutube = plugin.s.registerNewTeam("iyoutuber");
			String prefixYtb = plugin.getConfig().getString("ranks.youtuber.prefixTab");
			tYoutube.setPrefix(prefixYtb);

			//good
			Team tVIPPLUS = plugin.s.registerNewTeam("jvip+");
			String prefixVipplus = plugin.getConfig().getString("ranks.vip+.prefixTab");
			tVIPPLUS.setPrefix(prefixVipplus);

			//good
			Team tVIP = plugin.s.registerNewTeam("kvip");
			String prefixVip = plugin.getConfig().getString("ranks.vip.prefixTab");
			tVIP.setPrefix(prefixVip);

			//good
			Team tPlayer = plugin.s.registerNewTeam("lplayer");
			tPlayer.setPrefix(ChatColor.GRAY + "");

			for(Player player : Bukkit.getOnlinePlayers()) {
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

		} else {
			plugin.teamTagOn = false;
			if(plugin.s.getTeam("aadmin") != null) {
				plugin.s.getTeam("aadmin").unregister();
			}

			if(plugin.s.getTeam("bdeveloper") != null) {
				plugin.s.getTeam("bdeveloper").unregister();
			}

			if(plugin.s.getTeam("cresp_mod") != null) {
				plugin.s.getTeam("cresp_mod").unregister();
			}

			if(plugin.s.getTeam("dmoderator") != null) {
				plugin.s.getTeam("dmoderator").unregister();
			}

			if(plugin.s.getTeam("ehelper") != null) {
				plugin.s.getTeam("ehelper").unregister();
			}

			if(plugin.s.getTeam("fbuilder") != null) {
				plugin.s.getTeam("fbuilder").unregister();
			}

			if(plugin.s.getTeam("gpartner") != null) {
				plugin.s.getTeam("gpartner").unregister();
			}

			if(plugin.s.getTeam("hfriend") != null) {
				plugin.s.getTeam("hfriend").unregister();
			}

			if(plugin.s.getTeam("iyoutuber") != null) {
				plugin.s.getTeam("iyoutuber").unregister();
			}

			if(plugin.s.getTeam("jvip+") != null) {
				plugin.s.getTeam("jvip+").unregister();
			}

			if(plugin.s.getTeam("kvip") != null) {
				plugin.s.getTeam("kvip").unregister();
			}

			if(plugin.s.getTeam("lplayer") != null) {
				plugin.s.getTeam("lplayer").unregister();
			}

			for(Player player : Bukkit.getOnlinePlayers()) {
				player.setPlayerListName(player.getName());
			}

		}
		
	}

}
