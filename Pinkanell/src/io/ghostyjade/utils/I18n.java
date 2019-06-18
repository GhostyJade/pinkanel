package io.ghostyjade.utils;

import java.io.BufferedReader;
import java.io.InputStream;
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(I18n.class.getResourceAsStream(localeName)));
	}

	public String getTranslationString(String key) {
		return translations.containsKey(key) ? translations.get(key) : key;
	}

}
