package datamodeler;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import datamodeler.awt.components.AlertDialog;
import datamodeler.awt.components.ConnectionExplorer;
import datamodeler.awt.components.ConnectionForm;
import datamodeler.awt.components.FormatMenuItem;
import datamodeler.awt.listeners.WindowCloser;
import datamodeler.events.ConnectionEvent;
import datamodeler.formats.SimpleFormat;
import datamodeler.formats.XMLFormat;
import datamodeler.listeners.ConnectionListener;

/**
 * @author Kyle Sletten
 */
public class DataModeler {
	public static void awt() {
		final Frame frame = new Frame("DataModeler");

		final ConnectionForm connectionForm = new ConnectionForm();

		final ConnectionExplorer connectionExplorer = new ConnectionExplorer();

		connectionForm.addConnectionListener(connectionExplorer);

		frame.add(connectionExplorer);

		final Dialog formDialog = new Dialog(frame, "Connection Form", true);

		formDialog.add(connectionForm);

		final WindowListener windowCloser = new WindowCloser();

		formDialog.addWindowListener(windowCloser);

		connectionForm.addConnectionListener(new ConnectionListener() {
			@Override
			public void connectionAttempted(ConnectionEvent connectionEvent) {
				if (connectionEvent.isSuccessful()) {
					formDialog.setVisible(false);
				} else {
					Dialog errorDialog = new AlertDialog(frame,
							"Connection Error",
							"Unable to connect to the database");

					errorDialog.pack();
					errorDialog.setVisible(true);
				}
			}
		});

		MenuBar menuBar = new MenuBar();

		Menu fileMenu = new Menu("File");

		FormatMenuItem saveMenuItem = new FormatMenuItem(frame, connectionForm,
				new SimpleFormat(), "Save", KeyEvent.VK_S, false);

		fileMenu.add(saveMenuItem);

		FormatMenuItem saveToXMLMenuItem = new FormatMenuItem(frame,
				connectionForm, new XMLFormat(), "Save to XML", KeyEvent.VK_X,
				true);

		fileMenu.add(saveToXMLMenuItem);

		Menu connectionMenu = new Menu("Connection");

		MenuItem connectMenuItem = new MenuItem("Connect");

		connectMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				formDialog.pack();
				formDialog.setVisible(true);
			}
		});

		connectMenuItem.setShortcut(new MenuShortcut(KeyEvent.VK_C, true));

		connectionMenu.add(connectMenuItem);

		menuBar.add(fileMenu);
		menuBar.add(connectionMenu);

		frame.setMenuBar(menuBar);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			switch (args[0]) {
			case "awt":
				awt();
				break;
			}
		} else {
			awt();
		}
	}
}
