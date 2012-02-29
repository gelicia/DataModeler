package datamodeler.events;

import datamodeler.models.Connection;

/**
 * An event that signals an attempt to connect to a database
 * 
 * @author Kyle Sletten
 */
public class ConnectionEvent {
	private Connection connection;
	private String host;
	private int port;
	private String username;
	private String password;

	public ConnectionEvent(Connection connection, String host, int port,
			String username, String password) {
		super();
		this.connection = connection;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public String getHost() {
		return this.host;
	}

	public int getPort() {
		return this.port;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public boolean isSuccessful() {
		return this.connection != null;
	}
}
