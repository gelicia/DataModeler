package datamodeler.awt.listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A quick <code>WindowListener</code> that closes its parent window
 * 
 * @author Kyle Sletten
 * 
 */
public class WindowCloser extends WindowAdapter {
	@Override
	public void windowClosing(WindowEvent windowEvent) {
		windowEvent.getWindow().setVisible(false);
	}
}
