package datamodeler.components;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import datamodeler.events.ConnectionEvent;
import datamodeler.formats.Format;
import datamodeler.models.Database;

@SuppressWarnings("serial")
public class FormatMenuItem extends MenuItem {
	public FormatMenuItem(final Frame frame,
			final ConnectionForm connectionForm, final Format<Database> format,
			final String title, int keyBinding, boolean useShiftModifier) {
		super(title);

		super.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				frame.setEnabled(false);

				ConnectionEvent connectionEvent = connectionForm
						.getConnection();

				Iterable<Database> databases;

				if (connectionEvent.isSuccessful()
						&& connectionEvent.getConnection().loadMetaData()) {
					try {
						databases = connectionEvent.getConnection()
								.getDatabases();
					} catch (SQLException e) {
						databases = null;
					}

					if (databases != null) {
						FileDialog fileDialog = new FileDialog(frame, title,
								FileDialog.SAVE);

						fileDialog.setVisible(true);

						String directory = fileDialog.getDirectory();
						String fileName = fileDialog.getFile();

						if (fileName != null && !fileName.isEmpty()) {
							try {
								BufferedWriter bufferedWriter = new BufferedWriter(
										new FileWriter(new File(directory,
												fileName)));

								format.write(databases, bufferedWriter);

								bufferedWriter.close();
							} catch (IOException e) {
								e.printStackTrace(System.err);
							}
						}
					}
				}

				frame.setEnabled(true);
			}
		});

		super.setShortcut(new MenuShortcut(keyBinding, useShiftModifier));
	}
}
