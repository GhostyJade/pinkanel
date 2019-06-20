package io.ghostyjade.opencv;

import java.awt.Container;

import javax.swing.WindowConstants;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.opencv.opencv_core.Mat;

import io.ghostyjade.pinkanell.PinkanellMain;

/**
 * This class manage the {@linkplain CameraFramer camera} acquisition. Also,
 * creates the {@linkplain BallRecognizer ball recognizer} instance and the
 * {@linkplain RectangleRecognizer rectangle recognizer} instance.
 * 
 * @author GhostyJade
 */
public class CvManager {

	/**
	 * The {@linkplain CameraFramer} instance.
	 */
	private CameraFramer cameraInstance;

	/**
	 * The {@linkplain BallRecognizer} instance.
	 */
	private BallRecognizer ballRecognizer;
	/**
	 * The {@linkplain RectangleRecognizer} instance.
	 */
	private RectangleRecognizer rectRecognizer;

	/**
	 * Convert {@link Mat mat} to {@link Frame frame}
	 */
	private ToIplImage converter;

	/**
	 * The window frame
	 */
	private CanvasFrame frame;

	/**
	 * Tell to threads if rendering is enabled.
	 */
	private boolean renderingEnabled = false;

	/**
	 * Class constructor.
	 */
	public CvManager() {
		cameraInstance = new CameraFramer();

		ballRecognizer = new BallRecognizer(cameraInstance);
		rectRecognizer = new RectangleRecognizer(cameraInstance);
	}

	/**
	 * Starts the camera thread.
	 */
	public synchronized void start() {
		PinkanellMain.serviceExecutor.execute(cameraInstance);
	}

	/**
	 * Starts the {@linkplain BallRecognizer ball} and
	 * {@linkplain RectangleRecognizer rectangle} recognizers.
	 */
	public synchronized void postInit() {
		PinkanellMain.serviceExecutor.execute(ballRecognizer);
		PinkanellMain.serviceExecutor.execute(rectRecognizer);
	}

	/**
	 * If the rendering is enabled, render the camera frame to the preview.
	 */
	public void render() {
		if (renderingEnabled) {
			ballRecognizer.render();
			frame.showImage(converter.convert(cameraInstance.getCurrentFrame()));
		}
	}

	/**
	 * Initialize the {@link ToIplImage image converter} and the {@link CameraFramer
	 * camera}.
	 */
	public void init() {
		converter = new ToIplImage();
		cameraInstance.init();
	}

	/**
	 * Destroys all the components.
	 */
	public void destroy() {
		rectRecognizer.destroy();
		ballRecognizer.destroy();
		cameraInstance.close();
	}

	/**
	 * Creates the panel used to rendering.
	 */
	public void createPanel() {
		frame = new CanvasFrame("Pallettah-Recognition", 1);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(false);
		renderingEnabled = true;
		PinkanellMain.serviceExecutor.execute(new Runnable() {

			@Override
			public void run() {
				while (cameraInstance.isCameraAlive()) {
					render();
				}
			}
		});
	}

	/**
	 * Destroys the panel used to rendering
	 */
	public void destroyPanel() {
		renderingEnabled = false;
		frame = null;
	}

	/**
	 * @return the frame internal container.
	 */
	public Container getCameraPane() {
		return frame.getContentPane();
	}

}
