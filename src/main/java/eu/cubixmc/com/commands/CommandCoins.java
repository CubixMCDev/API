package eu.cubixmc.com.commands;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
				p.sendMessage(plugin.prefix+ChatColor.YELLOW+"Vous possedez "+ChatColor.GOLD+balance+ChatColor.YELLOW+" coins.");
				return true;
			}
			
			if(!p.hasPermission("coins.edit")) {
				p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: commande inconnue.");
				return true;
			}
			
			if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("add")) {
					
					if(args.length == 1 || args.length == 2) {
						p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: commande correcte: "+ChatColor.DARK_RED+"/coins add <montant> <joueur>"+ChatColor.RED+".");
						return true;
					}
					
					if(args.length == 3) {
						Player receiver = Bukkit.getPlayer(args[2]);

						if(receiver.isOnline()) {
							if(receiver != null) {
								int amount = Integer.valueOf(args[1]);
								plugin.getUserManager().getUser(receiver).addCoins(amount);
								receiver.sendMessage(plugin.prefix+ChatColor.YELLOW+"Vous venez de recevoir "+ChatColor.GOLD+amount+" coins"+ChatColor.YELLOW+".");
								p.sendMessage(plugin.prefix+ChatColor.YELLOW+"Vous venez d'envoyer "+ChatColor.GOLD+amount+" coins "+ChatColor.YELLOW+"à "+ChatColor.GOLD +receiver.getName()+ChatColor.YELLOW+".");
							} else {
								p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: null.");
							}
						} else {
							p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: le joueur "+ChatColor.DARK_RED+receiver.getName()+ChatColor.RED+" n'est pas en ligne !");
						}
					}
				}
			}
			
			if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("remove")) {

					if(args.length == 1 || args.length == 2) {
						p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: commande correcte: "+ChatColor.DARK_RED+"/coins remove <montant> <joueur>"+ChatColor.RED+".");
						return true;
					}

					if(args.length == 3) {
						Player receiver = Bukkit.getPlayer(args[2]);

						if(receiver.isOnline()) {
							if(receiver != null) {
								int amount = Integer.valueOf(args[1]);
								plugin.getUserManager().getUser(receiver).removeCoins(amount);
								p.sendMessage(plugin.prefixError+ChatColor.YELLOW+"Vous venez de retirer "+ChatColor.GOLD+amount+" coins "+ChatColor.YELLOW+"à "+ChatColor.GOLD+receiver.getName());
							} else {
								p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: null.");
							}
						} else {
							p.sendMessage(plugin.prefixError+ ChatColor.RED+"Erreur: le joueur "+ChatColor.DARK_RED+receiver.getName()+ChatColor.RED+" n'est pas en ligne !");
						}
					}
				}
			}
		}
		return false;
	}
}
