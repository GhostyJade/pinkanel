package io.ghostyjade.opencv;

import static org.bytedeco.opencv.global.opencv_videoio.CAP_PROP_FRAME_WIDTH;
import static org.bytedeco.opencv.global.opencv_videoio.CAP_PROP_FRAME_HEIGHT;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

public class CameraFramer extends Thread {

	/**
	 * The {@link VideoCapture}. Grab frames from camera.
	 */
	private VideoCapture grabber;

	private Mat currentFrame;

	public CameraFramer() {
		currentFrame = new Mat();
		grabber = new VideoCapture(0);
	}

	public void init() {
		grabber.open(0);
	}

	public boolean isCameraAlive() {
		return grabber.isOpened();
	}

	@Override
	public void run() {
		while (grabber.grab()) {
			grabber.read(currentFrame);
		}
	}

	public Mat getCurrentFrame() {
		return currentFrame;
	}

	public void close() {
		grabber.close();
	}

	public int getCameraWidth() {
		return (int) grabber.get(CAP_PROP_FRAME_WIDTH);
	}

	public int getCameraHeight() {
		return (int) grabber.get(CAP_PROP_FRAME_HEIGHT);
	}

}
