package datamodeler;

import datamodeler.threads.ThreadManager;

/**
 * @author Kyle Sletten
 */
public class DataModeler {
	public static void main(String[] args) {
		Runnable app;

		if (args != null && args.length > 0) {
			if (args[0].equals("awt")) {
				app = new datamodeler.awt.DataModeler();
			} else {
				app = new datamodeler.awt.DataModeler();
			}
		} else {
			app = new datamodeler.awt.DataModeler();
		}

		ThreadManager.run(app);
	}
}
