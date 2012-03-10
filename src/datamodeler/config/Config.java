package datamodeler.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Config {
    private static File globalConfigDir;
    private static Map<String, File> globalConfigFiles;
    private static File userConfigDir;
    private static Map<String, File> userConfigFiles;

    private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
	    .newInstance();

    private static File getConfigFile(Map<String, File> fileMap,
	    File configDir, String fileName) throws IOException {
	File file = fileMap.get(fileName);

	if (file == null) {
	    file = new File(configDir, fileName);

	    if (!file.exists()) {
		file.createNewFile();
		fileMap.put(fileName, file);
	    }
	}

	return file;
    }

    public static File getGlobalConfigFile(String name) throws IOException {
	return getConfigFile(globalConfigFiles, globalConfigDir, name);
    }

    public static File getUserConfigFile(String name) throws IOException {
	return getConfigFile(userConfigFiles, userConfigDir, name);
    }

    private static Iterable<String> getConfigLines(File file)
	    throws IOException {
	BufferedReader reader = new BufferedReader(new FileReader(file));

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

    public static Iterable<String> getGlobalConfigLines(String name)
	    throws IOException {
	return getConfigLines(getGlobalConfigFile(name));
    }

    public static Iterable<String> getUserConfigLines(String name)
	    throws IOException {
	return getConfigLines(getUserConfigFile(name));
    }

    public static Element getConfigElement(File file)
	    throws ParserConfigurationException, SAXException, IOException {
	DocumentBuilder documentBuilder = documentBuilderFactory
		.newDocumentBuilder();

	Document document = documentBuilder.parse(file);

	return document.getDocumentElement();
    }

    public static Element getGlobalConfigElement(String name)
	    throws ParserConfigurationException, SAXException, IOException {
	return getConfigElement(getGlobalConfigFile(name));
    }

    public static Element getUserConfigElement(String name)
	    throws ParserConfigurationException, SAXException, IOException {
	return getConfigElement(getUserConfigFile(name));
    }

    static {
	boolean windows = System.getProperty("os.name").startsWith("Windows");

	globalConfigDir = new File("config");

	if (globalConfigDir.exists() && !globalConfigDir.isDirectory()) {
	    globalConfigDir.delete();
	}
	if (!globalConfigDir.exists()) {
	    globalConfigDir.mkdir();
	}

	if (windows) {
	    userConfigDir = new File(System.getenv("USERPROFILE")
		    + "\\AppData\\Local\\DataModeler");
	} else {
	    userConfigDir = new File(System.getenv("HOME") + "/.DataModeler");
	}

	if (userConfigDir.exists() && !userConfigDir.isDirectory()) {
	    userConfigDir.delete();
	}
	if (!userConfigDir.exists()) {
	    userConfigDir.mkdir();
	}

	globalConfigFiles = new HashMap<String, File>();
	userConfigFiles = new HashMap<String, File>();
    }
}
