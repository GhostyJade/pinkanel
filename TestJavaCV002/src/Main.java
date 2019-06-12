import static org.bytedeco.flandmark.global.flandmark.flandmark_detect;
import static org.bytedeco.flandmark.global.flandmark.flandmark_init;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_FILLED;
import static org.bytedeco.opencv.global.opencv_imgproc.circle;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.WindowConstants;

import org.bytedeco.flandmark.FLANDMARK_Model;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

/**
 * This program perform a face recognition based on a {@link VideoCapture
 * webcam} stream.
 * 
 * @author Andrea-C1201
 */
public class Main {

	private CascadeClassifier faceCascade = null;
	private FLANDMARK_Model model = null;

	/**
	 * The window frame canvas object
	 */
	private CanvasFrame canvas;
	/**
	 * The image converter (used to convert a {@link Mat} to an image that could be
	 * shown on the canvas)
	 */
	private ToIplImage converter;
	/**
	 * The webcam stream grabber
	 */
	private VideoCapture grabber;
	/**
	 * The current frame
	 */
	private Mat currentFrame = new Mat();

	/**
	 * Load the model file.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private FLANDMARK_Model loadFLandmarkModel(File file) throws IOException {
		if (!file.exists()) {
			throw new FileNotFoundException("FLandmark model file does not exist: " + file.getAbsolutePath());
		}

		final FLANDMARK_Model model = flandmark_init("flandmark_model.dat");
		if (model == null) {
			throw new IOException("Failed to load FLandmark model from file: " + file.getAbsolutePath());
		}

		return model;
	}

	/**
	 * Initialize the {@linkplain CanvasFrame canvas}.
	 */
	public void createWindow() {
		canvas = new CanvasFrame("Webcam test", 1);
		canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		converter = new ToIplImage();
	}

	/**
	 * Try to detect the face. An exception is thrown if no face are recognized.
	 * 
	 * @param orig
	 * @param input
	 * @param faceCascade
	 * @param model
	 * @param bbox
	 * @param landmarks
	 * @throws Exception
	 */
	private void detectFaceInImage(Mat orig, Mat input, CascadeClassifier faceCascade, FLANDMARK_Model model,
			int[] bbox, double[] landmarks) throws Exception {

		RectVector faces = new RectVector();
		faceCascade.detectMultiScale(input, faces);

		long nFaces = faces.size();
		// System.out.println("Faces detected: " + nFaces);
		if (nFaces == 0) {
			throw new Exception("No faces detected");
		}

		for (int iface = 0; iface < nFaces; ++iface) {
			Rect rect = faces.get(iface);

			bbox[0] = rect.x();
			bbox[1] = rect.y();
			bbox[2] = rect.x() + rect.width();
			bbox[3] = rect.y() + rect.height();

			flandmark_detect(new IplImage(input), bbox, model, landmarks);

			// display landmarks
			rectangle(orig, new Point(bbox[0], bbox[1]), new Point(bbox[2], bbox[3]), new Scalar(255, 0, 0, 128));
			rectangle(orig, new Point((int) model.bb().get(0), (int) model.bb().get(1)),
					new Point((int) model.bb().get(2), (int) model.bb().get(3)), new Scalar(0, 0, 255, 128));
			circle(orig, new Point((int) landmarks[0], (int) landmarks[1]), 3, new Scalar(0, 0, 255, 128), CV_FILLED, 8,
					0);
			for (int i = 2; i < 2 * model.data().options().M(); i += 2) {
				circle(orig, new Point((int) (landmarks[i]), (int) (landmarks[i + 1])), 3, new Scalar(255, 0, 0, 128),
						CV_FILLED, 8, 0);
			}
		}
	}

	/**
	 * Starts the {@link VideoCapture} stream.
	 */
	public void startImageGrabber() {
		grabber = new VideoCapture(0);
		grabber.open(0);
	}

	/**
	 * This method load the cascade file and the flandmark model. Then starts the
	 * Webcam image grabber and creates the program window.
	 */
	public void init() {
		File faceCascadeFile = new File("haarcascade_frontalface_alt.xml");
		File flandmarkModelFile = new File("flandmark_model.dat");
		try {
			faceCascade = new CascadeClassifier(faceCascadeFile.getCanonicalPath());
			model = loadFLandmarkModel(flandmarkModelFile);
			System.out.println("Model w_cols: " + model.W_COLS());
			System.out.println("Model w_rows: " + model.W_ROWS());
		} catch (Exception e) {
			e.printStackTrace();
		}
		startImageGrabber();
		createWindow();
	}

	/**
	 * This is the program.
	 */
	public void performThings() {

		while (grabber.grab()) {
			grabber.read(currentFrame);
			Mat imageBW = new Mat();
			cvtColor(currentFrame, imageBW, CV_BGR2GRAY);
			final int[] bbox = new int[4];
			final double[] landmarks = new double[2 * model.data().options().M()];

			try {
				detectFaceInImage(currentFrame, imageBW, faceCascade, model, bbox, landmarks);
			} catch (Exception e) {
				// No face detected
			}
			canvas.showImage(converter.convert(currentFrame));
		}
		grabber.release();
	}

	/**
	 * Initialize all the stuff.
	 */
	public Main() {
		init();
	}

	public static void main(String[] args) {
		new Main().performThings();
	}
}