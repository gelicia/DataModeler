package datamodeler.adapters;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class that maintains a list of all available adapters
 * @author Kyle Sletten
 */
public class AdapterManager {
	private static Map<String, Adapter> adapters;

	/**
	 * Get a list of available adapters
	 * @return A list of all available adapters
	 */
	public static Iterable<String> getAdapterNames() {
		return adapters.keySet();
	}

	/**
	 * Get an adapter by its name
	 * @param name the name of the adapter to get
	 * @return the adapter or null if the adapter was not found
	 */
	public static Adapter getAdapterByName(String name) {
		return adapters.get(name);
	}

	static {
		adapters = new HashMap<String, Adapter>();

		// TODO add more adapters
		adapters.put("mysql", new MySQLAdapter());
	}
}
