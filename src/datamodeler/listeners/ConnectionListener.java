package datamodeler.listeners;

import datamodeler.events.ConnectionEvent;

/**
 * An interface that allows an object to listen for attempted connections
 * 
 * @author Kyle Sletten
 */
public interface ConnectionListener {
	/**
	 * Handle an event signal
	 * 
	 * @param connectionEvent
	 *            the signal
	 */
	public void connectionAttempted(ConnectionEvent connectionEvent);
}
