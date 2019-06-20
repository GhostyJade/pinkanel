package io.ghostyjade.pinkanell;

import java.awt.EventQueue;

import io.ghostyjade.opencv.BallRecognizer;
import io.ghostyjade.utils.Constants;
import io.ghostyjade.utils.Debug;
import io.ghostyjade.utils.I18n;
import io.ghostyjade.utils.Settings;
import io.ghostyjade.utils.listener.GoalListener;
import io.taglioiscoding.ui.MainWindow;
import io.zaffino.math.MathUtil;
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

	private static MathUtil math;

	/**
	 * The constructor, initialize all the components.
	 */
	public PinkanellMain() {
		Settings.loadSettingsFromFile("pinkanell.properties");
		Settings.initConstants();
		i18n = new I18n(Constants.LOCALE_NAME);
		window = new MainWindow();
		math = new MathUtil();
		recognizer = new BallRecognizer();
		recognizer.init();
		
		serial = new Serial(new GoalListener() {

			@Override
			public void actionPerform() {
				System.out.println(Debug.printBallPointPen(math.getBallPositions()));
				math.performCalculation();
				math.resetPoint();
				window.updateScore();
			}
		});

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
		System.loadLibrary("rxtxSerial");
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

	public static Serial getSerial() {
		return serial;
	}

	public static MathUtil getMath() {
		return math;
	}

	/**
	 * Destroy the program. It closes serial communication and the camera.
	 */
	public static void destroy() {

	}

}
