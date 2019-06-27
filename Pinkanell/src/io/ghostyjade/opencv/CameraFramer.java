package io.ghostyjade.opencv;

import static org.bytedeco.opencv.global.opencv_videoio.CAP_PROP_FRAME_HEIGHT;
import static org.bytedeco.opencv.global.opencv_videoio.CAP_PROP_FRAME_WIDTH;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

/**
 * The Camera grabber.
 * 
 * @author GhostyJade
 */
public class CameraFramer extends Thread {

	/**
	 * The camera id. If 0, uses an internal camera (if is attached), if 1, uses an
	 * external camera. Note that if the specified value isn't valid throws an
	 * exception.
	 */
	private final int CAMERA_ID = 0;

	/**
	 * The {@link VideoCapture}. Grab frames from camera.
	 */
	private VideoCapture grabber;

	/**
	 * A {@link Mat} that represent the current frame.
	 */
	private Mat currentFrame;

	/**
	 * Class constructor
	 */
	public CameraFramer() {
		currentFrame = new Mat();
		grabber = new VideoCapture(CAMERA_ID);
	}

	/**
	 * Open the camera acquisition.
	 */
	public void init() {
		grabber.open(CAMERA_ID);
	}

	/**
	 * @return <code>true</code> if the camera is alive, <code>false</code>
	 *         otherwise.
	 */
	public boolean isCameraAlive() {
		return grabber.isOpened();
	}

	/**
	 * Grab the image from the camera.
	 */
	@Override
	public void run() {
		while (grabber.grab()) {
			grabber.read(currentFrame);
		}
	}

	/**
	 * @return the current frame
	 */
	public Mat getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * Close the camera
	 */
	public void close() {
		grabber.close();
	}

	/**
	 * Get the camera width. If the camera is not initialized or something happens,
	 * it return 0.
	 * 
	 * @return the camera width
	 */
	public int getCameraWidth() {
		try {
			return (int) grabber.get(CAP_PROP_FRAME_WIDTH);
		} catch (Exception e) {
			return 0;
		}
	}

	// Sappiate che se il valore è 0, la heatmap diventa inutile perchè viene
	// generata come 0 (o ritorna un eccezione, non so come gestisca bufferedimage
	// con dimensioni 0,0)

	/**
	 * Get the camera height. If the camera is not initialized or something happens,
	 * it return 0.
	 * 
	 * @return the camera height
	 */ 
	public int getCameraHeight() {
		try {
			return (int) grabber.get(CAP_PROP_FRAME_HEIGHT);
		} catch (Exception e) {
			return 0;
		}
	}

}
