package datamodeler.listeners;

import datamodeler.events.ConnectionEvent;

public interface ConnectionListener {
	public void connectionAttempted(ConnectionEvent event);
}
