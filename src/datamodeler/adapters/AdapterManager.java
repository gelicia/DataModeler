package datamodeler.adapters;

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
	 * Get a list of available adapters
	 * 
	 * @return A list of all available adapters
	 */
	public static Iterable<String> getAdapterNames() {
		return adapters.keySet();
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
}
