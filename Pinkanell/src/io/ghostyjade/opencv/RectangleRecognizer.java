package io.ghostyjade.opencv;

import static org.bytedeco.opencv.global.opencv_imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_CHAIN_APPROX_NONE;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_RETR_EXTERNAL;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_THRESH_BINARY;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_THRESH_OTSU;
import static org.bytedeco.opencv.global.opencv_imgproc.GaussianBlur;
import static org.bytedeco.opencv.global.opencv_imgproc.adaptiveThreshold;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.findContours;
import static org.bytedeco.opencv.global.opencv_imgproc.threshold;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;

import static org.bytedeco.opencv.global.opencv_imgproc.boundingRect;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

//http://bytedeco.org/javacpp-presets/opencv/apidocs/

/**
 * THIS CLASS IS NOT IMPLEMENTED YET.
 * 
 * @author GhostyJade
 */
public class RectangleRecognizer implements Runnable {

	private CameraFramer cameraInstance;

	private boolean running = false;
	private Mat result;
	private VideoCapture cam;

	public RectangleRecognizer(CameraFramer cameraInstance) {
		this.cameraInstance = cameraInstance;
		cam = new VideoCapture(0);
		result = new Mat();
		run();
	}

	public void recognizeRectangle() {
		Mat gray = new Mat();
		double treshold = 127, maxValue = 255;
		cvtColor(cameraInstance.getCurrentFrame(), gray, COLOR_BGR2GRAY);
		Mat blur = new Mat();
		GaussianBlur(gray, blur, new Size(5,5),0);
		gray.release();
		Mat partial = new Mat();
		adaptiveThreshold(blur, partial, maxValue, ADAPTIVE_THRESH_GAUSSIAN_C,CV_THRESH_BINARY, 11, 2);
		blur.release();
		threshold(partial, result, treshold, maxValue, CV_THRESH_BINARY | CV_THRESH_OTSU);
		partial.release();
		MatVector points = new MatVector();
		findContours(result, points, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_NONE);
		//System.out.println("VectorSize: " + points.size());
		for (int i = 0; i < points.size(); i++) {
			Rect r = boundingRect(points.get(i));
			//System.out.println("Mat " + i + "X: " + r.x() + " Y: " + r.y() + " W: " + r.width() + " H: " + r.height());
			rectangle(result,r,new Scalar(0));
		}
	}

	public void render() {

	}

	public void destroy() {
		running = false;
	}

	@Override
	public void run() {
		while (running) {
			cam.read(cameraInstance.getCurrentFrame());
			recognizeRectangle();
		}
	}

}
