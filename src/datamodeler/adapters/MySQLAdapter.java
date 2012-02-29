package datamodeler.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import datamodeler.models.Column;
import datamodeler.models.Database;
import datamodeler.models.Table;

/**
 * An adapter for MySQL databases
 * 
 * @author Kyle Sletten
 */
public class MySQLAdapter implements Adapter {
	@Override
	public String getConnectionString(String host, int port, String username,
			String password) {
		return "jdbc:mysql://" + host + ":" + port + "/?user=" + username
				+ "&password=" + password;
	}

	@Override
	public Iterable<Database> getDatabases(Statement statement) {
		List<Database> databases = new ArrayList<Database>();

		try {
			ResultSet resultSet = statement.executeQuery("SHOW DATABASES;");

			while (resultSet.next()) {
				databases.add(new Database(resultSet.getString(1)));
			}

			resultSet.close();
		} catch (SQLException e) {
			return null;
		}

		return databases;
	}

	@Override
	public Iterable<Table> getTables(Statement statement, Database database) {
		try {
			ResultSet resultSet = statement.executeQuery("SHOW TABLES IN "
					+ database + ";");

			while (resultSet.next()) {
				new Table(database, resultSet.getString(1));
			}

			resultSet.close();
		} catch (SQLException e) {
			return null;
		}

		return database.getTables();
	}

	@Override
	public Iterable<Column> getColumns(Statement statement, Table table) {
		try {
			ResultSet resultSet = statement.executeQuery("SHOW COLUMNS IN "
					+ table + ";");

			while (resultSet.next()) {
				new Column(table, resultSet.getString(1), resultSet
						.getString(2).toUpperCase(), resultSet.getString(3)
						.equals("YES"), resultSet.getString(5));
			}

			resultSet.close();
		} catch (SQLException e) {
			return null;
		}

		return table.getColumns();
	}

	@Override
	public int getDefaultPort() {
		return 3306;
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {

		}
	}
}
