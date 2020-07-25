package eu.cubixmc.com.commands;

import eu.cubixmc.com.CubixAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCredits implements CommandExecutor {

	private CubixAPI main;
	
	public CommandCredits(CubixAPI cubixapi) {
		this.main = cubixapi;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if(main.getRankManager().getRank(p).getPower() < 100) {
				p.sendMessage("§6[§eCubixMC§6]§e Commande inconnu.");
				return true;
			}
			
			if(args.length == 0) {
				int balance = main.getEcoManager().getBalanceCredits(p);
				p.sendMessage("§6[§eCubixMC§6]§e Vous possedez §6" + balance + " §ecrédits.");
			}
			
			//cr§dits add <montant> <joueur>
			
			if(args.length >= 1) {
				//cr§dits add
				if(args[0].equalsIgnoreCase("add")) {
					
					if(args.length == 1 || args.length == 2) {

						p.sendMessage("§6[§eCubixMC§6]§e Commande correcte: /credits add <montant> <joueur>");
					}
					
					if(args.length == 3) {
						
						Player receiver = Bukkit.getPlayer(args[2]);
						
						if(receiver != null) {
							int amount = Integer.valueOf(args[1]);
							main.getEcoManager().addCredits(receiver, amount);
							receiver.sendMessage("§6[§eCubixMC§6]§e Vous venez de recevoir " + amount + " crédits.");
							p.sendMessage("§eVous venez d'envoyer §6" + amount + "§e crédits § " + receiver.getName());
						}
						
					}
					
				}
				
			}
			
			if(args.length >= 1) {
				//cr§dits add
				if(args[0].equalsIgnoreCase("remove")) {
					
					if(args.length == 1 || args.length == 2) {

						p.sendMessage("§6[§eCubixMC§6]§e Commande correcte: /credits remove <montant> <joueur>");
					}
					
					if(args.length == 3) {
						
						Player receiver = Bukkit.getPlayer(args[2]);
						
						if(receiver != null) {
							int amount = Integer.valueOf(args[1]);
							main.getEcoManager().removeCredits(receiver, amount);
							p.sendMessage("§eVous venez de retirer §6" + amount + "§e crédits à §6" + receiver.getName());
						}
						
					}
					
				}
				
			}
			
		}
		
		return false;
	}
	
}
