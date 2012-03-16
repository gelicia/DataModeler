package datamodeler.adapters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import datamodeler.config.Config;

/**
 * Factory class that maintains a list of all available adapters
 * 
 * @author Kyle Sletten
 * 
 *         When the AdapterManager is loaded, it will look for a configuration
 *         file called <code>adapters.list</code> in the project root for a list
 *         of adapters to attempt to load
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
	    for (String line : Config.getGlobalConfigLines("adapters.list")) {
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
		} catch (Exception e) {
		    e.printStackTrace(System.err);
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace(System.err);
	}
    }
}
