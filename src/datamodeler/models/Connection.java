package datamodeler.models;

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

    public Connection(Adapter adapter, java.sql.Connection connection)
	    throws SQLException {
	this.adapter = adapter;
	this.connection = connection;
    }

    public Iterable<Database> getDatabases() throws SQLException {
	synchronized (this) {
	    if (this.databases == null) {
		this.databases = this.adapter.getDatabases(this.connection
			.createStatement());

	    }
	}
	return this.databases;
    }

    public Iterable<Table> getTables(Database database) throws SQLException {
	synchronized (database) {
	    if (database.getTables() == null || database.getTables().isEmpty()) {
		this.adapter.getTables(this.connection.createStatement(),
			database);
	    }
	}
	return database.getTables();
    }

    public Iterable<Column> getColumns(Table table) throws SQLException {
	synchronized (table) {
	    if (table.getColumns() == null || table.getColumns().isEmpty()) {
		this.adapter.getColumns(this.connection.createStatement(),
			table);
	    }
	}
	return table.getColumns();
    }
}
