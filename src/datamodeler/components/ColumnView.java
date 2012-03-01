package datamodeler.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

import datamodeler.models.Column;

@SuppressWarnings("serial")
public class ColumnView extends Panel {
	final private TextField name = new TextField();
	final private TextField columnType = new TextField();
	final private TextField nullable = new TextField();
	final private KeyTypeChoice key = new KeyTypeChoice();
	final private TextField defaultValue = new TextField();

	public ColumnView() {
		super(new BorderLayout());

		Panel labelPanel = new Panel(new GridLayout(0, 1));
		Panel inputPanel = new Panel(new GridLayout(0, 1));

		labelPanel.add(new Label("Name"));
		inputPanel.add(this.name);

		labelPanel.add(new Label("Type"));
		inputPanel.add(this.columnType);

		labelPanel.add(new Label("Nullable"));
		inputPanel.add(this.nullable);

		labelPanel.add(new Label("Key"));
		inputPanel.add(this.key);

		labelPanel.add(new Label("Default"));
		inputPanel.add(this.defaultValue);

		super.add(labelPanel, BorderLayout.WEST);
		super.add(inputPanel, BorderLayout.CENTER);

		this.name.setPreferredSize(new Dimension(200, 0));
	}

	public ColumnView(Column column) {
		this();
		this.read(column);
	}

	public void clear() {
		this.name.setText("");
		this.columnType.setText("");
		this.nullable.setText("");
		this.defaultValue.setText("");
	}

	public void read(Column column) {
		this.name.setText(column.getName());
		this.columnType.setText(column.getColumnType());
		this.nullable.setText(column.isNullable() ? "YES" : "NO");
		this.key.setSelectedKeyType(column.getKeyType());
		this.defaultValue.setText(column.getDefaultValue());
	}
}
