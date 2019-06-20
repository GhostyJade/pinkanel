package io.ghostyjade.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * This class provides methods to store and load properties. Also, it gets some
 * program properties from the file.
 * 
 * @author GhostyJade
 */
public class Settings {

	/**
	 * The {@link Properties} class instance.
	 */
	private static Properties properties = new Properties();

	/**
	 * Load settings file from the specified file.
	 * 
	 * @param filename the file name
	 */
	public static void loadSettingsFromFile(String filename) {
		try {
			File file = new File(filename);
			properties.load(new InputStreamReader(new FileInputStream(file)));
		} catch (IOException e) {
			// TODO write default settings
			System.err.println("Couldn't load settings.");
		}
	}

	/**
	 * Update the properties using the specified values.
	 * 
	 * @param localeName  the new locale name
	 * @param fieldWidth  the field width
	 * @param fieldHeight the field height
	 */
	public static void updateSettings(String localeName, int fieldWidth, int fieldHeight) {
		properties.setProperty("fieldWidth", String.valueOf(fieldWidth));
		properties.setProperty("fieldHeight", String.valueOf(fieldHeight));
		properties.setProperty("locale", localeName);
	}

	/**
	 * Store settings file to the specified file
	 * 
	 * @param filename the file name
	 */
	public static void storeSettingsToFile(String filename) {
		File file = new File(filename);
		try {
			properties.store(new FileOutputStream(file), "Pinkanell's settings file");
		} catch (IOException e) {
			System.err.println("Couldn't save settings.");
		}
	}

	/**
	 * @return the field width
	 */
	public static int getFieldWidth() {
		return Integer.parseInt(properties.getProperty("fieldWidth"));
	}

	/**
	 * @return the field height
	 */
	public static int getFieldHeight() {
		return Integer.parseInt(properties.getProperty("fieldHeight"));
	}

	/**
	 * @return the locale name
	 */
	public static String getLocaleName() {
		return properties.getProperty("locale");
	}

	/**
	 * Initialize the constants properties with the loaded properties
	 */
	public static void initConstants() {
		if (properties == null)
			throw new RuntimeException("Properties hasn't been loaded.");
		Constants.init(properties);
	}

}
