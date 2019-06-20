package io.ghostyjade.opencv;

import static org.bytedeco.opencv.global.opencv_imgproc.CV_CHAIN_APPROX_NONE;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_RETR_EXTERNAL;
import static org.bytedeco.opencv.global.opencv_imgproc.findContours;

import org.bytedeco.opencv.opencv_core.MatVector;

/**
 * 
 * 
 * @author GhostyJade
 */
public class RectangleRecognizer implements Runnable {

	private CameraFramer cameraInstance;

	private boolean running = false;

	public RectangleRecognizer(CameraFramer cameraInstance) {
		this.cameraInstance = cameraInstance;
	}

	public void recognizeRectangle() {
		MatVector points = new MatVector();
		findContours(cameraInstance.getCurrentFrame(), points, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_NONE);
	}

	public void render() {

	}

	public void destroy() {
		running = false;
	}

	@Override
	public void run() {
		while (running) {
			recognizeRectangle();
		}
	}

}
