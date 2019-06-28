package io.ghostyjade.opencv;

import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.HOUGH_GRADIENT;
import static org.bytedeco.opencv.global.opencv_imgproc.HoughCircles;
import static org.bytedeco.opencv.global.opencv_imgproc.circle;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.medianBlur;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_imgproc.Vec3fVector;

import io.ghostyjade.pinkanell.PinkanellMain;
import io.ghostyjade.utils.Constants;
import io.hmatte.pinkadb.Pinkadb;

/**
 * This class provides methods to recognize a ball from a Webcam stream.
 * 
 * @author GhostyJade
 * @since v1.0
 * @see opencv_imgproc#HoughCircles(Mat, Vec3fVector, int, double, double,
 *      double, double, int, int) HoughCircles
 */
public class BallRecognizer implements Runnable {

	/**
	 * The camera framer instance
	 */
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

	/**
	 * Is this rendering?
	 */
	private boolean rendering = false;
     
	/**
	 * Class constructor.
	 * 
	 * @param cameraInstance the camera instance object
	 */
	public BallRecognizer(CameraFramer cameraInstance) {
		this.cameraInstance = cameraInstance;
		running = true;
	}

	/**
	 * Try to recognize all the circle present in the grabbed frame.
	 * @throws SQLException 
	 */
	public void recognizeBall() throws SQLException {
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
			Point p = new Point(Math.round(c[0]), Math.round(c[1]));
			if (!(p.x() == 0 && p.y() == 0)) {
				// TODO move to BallRecognizer
				PinkanellMain.getWindow().setPoint(p);
				PinkanellMain.getMath().addPoint(p);
				Pinkadb.insertPoint(p.x(), p.y());
						
			}
		}
	}

	/**
	 * Render and recognize all the circles in the current frame
	 */
	@Override
	public void run() {
		while (running) {
			try {
				recognizeBall();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (rendering) //penso sia perchè gli manchino i dati di accesso...
				//ma è un problema della fotocamera
				render();
		}
	}

	/**
	 * Render all the circles present in the current frame
	 */
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
		PinkanellMain.getCVManager().render();
	}

	/**
	 * Stop this thread
	 */
	public void destroy() {
		running = false;
	}

	/**
	 * Set if rendering is enabled.
	 * 
	 * @param value the value.
	 */
	public void setRendering(boolean value) {
		this.rendering = value;
	}
}
