package eu.cubixmc.com.data;

import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.file.Color;
import eu.cubixmc.com.ranks.Rank;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User {

	private UUID uuid;
	private String username;
	private int coins;
	private int credits;
	private int level;
	private int exp;
	private Rank primaryRank;
	private Rank secondaryRank;
	private boolean hasLoadedPerms = false;
	private List<String> perms;
	private PermissionAttachment attachment;

	public User(UUID uuid) {
		this.uuid = uuid;
		Player player = Bukkit.getPlayer(uuid);
		if (player != null) this.username = player.getName();
		this.coins = 100;
		this.credits = 0;
		this.level = 0;
		this.exp = 0;
		this.primaryRank = JavaPlugin.getPlugin(CubixAPI.class).getDefaultRank();
		this.perms = new ArrayList<>();
	}

	public void addCoins(int amount) {
		coins += amount;
	}

	public void removeCoins(int amount) {
		coins -= amount;
	}

	public void addCredits(int amount) {
		credits += amount;
	}

	public void removeCredits(int amount) {
		credits -= amount;
	}

	public String permsToStr() {
		StringBuilder builder = new StringBuilder();
		for (String s : perms) {
			builder.append(s);
			builder.append(":");
		}
		return builder.toString();
	}

	public void loadPerms(String s, CubixAPI plugin) {
		this.perms = new ArrayList<>();
		if (s != null) {
			String[] parts = s.split(":");
			for (String part : parts) {
				if (part != null) {
					perms.add(part);
				}
			}
		}
		plugin.getServer().getScheduler().runTask(plugin, () -> {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null) {
				attachment = player.addAttachment(plugin);
				if (!perms.isEmpty()) {
					for (String str : perms) {
						attachment.setPermission(str, true);
					}
				}
				//
				if (plugin.getIdToRank().containsKey(getPrimaryRank().getId().toLowerCase())) {
					for (String perm : plugin.getIdToRank().get(getPrimaryRank().getId().toLowerCase()).getPermissions()) {
						if (perm.equalsIgnoreCase("*")) {
							player.setOp(true);
						} else {
							attachment.setPermission(perm, true);
						}
					}
					if (plugin.getIdToRank().get(getPrimaryRank().getId().toLowerCase()).getInherits() != null) {
						for (String inheritRank : plugin.getIdToRank().get(getPrimaryRank().getId().toLowerCase()).getInherits()) {
							for (String inhereRankPerm : plugin.getIdToRank().get(inheritRank.toLowerCase()).getPermissions()) {
								attachment.setPermission(inhereRankPerm, true);
							}
							if (plugin.getIdToRank().get(inheritRank.toLowerCase()).getInherits() != null) {
								for (String tmp : plugin.getIdToRank().get(inheritRank.toLowerCase()).getPermissions()) {
									attachment.setPermission(tmp, true);
								}
								if (plugin.getIdToRank().get(inheritRank.toLowerCase()).getInherits() != null) {
									for (String inhereRank : plugin.getIdToRank().get(inheritRank.toLowerCase()).getInherits()) {
										if (plugin.getIdToRank().get(inhereRank.toLowerCase()).getPermissions() != null) {
											for (String inhereRankPerm : plugin.getIdToRank().get(inhereRank.toLowerCase()).getPermissions()) {
												if (inhereRankPerm != null) {
													attachment.setPermission(inhereRankPerm, true);
												}
											}
										}
									}
								}
							}
						}
					}
				} else {
					System.out.println("Joueur a un rank (primary) qui n'a pas été set dans la config. UUID: " + uuid);
				}

				//

				if(secondaryRank != null) {
					if (plugin.getIdToRank().containsKey(getSecondaryRank().getId().toLowerCase())) {
						for (String perm : plugin.getIdToRank().get(getSecondaryRank().getId().toLowerCase()).getPermissions()) {
							if (perm.equalsIgnoreCase("*")) {
								player.setOp(true);
							} else {
								attachment.setPermission(perm, true);
							}
						}
						if (plugin.getIdToRank().get(getSecondaryRank().getId().toLowerCase()).getInherits() != null) {
							for (String inheritRank : plugin.getIdToRank().get(getSecondaryRank().getId().toLowerCase()).getInherits()) {
								for (String inhereRankPerm : plugin.getIdToRank().get(inheritRank.toLowerCase()).getPermissions()) {
									attachment.setPermission(inhereRankPerm, true);
								}
								if (plugin.getIdToRank().get(inheritRank.toLowerCase()).getInherits() != null) {
									for (String tmp : plugin.getIdToRank().get(inheritRank.toLowerCase()).getPermissions()) {
										attachment.setPermission(tmp, true);
									}
									if (plugin.getIdToRank().get(inheritRank.toLowerCase()).getInherits() != null) {
										for (String inhereRank : plugin.getIdToRank().get(inheritRank.toLowerCase()).getInherits()) {
											if (plugin.getIdToRank().get(inhereRank.toLowerCase()).getPermissions() != null) {
												for (String inhereRankPerm : plugin.getIdToRank().get(inhereRank.toLowerCase()).getPermissions()) {
													if (inhereRankPerm != null) {
														attachment.setPermission(inhereRankPerm, true);
													}
												}
											}
										}
									}
								}
							}
						}
					} else {
						System.out.println("Joueur a un rank (secondary) qui n'a pas été set dans la config. UUID: " + uuid);
					}
				}

				hasLoadedPerms = true;
			}
		});

	}

	public void addPermission(String perm) {
		if (!perms.contains(perm)) {
			perms.add(perm);
			attachment.setPermission(perm, true);
		}
	}

	public String getRankToString() {
		if(secondaryRank != null) {
			return Color.color(CubixAPI.getInstance().getConfig().getString("ranks." + secondaryRank.getId() + ".rankToString"));
		} else {
			return Color.color(CubixAPI.getInstance().getConfig().getString("ranks." + primaryRank.getId() + ".rankToString"));
		}
	}

	public String getRankID() {
	    if(secondaryRank != null) {
	        return secondaryRank.getId();
        } else {
            return primaryRank.getId();
        }
    }

}
