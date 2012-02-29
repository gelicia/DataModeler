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
	private TextField name;
	private TextField columnType;
	private TextField nullable;
	private TextField defaultValue;

	public ColumnView() {
		super(new BorderLayout());

		Panel labelPanel = new Panel(new GridLayout(0, 1));
		Panel inputPanel = new Panel(new GridLayout(0, 1));

		labelPanel.add(new Label("Name"));
		inputPanel.add(this.name = new TextField());

		labelPanel.add(new Label("Type"));
		inputPanel.add(this.columnType = new TextField());

		labelPanel.add(new Label("Nullable"));
		inputPanel.add(this.nullable = new TextField());

		labelPanel.add(new Label("Default"));
		inputPanel.add(this.defaultValue = new TextField());

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
		this.defaultValue.setText(column.getDefaultValue());
	}
}
