package io.ghostyjade.pinkanell;

import java.awt.EventQueue;

import io.ghostyjade.opencv.BallRecognizer;
import io.ghostyjade.utils.I18n;
import io.taglioiscoding.ui.MainWindow;

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

	/**
	 * The i18n class instance
	 */
	private static I18n i18n;

	/**
	 * The constructor, initialize all the components.
	 */
	public PinkanellMain() {
		i18n = new I18n("en_US");
		window = new MainWindow();
		recognizer = new BallRecognizer();
		createThreads();
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

}
