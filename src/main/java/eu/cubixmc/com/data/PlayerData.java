package eu.cubixmc.com.data;

import eu.cubixmc.com.ranks.Ranks;

public class PlayerData {

	private int coins;
	private int credits;
	private Ranks rank;
	
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public Ranks getRank() {
		return rank;
	}
	public void setRank(Ranks rank) {
		this.rank = rank;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
}
