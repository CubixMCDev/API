package eu.cubixmc.com.data.sanctions;

import java.util.UUID;

public class MutePlayerData {

	private UUID uuid;
	private String reason;
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}

}
