package eu.cubixmc.com.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;

public class SqlConnection {
	
	private Connection connection;
	private String urlbase, host, database, user, password;

	public SqlConnection(String urlbase, String host, String database, String user, String password) {
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
	}

	public void connection() {
		if(!isConnected()) {
			try {
				connection = DriverManager.getConnection(urlbase + host + "/" + database, user, password);
				Bukkit.broadcastMessage("Database connected succesfully.");
				System.out.println("BDD -> OK");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void disconnect() {
		if(isConnected()) {
			try {
				connection.close();
			} catch (SQLException e) {
				Bukkit.broadcastMessage("Database disconnect succesfully.");
			}
		}
	}
	
	public boolean isConnected() {
		return connection != null;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}
