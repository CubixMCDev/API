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
			String prefixAdmin = "§cAdmin | ";
			tAdmin.setPrefix(prefixAdmin);

			//good
			Team tDev = plugin.s.registerNewTeam("bdeveloper");
			String prefixDev = "§9Developer | ";
			tDev.setPrefix(prefixDev);

			//good
			Team tRespMod = plugin.s.registerNewTeam("cresp_mod");
			String prefixRMod = "§cR. Mod | ";
			tRespMod.setPrefix(prefixRMod);

			//good
			Team tMod = plugin.s.registerNewTeam("dmoderator");
			String prefixMod = "§3Mod | ";
			tMod.setPrefix(prefixMod);

			//good
			Team tHelp = plugin.s.registerNewTeam("ehelper");
			String prefixHelp = "§bHelper | ";
			tHelp.setPrefix(prefixHelp);

			//good
			Team tBuilder = plugin.s.registerNewTeam("fbuilder");
			String prefixBuilder = "§2Builder | ";;
			tBuilder.setPrefix(prefixBuilder);

			//good
			Team tPartner = plugin.s.registerNewTeam("gpartner");
			String prefixPartner = "§6Partner | ";
			tPartner.setPrefix(prefixPartner);

			//good
			Team tFriend = plugin.s.registerNewTeam("hfriend");
			String prefixFriend = "§fFriend | ";
			tFriend.setPrefix(prefixFriend);

			//good
			Team tYoutube = plugin.s.registerNewTeam("iyoutuber");
			String prefixYtb = "§6Ytb | ";
			tYoutube.setPrefix(prefixYtb);

			//good
			Team tVIPPLUS = plugin.s.registerNewTeam("jvip+");
			String prefixVipplus = "§6VIP+ | ";
			tVIPPLUS.setPrefix(prefixVipplus);

			//good
			Team tVIP = plugin.s.registerNewTeam("kvip");
			String prefixVip = "§eVIP | ";
			tVIP.setPrefix(prefixVip);

			//good
			Team tPlayer = plugin.s.registerNewTeam("lplayer");
			tPlayer.setPrefix(ChatColor.GRAY + "");

			for(Player player : Bukkit.getOnlinePlayers()) {
				User user = plugin.getUserManager().getUser(player);
				String rankId = user.getRankID();
				if(rankId.equalsIgnoreCase("admin")) {
					plugin.s.getTeam("aadmin").addPlayer(player);
					player.setPlayerListName("§cAdmin §8|§c " + player.getName());
				} else if(rankId.equalsIgnoreCase("developer")) {
					plugin.s.getTeam("bdeveloper").addPlayer(player);
					player.setPlayerListName("§9Developer §8|§9 " + player.getName());
				} else if(rankId.equalsIgnoreCase("resp_mod")) {
					plugin.s.getTeam("cresp_mod").addPlayer(player);
					player.setPlayerListName("§cR. Mod §8|§c " + player.getName());
				} else if(rankId.equalsIgnoreCase("moderator")) {
					plugin.s.getTeam("dmoderator").addPlayer(player);
					player.setPlayerListName("§3Mod §8|§3 " + player.getName());
				} else if(rankId.equalsIgnoreCase("helper")) {
					plugin.s.getTeam("ehelper").addPlayer(player);
					player.setPlayerListName("§bHelper §8|§b " + player.getName());
				} else if(rankId.equalsIgnoreCase("builder")) {
					plugin.s.getTeam("fbuilder").addPlayer(player);
					player.setPlayerListName("§2Builder §8|§2 " + player.getName());
				} else if(rankId.equalsIgnoreCase("partner")) {
					plugin.s.getTeam("gpartner").addPlayer(player);
					player.setPlayerListName("§6Partner §8|§6 " + player.getName());
				} else if(rankId.equalsIgnoreCase("friend")) {
					plugin.s.getTeam("hfriend").addPlayer(player);
					player.setPlayerListName("§fFriend §8|§f " + player.getName());
				} else if(rankId.equalsIgnoreCase("youtuber")) {
					plugin.s.getTeam("iyoutuber").addPlayer(player);
					player.setPlayerListName("§6Ytb §8|§6 " + player.getName());
				} else if(rankId.equalsIgnoreCase("vip+")) {
					plugin.s.getTeam("jvip+").addPlayer(player);
					player.setPlayerListName("§6VIP+ §8|§6 " + player.getName());
				} else if(rankId.equalsIgnoreCase("vip")) {
					plugin.s.getTeam("kvip").addPlayer(player);
					player.setPlayerListName("§eVIP §8|§e " + player.getName());
				} else if(rankId.equalsIgnoreCase("player")) {
					plugin.s.getTeam("lplayer").addPlayer(player);
					player.setPlayerListName("§7" + player.getName());
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
