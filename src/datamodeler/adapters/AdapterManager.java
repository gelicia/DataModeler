package datamodeler.adapters;

import java.util.HashMap;
import java.util.Map;

public class AdapterManager {
	private static Map<String, Adapter> adapters;

	public static Iterable<String> getAdapterNames() {
		return adapters.keySet();
	}

	public static Adapter getAdapterByName(String name) {
		return adapters.get(name);
	}

	static {
		adapters = new HashMap<String, Adapter>();

		// TODO add more adapters
		adapters.put("mysql", new MySQLAdapter());
	}
}
