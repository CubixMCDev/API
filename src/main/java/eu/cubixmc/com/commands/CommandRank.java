package eu.cubixmc.com.commands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.ranks.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRank implements CommandExecutor {

	private CubixAPI main;
	
	public CommandRank(CubixAPI cubixapi) {
		this.main = cubixapi;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
	
	
		/*
		 *  /rank defset <player> <grade>
		 *  /rank tempset <player> <grade> <jours> <heure>
		 */
	
		if(command.getName().equalsIgnoreCase("rank")) {
			
			Player player = (Player) sender;
			
			if(main.getRankManager().getRank(player).getPower() < 100) {
				player.sendMessage("Commande inconnue.");
				return true;
			}
			
			if(args.length == 0) {
				sender.sendMessage("§6[§eCubixMC§6]§e /rank defset <joueur> <grade>");
				sender.sendMessage("§6[§eCubixMC§6]§e /rank tempset <joueur> <grade> <days> <hours>");
				return true;
			}
			
			if(args.length >= 1) {
				
				//rank defset
				if(args[0].equalsIgnoreCase("defset")){
					
					if(args.length < 3) {
						sender.sendMessage("§6[§eCubixMC§6]§e /rank defset <joueur> <grade>");
						sender.sendMessage("§6[§eCubixMC§6]§e /rank tempset <joueur> <grade> <days> <hours>");
						return true;
					}
					
					Player target = Bukkit.getPlayer(args[1]);
					if(!Bukkit.getOnlinePlayers().contains(target)) {
						sender.sendMessage("§cLe joueur n'est pas en ligne (ou n'existe pas).");
						return true;
					}
					
					String rankName = args[2];
					if(rankName != null) {
						Ranks rankSelect =  Ranks.valueOf(rankName.toUpperCase());
						if(rankSelect != null) {
							main.getRankManager().setRank(target, rankSelect);
							sender.sendMessage("§eVous venez d'assigner §f" + target.getName() + "§e le grade " + args[2] +" !");
							
							if(rankSelect.getPower() > 0) {
								target.sendMessage("§eGrade reçu >> " + rankSelect.getTagColor() + rankSelect.getNameTag());
							} else {
								target.sendMessage("§eGrade reçu >> " + rankSelect.getTagColor() + "player");
							}
							
							try {
								PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE players SET expirerank = ? WHERE uuid = ?");
								q.setInt(1, 0);
								q.setString(2, target.getUniqueId().toString());
								q.executeUpdate();
								q.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
					
					return true;
				}
					
				//rank tempset
				if(args[0].equalsIgnoreCase("tempset")){
					
					if(args.length < 5) {
						sender.sendMessage("§6[§eCubixMC§6]§e /rank defset <joueur> <grade>");
						sender.sendMessage("§6[§eCubixMC§6]§e /rank tempset <joueur> <grade> <days> <hours>");
						return true;
					}
					
					Player target = Bukkit.getPlayer(args[1]);
					if(!Bukkit.getOnlinePlayers().contains(target)) {
						sender.sendMessage("§cLe joueur n'est pas en ligne (ou n'existe pas).");
						return true;
					}
					
					String rankName = args[2];
					if(rankName != null) {
						Ranks rankSelect =  Ranks.valueOf(rankName.toUpperCase());
						if(rankSelect != null && args[3] != null && args[4] !=null) {
							
							int days = (int) Integer.valueOf(args[3]);
							int hours = (int) Integer.valueOf(args[4]);
							int time = hours + (days * 24);
							
							
							main.getRankManager().setRank(target, rankSelect);
							
							if(rankSelect.getPower() > 0) {
								target.sendMessage("§eGrade reçu >> " + rankSelect.getTagColor() + rankSelect.getNameTag());
							} else {
								target.sendMessage("§eGrade reçu >> " + rankSelect.getTagColor() + "player");
							}
							
							try {
								PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE players SET expirerank = ?, expiredate = DATE_ADD(NOW(), INTERVAL " +time+" HOUR) WHERE uuid = ?");
								q.setInt(1, 1);
								q.setString(2, target.getUniqueId().toString());
								q.executeUpdate();
								q.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
					
					return true;
				}
				
			}
			
			
			return true;
		}
		
		return false;
		
	}

}
