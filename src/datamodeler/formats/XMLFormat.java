package datamodeler.formats;

import java.io.IOException;
import java.io.Writer;

import datamodeler.models.Column;
import datamodeler.models.Database;
import datamodeler.models.Table;

/**
 * A simple XML format
 * 
 * @author Kyle Sletten
 */
public class XMLFormat implements Format<Database> {
	public void write(Iterable<Database> databases, Writer writer)
			throws IOException {
		writer.write("<databases>");
		for (Database database : databases) {
			this.write(database, writer);
		}
		writer.write("</databases>");
	}

	public void write(Database database, Writer writer) throws IOException {
		if (database == null) {
			throw new NullPointerException();
		}
		writer.write("<database>");
		this.writeTag("name", database.getName(), writer);
		if (database.getTables() != null && !database.getTables().isEmpty()) {
			writer.write("<tables>");
			for (Table table : database.getTables()) {
				this.write(table, writer);
			}
			writer.write("</tables>");
		} else {
			writer.write("<tables/>");
		}
		writer.write("</database>");
	}

	public void write(Table table, Writer writer) throws IOException {
		if (table == null) {
			throw new NullPointerException();
		}
		writer.write("<table>");
		this.writeTag("name", table.getName(), writer);
		if (table.getColumns() != null && !table.getColumns().isEmpty()) {
			writer.write("<columns>");
			for (Column column : table.getColumns()) {
				this.write(column, writer);
			}
			writer.write("</columns>");
		} else {
			writer.write("<columns/>");
		}
		writer.write("</table>");
	}

	public void write(Column column, Writer writer) throws IOException {
		if (column == null) {
			throw new NullPointerException();
		}
		writer.write("<column>");
		this.writeTag("name", column.getName(), writer);
		this.writeTag("type", column.getColumnType(), writer);
		this.writeTag("nullable", column.isNullable(), writer);
		this.writeTag("default", column.getDefaultValue(), writer);
		writer.write("</column>");
	}

	private void writeTag(String name, boolean value, Writer writer)
			throws IOException {
		this.writeTag(name, String.valueOf(value), writer);
	}

	private void writeTag(String name, String value, Writer writer)
			throws IOException {
		writer.write("<");
		writer.write(name);
		if (value != null && !value.isEmpty()) {
			writer.write(">");
			writer.write(value);
			writer.write("</");
			writer.write(name);
			writer.write(">");
		} else {
			writer.write("/>");
		}
	}
}
