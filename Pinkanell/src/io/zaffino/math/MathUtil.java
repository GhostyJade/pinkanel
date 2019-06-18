package io.zaffino.math;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bytedeco.opencv.opencv_core.Point;

/**
 * This class is a thread that calculate the average value and the max value of
 * a match.
 * 
 * @author GhostyJade
 * @author Zaffino
 *
 */
public class MathUtil extends Thread {

	/**
	 * Is this thread running?
	 */
	private boolean running = false;

	/**
	 * List that stores the ball position coordinates
	 */
	private List<Point> ballPositions = new CopyOnWriteArrayList<>();

	/**
	 * the average speed
	 */
	private double averageSpeed;

	/**
	 * the max speed
	 */
	private double maxSpeed;

	/**
	 * constantly updated attribute, calculate every time
	 */
	private long timeOnMathStart;

	/**
	 * Hold the space value between every two points.
	 */
	private List<Double> spaceValues = new CopyOnWriteArrayList<>();

	private Point lastPoint;

	/**
	 * Class constructor.
	 */
	public MathUtil() {
		setName("MathUtilThread");
		running = true;
		// add time calculation and calculate average
	}

	public void addPoint(Point p) {
		if (lastPoint == null) {
			timeOnMathStart = System.currentTimeMillis();
			lastPoint = p;
			return;
		}
		spaceValues.add(calculateSpace(p, lastPoint));
		lastPoint = p;
	}

	private double calculateSpace(Point p0, Point p1) {
		return Math.sqrt(Math.pow(p0.x() - p1.x(), 2) + Math.pow(p0.y() - p1.y(), 2));
	}

	/**
	 * Calculate the average speed and the max speed
	 */
	@Override
	public void run() {
		while (running) {
			// double space = calculateSpace();
			// double time = calculateTime();

			// if (space != -1) {
			// speedValues.add(calculateSpeed(time, space));
			// calculateAverage();
			// calculateMaxValue();
			// }

		}
	}

	/**
	 * This method should be called when the team makes a goal.
	 * 
	 * @author GhostyJade
	 */
	public void resetPoint() {
		ballPositions.clear();
	}

	/**
	 * calculate the time spent since the last calculation
	 * 
	 * @return the difference between the current time and the previous one
	 */
	// FIXME
	private double calculateTime() { // ricontrolla metodo
		long timeDifference = System.currentTimeMillis();// - time0;
		return timeDifference;
	}

	/**
	 * calculate the space traveled by the ball
	 * 
	 * @return the total space traveled by the ball
	 */

	/**
	 * calculates the speed
	 * 
	 * @param time
	 * @param space
	 * @return speed
	 */
	private double calculateSpeed(double time, double space) {
		return (space / time);
	}

	/**
	 * Get the max speed from
	 */
	// TODO this is completed
	private void calculateMaxValue() {
		maxSpeed = Collections.max(spaceValues);
	}

	/**
	 * Calculate the average speed
	 */
	//TODO idk if this is done
	private void calculateAverage() {
		double spaceSum = 0;
		for (int i = 0; i < spaceValues.size(); i++) {
			spaceSum += spaceValues.get(i);
		}
		averageSpeed = (spaceSum / (System.currentTimeMillis() - timeOnMathStart));
	}

	/**
	 * @return the average speed
	 */
	public double getAverageSpeed() {
		return averageSpeed;
	}

	/**
	 * @return the max speed
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}

}
