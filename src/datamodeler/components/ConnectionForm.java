package datamodeler.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.sql.SQLException;

import datamodeler.adapters.Adapter;
import datamodeler.delegates.ConnectionDelegate;
import datamodeler.events.ConnectionEvent;
import datamodeler.listeners.ConnectionListener;
import datamodeler.models.Connection;

@SuppressWarnings("serial")
public class ConnectionForm extends Panel {
	private final AdapterChoice adapter;
	private final TextField host = new TextField();
	private final TextField port = new TextField();
	private final TextField username = new TextField();
	private final TextField password = new TextField();
	private final ConnectionDelegate connectionListeners = new ConnectionDelegate();
	private ConnectionEvent connectionEvent = null;

	public ConnectionForm() {
		super(new BorderLayout());

		TextListener textListener = new TextListener() {
			@Override
			public void textValueChanged(TextEvent textEvent) {
				ConnectionForm.this.connectionEvent = null;
			}
		};

		KeyListener keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					ConnectionForm.this.setEnabled(false);
					ConnectionForm.this.getConnection();
					ConnectionForm.this.setEnabled(true);
				}
			}
		};

		Panel titlePanel = new Panel(new GridLayout(0, 1));
		Panel inputPanel = new Panel(new GridLayout(0, 1));

		titlePanel.add(new Label("Adapter"));
		inputPanel.add(this.adapter = new AdapterChoice());
		this.adapter.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				ConnectionForm.this.connectionEvent = null;
			}
		});

		titlePanel.add(new Label("Host"));
		inputPanel.add(this.host);
		this.host.addTextListener(textListener);
		this.host.addKeyListener(keyListener);

		titlePanel.add(new Label("Port"));
		inputPanel.add(this.port);
		this.port.addTextListener(textListener);
		this.port.addKeyListener(keyListener);

		titlePanel.add(new Label("Username"));
		inputPanel.add(this.username);
		this.username.addTextListener(textListener);
		this.username.addKeyListener(keyListener);

		titlePanel.add(new Label("Password"));
		inputPanel.add(this.password);
		this.password.addTextListener(textListener);
		this.password.addKeyListener(keyListener);
		this.password.setEchoChar('*');

		super.add(titlePanel, BorderLayout.WEST);
		super.add(inputPanel, BorderLayout.CENTER);

		this.host.setPreferredSize(new Dimension(200, 0));
	}

	public void addConnectionListener(ConnectionListener connectionListener) {
		this.connectionListeners.addConnectionListener(connectionListener);
	}

	public void removeConnectionListener(ConnectionListener connectionListener) {
		this.connectionListeners.removeConnectionListener(connectionListener);
	}

	public void setEditable(boolean editable) {
		this.adapter.setEnabled(editable);
		this.host.setEditable(editable);
		this.port.setEditable(editable);
		this.username.setEditable(editable);
		this.password.setEditable(editable);
	}

	public ConnectionEvent getConnection() {
		if (this.connectionEvent == null) {
			return this.connectionEvent = this.startConnection();
		} else {
			return this.connectionListeners
					.connectionAttempted(this.connectionEvent);
		}
	}

	private ConnectionEvent startConnection() {
		Connection connection;
		Adapter adapter = this.adapter.getSelectedAdapter();
		String host = this.host.getText();
		int port = adapter.getDefaultPort();
		String username = this.username.getText();
		String password = this.password.getText();

		if (this.port.getText() != null && !this.port.getText().isEmpty()) {
			try {
				port = Integer.parseInt(this.port.getText());
			} catch (Exception e) {
				port = Integer.MIN_VALUE;
			}
		}

		try {
			connection = new Connection(adapter, host, port, username, password);
		} catch (SQLException e) {
			connection = null;
		}

		return this.connectionListeners
				.connectionAttempted(new ConnectionEvent(connection, host,
						port, username, password));
	}
}