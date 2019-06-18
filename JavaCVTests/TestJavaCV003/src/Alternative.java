import javax.swing.WindowConstants;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Alternative {

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
		Imgproc.cvtColor(currentFrame, gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.medianBlur(gray, gray, 5);
		Mat circles = new Mat();
		Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1.0, (double) gray.rows() / 16, // change this value
				// to detect circles
				// with different
				// distances to each
				// other
				100.0, 30.0, 1, 30); // change the last two parameters
		// (min_radius & max_radius) to detect larger circles
		for (int x = 0; x < circles.cols(); x++) {
			double[] c = circles.get(0, x);
			Point center = new Point(Math.round(c[0]), Math.round(c[1]));
			// circle center
			Imgproc.circle(currentFrame, center, 1, new Scalar(0, 100, 100), 3, 8, 0);
			// circle outline
			int radius = (int) Math.round(c[2]);
			Imgproc.circle(currentFrame, center, radius, new Scalar(255, 0, 255), 3, 8, 0);
		}
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

	public static void mainnnnnn(String[] args) {
		// Load the native library.
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			Alternative alternative = new Alternative();
			alternative.init();
			alternative.loop();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
