package fr.cubixmc.api.managers.sanctions;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
 
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cubixmc.api.CubixAPI;
import fr.cubixmc.api.data.BanPlayerData;
 
public class BanManager implements CommandExecutor {
   
    public CubixAPI main;
 
    public BanManager(CubixAPI cubixapi) {
        this.main = cubixapi;
    }
 
    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO Auto-generated method stub
       
        if(command.getName().endsWith("tempban")){

			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(main.getRankManager().getRank(player).getPower() < 100) {
					player.sendMessage("Commande Inconnue.");
					return true;
				}
			}
        	
            if(args.length == 0){
            	sender.sendMessage("§4[CubixMC] commande correct: §4/tempban <joueur> <days> <hours> <minutes> <categorie 1/2/3> <reason>");
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
                    
                    if(Bukkit.getOnlinePlayers().contains(target)){
                    	
                        Player p = target.getPlayer();
                        p.kickPlayer("§eYou have been banned\n\n" + "§eReason:§a "+ raison.toString()  + "\n\n§c§lDuration:§r§c " + days + " days,  " + hours + " hours and " + minutes + " minutes.");
                    }
                   
                    banPlayer(sender, targetUUID, target.getName(), raison.toString(), false, days, hours, minutes, categorie);
                }
            }
               
               
            return true;
        }
       
        if(command.getName().equalsIgnoreCase("ban")){
           
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(main.getRankManager().getRank(player).getPower() < 80) {
					player.sendMessage("Commande Inconnue.");
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
               
                if(Bukkit.getOnlinePlayers().contains(target)){
                	
                    Player p = target.getPlayer();
                    p.kickPlayer("§eYou have been banned\n\n" + "§eReason:§a "+ raison.toString()  + "\n\n§c§lDuration:§r§c Permanent");
                }
               
                banPlayer(sender, targetUUID, target.getName(), raison.toString(), true, 0, 0, 0, 1);
               
            }
           
            return true;
        }
       
       
        //unban <player>
        if(command.getName().equalsIgnoreCase("unban")){
           
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(main.getRankManager().getRank(player).getPower() < 80) {
					player.sendMessage("Commande Inconnue.");
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
   
	public void loadBannedPlayer() {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT uuid, reason, expiredate FROM banned WHERE current_timestamp < expiredate");
			
			ResultSet result = q.executeQuery();
			
			while(result.next()) {
				UUID uuid = UUID.fromString(result.getString("uuid"));
				String reason = result.getString("reason");
				
				BanPlayerData ban = new BanPlayerData();
				ban.setReason(reason);
				ban.setUuid(uuid);
				
				main.banned.put(uuid, ban);
				
			}
			
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(main.banned.size() + " joueurs ont été ban sur le serveur.");
		
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

       if(hasBeenBannedBefore(targetUUID) == true) {
   		int nb = main.getBanManager().getNumberOfPreviousBans(targetUUID, categorie);
   		timeAsDays = (int) (timeAsDays * (1.25*nb));
   		timeAsHours = (int) (timeAsHours * (1.25*nb));
   		timeAsMinutes = (int) (timeAsMinutes * (1.25*nb));
    	   try {
			PreparedStatement ps = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE banned SET expiredate = DATE_ADD(NOW(), INTERVAL " + timeOverall +"), last = " + categorie + " WHERE uuid = ?");
			ps.setString(1, targetUUID.toString());
			ps.execute();
			ps.close();
			
			setCategoryValue(targetUUID, categorie, getNumberOfPreviousBans(targetUUID, categorie) + 1);
			Bukkit.broadcastMessage("§c§lUn joueur vient de se faire ban de votre serveur par un Moderateur!");
			
            if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(targetUUID))){
            	
                Player p = Bukkit.getPlayer(targetUUID);
                p.kickPlayer("§eYou have been banned\n\n" + "§eReason:§a "+ raison.toString()  + "\n\n§c§lDuration:§r§c " + timeAsDays + " days,  " + timeAsHours + " hours and " + timeAsMinutes + " minutes.");
            }
			
    	   } catch (SQLException e) {
    		   e.printStackTrace();
    	   }
    	   
       } else {
    	   
    	   try {
               PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("INSERT INTO banned(uuid,reason,expiredate,"+getCategorie(categorie)+",last) VALUES (?,?,DATE_ADD(NOW(), INTERVAL "+ timeOverall +"),1,"+categorie+")");
               q.setString(1, targetUUID.toString());
               q.setString(2, raison);
               q.execute();
               q.close();
               Bukkit.broadcastMessage("§c§lUn joueur vient de se faire ban de votre serveur par un Moderateur!");
               
               if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(targetUUID))){
               	
                   Player p = Bukkit.getPlayer(targetUUID);
                   p.kickPlayer("§eYou have been banned\n\n" + "§eReason:§a "+ raison.toString()  + "\n\n§c§lDuration:§r§c " + timeAsDays + " days,  " + timeAsHours + " hours and " + timeAsMinutes + " minutes.");
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
           
       }
       
        BanPlayerData ban = new BanPlayerData();
        ban.setUuid(targetUUID);
        ban.setReason(raison);
        main.banned.put(targetUUID, ban);
		
	}
	
	public void unbanPlayer(CommandSender sender, UUID targetUUID, String targetName){
	       
        if(!main.banned.containsKey(targetUUID)){
            sender.sendMessage("§6Le joueur n'est pas présent dans le répertoire des joueurs banned !" );
            return;
        }
       
        main.banned.remove(targetUUID);
        sender.sendMessage("§c"+ targetName +"§6 à été unban !");
       
        int nb = getNumberOfPreviousBans(targetUUID, getLastBan(targetUUID)) - 1;
        
        try {
            PreparedStatement q = main.getDataBase().getConnection().prepareStatement("UPDATE banned SET expiredate = current_timestamp, " + getCategorie(getLastBan(targetUUID)) + " = " + nb + " WHERE uuid = ?");
            q.setString(1, targetUUID.toString());
            q.execute();
            q.close();         
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        int cheat = getNumberOfPreviousBans(targetUUID, 1);
        int gameplay = getNumberOfPreviousBans(targetUUID, 2);
        int chat = getNumberOfPreviousBans(targetUUID, 3);
        
        if(cheat == 0 && gameplay == 0 && chat == 0){
        	try {
				PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("DELETE FROM banned WHERE uuid = ?");
				q.setString(1, targetUUID.toString());
				q.execute();
				q.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
       
    }
	
	public boolean isBanned(UUID playerUUID) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT uuid = ? FROM banned WHERE current_timestamp < expiredate");
			q.setString(1, playerUUID.toString());
			ResultSet result = q.executeQuery();
			boolean b = result.next();
			q.execute();
			q.close();
			return b;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return false;
	}
	
	public boolean hasBeenBannedBefore(UUID playerUUID) {

		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT uuid FROM banned WHERE uuid = ?");
			q.setString(1, playerUUID.toString());
			ResultSet result = q.executeQuery();
			boolean b = result.next();
			q.execute();
			q.close();
			return b;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getBanReason(UUID playerUUID) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT reason FROM banned WHERE uuid = ?");
			q.setString(1, playerUUID.toString());
			ResultSet result = q.executeQuery();
			while(result.next()) {
				return result.toString();
			}
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getNumberOfPreviousBans(UUID playerUUID, int categorie) {
		
		if(hasBeenBannedBefore(playerUUID)) {
			try {
				PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT " + getCategorie(categorie) + " FROM banned WHERE uuid = ?");
				q.setString(1, playerUUID.toString());
				ResultSet result = q.executeQuery();
				while(result.next()) {
					int nb = result.getInt(getCategorie(categorie));
					return nb;
				}
				q.execute();
				q.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return 0;
		}
		return 0;
	}
	
	public int getLastBan(UUID playerUUID) {
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT last FROM banned WHERE uuid = ?");
			q.setString(1, playerUUID.toString());
			ResultSet result = q.executeQuery();
			while(result.next()) {
				int index = result.getInt("last");
				return index;
			}
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public void setCategoryValue(UUID playerUUID,int categorie, int value) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareCall("UPDATE banned SET " + getCategorie(categorie) + " = " + value + " WHERE uuid = ?");
			q.setString(1, playerUUID.toString());
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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