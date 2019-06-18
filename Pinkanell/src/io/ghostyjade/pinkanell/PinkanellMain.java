package io.ghostyjade.pinkanell;

import java.awt.EventQueue;

import io.ghostyjade.opencv.BallRecognizer;
import io.ghostyjade.utils.I18n;
import io.taglioiscoding.ui.MainWindow;

public class PinkanellMain {

	private static MainWindow window;
	private static BallRecognizer recognizer;
	
	private static I18n i18n;

	public PinkanellMain() {
		i18n = new I18n("en_US");
		window = new MainWindow();
		recognizer = new BallRecognizer();
		createThreads();
	}

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

	public static BallRecognizer getRecognizerInstance() {
		return recognizer;
	}

	public static void main(String[] args) {
		new PinkanellMain();
	}
	
	public static I18n getI18n() {
		return i18n;
	}

}
