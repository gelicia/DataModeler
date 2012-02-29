package datamodeler.adapters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class that maintains a list of all available adapters
 * 
 * @author Kyle Sletten
 */
public class AdapterManager {
	private static Map<String, Adapter> adapters = new HashMap<String, Adapter>();

	/**
	 * Register an <code>Adapter</code> with the specified key
	 * 
	 * @param name
	 *            the name to register the adapter by
	 * @param adapter
	 *            the adapter to register
	 */
	public static void registerAdapter(String name, Adapter adapter) {
		adapters.put(name, adapter);
	}

	/**
	 * Get a list of available adapter names
	 * 
	 * @return A list of all available adapter names
	 */
	public static Iterable<String> getAdapterNames() {
		return adapters.keySet();
	}

	/**
	 * Get a list of available adapters
	 * 
	 * @return A list of all available adapters
	 */
	public static Iterable<Adapter> getAdapters() {
		return adapters.values();
	}

	/**
	 * Get an adapter by its name
	 * 
	 * @param name
	 *            the name of the adapter to get
	 * @return the adapter or null if the adapter was not found
	 */
	public static Adapter getAdapterByName(String name) {
		return adapters.get(name);
	}

	static {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					"adapters.list"));
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				String name;
				String className;

				if (line.matches("\\S+\\s+\\S+")) {
					String[] tokens = line.split("\\s+");

					name = tokens[0];
					className = tokens[1];
				} else {
					className = line;
					name = null;
				}

				try {
					Adapter adapter = (Adapter) Class.forName(className)
							.newInstance();

					if (name == null) {
						name = adapter.getClass().getSimpleName();
					}

					adapters.put(name, adapter);
				} catch (ClassNotFoundException e) {
					e.printStackTrace(System.err);
				} catch (InstantiationException e) {
					e.printStackTrace(System.err);
				} catch (IllegalAccessException e) {
					e.printStackTrace(System.err);
				}
			}

			bufferedReader.close();
		} catch (IOException e) {

		}
	}
}
