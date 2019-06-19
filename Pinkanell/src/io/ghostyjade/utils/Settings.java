package io.ghostyjade.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Settings {

	private static Properties properties = new Properties();

	public static void loadSettingsFromFile(String filename) {
		try {
			File file = new File(filename);
			properties.load(new InputStreamReader(new FileInputStream(file)));
		} catch (IOException e) {
			// TODO write default settings
			System.err.println("Couldn't load settings.");
		}
	}

	public static void updateSettings(String localeName, int fieldWidth, int fieldHeight) {
		properties.setProperty("fieldWidth", String.valueOf(fieldWidth));
		properties.setProperty("fieldHeight", String.valueOf(fieldHeight));
		properties.setProperty("locale", localeName);
	}

	public static void storeSettingsToFile(String filename) {
		File file = new File(filename);
		try {
			properties.store(new FileOutputStream(file), "Pinkanell's settings file");
		} catch (IOException e) {
			System.err.println("Couldn't save settings.");
		}
	}

	public static int getFieldWidth() {
		return Integer.parseInt(properties.getProperty("fieldWidth"));
	}

	public static void initConstants() {
		Constants.init(properties);
	}

}
