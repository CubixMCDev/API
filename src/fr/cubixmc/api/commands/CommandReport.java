package fr.cubixmc.api.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cubixmc.api.CubixAPI;

public class CommandReport implements CommandExecutor {
	
	private CubixAPI main;
	
	public CommandReport(CubixAPI main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(command.getName().equalsIgnoreCase("report")){
			
			if(sender instanceof Player) {
				Player player = (Player) sender;
				
				if(args.length == 0){
	                sender.sendMessage("§6[§eCubixMC§6] /report <joueur> <raison>");
	                return true;
	            }
				
				if(args.length >= 1){
					
	            	UUID targetUUID = null;
	            	
	                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
	                
	                if(target.hasPlayedBefore()) {
	                	targetUUID = target.getUniqueId();
	                } else {
	                	sender.sendMessage("§cErreur: Joueur n'a jamais joué sur le serveur !");
	                	return true;
	                }
	                
	                StringBuilder reason = new StringBuilder("");
	               
	                if(args.length >= 2){
	                    for(String str : args){
	                        if(args[0] != str){
	                        	reason.append(" " + str);
	                        }
	                    }
	                }
	                
	                main.getReportManager().reportPlayer(player.getUniqueId(), targetUUID, reason.toString());
	                
	                player.sendMessage("§a§lVotre Report contre §e§l" + target.getName() + "§a§l à  bien été pris en compte.");
	                player.sendMessage("§4§l\u26A0 §c§lATTENTION:§c Tout abus de la commande /report peut sanctionné.");
	                
				}
				
			} else {
				sender.sendMessage("Only a player entity may perform this action !");
			}
			
		}
		
		return false;
	}
	
}
