package eu.cubixmc.com.managers;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.sanctions.BanPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.UUID;

public class BanManager implements CommandExecutor {
   
    public CubixAPI plugin;
 
    public BanManager(CubixAPI plugin) {
        this.plugin = plugin;
    }
 
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO Auto-generated method stub
       
    	
        if(command.getName().endsWith("tempban")){

			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(!player.hasPermission("ban.custom")) {
					player.sendMessage("§cCubixMC §4» §cErreur: commande inconnue.");
					return true;
				}
			}
        	
            if(args.length == 0){
            	sender.sendMessage("§c[CubixMC] commande correct: /tempban <joueur> <days> <hours> <minutes> <categorie 1/2/3> <reason>");
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
           
                if(args[1] != null && args[2] != null && args.length >= 4){
                   
                	int days = (int) Integer.valueOf(args[1]);
                    int hours = (int) Integer.valueOf(args[2]);
                    int minutes = (int) Integer.valueOf(args[3]);
                    int categorie = (int) Integer.valueOf(args[4]);
                   
                    for(String str : args){
                        if(args[0] != str && args[1] != str && args[2] != str && args[3] != str &&  args[4] != str){
                            raison.append(" " + str);
                        }
                    }
                   
                    banPlayer(sender, targetUUID, target.getName(), raison.toString(), false, days, hours, minutes, categorie);
                }
            }
               

            return true;
        }
       
        if(command.getName().equalsIgnoreCase("ban")){
           
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(!player.hasPermission("ban.custom")) {
					player.sendMessage("§cCubixMC §4» §cErreur: commande inconnue.");
					return true;
				}
			}
        	
            if(args.length == 0){
                sender.sendMessage("§4[CubixMC] §cErreur: commande correct: §4/ban <joueur> <raison>");
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
               
                banPlayer(sender, targetUUID, target.getName(), raison.toString(), true, 0, 0, 0, 1);
               
            }
           
            return true;
        }
       
       
        //unban <player>
        if(command.getName().equalsIgnoreCase("unban")){
           
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(!player.hasPermission("unban.use")) {
					player.sendMessage("§cCubixMC §4» §cErreur: commande inconnue.");
					return true;
				}
			}
        	
            //unban
            if(args.length == 0){
                sender.sendMessage("§4[CubixMC] §cErreur: commande correct: §4/unban <joueur>");
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
                
                unbanPlayer(sender, targetUUID, target.getName());
               
            }
           
           
            return true;
        }
       
        return false;
    }

	public void loadBannedPlayers() {

		plugin.getDatabaseManager().performAsyncQuery("SELECT uuid, reason, expiredate FROM banned WHERE current_timestamp < expiredate", set -> {
			while (set.next()) {

				UUID uuid = UUID.fromString(set.getString("uuid"));
				String reason = set.getString("reason");

				BanPlayerData ban = new BanPlayerData();
				ban.setReason(reason);
				ban.setUuid(uuid);

				plugin.getBanned().put(uuid, ban);

			}
		});
		
		System.out.println(plugin.getBanned().size() + " joueurs ont été ban sur le serveur.");
		
	}

	public void banPlayer(CommandSender sender, UUID targetUUID, String targetName, String raison, boolean isDefinitif, int timeAsDays, int timeAsHours, int timeAsMinutes, int categorie){
		
		if(isBanned(targetUUID)){
            sender.sendMessage("§cJoueur déjà ban.");
            return;
        }
       
        sender.sendMessage("§cVous avez ban le joueur §f" + targetName + "§c pour: §f" + raison);
       
        /*
        String timeH = "17 YEAR";
       
        if(!isDefinitif){
            timeH = timeAsHours +" HOUR";
        } 2880
        */ 
        
       int timeO = timeAsDays * 1440 + timeAsHours * 60 + timeAsMinutes;
       String timeOverall = timeO + " MINUTE";

       if(hasBeenBannedBefore(targetUUID)) {
		   int nb = getNumberOfPreviousBans(targetUUID, categorie);
		   timeAsDays = (int) (timeAsDays * (1.25*nb));
		   timeAsHours = (int) (timeAsHours * (1.25*nb));
		   timeAsMinutes = (int) (timeAsMinutes * (1.25*nb));

		   plugin.getDatabaseManager().performAsyncUpdate("UPDATE banned SET expiredate = DATE_ADD(NOW(), INTERVAL "
				   + timeOverall +"), last = " + categorie + " WHERE uuid = ?", targetUUID.toString());

		   setCategoryValue(targetUUID, categorie, getNumberOfPreviousBans(targetUUID, categorie) + 1);
		   Bukkit.broadcastMessage("§c§lUn joueur vient de se faire ban de votre serveur par un Moderateur!");

		   Player p = Bukkit.getPlayer(targetUUID);
		   if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(targetUUID))){
			   p.kickPlayer("§eYou have been banned from Cubix§6MC§e Networks\n\n" + "§eReason:§a "+ raison.toString()  + "\n\n§c§lDuration:§r§c " + timeAsDays + " days, " + timeAsHours + " hours and " + timeAsMinutes + " minutes.");
		   } else {
			   Player player = (Player) sender;
			   player.performCommand("push " + player.getName() + " kick " + targetName + " §eYou have been banned " + "from Cubix§6MC§e Networks\n\n" + "§eReason:§a "+ raison.toString()  + "\n\n§c§lDuration:§r§c " + timeAsDays + " days, " + timeAsHours + " hours and " + timeAsMinutes + " minutes.");
		   }

	   } else {
       		plugin.getDatabaseManager().performAsyncUpdate("INSERT INTO banned(uuid,reason,expiredate,"+getCategorie(categorie)+",last) VALUES (?,?,DATE_ADD(NOW(), INTERVAL "+ timeOverall +"),1,"+categorie+")"
			, targetUUID.toString(), raison);

		   Player p = Bukkit.getPlayer(targetUUID);
		   if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(targetUUID))){
			   p.kickPlayer("§eYou have been banned from Cubix§6MC§e Networks\n\n" + "§eReason:§a "+ raison.toString()  + "\n\n§c§lDuration:§r§c " + timeAsDays + " days, " + timeAsHours + " hours and " + timeAsMinutes + " minutes.");
		   } else {
			   Player player = (Player) sender;
			   player.performCommand("push " + player.getName() + " kick " + targetName + " §eYou have been banned from Cubix§6MC§e Networks\n\n" + "§eReason:§a "+ raison.toString()  + "\n\n§c§lDuration:§r§c " + timeAsDays + " days, " + timeAsHours + " hours and " + timeAsMinutes + " minutes.");

		   }

	   }
       
        BanPlayerData ban = new BanPlayerData();
        ban.setUuid(targetUUID);
        ban.setReason(raison);
        plugin.getBanned().put(targetUUID, ban);

	}

	public void unbanPlayer(CommandSender sender, UUID targetUUID, String targetName){
	       
        if(!plugin.getBanned().containsKey(targetUUID)){
            sender.sendMessage("§6Le joueur n'est pas présent dans le répertoire des joueurs banned !" );
            return;
        }

		plugin.getBanned().remove(targetUUID);
        sender.sendMessage("§c"+ targetName +"§6 à été unban !");
       
        int nb = getNumberOfPreviousBans(targetUUID, getLastBan(targetUUID)) - 1;

		plugin.getDatabaseManager().performAsyncUpdate("UPDATE banned SET expiredate = current_timestamp, " + getCategorie(getLastBan(targetUUID)) + " = " + nb + " WHERE uuid = ?"
				, targetUUID.toString());
        
        int cheat = getNumberOfPreviousBans(targetUUID, 1);
        int gameplay = getNumberOfPreviousBans(targetUUID, 2);
        int chat = getNumberOfPreviousBans(targetUUID, 3);
        
        if(cheat == 0 && gameplay == 0 && chat == 0){
			plugin.getDatabaseManager().performAsyncUpdate("DELETE FROM banned WHERE uuid = ?", targetUUID.toString());
        }
       
    }
	
	public boolean isBanned(UUID playerUUID) {
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT uuid = ? FROM banned WHERE current_timestamp < expiredate", playerUUID.toString());
			if (set.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean hasBeenBannedBefore(UUID playerUUID) {
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT uuid FROM banned WHERE uuid = ?", playerUUID.toString());
			if (set.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
			e.printStackTrace();
		}
		return false;
	}
	
	public String getBanReason(UUID playerUUID) {
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT reason FROM banned WHERE uuid = ?", playerUUID.toString());
			if (set.next()) {
				return set.toString();
			}
		} catch (SQLException e) {
			System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
			e.printStackTrace();
		}
		return null;
	}
	
	public int getNumberOfPreviousBans(UUID playerUUID, int categorie) {
		if(hasBeenBannedBefore(playerUUID)) {
			try {
				CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT " + getCategorie(categorie) + " FROM banned WHERE uuid = ?", playerUUID.toString());
				if (set.next()) {
					int nb = set.getInt(getCategorie(categorie));
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
	
	public int getLastBan(UUID playerUUID) {
		try {
			CachedRowSet set = plugin.getDatabaseManager().performQuery("SELECT last FROM banned WHERE uuid = ?", playerUUID.toString());
			if (set.next()) {
				int index = set.getInt("last");
				return index;
			}
		} catch (SQLException e) {
			System.out.println("Error performing SQL query: " + e.getMessage() + " (" + e.getClass().getSimpleName() + ")");
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setCategoryValue(UUID playerUUID,int categorie, int value) {
    	plugin.getDatabaseManager().performAsyncUpdate("UPDATE banned SET " + getCategorie(categorie) + " = " + value + " WHERE uuid = ?", playerUUID.toString());
	}
	
	public String getCategorie(int cat) {
		if(cat == 1) {
			return "cheat";
		} else if(cat == 2) {
			return "gameplay";
		}else if(cat == 3) {
			return "chat";
		} else {
			return null;
		}
	}

}