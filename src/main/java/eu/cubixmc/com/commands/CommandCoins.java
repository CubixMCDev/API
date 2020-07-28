package eu.cubixmc.com.commands;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCoins implements CommandExecutor {

	private CubixAPI plugin;
	
	public CommandCoins(CubixAPI plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender instanceof Player) {
			
			Player p = (Player) sender;
			User user = plugin.getUserManager().getUser(p);
			
			if(args.length == 0) {
				int balance = user.getCoins();
				p.sendMessage("§6[§eCubixMC§6]§e Vous possedez §6" + balance + " §ecoins.");
				return true;
			}
			
			if(!p.hasPermission("coins.edit")) {
				p.sendMessage("§cCubixMC §4» §cErreur: commande inconnue.");
				return true;
			}
			//coins add <montant> <joueur>
			
			if(args.length >= 1) {
				//coins add
				if(args[0].equalsIgnoreCase("add")) {
					
					if(args.length == 1 || args.length == 2) {
						p.sendMessage("§6[§eCubixMC§6]§e Commande correcte: /coins add <montant> <joueur>");
						return true;
					}
					
					if(args.length == 3) {
						
						Player receiver = Bukkit.getPlayer(args[2]);

						if(receiver.isOnline()) {
							if(receiver != null) {
								int amount = Integer.valueOf(args[1]);
								plugin.getUserManager().getUser(receiver).addCoins(amount);
								receiver.sendMessage("§6[§eCubixMC§6]§e Vous venez de recevoir " + amount + " coins.");
								p.sendMessage("§eVous venez d'envoyer §6" + amount + "§e coins à " + receiver.getName());
							} else {
								p.sendMessage("erreur null");
							}
						} else {
							p.sendMessage("§6" + receiver.getName() + "§c n'est pas en ligne !");
						}
						
					}
					
				}
				
			}
			
			if(args.length >= 1) {
				//coins add
				if(args[0].equalsIgnoreCase("remove")) {

					if(args.length == 1 || args.length == 2) {
						p.sendMessage("§6[§eCubixMC§6]§e Commande correcte: /coins remove <montant> <joueur>");
						return true;
					}

					if(args.length == 3) {

						Player receiver = Bukkit.getPlayer(args[2]);

						if(receiver.isOnline()) {
							if(receiver != null) {
								int amount = Integer.valueOf(args[1]);
								plugin.getUserManager().getUser(receiver).removeCoins(amount);
								p.sendMessage("§eVous venez de retirer §6" + amount + "§e coins à " + receiver.getName());
							} else {
								p.sendMessage("erreur null");
							}
						} else {
							p.sendMessage("§6" + receiver.getName() + "§c n'est pas en ligne !");
						}

					}
					
				}
				
			}
			
		}

		return false;
	}

}
