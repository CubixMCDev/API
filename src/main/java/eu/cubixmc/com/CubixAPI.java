package eu.cubixmc.com;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import eu.cubixmc.com.DataBase.SqlConnection;
import eu.cubixmc.com.commands.CommandCoins;
import eu.cubixmc.com.commands.CommandCredits;
import eu.cubixmc.com.commands.CommandRank;
import eu.cubixmc.com.data.BanPlayerData;
import eu.cubixmc.com.data.MutePlayerData;
import eu.cubixmc.com.data.PlayerData;
import eu.cubixmc.com.events.EventsListener;
import eu.cubixmc.com.managers.*;
import eu.cubixmc.com.managers.friendsAndParty.FriendsManager;
import eu.cubixmc.com.managers.friendsAndParty.PartyManager;
import eu.cubixmc.com.managers.sanctions.BanManager;
import eu.cubixmc.com.managers.sanctions.ModManager;
import eu.cubixmc.com.managers.sanctions.MuteManager;
import eu.cubixmc.com.ranks.ScoarboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public class CubixAPI extends JavaPlugin implements Listener {
	
	private SqlConnection sql;
	public Map<Player, PlayerData> dataPlayers = new HashMap<>();
	public Map<UUID, BanPlayerData> banned = new HashMap<>();
	public Map<UUID, MutePlayerData> muted = new HashMap<>();
	public CommandCoins commandCoin = new CommandCoins(this);
	public CommandCredits commandCredits = new CommandCredits(this);
	
	private PlayerDataManager dataManager = new PlayerDataManager(this);
	private BanManager banManager = new BanManager(this);
	private MuteManager muteManager = new MuteManager(this);
	private EcoManager ecoManager = new EcoManager(this);
	private RankManager rankManager = new RankManager(this);
	private PvPBoxManager pvpBoxManager = new PvPBoxManager(this);
	private ModManager modManager = new ModManager(this);
	private FriendsManager friendsManager = new FriendsManager(this);
	private PartyManager partyManager = new PartyManager(this);
	private ExpManager expManager = new ExpManager(this);
	
	public ScoarboardTeam scTeam = new ScoarboardTeam(this);
	
	public Scoreboard s;
	
	String base = getConfig().getString("sql.base");
	String id = getConfig().getString("sql.identifiant");
	String mdp = getConfig().getString("sql.mdp");
	
	@Override
	public void onEnable() {
		loadConfig();
		s = Bukkit.getScoreboardManager().getMainScoreboard();
		
		sql = new SqlConnection("jdbc:mysql://", "localhost", base, id, mdp);
		sql.connection();
		
		getCommand("coins").setExecutor(commandCoin);
		getCommand("credits").setExecutor(commandCredits);
		getCommand("ban").setExecutor(banManager);
		getCommand("unban").setExecutor(banManager);
		getCommand("tempban").setExecutor(banManager);
		getCommand("mute").setExecutor(muteManager);
		getCommand("tempmute").setExecutor(muteManager);
		getCommand("unmute").setExecutor(muteManager);
		getCommand("rank").setExecutor(new CommandRank(this));
		
		getServer().getPluginManager().registerEvents(new EventsListener(this), this);
		
		banManager.loadBannedPlayer();
		muteManager.loadMutedPlayer();
		scTeam.registerTeamTag();
		
	}
	
	@Override
	public void onDisable() {
		sql.disconnect();
	}
	
	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public SqlConnection getDataBase() {
		return sql;
	}

	public PlayerDataManager getPlayerDataManager() {
		return dataManager;
	}

	public BanManager getBanManager() {
		return banManager;
	}

	public MuteManager getMuteManager() {
		return muteManager;
	}

	public EcoManager getEcoManager() {
		return ecoManager;
	}

	public RankManager getRankManager() {
		return rankManager;
	}

	public PvPBoxManager getPvPBoxManager() {
		return pvpBoxManager;
	}

	public ModManager getModManager() {
		return modManager;
	}

	public ExpManager getExpManager() {
		return expManager;
	}

	public FriendsManager getFriendsManager() {
		return friendsManager;
	}

	public PartyManager getPartyManager() {
		return partyManager;
	}

}
