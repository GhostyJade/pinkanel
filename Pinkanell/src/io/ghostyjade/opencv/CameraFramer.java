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
		grabber = new VideoCapture(1);
	}

	/**
	 * Open the camera acquisition.
	 */
	public void init() {
		grabber.open(1);
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
	 * @return the camera width
	 */
	public int getCameraWidth() {
		return (int) grabber.get(CAP_PROP_FRAME_WIDTH);
	}

	/**
	 * @return the camera height
	 */
	public int getCameraHeight() {
		return (int) grabber.get(CAP_PROP_FRAME_HEIGHT);
	}

}
