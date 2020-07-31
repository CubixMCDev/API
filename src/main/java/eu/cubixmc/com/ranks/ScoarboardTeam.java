package eu.cubixmc.com.ranks;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class ScoarboardTeam {

	private CubixAPI plugin;

	public ScoarboardTeam(CubixAPI plugin) {
		this.plugin = plugin;

	}

	public void registerTeamTag(boolean trueOrFalse) {
		if(trueOrFalse) {
			plugin.setTeamTagOn(true);

			if(plugin.getS().getTeam("aadmin") != null) {
				plugin.getS().getTeam("aadmin").unregister();
			}

			if(plugin.getS().getTeam("bdeveloper") != null) {
				plugin.getS().getTeam("bdeveloper").unregister();
			}

			if(plugin.getS().getTeam("cresp_mod") != null) {
				plugin.getS().getTeam("cresp_mod").unregister();
			}

			if(plugin.getS().getTeam("dmoderator") != null) {
				plugin.getS().getTeam("dmoderator").unregister();
			}

			if(plugin.getS().getTeam("ehelper") != null) {
				plugin.getS().getTeam("ehelper").unregister();
			}

			if(plugin.getS().getTeam("fbuilder") != null) {
				plugin.getS().getTeam("fbuilder").unregister();
			}

			if(plugin.getS().getTeam("gpartner") != null) {
				plugin.getS().getTeam("gpartner").unregister();
			}

			if(plugin.getS().getTeam("hfriend") != null) {
				plugin.getS().getTeam("hfriend").unregister();
			}

			if(plugin.getS().getTeam("iyoutuber") != null) {
				plugin.getS().getTeam("iyoutuber").unregister();
			}

			if(plugin.getS().getTeam("jvip+") != null) {
				plugin.getS().getTeam("jvip+").unregister();
			}

			if(plugin.getS().getTeam("kvip") != null) {
				plugin.getS().getTeam("kvip").unregister();
			}

			if(plugin.getS().getTeam("lplayer") != null) {
				plugin.getS().getTeam("lplayer").unregister();
			}

			//good
			Team tAdmin = plugin.getS().registerNewTeam("aadmin");
			String prefixAdmin = "§cAdmin | ";
			tAdmin.setPrefix(prefixAdmin);

			//good
			Team tDev = plugin.getS().registerNewTeam("bdeveloper");
			String prefixDev = "§9Developer | ";
			tDev.setPrefix(prefixDev);

			//good
			Team tRespMod = plugin.getS().registerNewTeam("cresp_mod");
			String prefixRMod = "§cR. Mod | ";
			tRespMod.setPrefix(prefixRMod);

			//good
			Team tMod = plugin.getS().registerNewTeam("dmoderator");
			String prefixMod = "§3Mod | ";
			tMod.setPrefix(prefixMod);

			//good
			Team tHelp = plugin.getS().registerNewTeam("ehelper");
			String prefixHelp = "§bHelper | ";
			tHelp.setPrefix(prefixHelp);

			//good
			Team tBuilder = plugin.getS().registerNewTeam("fbuilder");
			String prefixBuilder = "§2Builder | ";;
			tBuilder.setPrefix(prefixBuilder);

			//good
			Team tPartner = plugin.getS().registerNewTeam("gpartner");
			String prefixPartner = "§6Partner | ";
			tPartner.setPrefix(prefixPartner);

			//good
			Team tFriend = plugin.getS().registerNewTeam("hfriend");
			String prefixFriend = "§fFriend | ";
			tFriend.setPrefix(prefixFriend);

			//good
			Team tYoutube = plugin.getS().registerNewTeam("iyoutuber");
			String prefixYtb = "§6Ytb | ";
			tYoutube.setPrefix(prefixYtb);

			//good
			Team tVIPPLUS = plugin.getS().registerNewTeam("jvip+");
			String prefixVipplus = "§6VIP+ | ";
			tVIPPLUS.setPrefix(prefixVipplus);

			//good
			Team tVIP = plugin.getS().registerNewTeam("kvip");
			String prefixVip = "§eVIP | ";
			tVIP.setPrefix(prefixVip);

			//good
			Team tPlayer = plugin.getS().registerNewTeam("lplayer");
			tPlayer.setPrefix(ChatColor.GRAY + "");

			for(Player player : Bukkit.getOnlinePlayers()) {
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

		} else {
			plugin.setTeamTagOn(false);
			if(plugin.getS().getTeam("aadmin") != null) {
				plugin.getS().getTeam("aadmin").unregister();
			}

			if(plugin.getS().getTeam("bdeveloper") != null) {
				plugin.getS().getTeam("bdeveloper").unregister();
			}

			if(plugin.getS().getTeam("cresp_mod") != null) {
				plugin.getS().getTeam("cresp_mod").unregister();
			}

			if(plugin.getS().getTeam("dmoderator") != null) {
				plugin.getS().getTeam("dmoderator").unregister();
			}

			if(plugin.getS().getTeam("ehelper") != null) {
				plugin.getS().getTeam("ehelper").unregister();
			}

			if(plugin.getS().getTeam("fbuilder") != null) {
				plugin.getS().getTeam("fbuilder").unregister();
			}

			if(plugin.getS().getTeam("gpartner") != null) {
				plugin.getS().getTeam("gpartner").unregister();
			}

			if(plugin.getS().getTeam("hfriend") != null) {
				plugin.getS().getTeam("hfriend").unregister();
			}

			if(plugin.getS().getTeam("iyoutuber") != null) {
				plugin.getS().getTeam("iyoutuber").unregister();
			}

			if(plugin.getS().getTeam("jvip+") != null) {
				plugin.getS().getTeam("jvip+").unregister();
			}

			if(plugin.getS().getTeam("kvip") != null) {
				plugin.getS().getTeam("kvip").unregister();
			}

			if(plugin.getS().getTeam("lplayer") != null) {
				plugin.getS().getTeam("lplayer").unregister();
			}

			for(Player player : Bukkit.getOnlinePlayers()) {
				player.setPlayerListName(player.getName());
			}

		}
		
	}

}
