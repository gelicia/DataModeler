package datamodeler.threads;

public class ThreadManager {
	public static void run(Runnable runnable) {
		new Thread(runnable).start();
	}
}
