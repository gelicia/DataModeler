package datamodeler.awt.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import datamodeler.events.ConnectionEvent;
import datamodeler.listeners.ConnectionListener;
import datamodeler.models.Column;
import datamodeler.models.Connection;
import datamodeler.models.Database;
import datamodeler.models.Table;

@SuppressWarnings("serial")
public class ConnectionExplorer extends Panel implements ConnectionListener {
	private Connection connection;
	private List databaseList;
	private List tableList;
	private List columnList;
	private ColumnView column;

	public ConnectionExplorer() {
		super(new BorderLayout());

		Panel listPanel = new Panel(new GridLayout(1, 0));

		listPanel.add(this.databaseList = new List());
		listPanel.add(this.tableList = new List());
		listPanel.add(this.columnList = new List());

		Panel columnPanel = new Panel(new BorderLayout());

		columnPanel.add(this.column = new ColumnView(), BorderLayout.NORTH);
		columnPanel.add(new Panel(), BorderLayout.CENTER);

		this.column.setEnabled(false);

		super.add(listPanel, BorderLayout.CENTER);
		super.add(columnPanel, BorderLayout.EAST);

		this.databaseList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				ConnectionExplorer.this.tableList.removeAll();
				ConnectionExplorer.this.columnList.removeAll();
				ConnectionExplorer.this.column.clear();

				if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
					Database database = ConnectionExplorer.this
							.getSelectedDatabase();

					try {
						for (Table table : ConnectionExplorer.this.connection
								.getTables(database)) {
							ConnectionExplorer.this.tableList.add(table
									.getName());
						}
					} catch (SQLException e) {
						ConnectionExplorer.this.error(e);
					}

					ConnectionExplorer.this.validate();
				}
			}
		});

		this.tableList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				ConnectionExplorer.this.columnList.removeAll();
				ConnectionExplorer.this.column.clear();

				if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
					Table table = ConnectionExplorer.this.getSelectedTable();

					try {
						for (Column column : ConnectionExplorer.this.connection
								.getColumns(table)) {
							ConnectionExplorer.this.columnList.add(column
									.getName());
						}
					} catch (SQLException e) {
						ConnectionExplorer.this.error(e);
					}

					ConnectionExplorer.this.validate();
				}
			}
		});

		this.columnList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
					Column column = ConnectionExplorer.this.getSelectedColumn();

					ConnectionExplorer.this.column.read(column);
				} else {
					ConnectionExplorer.this.column.clear();
				}
			}
		});

		super.setPreferredSize(new Dimension(700, 400));
	}

	private Database getSelectedDatabase() {
		try {
			for (Database database : this.connection.getDatabases()) {
				if (database.getName().equals(
						this.databaseList.getSelectedItem())) {
					return database;
				}
			}
		} catch (SQLException e) {
			this.error(e);
		}
		throw new IllegalStateException("Could not find database: \""
				+ this.databaseList.getSelectedItem() + "\"");
	}

	private Table getSelectedTable() {
		Database database = this.getSelectedDatabase();

		try {
			for (Table table : this.connection.getTables(database)) {
				if (table.getName().equals(this.tableList.getSelectedItem())) {
					return table;
				}
			}
		} catch (SQLException e) {
			this.error(e);
		}
		throw new IllegalStateException("Could not find table: \""
				+ this.tableList.getSelectedItem() + "\" in \""
				+ database.getName() + "\"");
	}

	private Column getSelectedColumn() {
		Table table = this.getSelectedTable();

		try {
			for (Column column : this.connection.getColumns(table)) {
				if (column.getName().equals(this.columnList.getSelectedItem())) {
					return column;
				}
			}
		} catch (SQLException e) {
			this.error(e);
		}
		throw new IllegalStateException("Could not find column: \""
				+ this.columnList.getSelectedItem() + "\" in \""
				+ table.getName() + "\"");
	}

	private Frame getFrame() {
		Component component = this;

		while (component != null) {
			if (component instanceof Frame) {
				return (Frame) component;
			} else {
				component = component.getParent();
			}
		}
		return null;
	}

	private void error(Exception e) {
		this.error(e.getClass().getName(), e.getMessage());
	}

	private void error(String title, String message) {
		Dialog errorDialog = new AlertDialog(this.getFrame(), title, message);
		errorDialog.pack();
		errorDialog.setVisible(true);
	}

	private void refresh() {
		this.databaseList.removeAll();
		this.tableList.removeAll();
		this.columnList.removeAll();

		try {
			for (Database database : this.connection.getDatabases()) {
				this.databaseList.add(database.getName());
			}
		} catch (SQLException e) {
			this.error(e);
		}

		this.validate();
	}

	@Override
	public void connectionAttempted(ConnectionEvent event) {
		if (event.isSuccessful()) {
			this.connection = event.getConnection();
			this.refresh();
		}
	}
}
