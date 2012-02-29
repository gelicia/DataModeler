package datamodeler.formats;

import java.io.IOException;
import java.io.Writer;

import datamodeler.models.Column;
import datamodeler.models.Database;
import datamodeler.models.Table;

/**
 * A simple, easily diff-able format
 * 
 * @author Kyle Sletten
 * 
 */
public class SimpleFormat implements Format<Database> {
	public void write(Iterable<Database> databases, Writer writer)
			throws IOException {
		boolean firstDatabase = true;
		for (Database database : databases) {
			if (firstDatabase) {
				firstDatabase = false;
			} else {
				writer.write("\n");
			}
			this.write(database, writer);
		}
	}

	public void write(Database database, Writer writer) throws IOException {
		writer.write(database.getName());
		for (Table table : database.getTables()) {
			writer.write("\n\t");
			writer.write(table.getName());
			for (Column column : table.getColumns()) {
				writer.write("\n\t\t");
				writer.write(column.getName());
				writer.write(" ");
				writer.write(column.getColumnType());
				writer.write(" ");
				if (column.isNullable()) {
					writer.write("NOT ");
				}
				writer.write("NULL");
				if (column.getDefaultValue() != null) {
					writer.write(" DEFAULT \"");
					writer.write(column.getDefaultValue());
					writer.write("\"");
				}
			}
		}
	}
}
