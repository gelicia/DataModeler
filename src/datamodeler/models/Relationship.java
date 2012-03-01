package datamodeler.models;

/**
 * A link between two columns
 * 
 * @author Kyle Sletten
 * 
 */
public class Relationship {
	private Column from;
	private Column to;

	public Column getFromColumn() {
		return this.from;
	}

	public Table getFromTable() {
		return this.from.getTable();
	}

	public Column getToColumn() {
		return this.to;
	}

	public Table getToTable() {
		return this.to.getTable();
	}
}
