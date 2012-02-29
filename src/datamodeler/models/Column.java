package datamodeler.models;

/**
 * Meta-data about a database column
 * 
 * @author Kyle Sletten
 * 
 */
public class Column {
	private Table table;
	private String name;
	private String columnType;
	private boolean nullable;
	private String defaultValue;

	public Column(Table table, String name, String type, boolean nullable,
			String defaultValue) {
		this.setTable(table);
		this.setName(name);
		this.setColumnType(type);
		this.setNullable(nullable);
		this.setDefaultValue(defaultValue);
	}

	public Table getTable() {
		return this.table;
	}

	public void setTable(Table table) {
		Table oldTable = this.table;
		this.table = table;

		if (oldTable != null && oldTable != table
				&& oldTable.getColumns() != null) {
			oldTable.getColumns().remove(this);
		}
		if (table != null && table.getColumns() != null
				&& !table.getColumns().contains(this)) {
			table.getColumns().add(this);
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new NullPointerException("Column name cannot be null");
		}
		this.name = name;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String type) {
		this.columnType = type;
	}

	public boolean isNullable() {
		return this.nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
