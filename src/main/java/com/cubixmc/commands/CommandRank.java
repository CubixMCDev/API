package com.cubixmc.commands;

import com.cubixmc.CubixAPI;
import com.cubixmc.data.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRank implements CommandExecutor {

    private CubixAPI plugin;

    public CommandRank(CubixAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("rank")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.hasPermission("*")) {
                    if(args.length == 0) {
                        User user = plugin.getUserManager().getUser(p.getUniqueId());
                        p.sendMessage(plugin.prefix+ ChatColor.YELLOW+"Vous êtes actuellement "+user.getRankToStringWithColor()+ChatColor.YELLOW+".");
                    } else if(args.length == 3) {
                        if(args[0].equalsIgnoreCase("setprimary")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target == null) {
                                p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: le joueur"+ChatColor.DARK_RED+target.getName()+ChatColor.RED+" n'est pas en ligne !");
                                return true;
                            } else {
                                User user = plugin.getUserManager().getUser(target.getUniqueId());
                                String rank = args[2];
                                if(plugin.getIdToRank().containsKey(rank)) {
                                    user.setPrimaryRank(plugin.getIdToRank().get(rank));
                                    plugin.getDatabaseManager().performAsyncUpdate("UPDATE players SET primary_rank = ? WHERE uuid = ?", rank.toLowerCase(), target.getUniqueId().toString());
                                    p.sendMessage(plugin.prefix+ChatColor.YELLOW+"Vous venez d'assigner "+ChatColor.GOLD+target.getName()+ChatColor.YELLOW+" au rang de/d' "+plugin.getIdToRank().get(rank).getRankToStringWithColor()+ChatColor.YELLOW+".");
                                } else {
                                    p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: le rang "+ChatColor.DARK_RED+rank+ChatColor.RED+" n'existe pas !");
                                }
                            }
                        } else if(args[0].equalsIgnoreCase("setsecondary")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target == null) {
                                p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: le joueur"+ChatColor.DARK_RED+target.getName()+ChatColor.RED+" n'est pas en ligne !");
                                return true;
                            } else {
                                String rank = args[2];
                                User user = plugin.getUserManager().getUser(target.getUniqueId());
                                if(plugin.getIdToRank().containsKey(rank)) {
                                    user.setSecondaryRank(plugin.getIdToRank().get(rank));
                                    plugin.getDatabaseManager().performAsyncUpdate("UPDATE players SET secondary_rank = ? WHERE uuid = ?", rank.toLowerCase(), target.getUniqueId().toString());
                                    p.sendMessage(plugin.prefix+ChatColor.YELLOW+"Vous venez d'assigner "+ChatColor.GOLD+target.getName()+ChatColor.YELLOW+" au rang de/d' "+plugin.getIdToRank().get(rank).getRankToStringWithColor()+ChatColor.YELLOW+".");
                                } else {
                                    p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: le rang "+ChatColor.DARK_RED+rank+ChatColor.RED+" n'existe pas !");
                                }
                            }
                        }
                    } else if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: le joueur"+ChatColor.DARK_RED+target.getName()+ChatColor.RED+" n'est pas en ligne !");
                            return true;
                        } else {
                            User user = plugin.getUserManager().getUser(target.getUniqueId());
                            user.setPrimaryRank(plugin.getDefaultRank());
                            user.setSecondaryRank(plugin.getIdToRank().get("none"));
                            plugin.getDatabaseManager().performAsyncUpdate("UPDATE players SET secondary_rank = ?, primary_rank = ? WHERE uuid = ?", "player", "NONE", target.getUniqueId().toString());
                            p.sendMessage(plugin.prefix+ChatColor.YELLOW+"Vous venez de supprmier le rang de "+ChatColor.GOLD+target.getName()+ChatColor.YELLOW+".");
                        }
                    }
                } else {
                    p.sendMessage(plugin.prefixError+ChatColor.RED+"Erreur: commande inconnue.");
                }
            } else {
                sender.sendMessage("Vous devez être un joueur pour éxecuter cette commande !");
                return true;
            }
        }

        return false;
    }
}
