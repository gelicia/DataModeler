package datamodeler.adapters;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import datamodeler.models.Column;
import datamodeler.models.Database;
import datamodeler.models.KeyType;
import datamodeler.models.Table;
import datamodeler.models.Connection;

/**
 * An adapter for SQL Server databases
 * 
 * @author Kristina Durivage
 */
public class MSSQLAdapter implements Adapter {
	@Override
	
	public Connection getConnection (String host, String port, String username, String password) throws SQLException
	{
		return new Connection(this, DriverManager.getConnection("jdbc:sqlserver://" + host + ":" + port + ";user=" + username
				+ ";password=" + password));
	}

	public Iterable<Database> getDatabases(Statement statement) throws SQLException {
		List<Database> databases = new ArrayList<Database>();

			ResultSet resultSet = statement.executeQuery("SELECT name FROM sys.databases");

			while (resultSet.next()) {
				databases.add(new Database(resultSet.getString(1)));
		}

			resultSet.close();
			
		return databases;
	}

	public Iterable<Table> getTables(Statement statement, Database database) throws SQLException  {
		//try {
			String SQL = " USE [" + database.getName() + "]; " +
					" SELECT name " +
					" FROM sys.Tables" +
					" ORDER BY name " ;
			
			ResultSet resultSet = statement.executeQuery(SQL);

			while (resultSet.next()) {
				new Table(database, resultSet.getString(1));
			}

			resultSet.close();

		return database.getTables();
	}

	public Iterable<Column> getColumns(Statement statement, Table table) throws SQLException{
			String SQL = "USE [" + table.getDatabase().getName() + "]; " +
					" SELECT col.name colName, typ.name typeName, col.is_nullable, " +
					" object_definition(col.default_object_id) defaultValue, cont.[type] keyType " +
					" FROM sys.columns col " +
					" INNER JOIN sys.types typ ON typ.system_type_id = col.system_type_id " +
					" LEFT OUTER JOIN sys.index_columns idxCol ON  " +
					" idxCol.OBJECT_ID = col.OBJECT_ID " +
					" AND col.column_id = idxCol.column_id " +
					" LEFT OUTER JOIN sys.key_constraints cont ON " + 
					" idxCol.OBJECT_ID = cont.parent_object_id " +
					" AND cont.unique_index_id = idxCol.index_id " +
					" WHERE col.OBJECT_ID =  OBJECT_ID(\'" + table.getName() +"\')" +
					" ORDER BY col.name ";
		
			ResultSet resultSet = statement.executeQuery(SQL);
			
			while (resultSet.next()) {
				KeyType keyType;
				
				String colName = resultSet.getString("colName");
				String typeName = resultSet.getString("typeName");
				boolean isNullable = resultSet.getBoolean("is_nullable");
				String defaultValue = resultSet.getString("defaultValue");

				String value = resultSet.getString("keyType");

				if (value == null || value.isEmpty()) {
					keyType = KeyType.NONE;
				} else if (value.equals("PK")) {
					keyType = KeyType.PRIMARY;
				} else if (value.equals("UQ")) {
					keyType = KeyType.UNIQUE;
				} else {
					keyType = KeyType.NONE;
				}
				
				new Column(table, colName, typeName, isNullable, keyType, defaultValue);
			}

			resultSet.close();

		return table.getColumns();
	}

}
