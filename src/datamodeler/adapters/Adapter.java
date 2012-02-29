package datamodeler.adapters;

import java.sql.SQLException;
import java.sql.Statement;

import datamodeler.models.Column;
import datamodeler.models.Database;
import datamodeler.models.Table;

public interface Adapter {
	public String getConnectionString(String host, int port, String username,
			String password);

	public Iterable<Database> getDatabases(Statement statement)
			throws SQLException;

	public Iterable<Table> getTables(Statement statement, Database database)
			throws SQLException;

	public Iterable<Column> getColumns(Statement statement, Table tables)
			throws SQLException;

	public int getDefaultPort();
}
