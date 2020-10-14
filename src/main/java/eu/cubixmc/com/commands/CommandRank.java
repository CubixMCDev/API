package eu.cubixmc.com.commands;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.data.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandRank implements CommandExecutor {

    private CubixAPI plugin;
    private String prefix = "§eCubixMC §f»";
    private ArrayList<String> secondaryranks = new ArrayList<>();

    public CommandRank(CubixAPI plugin) {
        this.plugin = plugin;
        secondaryranks.add("admin");
        secondaryranks.add("developer");
        secondaryranks.add("respmod");
        secondaryranks.add("mod");
        secondaryranks.add("helper");
        secondaryranks.add("builder");
        secondaryranks.add("friend");
        secondaryranks.add("youtube");
        secondaryranks.add("partner");
        secondaryranks.add("player");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        /*
        /rank
        /rank setprimary <player> <rank>
        /rank setsecondary <player> <rank>
        /rank remove <player>
         */

        if(cmd.getName().equalsIgnoreCase("rank")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.hasPermission("*")) {
                    if(args.length == 0) {
                        User user = plugin.getUserManager().getUser(p.getUniqueId());
                        p.sendMessage(prefix +"§6Vous êtes actuellement " + user.getRankToStringWithColor());
                    } else if(args.length == 3) {
                        if(args[0].equalsIgnoreCase("setprimary")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target == null) {
                                p.sendMessage(prefix + "§cCe joueur n'est actuellement pas connecté.");
                                return true;
                            } else {
                                String rank = args[2];
                                if(rank.equalsIgnoreCase("vip") || rank.equalsIgnoreCase("vipplus")) {
                                    plugin.getDatabaseManager().performAsyncUpdate("UPDATE players SET primary_rank = ? WHERE uuid = ?", rank.toLowerCase(), p.getUniqueId().toString());
                                    p.sendMessage(prefix + "§eVous venez d'assigner §6" + target.getName() + " §eau rang de §6" + rank);
                                } else {
                                    p.sendMessage(prefix + "§cCe rang n'existe pas !");
                                }
                            }
                        } else if(args[0].equalsIgnoreCase("setsecondary")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target == null) {
                                p.sendMessage(prefix + "§cCe joueur n'est actuellement pas connecté.");
                                return true;
                            } else {
                                String rank = args[2];
                                if(secondaryranks.contains(rank.toLowerCase())) {
                                    plugin.getDatabaseManager().performAsyncUpdate("UPDATE players SET secondary_rank = ? WHERE uuid = ?", rank.toLowerCase(), p.getUniqueId().toString());
                                    p.sendMessage(prefix + "§eVous venez d'assigner §6" + target.getName() + " §eau rang de §6" + rank);
                                } else {
                                    p.sendMessage(prefix + "§cCe rang n'existe pas !");
                                }
                            }
                        } else {
                            sendHelp(p);
                        }
                    } else if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            p.sendMessage(prefix + "§cCe joueur n'est actuellement pas connecté.");
                            return true;
                        } else {
                            plugin.getDatabaseManager().performAsyncUpdate("UPDATE players SET secondary_rank = ?, primary_rank = ? WHERE uuid = ?", "player", "NONE", p.getUniqueId().toString());
                            p.sendMessage(prefix + "§eVous venez de supprmier le rang de  §6" + target.getName());
                        }
                    }
                } else {
                    p.sendMessage("§fCommande inconnue.");
                }
            } else {
                sender.sendMessage("§cVous devez être un joueur pour éxecuter cette commande !");
                return true;
            }
        }

        return false;
    }

    private void sendHelp(Player player) {

    }
}
