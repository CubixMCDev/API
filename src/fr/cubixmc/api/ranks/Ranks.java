package fr.cubixmc.api.ranks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;

public enum Ranks {

	PLAYER(0, ChatColor.GRAY, "§7Player", ChatColor.GRAY),
	VIP(10, ChatColor.YELLOW, "VIP ", ChatColor.WHITE),
	VIPPLUS(20, ChatColor.GOLD, "VIP+ ", ChatColor.WHITE),
	PARTNER(30, ChatColor.GOLD, "Partner ", ChatColor.WHITE),
	YOUTUBE(40, ChatColor.GOLD, "YouTube ", ChatColor.WHITE),
	FRIEND(50, ChatColor.WHITE, "Friend ", ChatColor.WHITE),
	BUILDER(60, ChatColor.DARK_GREEN, "Builder ", ChatColor.WHITE),
	ASSISTANT(70, ChatColor.AQUA, "Assistant ", ChatColor.WHITE),
	MOD(80, ChatColor.DARK_AQUA, "Moderator ", ChatColor.WHITE),
	RESPMOD(90, ChatColor.RED, "Resp. Mod ", ChatColor.WHITE),
	DEV(100, ChatColor.BLUE, "Developer ", ChatColor.WHITE),
	ADMIN(110, ChatColor.DARK_RED, "§lAdmin ", ChatColor.RED);
	
	private int power;
	private ChatColor tagColor;
	private String nameTag;
	private ChatColor chatColorTag;
	public static Map<Integer, Ranks> grade = new HashMap<>();
	
	Ranks(int power, ChatColor tagColor, String nameTag, ChatColor chatColorTag){
		this.power=power;
		this.tagColor = tagColor;
		this.nameTag = nameTag;
		this.chatColorTag=chatColorTag;
	}
	
	static {
		
		for(Ranks ranks: Ranks.values()) {
			grade.put(ranks.getPower(), ranks);
		}
	}
	
	public int getPower() {
		return power;
	}
	
	public ChatColor getTagColor() {
		return tagColor;
	}
	
	public String getNameTag() {
		return nameTag;
	}
	
	public ChatColor getChatColorTag() {
		return chatColorTag;
	}
	
	public static Ranks powerToRank(int power) {
		return grade.get(power);
	}
	
}
