package datamodeler.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Meta-data about the table
 * 
 * @author Kyle Sletten
 * 
 */
public class Table {
	private Database database;
	private String name;
	private List<Column> columns;

	public Table(String name) {
		this(null, name, null);
	}

	public Table(Database database, String name) {
		this(database, name, null);
	}

	public Table(Database database, String name, List<Column> columns) {
		this.setDatabase(database);
		this.setName(name);
		this.setColumns(columns);
	}

	public Database getDatabase() {
		return this.database;
	}

	public void setDatabase(Database database) {
		Database oldDatabase = this.database;
		this.database = database;

		if (oldDatabase != null && oldDatabase != database
				&& oldDatabase.getTables() != null) {
			oldDatabase.getTables().remove(this);
		}
		if (database != null && database.getTables() != null
				&& !database.getTables().contains(this)) {
			database.getTables().add(this);
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new NullPointerException("Table name cannot be null");
		}
		this.name = name;
	}

	public List<Column> getColumns() {
		if (this.columns == null) {
			this.columns = new ArrayList<Column>();
		}
		return this.columns;
	}

	public void setColumns(List<Column> columns) {
		List<Column> oldColumns = this.columns;
		this.columns = columns;

		if (oldColumns != null && oldColumns != columns) {
			for (Column column : oldColumns) {
				column.setTable(null);
			}
		}
		if (columns != null) {
			for (Column column : columns) {
				column.setTable(this);
			}
		}
	}

	@Override
	public String toString() {
		return this.database == null ? this.getName() : this.database.getName()
				+ "." + this.name;
	}
}
