package io.ghostyjade.opencv;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

public class CameraFramer extends Thread {
	
	/**
	 * The {@link VideoCapture}. Grab frames from camera.
	 */
	private VideoCapture grabber;
	
	private Mat currentFrame;
	
	public void init() {
		currentFrame = new Mat();
		grabber = new VideoCapture(0);
		grabber.open(0);
	}
	
	public boolean isCameraAlive() {
		return grabber.isOpened();
	}
	
	@Override
	public void run() {
		while(grabber.grab()) {
			grabber.read(currentFrame);
		}
	}
	
	public Mat getCurrentFrame() {
		return currentFrame;
	}

	public void close() {
		grabber.close();
	}

}
