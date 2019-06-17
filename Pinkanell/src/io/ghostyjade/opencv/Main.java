package io.ghostyjade.opencv;

import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.HOUGH_GRADIENT;
import static org.bytedeco.opencv.global.opencv_imgproc.HoughCircles;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.medianBlur;
import static org.bytedeco.opencv.global.opencv_imgproc.circle;

import javax.swing.WindowConstants;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_imgproc.Vec3fVector;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

public class Main {

	private VideoCapture grabber;
	private Mat currentFrame;

	private CanvasFrame frame;
	private ToIplImage converter;

	public void loop() {
		while (grabber.grab()) {
			grabber.read(currentFrame);
			recognizeBall();
			render();
		}
	}

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

	public String buildString(float[] p) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < p.length; i++) {
			sb.append(p[i] + " ");
		}
		return sb.toString();
	}

	public void init() {
		currentFrame = new Mat();
		grabber = new VideoCapture(0);
		grabber.open(0);

		frame = new CanvasFrame("Pallettah-Recognition", 1);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		converter = new ToIplImage();
	}

	public void render() {
		frame.showImage(converter.convert(currentFrame));
	}

	public static void main(String[] args) {
		// Load the native library.
		try {
			Main alternative = new Main();
			alternative.init();
			alternative.loop();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
