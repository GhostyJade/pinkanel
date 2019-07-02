package io.ghostyjade.opencv;

import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_CHAIN_APPROX_NONE;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_RETR_EXTERNAL;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_THRESH_BINARY;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.findContours;
import static org.bytedeco.opencv.global.opencv_imgproc.threshold;
import static org.bytedeco.opencv.global.opencv_imgproc.boundingRect;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;

//http://bytedeco.org/javacpp-presets/opencv/apidocs/

/**
 * THIS CLASS IS NOT IMPLEMENTED YET.
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
		Mat gray = new Mat();
		cvtColor(cameraInstance.getCurrentFrame(), gray, COLOR_BGR2GRAY);
		Mat dest = new Mat();
		double treshold = 127, maxValue = 255;
		threshold(gray, dest, treshold, maxValue, CV_THRESH_BINARY);
		MatVector points = new MatVector();
		findContours(gray, points, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_NONE);
		System.out.println("VectorSize: " + points.size());
		for(int i = 0; i < points.size(); i++) {
			Rect r = boundingRect(points.get()[i]);
			System.out.println("Mat " + i + "X: " + r.x() + " Y: " + r.y());
		} //guyz io devo andare a mangiare, continuo dopo, plz caricatelo su git
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
