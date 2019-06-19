package io.ghostyjade.utils;

/**
 * This class hold some constants values.
 * 
 * @author GhostyJade
 */
public class Constants {

	/**
	 * The field width
	 */
	public static int FIELD_WIDHT;
	/**
	 * The field height
	 */
	public static int FIELD_HEIGHT;

	/**
	 * The locale name
	 */
	public static String LOCALE_NAME;
	
	/**
	 * the proportion value
	 */
	public static double CONST_FIELD;
	
	public static double FIELD_HEIGHT_DOTS;
	
	
	/**
	 * Initializes the fields with some values.
	 */
	static {
		FIELD_WIDHT = 0;
		FIELD_HEIGHT = 0;
		FIELD_HEIGHT_DOTS = 0;
		LOCALE_NAME = "it_IT";
	}

	
	/**
	 * 
	 * @return 
	 */
	public void calculateConstFiled() {
		CONST_FIELD = FIELD_HEIGHT / FIELD_HEIGHT_DOTS;
	}
}
