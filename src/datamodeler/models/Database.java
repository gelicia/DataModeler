package datamodeler.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Meta-data about the database
 * 
 * @author Kyle Sletten
 * 
 */
public class Database {
	private String name;
	private List<Table> tables;

	public Database(String name) {
		this(name, null);
	}

	public Database(String name, List<Table> tables) {
		this.setName(name);
		this.setTables(tables);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new NullPointerException("Database name cannot be null");
		}
		this.name = name;
	}

	public List<Table> getTables() {
		if (this.tables == null) {
			this.tables = new ArrayList<Table>();
		}
		return this.tables;
	}

	public void setTables(List<Table> tables) {
		List<Table> oldTables = this.tables;
		this.tables = tables;

		if (oldTables != null && oldTables != tables) {
			for (Table table : oldTables) {
				table.setDatabase(null);
			}
		}
		if (tables != null) {
			for (Table table : tables) {
				table.setDatabase(this);
			}
		}
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
