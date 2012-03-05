package datamodeler.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Config {
	private static File configDir;
	private static Map<String, File> configFiles;

	public static File getConfigFile(String name) throws IOException {
		File file = configFiles.get(name);

		if (file == null) {
			file = new File(configDir, name);

			if (!file.exists()) {
				file.createNewFile();
				configFiles.put(name, file);
			}
		}

		return file;
	}

	public static Iterable<String> getConfigurationLines(String name)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(
				getConfigFile(name)));

		ArrayList<String> lines = new ArrayList<String>();

		for (String line = reader.readLine(); line != null; line = reader
				.readLine()) {
			if (!line.isEmpty() && !line.matches("^\\s*$")
					&& !line.matches("^\\s*#.*$")) {
				lines.add(line);
			}
		}

		return lines;
	}

	static {
		configDir = new File("config");

		if (!configDir.exists()) {
			configDir.mkdir();
		} else if (!configDir.isDirectory()) {
			configDir.delete();
			configDir.mkdir();
		}

		configFiles = new HashMap<String, File>();
	}
}
