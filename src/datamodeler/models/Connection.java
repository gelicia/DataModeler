package datamodeler.models;

import java.sql.DriverManager;
import java.sql.SQLException;

import datamodeler.adapters.Adapter;

/**
 * Meta-data about a connection
 * 
 * @author Kyle Sletten
 * 
 */
public class Connection {
	private java.sql.Connection connection;
	private Adapter adapter;
	private Iterable<Database> databases;

	private Connection(Adapter adapter) {
		this.adapter = adapter;
	}

	public Connection(Adapter adapter, String host, int port, String username,
			String password) throws SQLException {
		this(adapter);
		this.connection = DriverManager.getConnection(this.adapter
				.getConnectionString(host, port, username, password));
	}

	public synchronized Iterable<Database> getDatabases() throws SQLException {
		if (this.databases == null) {
			this.databases = this.adapter.getDatabases(this.connection
					.createStatement());
		}
		return this.databases;
	}

	public synchronized Iterable<Table> getTables(Database database)
			throws SQLException {
		if (database.getTables() == null || database.getTables().isEmpty()) {
			this.adapter.getTables(this.connection.createStatement(), database);
		}
		return database.getTables();
	}

	public synchronized Iterable<Column> getColumns(Table table)
			throws SQLException {
		if (table.getColumns() == null || table.getColumns().isEmpty()) {
			this.adapter.getColumns(this.connection.createStatement(), table);
		}
		return table.getColumns();
	}

	public boolean loadMetaData() {
		try {
			for (Database database : this.getDatabases()) {
				for (Table table : this.getTables(database)) {
					this.getColumns(table);
				}
			}
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
