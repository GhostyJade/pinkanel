package io.ghostyjade.pinkanell;

import java.awt.EventQueue;

import io.ghostyjade.opencv.BallRecognizer;
import io.ghostyjade.utils.Constants;
import io.ghostyjade.utils.I18n;
import io.ghostyjade.utils.Settings;
import io.taglioiscoding.ui.MainWindow;
import io.zamp.serial.Serial;

/**
 * This is the main class of our program.
 * 
 * @author GhostyJade
 * @since v1.0
 */
public class PinkanellMain {

	/**
	 * The window instance
	 */
	private static MainWindow window;
	/**
	 * The ball recognizer instance
	 */
	private static BallRecognizer recognizer;

	private static Serial serial;

	/**
	 * The i18n class instance
	 */
	private static I18n i18n;

	/**
	 * The constructor, initialize all the components.
	 */
	public PinkanellMain() {
		Settings.loadSettingsFromFile("pinkanell.properties");
		Settings.initConstants();
		i18n = new I18n(Constants.LOCALE_NAME);
		window = new MainWindow();
		recognizer = new BallRecognizer();
		recognizer.init();
		serial = new Serial();
		createThreads();
		Constants.calculateConstFiled();
	}

	/**
	 * This function is used to create the window thread and the recognizer thread.
	 */
	private void createThreads() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				window.create();
			}
		});
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				recognizer.init();
				recognizer.start();
			}
		});
		serial.initialize();
	}

	/**
	 * @return the {@link BallRecognizer recognizer} instance.
	 */
	public static BallRecognizer getRecognizerInstance() {
		return recognizer;
	}

	/**
	 * @param args the program arguments (unused in our application)
	 */
	public static void main(String[] args) {
		new PinkanellMain();
	}

	/**
	 * @return the {@link I18n i18n} class instance.
	 */
	public static I18n getI18n() {
		return i18n;
	}

	/**
	 * @return the {@linkplain MainWindow window} instance.
	 */
	public static MainWindow getWindow() {
		return window;
	}

	/**
	 * Destroy the program. It closes serial communication and the camera.
	 */
	public static void destroy() {

	}

}
