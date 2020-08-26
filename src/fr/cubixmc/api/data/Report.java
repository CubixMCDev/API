package fr.cubixmc.api.data;

import java.sql.Timestamp;

import org.bukkit.OfflinePlayer;

public class Report {
	
	private int id;
	
	private OfflinePlayer sender;
	
	private OfflinePlayer offender;

	private String reason;
	
	private Timestamp date;
	
	private boolean resolved;

	public Report(int id, OfflinePlayer sender, OfflinePlayer offender, String reason, Timestamp date, boolean resolved) {
		this.id = id;
		this.sender = sender;
		this.offender = offender;
		this.reason = reason;
		this.date = date;
		this.resolved = false;
		this.resolved = resolved;
	}
	
	public int getId() {
		return this.id;
	}
	
	public OfflinePlayer getSender() {
		return this.sender;
	}
	
	public OfflinePlayer getOffender() {
		return this.offender;
	}
	
	public String getReason() {
		return reason;
	}
	
	public Timestamp getDate() {
		return date;
	}
	
	public boolean isResolved() {
		return this.resolved;
	}
	
	public void setResolved(boolean trueOrFalse) {
		this.resolved = trueOrFalse;
	}

}
