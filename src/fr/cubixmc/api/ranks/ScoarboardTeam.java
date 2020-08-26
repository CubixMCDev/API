package fr.cubixmc.api.ranks;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

import fr.cubixmc.api.CubixAPI;

public class ScoarboardTeam {

	private CubixAPI main;
	
	public ScoarboardTeam(CubixAPI api) {
		this.main = api;
	}
	
	public void registerTeamTag() {
		
		if(main.s.getTeam("aAdmin") != null) {
			main.s.getTeam("aAdmin").unregister();
		}
		
		if(main.s.getTeam("bRespMod") != null) {
			main.s.getTeam("bRespMod").unregister();
		}
		
		if(main.s.getTeam("cDev") != null) {
			main.s.getTeam("cDev").unregister();
		}
		
		if(main.s.getTeam("dMod") != null) {
			main.s.getTeam("dMod").unregister();
		}
		
		if(main.s.getTeam("eAss") != null) {
			main.s.getTeam("eAss").unregister();
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
		
		Team tAdmin = main.s.registerNewTeam("aAdmin");
		tAdmin.setPrefix("§4Admin §8| §c");
		
		Team tRespMod = main.s.registerNewTeam("bRespMod");
		//tRespMod.setPrefix("§cResp. Mod §8| §c");   TROP LONG
		tRespMod.setPrefix("§cR. Mod §8| §c");    // VERSION COURTE
		
		Team tDev = main.s.registerNewTeam("cDev");
		tDev.setPrefix("§9Dev §8| §9");
		
		Team tMod = main.s.registerNewTeam("dMod");
		tMod.setPrefix("§3Mod §8| §3");
		
		Team tAss = main.s.registerNewTeam("eAss");
		//tAss.setPrefix("§bAssistant §8| §b");      TROP LONG
		tAss.setPrefix("§bAss §8| §b");    // VERSION COURTE

		Team tBuilder = main.s.registerNewTeam("fBuilder");
		tBuilder.setPrefix("§2Builder §8| §2");
		
		Team tPartner = main.s.registerNewTeam("gPartner");
		tPartner.setPrefix("§6Partner §8| §6");
		
		Team tFriend = main.s.registerNewTeam("hFriend");
		tFriend.setPrefix("§fFriend §8| §f");
		
		Team tYoutube = main.s.registerNewTeam("iYoutube");
		//tYoutube.setPrefix("§6Youtube §8| §6");     TROP LONG
		tYoutube.setPrefix("§6YTB §8| §6");       //  VERSION COURTE
		
		Team tVIPPLUS = main.s.registerNewTeam("jVip+");
		tVIPPLUS.setPrefix("§6Vip+ §8| §6");
		
		Team tVIP = main.s.registerNewTeam("kVip");
		tVIP.setPrefix("§eVip §8| §e");
		
		Team tPlayer = main.s.registerNewTeam("lPlayer");
		tPlayer.setPrefix(ChatColor.GRAY + "");
		
	}
	
}
