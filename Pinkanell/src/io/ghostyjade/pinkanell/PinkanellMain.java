package io.ghostyjade.pinkanell;

import java.awt.EventQueue;

import io.ghostyjade.opencv.BallRecognizer;
import io.taglioiscoding.ui.MainWindow;

public class PinkanellMain {

	private MainWindow window;
	private BallRecognizer recognizer;

	public PinkanellMain() {
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

	public static void main(String[] args) {
		new PinkanellMain();
	}

	// Add EventQueue.invokeLater() to create multithreaded ;
}
