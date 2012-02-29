package datamodeler.listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowCloser extends WindowAdapter {
	@Override
	public void windowClosing(WindowEvent windowEvent) {
		windowEvent.getWindow().setVisible(false);
	}
}
