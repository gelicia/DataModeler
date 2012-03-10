package datamodeler.awt;

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
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import datamodeler.awt.components.AlertDialog;
import datamodeler.awt.components.ConnectionExplorer;
import datamodeler.awt.components.ConnectionForm;
import datamodeler.awt.components.FormatMenuItem;
import datamodeler.awt.listeners.WindowCloser;
import datamodeler.config.Config;
import datamodeler.events.ConnectionEvent;
import datamodeler.formats.SimpleFormat;
import datamodeler.formats.XMLFormat;
import datamodeler.listeners.ConnectionListener;
import datamodeler.models.Connection;
import datamodeler.threads.ThreadManager;

public class DataModeler implements Runnable {
    @Override
    public void run() {
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

		    final Connection connection = connectionEvent
			    .getConnection();

		    ThreadManager.run(new Runnable() {
			@Override
			public void run() {
			    connection.loadMetaData();
			}
		    });
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

	Element userConnectionsConfig;

	try {
	    userConnectionsConfig = Config
		    .getUserConfigElement("connections.xml");
	} catch (ParserConfigurationException | SAXException | IOException e) {
	    userConnectionsConfig = null;
	    e.printStackTrace(System.err);
	}

	if (userConnectionsConfig != null) {
	    NodeList connections = userConnectionsConfig
		    .getElementsByTagName("connection");

	    if (connections != null && connections.getLength() > 0) {
		Menu userConnections = new Menu("Connections");

		for (int i = 0; i < connections.getLength(); i++) {
		    if (connections.item(i) instanceof Element) {
			Element element = (Element) connections.item(i);

			final String host = element.getAttribute("host");
			final String username = element
				.getAttribute("username");
			final String password = element
				.getAttribute("password");
			final String port = element.getAttribute("port");

			MenuItem userConnectionMenuItem = new MenuItem(host);

			userConnectionMenuItem
				.addActionListener(new ActionListener() {
				    @Override
				    public void actionPerformed(
					    ActionEvent actionEvent) {
					connectionForm.setHost(host);
					connectionForm.setUsername(username);
					connectionForm.setPassword(password);
					connectionForm.setPort(port);
					
					formDialog.pack();
					formDialog.setVisible(true);
				    }
				});

			userConnections.add(userConnectionMenuItem);
		    }
		}

		connectionMenu.add(userConnections);
	    }
	}

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
}
