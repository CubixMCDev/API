package eu.cubixmc.com.ranks;

import eu.cubixmc.com.CubixAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class ScoarboardTeam {

	private CubixAPI main;
	
	public ScoarboardTeam(CubixAPI api) {
		this.main = api;
	}
	
	public void registerTeamTag(boolean trueOrFalse) {
		if(trueOrFalse) {
			main.teamTagOn = true;
			if(main.s.getTeam("aAdmin") != null) {
				main.s.getTeam("aAdmin").unregister();
			}

			if(main.s.getTeam("bDev") != null) {
				main.s.getTeam("bDev").unregister();
			}

			if(main.s.getTeam("cRespMod") != null) {
				main.s.getTeam("cRespMod").unregister();
			}

			if(main.s.getTeam("dMod") != null) {
				main.s.getTeam("dMod").unregister();
			}

			if(main.s.getTeam("eHelp") != null) {
				main.s.getTeam("eHelp").unregister();
			}

			if(main.s.getTeam("fBuilder") != null) {
				main.s.getTeam("fBuilder").unregister();
			}

			if(main.s.getTeam("gPartner") != null) {
				main.s.getTeam("gPartner").unregister();
			}

			if(main.s.getTeam("hFriend") != null) {
				main.s.getTeam("hFriend").unregister();
			}

			if(main.s.getTeam("iYoutube") != null) {
				main.s.getTeam("iYoutube").unregister();
			}

			if(main.s.getTeam("jVip+") != null) {
				main.s.getTeam("jVip+").unregister();
			}

			if(main.s.getTeam("kVip") != null) {
				main.s.getTeam("kVip").unregister();
			}

			if(main.s.getTeam("lPlayer") != null) {
				main.s.getTeam("lPlayer").unregister();
			}

			//good
			Team tAdmin = main.s.registerNewTeam("aAdmin");
			String prefixAdmin = "§cAdmin ❘ ";
			tAdmin.setPrefix(prefixAdmin);

			//good
			Team tDev = main.s.registerNewTeam("bDev");
			String prefixDev = "§9Developer ❘ ";
			tDev.setPrefix(prefixDev);

			//good
			Team tRespMod = main.s.registerNewTeam("cRespMod");
			String prefixRMod = "§cR. Mod ❘ ";
			tRespMod.setPrefix(prefixRMod);

			//good
			Team tMod = main.s.registerNewTeam("dMod");
			String prefixMod = "§3Mod ❘ ";
			tMod.setPrefix(prefixMod);

			//good
			Team tHelp = main.s.registerNewTeam("eHelp");
			String prefixHelp = "§3Mod ❘ ";
			tHelp.setPrefix(prefixHelp);

			//good
			Team tBuilder = main.s.registerNewTeam("fBuilder");
			String prefixBuilder = "§2Builder ❘ ";
			tBuilder.setPrefix(prefixBuilder);

			//good
			Team tPartner = main.s.registerNewTeam("gPartner");
			String prefixPartner = "§6Partner ❘ ";
			tPartner.setPrefix(prefixPartner);

			//good
			Team tFriend = main.s.registerNewTeam("hFriend");
			String prefixFriend = "§fFriend ❘ ";
			tFriend.setPrefix(prefixFriend);

			//good
			Team tYoutube = main.s.registerNewTeam("iYoutube");
			String prefixYtb = "§6Ytb ❘ ";
			tYoutube.setPrefix(prefixYtb);

			//good
			Team tVIPPLUS = main.s.registerNewTeam("jVip+");
			String prefixVipplus = "§6Vip+ ❘ ";
			tVIPPLUS.setPrefix(prefixVipplus);

			//good
			Team tVIP = main.s.registerNewTeam("kVip");
			String prefixVip = "§eVip ❘ ";
			tVIP.setPrefix(prefixVip);

			//good
			Team tPlayer = main.s.registerNewTeam("lPlayer");
			tPlayer.setPrefix(ChatColor.GRAY + "");

		} else {
			main.teamTagOn = false;
			if(main.s.getTeam("aAdmin") != null) {
				main.s.getTeam("aAdmin").unregister();
			}

			if(main.s.getTeam("bDev") != null) {
				main.s.getTeam("bDev").unregister();
			}

			if(main.s.getTeam("cRespMod") != null) {
				main.s.getTeam("cRespMod").unregister();
			}

			if(main.s.getTeam("dMod") != null) {
				main.s.getTeam("dMod").unregister();
			}

			if(main.s.getTeam("eHelp") != null) {
				main.s.getTeam("eHelp").unregister();
			}

			if(main.s.getTeam("fBuilder") != null) {
				main.s.getTeam("fBuilder").unregister();
			}

			if(main.s.getTeam("gPartner") != null) {
				main.s.getTeam("gPartner").unregister();
			}

			if(main.s.getTeam("hFriend") != null) {
				main.s.getTeam("hFriend").unregister();
			}

			if(main.s.getTeam("iYoutube") != null) {
				main.s.getTeam("iYoutube").unregister();
			}

			if(main.s.getTeam("jVip+") != null) {
				main.s.getTeam("jVip+").unregister();
			}

			if(main.s.getTeam("kVip") != null) {
				main.s.getTeam("kVip").unregister();
			}

			if(main.s.getTeam("lPlayer") != null) {
				main.s.getTeam("lPlayer").unregister();
			}

			for(Player player : Bukkit.getOnlinePlayers()) {
				player.setPlayerListName(player.getName());
			}

		}
		
	}
	
}
