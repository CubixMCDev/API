package fr.cubixmc.api.managers.sanctions;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import fr.cubixmc.api.CubixAPI;
import fr.cubixmc.api.data.Report;

public class ReportManager {

	private CubixAPI main;
	
	public ReportManager(CubixAPI main) {
		this.main = main;
	}
	
	public void reportPlayer(UUID senderUUID, UUID offenderUUID, String reason) {
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("INSERT INTO reports(offender,sender,reason,date) values(?,?,?,current_timestamp)");
			q.setString(1, offenderUUID.toString());
			q.setString(2, senderUUID.toString());
			q.setString(3, reason);
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Report findReport(List<Report> reportList, int id) {
		for(Report report : reportList) {
			if(report.getId() == id) {
				return report;
			}
		}
		return null;
	}
	
	public void setArchive(Report report, boolean trueOrFalse) {
		int resolved;
		if(trueOrFalse == true) {
			resolved = 1;
		} else {
			resolved = 0;
		}
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("UPDATE reports SET resolved = " + resolved + " WHERE id = " + report.getId());
			q.executeUpdate();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteReport(Report report) {
		try {
			PreparedStatement q = (PreparedStatement) main.getDataBase().getConnection().prepareStatement("DELETE FROM reports WHERE id = " + report.getId());
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
