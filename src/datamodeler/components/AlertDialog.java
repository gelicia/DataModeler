package datamodeler.components;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import datamodeler.listeners.WindowCloser;

@SuppressWarnings("serial")
public class AlertDialog extends Dialog {
	final private Label messageLabel = new Label();

	public AlertDialog(Frame frame, String title, String message) {
		super(frame, title, true);

		super.setLayout(new BorderLayout());

		Panel northPanel = new Panel(new BorderLayout());

		northPanel.add(this.messageLabel, BorderLayout.CENTER);

		Panel southPanel = new Panel(new BorderLayout());

		Button okButton = new Button("OK");

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				AlertDialog.this.setVisible(false);
			}
		});

		southPanel.add(okButton, BorderLayout.CENTER);

		super.add(northPanel, BorderLayout.NORTH);
		super.add(southPanel, BorderLayout.SOUTH);

		super.addWindowListener(new WindowCloser());

		this.setMessage(message);
	}

	public String getMessage() {
		return this.messageLabel.getText();
	}

	public void setMessage(String message) {
		this.messageLabel.setText(message);
	}
}
