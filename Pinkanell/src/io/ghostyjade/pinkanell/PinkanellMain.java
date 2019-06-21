package io.ghostyjade.pinkanell;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.ghostyjade.opencv.CvManager;
import io.ghostyjade.utils.Constants;
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
	 * The serial instance
	 */
	private static Serial serial;

	/**
	 * The i18n class instance
	 */
	private static I18n i18n;

	/**
	 * The {@linkplain MathUtil math} instance
	 */
	private static MathUtil math;

	/**
	 * The {@linkplain CvManager OpenCV} class manager
	 */
	private static CvManager manager;

	/**
	 * Pool of threads, used to create new program's threads.
	 */
	public static ExecutorService serviceExecutor = Executors.newCachedThreadPool();

	/**
	 * The constructor, initialize all the components.
	 */
	public PinkanellMain() {
		Settings.loadSettingsFromFile("pinkanell.properties");
		Settings.initConstants();
		i18n = new I18n(Constants.LOCALE_NAME);
		manager = new CvManager();
		window = new MainWindow();
		math = new MathUtil();
		manager.init();

		serial = new Serial(new GoalListener() {

			@Override
			public void actionPerform() {
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
		manager.start();
		window.create();
		serial.initialize();
		manager.postInit();
	}

	/**
	 * @param args the program arguments (unused in our application)
	 */
	public static void main(String[] args) {
		System.loadLibrary("rxtxSerial");
		new PinkanellMain();
	}

	/**
	 * @return the {@linkplain I18n i18n} class instance.
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
	 * @return the {@linkplain Serial serial} class instance.
	 */
	public static Serial getSerial() {
		return serial;
	}

	/**
	 * @return the {@linkplain MathUtil math} class instance
	 */
	public static MathUtil getMath() {
		return math;
	}

	/**
	 * Destroy the program. It closes serial communication and the camera.
	 */
	public static void destroy() {
		serial.close();
		manager.destroy();
	}

	/**
	 * @return the {@linkplain CvManager OpenCV manager} instance.
	 */
	public static CvManager getCVManager() {
		return manager;
	}

}
