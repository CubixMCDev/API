package fr.cubixmc.api.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cubixmc.api.CubixAPI;

public class CommandCoins implements CommandExecutor {

	private CubixAPI main;
	
	public CommandCoins(CubixAPI cubixapi) {
		this.main = cubixapi;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			
			if(args.length == 0) {
				int balance = main.getEcoManager().getBalanceCoins(p);
				p.sendMessage("§6[§eCubixMC§6]§e Vous possedez §6" + balance + " §ecoins.");
			}
			
			if(main.getRankManager().getRank(p).getPower() < 100) {
				p.sendMessage("Commande inconnu.");
				return true;
			}
			//coins add <montant> <joueur>
			
			if(args.length >= 1) {
				//coins add
				if(args[0].equalsIgnoreCase("add")) {
					
					if(args.length == 1 || args.length == 2) {

						p.sendMessage("§6[§eCubixMC§6]§e Commande correcte: /coins add <montant> <joueur>");
					}
					
					if(args.length == 3) {
						
						Player receiver = Bukkit.getPlayer(args[2]);
						
						if(receiver != null) {
							int amount = Integer.valueOf(args[1]);
							main.getEcoManager().addCoins(receiver, amount);
							receiver.sendMessage("§6[§eCubixMC§6]§e Vous venez de recevoir " + amount + " coins.");
							p.sendMessage("§eVous venez d'envoyer §6" + amount + "§e coins à  " + receiver.getName());
						}
						
					}
					
				}
				
			}
			
			if(args.length >= 1) {
				//coins add
				if(args[0].equalsIgnoreCase("remove")) {
					
					if(args.length == 1 || args.length == 2) {

						p.sendMessage("§6[§eCubixMC§6]§e Commande correcte: /coins remove <montant> <joueur>");
					}
					
					if(args.length == 3) {
						
						Player receiver = Bukkit.getPlayer(args[2]);
						
						if(receiver != null) {
							int amount = Integer.valueOf(args[1]);
							main.getEcoManager().removeCoins(receiver, amount);
							p.sendMessage("§eVous venez de retirer §6" + amount + "§e coins à  §6" + receiver.getName());
						}
						
					}
					
				}
				
			}
			
		}
		
		return false;
	}

}
