package eu.cubixmc.com.managers.sanctions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.MutePlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteManager implements CommandExecutor {

	private CubixAPI main;
	
	public MuteManager(CubixAPI cubixAPI) {
		this.main = cubixAPI;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().endsWith("tempmute")){

			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(main.getRankManager().getRank(player).getPower() < 100) {
					player.sendMessage("Commande Inconnue.");
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
                    
                    
                    if(Bukkit.getOnlinePlayers().contains(target)){
                    	
                        Player p = target.getPlayer();
                        p.sendMessage("§c§lYou have been muted for: §r§c" + raison.toString());
                        p.sendMessage("§c§lDuration:§r§c " + days + " days, " + hours + " hours and " + minutes + " minutes.");
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
				if(main.getRankManager().getRank(player).getPower() < 80) {
					player.sendMessage("Commande Inconnue.");
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
               
                if(Bukkit.getOnlinePlayers().contains(target)){
                	
                    Player p = target.getPlayer();
                    p.sendMessage("§c§lYou have been muted !");
                }
               
                mutePlayer(sender, targetUUID, target.getName(), raison.toString(), true, 0 , 0, 0);
              
            }
           
            return true;
        }
       
       
        //unmute <player>
        if(command.getName().equalsIgnoreCase("unmute")){
           
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(main.getRankManager().getRank(player).getPower() < 80) {
					player.sendMessage("Commande Inconnue.");
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
	
	public void loadMutedPlayer() {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT uuid, reason, expiredate FROM muted WHERE current_timestamp < expiredate");
			
			ResultSet result = q.executeQuery();
			
			while(result.next()) {
				UUID uuid = UUID.fromString(result.getString("uuid"));
				String reason = result.getString("reason");
				
				MutePlayerData mute = new MutePlayerData();
				mute.setReason(reason);
				mute.setUuid(uuid);
				
				main.muted.put(uuid, mute);
				
			}
			
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(main.muted.size() + " joueurs ont été mute sur le serveur.");
		
	}
	
	public void mutePlayer(CommandSender sender, UUID targetUUID, String nameCible, String raison, boolean isDefinitif, int timeAsDays ,int timeAsHours, int timeAsMinutes){
		
		if(isMuted(targetUUID)){
            sender.sendMessage("§cJoueur déjà mute.");
            return;
        }
        sender.sendMessage("§cVous avez mute le joueur §f" + nameCible + "§c pour: §f" + raison.toString());
        sender.sendMessage("§c§lDuration:§r§c " + timeAsDays + " days, "+ timeAsHours + " hours and " + timeAsMinutes + " minutes.");
        int timeO = timeAsDays * 1440 + timeAsHours * 60 + timeAsMinutes;
        String timeOverall = timeO + " MINUTE"; 
       
       int nb = getNumberOfPreviousMutes(targetUUID) + 1;
       
       if(hasBeenMutedBefore(targetUUID) == true) {
      		int nb1 = main.getMuteManager().getNumberOfPreviousMutes(targetUUID);
       		timeAsDays = (int) (timeAsDays * (1.25*nb1));
       		timeAsHours = (int) (timeAsHours * (1.25*nb1));
       		timeAsMinutes = (int) (timeAsMinutes * (1.25*nb1));
    	   try {
			PreparedStatement ps = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE muted SET expiredate = DATE_ADD(NOW(), INTERVAL " + timeOverall +"), chat = " + nb1 + " WHERE uuid = ?");
			ps.setString(1, targetUUID.toString());
			ps.execute();
			ps.close();
			Bukkit.broadcastMessage("§c§lUn joueur vient de se faire mute de votre serveur par un Moderateur!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	   
       } else {

    	   try {
    		   
               PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("INSERT INTO muted(uuid,reason,expiredate,chat) VALUES (?,?,DATE_ADD(NOW(), INTERVAL "+ timeOverall +"),1)");
               q.setString(1, targetUUID.toString());
               q.setString(2, raison);
               q.execute();
               q.close();
               
           } catch (SQLException e) {
               e.printStackTrace();
           }
           
       }
       
        MutePlayerData mute = new MutePlayerData();
        mute.setUuid(targetUUID);
        mute.setReason(raison);
        main.muted.put(targetUUID, mute);
		
	}

	public void unmutePlayer(CommandSender sender, UUID targetUUID, String name){
	       
        if(!main.muted.containsKey(targetUUID)){
            sender.sendMessage("§6Le joueur n'est pas présent dans le répertoire des joueurs muted !" );
            return;
        }
       
        main.muted.remove(targetUUID);
        sender.sendMessage("§c"+ name +"§6 § été unmute !");
       
        int nb = getNumberOfPreviousMutes(targetUUID) - 1;
        
        //modifier la bdd a la date current et enlever 1 au chat (sql)
        try {
            PreparedStatement q = main.getDataBase().getConnection().prepareStatement("UPDATE muted SET expiredate = current_timestamp, chat =" + nb);
            q.execute();
            q.close();         
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
	
	public boolean isMuted(UUID playerUUID) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT uuid = ? FROM muted WHERE current_timestamp < expiredate");
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
	
	public boolean hasBeenMutedBefore(UUID playerUUID) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT uuid FROM muted WHERE uuid = ?");
			q.setString(1,  playerUUID.toString());
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
	
	public String getMuteReason(UUID playerUUID) {
		
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT reason FROM muted WHERE uuid = ?");
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
	
	public int getNumberOfPreviousMutes(UUID playerUUID) {
		
		if(hasBeenMutedBefore(playerUUID)) {
			try {
				PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("SELECT chat FROM muted WHERE uuid = ?");
				q.setString(1, playerUUID.toString());
				ResultSet result = q.executeQuery();
				while(result.next()) {
					int nb = result.getInt("chat");
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
}
