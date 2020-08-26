package fr.cubixmc.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import fr.cubixmc.api.DataBase.SqlConnection;
import fr.cubixmc.api.commands.CommandCoins;
import fr.cubixmc.api.commands.CommandCredits;
import fr.cubixmc.api.commands.CommandRank;
import fr.cubixmc.api.commands.CommandReport;
import fr.cubixmc.api.data.BanPlayerData;
import fr.cubixmc.api.data.MutePlayerData;
import fr.cubixmc.api.data.PlayerData;
import fr.cubixmc.api.events.EventsListener;
import fr.cubixmc.api.managers.EcoManager;
import fr.cubixmc.api.managers.PlayerDataManager;
import fr.cubixmc.api.managers.PvPBoxManager;
import fr.cubixmc.api.managers.RankManager;
import fr.cubixmc.api.managers.experience.ExpManager;
import fr.cubixmc.api.managers.friendsAndParty.FriendsManager;
import fr.cubixmc.api.managers.friendsAndParty.PartyManager;
import fr.cubixmc.api.managers.sanctions.BanManager;
import fr.cubixmc.api.managers.sanctions.ModManager;
import fr.cubixmc.api.managers.sanctions.MuteManager;
import fr.cubixmc.api.managers.sanctions.ReportManager;
import fr.cubixmc.api.ranks.ScoarboardTeam;

public class CubixAPI extends JavaPlugin implements Listener {
	
	private SqlConnection sql;
	public Map<Player, PlayerData> dataPlayers = new HashMap<Player, PlayerData>();
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
	private ReportManager reportManager = new ReportManager(this);
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
		
		sql = new SqlConnection("jdbc:mysql://", "localhost", "cubixmc", "root", "");
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
		getCommand("report").setExecutor(new CommandReport(this));
		
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
	
	public ReportManager getReportManager() {
		return reportManager;
	}
	
	public FriendsManager getFriendsManager() {
		return friendsManager;
	}
	
	public PartyManager getPartyManager() {
		return partyManager;
	}
	
	public ExpManager getExpManager() {
		return expManager;
	}
	
}
