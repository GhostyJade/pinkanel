package io.ghostyjade.opencv;

import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.HOUGH_GRADIENT;
import static org.bytedeco.opencv.global.opencv_imgproc.HoughCircles;
import static org.bytedeco.opencv.global.opencv_imgproc.circle;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.medianBlur;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_imgproc.Vec3fVector;

import io.ghostyjade.pinkanell.PinkanellMain;
import io.ghostyjade.utils.Constants;

/**
 * This class provides methods to recognize a ball from a Webcam stream.
 * 
 * @author GhostyJade
 * @since v1.0
 * @see opencv_imgproc#HoughCircles(Mat, Vec3fVector, int, double, double,
 *      double, double, int, int) HoughCircles
 */
public class BallRecognizer implements Runnable {

	private CameraFramer cameraInstance;

	/**
	 * Is this thread running?
	 */
	private boolean running = false;


	/**
	 * Vector that contains circles
	 */
	private Vec3fVector circles;

	/**
	 * A list of points.
	 */
	private List<Point> points = new CopyOnWriteArrayList<>();
	
	private boolean rendering = false;

	/**
	 * 
	 */
	public void recognizeBall() {
		points.clear();
		Mat gray = new Mat();
		cvtColor(cameraInstance.getCurrentFrame(), gray, COLOR_BGR2GRAY);
		medianBlur(gray, gray, 5);
		circles = new Vec3fVector();
		HoughCircles(gray, circles, HOUGH_GRADIENT, 1.0, (double) gray.rows() / 16, // change this value
				// to detect circles
				// with different
				// distances to each
				// other
				100.0, 30.0, Constants.MIN_CIRCLE_RADIUS, Constants.MAX_CIRCLE_RADIUS);

		for (int i = 0; i < circles.get().length; i++) {
			float c[] = new float[circles.get(i).sizeof()];
			circles.get(i).get(c);
			PinkanellMain.getMath().addPoint(new Point(Math.round(c[0]), Math.round(c[1])));
		}
	}

	@Override
	public void run() {
		while (running) {
			recognizeBall();
			if(rendering) render();
		}
	}

	private void render() {
		for (int i = 0; i < circles.get().length; i++) {
			float c[] = new float[circles.get(i).sizeof()];
			circles.get(i).get(c);
			Point center = new Point(Math.round(c[0]), Math.round(c[1]));
			circle(cameraInstance.getCurrentFrame(), center, 1, new Scalar(255, 0, 0, 0), 1, 8, 0);
			int radius = Math.round(c[2]);
			// ABGR color space
			circle(cameraInstance.getCurrentFrame(), center, radius, new Scalar(255, 0, 138, 255), 2, 8, 0);
		}
	}

	public BallRecognizer(CameraFramer cameraInstance) {
		this.cameraInstance = cameraInstance;
		running = true;
	}

	public void destroy() {
		running = false;
	}
	
	public void setRendering(boolean rendering) {
		this.rendering = rendering;
	}
}
