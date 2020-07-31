package eu.cubixmc.com;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import eu.cubixmc.com.commands.CommandCoins;
import eu.cubixmc.com.commands.CommandCredits;
import eu.cubixmc.com.managers.*;
import eu.cubixmc.com.encapsulation.Get;
import eu.cubixmc.com.encapsulation.Set;
import eu.cubixmc.com.events.EventsListener;
import eu.cubixmc.com.ranks.Rank;
import eu.cubixmc.com.data.sanctions.BanPlayerData;
import eu.cubixmc.com.data.sanctions.MutePlayerData;
import eu.cubixmc.com.data.User;
import eu.cubixmc.com.ranks.ScoarboardTeam;
import eu.cubixmc.com.sql.MysqlManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

@Getter
@Setter
public class CubixAPI extends JavaPlugin implements Listener {

	private static CubixAPI instance;
	public static CubixAPI getInstance() {
		return instance;
	}

	private Map<Player, User> dataPlayers = new HashMap<>();
	private Map<UUID, BanPlayerData> banned = new HashMap<>();
	private Map<UUID, MutePlayerData> muted = new HashMap<>();

	private final Map<String, Rank> idToRank = new HashMap<>();
	private Rank defaultRank;

	private Get get;
	private Set set;
	private UserManager userManager;
	private BanManager banManager;
	private MuteManager muteManager;
	private ModManager modManager;
	private FriendsManager friendsManager;
	private PartyManager partyManager;
	private MysqlManager databaseManager;
	
	private ScoarboardTeam scTeam;
	private boolean teamTagOn = true;
	
	private Scoreboard s;
	
	@Override
	public void onEnable() {
		instance = this;
		get = new Get(this);
		set = new Set(this);
		/* ENABLE WHEN RELEASING ON ALL SERVERS AT THE SAME TIME
		File configFile = new File(this.getDataFolder(), "config.yml");
		if(configFile.exists()) configFile.delete();
		 */
		loadConfig();
		s = Bukkit.getScoreboardManager().getMainScoreboard();
		databaseManager = new MysqlManager(this);
		userManager = new UserManager(this);
		scTeam = new ScoarboardTeam(this);
		banManager = new BanManager(this);
		muteManager = new MuteManager(this);
		modManager = new ModManager(this);
		friendsManager = new FriendsManager(this);
		partyManager = new PartyManager(this);

		loadRanks();
		registerCommands();

		Bukkit.getPluginManager().registerEvents(new EventsListener(this), this);

		banManager.loadBannedPlayers();
		muteManager.loadMutedPlayers();

		scTeam.registerTeamTag(true);
	}

	private void registerCommands() {
		getCommand("coins").setExecutor(new CommandCoins(this));
		getCommand("credits").setExecutor(new CommandCredits(this));
		getCommand("ban").setExecutor(banManager);
		getCommand("unban").setExecutor(banManager);
		getCommand("tempban").setExecutor(banManager);
		getCommand("mute").setExecutor(muteManager);
		getCommand("tempmute").setExecutor(muteManager);
		getCommand("unmute").setExecutor(muteManager);
	}

	private void loadRanks() {
		for (String s : getConfig().getConfigurationSection("ranks").getKeys(false)) {
			Rank rank = new Rank(getConfig().getString("ranks." + s + ".id"),
					ChatColor.valueOf(getConfig().getString("ranks." + s + ".colorChat")),
					getConfig().getStringList("ranks." + s + ".perms"));
			if (getConfig().isSet("ranks." + s + ".default")) {
				this.defaultRank = rank;
			}
			if (getConfig().isSet("ranks." + s + ".inherit")) {
				rank.setInherits(getConfig().getStringList("ranks." + s + ".inherit"));
			}
			idToRank.put(getConfig().getString("ranks." + s + ".id").toLowerCase(), rank);
		}
		if (defaultRank == null) {
			System.out.println("Aucun rank par default n'est set !");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}

	@Override
	public void onDisable() {
		this.databaseManager.close();
	}

	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public Get get() {
		return get;
	}

	public Set set(){
		return set;
	}
}
