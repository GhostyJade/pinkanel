package io.ghostyjade.opencv;

import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.HOUGH_GRADIENT;
import static org.bytedeco.opencv.global.opencv_imgproc.HoughCircles;
import static org.bytedeco.opencv.global.opencv_imgproc.circle;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.medianBlur;

import javax.swing.WindowConstants;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_imgproc.Vec3fVector;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

/**
 * This class provides methods to recognize a ball from a Webcam stream.
 * 
 * @author GhostyJade
 * @since v1.0
 * @see opencv_imgproc#HoughCircles(Mat, Vec3fVector, int, double, double,
 *      double, double, int, int) HoughCircles
 */
public class BallRecognizer extends Thread {

	private VideoCapture grabber;
	private Mat currentFrame;

	private CanvasFrame frame;
	private ToIplImage converter;

	public void recognizeBall() {
		Mat gray = new Mat();
		cvtColor(currentFrame, gray, COLOR_BGR2GRAY);
		medianBlur(gray, gray, 5);
		Vec3fVector circles = new Vec3fVector();
		HoughCircles(gray, circles, HOUGH_GRADIENT, 1.0, (double) gray.rows() / 16, // change this value
				// to detect circles
				// with different
				// distances to each
				// other
				100.0, 30.0, 1, 30); // change the last two parameters
		// (min_radius & max_radius) to detect larger circles

		for (int i = 0; i < circles.get().length; i++) {
			float c[] = new float[circles.get(i).sizeof()];
			circles.get(i).get(c);
			Point center = new Point(Math.round(c[0]), Math.round(c[1]));
			circle(currentFrame, center, 1, new Scalar(255, 0, 0, 0), 1, 8, 0);
			int radius = Math.round(c[2]);
			// ABGR color space
			circle(currentFrame, center, radius, new Scalar(255, 0, 138, 255), 2, 8, 0);
		}
	}

	/**
	 * Initializes some class' components, such as the {@link VideoCapture} instance
	 * and the {@link CanvasFrame frame}.
	 * This method must be <b>ALWAYS</b> called.
	 */
	public void init() {
		currentFrame = new Mat();
		grabber = new VideoCapture(0);
		grabber.open(0);

		frame = new CanvasFrame("Pallettah-Recognition", 1);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		converter = new ToIplImage();
	}
	
	@Override
	public void run() {
		while (grabber.grab()) {
			grabber.read(currentFrame);
			recognizeBall();
			render();
		}
	}

	public void render() {
		frame.showImage(converter.convert(currentFrame));
	}
}
