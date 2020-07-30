package eu.cubixmc.com.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.sanctions.BanPlayerData;
import eu.cubixmc.com.data.sanctions.MutePlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.sql.rowset.CachedRowSet;

public class MuteManager implements CommandExecutor {

	private CubixAPI plugin;
	
	public MuteManager(CubixAPI plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().endsWith("tempmute")){

			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(!player.hasPermission("mute.custom")) {
					player.sendMessage("§cCubixMC §4» §cErreur: commande inconnue.");
					return true;
				}
			}
			
            if(args.length == 0){
                sender.sendMessage("§6[§eCubixMC§6]§e Commande correcte: /tempmute <player> <days> <hours> <minutes> <reason>");
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
                
                StringBuilder raison = new StringBuilder("");
           
               
                if(args[1] != null && args[2] != null && args.length >= 3){
                   
                	int days = (int) Integer.valueOf(args[1]);
                    int hours = (int) Integer.valueOf(args[2]);
                    int minutes = (int) Integer.valueOf(args[3]);
                   
                    for(String morceau : args){
                        if(args[0] != morceau && args[1] != morceau && args[2] != morceau && args[3] != morceau){
                            raison.append(" " + morceau);
                        }
                    }

                    sender.sendMessage("§c§lDuration:§r§c " + days + " days, " + hours + " hours and " + minutes + " minutes.");
                   
                    mutePlayer(sender, targetUUID, target.getName(), raison.toString(), false, days, hours, minutes);
                }
            }
               
               
            return true;
        }
       
        if(command.getName().equalsIgnoreCase("mute")){
           
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(!player.hasPermission("mute.custom")) {
					player.sendMessage("§cCubixMC §4» §cErreur: commande inconnue.");
					return true;
				}
			}
        	
            if(args.length == 0){
                sender.sendMessage("§cCommande correcte: /mute <player> <raison>");
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
                
                StringBuilder raison = new StringBuilder("");
               
                if(args.length >= 2){
                    for(String morceau : args){
                        if(args[0] != morceau){
                            raison.append(" " + morceau);
                        }
                    }
                }
               
                mutePlayer(sender, targetUUID, target.getName(), raison.toString(), true, 0 , 0, 0);
              
            }
           
            return true;
        }
       
       
        //unmute <player>
        if(command.getName().equalsIgnoreCase("unmute")){
           
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(!player.hasPermission("unmute.use")) {
					player.sendMessage("§cCubixMC §4» §cErreur: commande inconnue.");
					return true;
				}
			}
        	
            //unmute
            if(args.length == 0){
                sender.sendMessage("§c§lCommande correcte: /unmute <player>");
            }
           
            if(args.length == 1){
               
            	UUID targetUUID = null;
            	
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                
                if(target.hasPlayedBefore()) {
                	targetUUID = target.getUniqueId();
                } else {
                	sender.sendMessage("§cErreur: Joueur n'a jamais joué sur le serveur !");
                	return true;
                }
                
                this.unmutePlayer(sender, targetUUID, target.getName());
               
            }
           
           
            return true;
        }
       
        return false;
	}
	
	public void loadMutedPlayers() {

		plugin.getDatabaseManager().performAsyncQuery("SELECT uuid, reason, expiredate FROM muted WHERE current_timestamp < expiredate", set -> {
			while (set.next()) {

				UUID uuid = UUID.fromString(set.getString("uuid"));
				String reason = set.getString("reason");

				MutePlayerData mute = new MutePlayerData();
				mute.setReason(reason);
				mute.setUuid(uuid);

				plugin.muted.put(uuid, mute);

			}
		});
		
		System.out.println(plugin.muted.size() + " joueurs ont été mute sur le serveur.");
		
	}
	
	public void mutePlayer(CommandSender sender, UUID targetUUID, String nameCible, String raison, boolean isDefinitif, int timeAsDays ,int timeAsHours, int timeAsMinutes){
		
		if(isMuted(targetUUID)){
            sender.sendMessage("§cJoueur déjà mute.");
            return;
        }
        sender.sendMessage("§cVous avez mute le joueur §f" + nameCible + "§c pour: §f" + raison.toString());
        sender.sendMessage("§c§lDuration:§r§c " + timeAsDays + " days, "+ timeAsHours + " hours and " + timeAsMinutes + " minutes.");

        Player target = Bukkit.getPlayer(targetUUID);
        if(target.isOnline()) {
        	if(isDefinitif) {
        		target.sendMessage("§c§lYou have been permanently muted for: §r§c" + raison.toString());
			} else {
				target.sendMessage("§c§lYou have been muted for: §r§c" + raison.toString());
				target.sendMessage("§c§lDuration:§r§c " + timeAsDays + " days, " + timeAsHours + " hours and " + timeAsMinutes + " minutes.");
			}
		}

        int timeO = timeAsDays * 1440 + timeAsHours * 60 + timeAsMinutes;
        String timeOverall = timeO + " MINUTE"; 
       
       int nb = getNumberOfPreviousMutes(targetUUID) + 1;
       
       if(hasBeenMutedBefore(targetUUID)) {
      		int nb1 = getNumberOfPreviousMutes(targetUUID);
       		timeAsDays = (int) (timeAsDays * (1.25*nb1));
       		timeAsHours = (int) (timeAsHours * (1.25*nb1));
       		timeAsMinutes = (int) (timeAsMinutes * (1.25*nb1));

		   plugin.getDatabaseManager().performAsyncUpdate("UPDATE muted SET expiredate = DATE_ADD(NOW(), INTERVAL " + timeOverall +"), chat = " + nb1 +
				   " WHERE uuid = ?", targetUUID.toString());
		   Bukkit.broadcastMessage("§c§lUn joueur vient de se faire mute de votre serveur par un Moderateur!");
    	   
       } else {

		   plugin.getDatabaseManager().performAsyncUpdate("INSERT INTO muted(uuid,reason,expiredate,chat) VALUES (?,?,DATE_ADD(NOW(), INTERVAL "+ timeOverall +"),1)"
				   , targetUUID.toString(), raison);

       }
       
        MutePlayerData mute = new MutePlayerData();
        mute.setUuid(targetUUID);
        mute.setReason(raison);
        plugin.muted.put(targetUUID, mute);
		
	}

	public void unmutePlayer(CommandSender sender, UUID targetUUID, String name){
	       
        if(!plugin.muted.containsKey(targetUUID)){
            sender.sendMessage("§6Le joueur n'est pas présent dans le répertoire des joueurs muted !" );
            return;
        }
       
        plugin.muted.remove(targetUUID);
        sender.sendMessage("§c"+ name +"§6 § été unmute !");

        int previousMutes = getNumberOfPreviousMutes(targetUUID);
        
        //modifier la bdd a la date current et enlever 1 au chat (sql)

		if(previousMutes == 1) {
			plugin.getDatabaseManager().performAsyncUpdate("DELETE FROM muted WHERE uuid = ?"
					, targetUUID.toString());
		} else {
			int nb = previousMutes - 1;
			plugin.getDatabaseManager().performAsyncUpdate("UPDATE muted SET expiredate = current_timestamp, chat =" + nb + " WHERE uuid = ?"
					, targetUUID.toString());
		}

       
    }
	
	public boolean isMuted(UUID playerUUID) {
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT uuid = ? FROM muted WHERE current_timestamp < expiredate", playerUUID.toString());
			if (set.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean hasBeenMutedBefore(UUID playerUUID) {
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT uuid FROM muted WHERE uuid = ?", playerUUID.toString());
			if (set.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
			e.printStackTrace();
		}
		return false;
	}
	
	public String getMuteReason(UUID playerUUID) {
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT reason FROM muted WHERE uuid = ?", playerUUID.toString());
			if (set.next()) {
				return set.toString();
			}
		} catch (SQLException e) {
			System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
			e.printStackTrace();
		}
		return null;
	}
	
	public int getNumberOfPreviousMutes(UUID playerUUID) {
		if(hasBeenMutedBefore(playerUUID)) {
			try {
				CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT chat FROM muted WHERE uuid = ?", playerUUID.toString());
				if (set.next()) {
					int nb = set.getInt("chat");
					return nb;
				}
			} catch (SQLException e) {
				System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
				e.printStackTrace();
			}
		} else {
			return 0;
		}
		return 0;
	}

}
