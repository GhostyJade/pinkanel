package io.ghostyjade.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author GhostyJade
 */
public class I18n {

	private Map<String, String> translations = new HashMap<>();

	public I18n(String localeName) {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(getClass().getResourceAsStream("/" + localeName + ".lang")));
			String s;
			while ((s = reader.readLine()) != null) {
				if (!s.startsWith("#") || s.startsWith("\n")) {
					String[] parts = s.split("=");
					translations.put(parts[0], parts[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getTranslationString(String key) {
		return translations.containsKey(key) ? translations.get(key) : key;
	}

}
