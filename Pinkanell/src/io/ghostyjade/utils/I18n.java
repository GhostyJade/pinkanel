package io.ghostyjade.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * The internationalization class.
 * 
 * @author GhostyJade
 */
public class I18n {

	/**
	 * Map that holds the translations values.
	 */
	private Map<String, String> translations = new HashMap<>();

	/**
	 * Create a new i18n with the specified locale.
	 * 
	 * @param localeName the locale name (en_US or it_IT)
	 */
	public I18n(String localeName) {
		loadLocale(localeName);
	}

	/**
	 * Get the translation from the locale
	 * 
	 * @param key the key
	 * @return the translation string if exists, the key if the specified key does't
	 *         exists.
	 */
	public String getTranslationString(String key) {
		return translations.containsKey(key) ? translations.get(key) : key;
	}

	/**
	 * Load the specified locale
	 * 
	 * @param localeName the locale name (en_US or it_IT)
	 */
	public void loadLocale(String localeName) {
		translations.clear();
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
}
