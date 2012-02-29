package datamodeler.delegates;

import java.util.HashSet;

import datamodeler.events.ConnectionEvent;
import datamodeler.listeners.ConnectionListener;

/**
 * An object that allows simple propagation of a <code>ConnectionEvent</code> to
 * a set of listeners
 * 
 * @author Kyle Sletten
 */
public class ConnectionDelegate {
	private HashSet<ConnectionListener> connectionListeners = new HashSet<ConnectionListener>();

	public void addConnectionListener(ConnectionListener connectionListener) {
		this.connectionListeners.add(connectionListener);
	}

	public void removeConnectionListener(ConnectionListener connectionListener) {
		this.connectionListeners.remove(connectionListener);
	}

	public ConnectionEvent connectionAttempted(ConnectionEvent connectionEvent) {
		for (ConnectionListener connectionListener : this.connectionListeners) {
			connectionListener.connectionAttempted(connectionEvent);
		}
		return connectionEvent;
	}
}
