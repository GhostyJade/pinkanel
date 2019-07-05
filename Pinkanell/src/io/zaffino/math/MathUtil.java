package io.zaffino.math;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bytedeco.opencv.opencv_core.Point;

import io.ghostyjade.utils.Constants;

/**
 * This class is a thread that calculate the average value and the max value of
 * a match.
 * 
 * @author GhostyJade
 * @author taglioIsCoding
 * @author Zaffino
 * 
 * while true thread sleep
 */
public class MathUtil {

	/**
	 * List that stores the ball position coordinates
	 */
	private List<Point> ballPositions = new CopyOnWriteArrayList<>();

	/**
	 * The average speed
	 */
	private double averageSpeed;

	/**
	 * The max speed
	 */
	private double maxSpeed;

	/**
	 * Get current time on first point added.
	 */
	private long timeOnMathStart;

	/**
	 * Hold the space value between every two points.
	 */
	private List<Double> speedValues = new CopyOnWriteArrayList<>();

	/**
	 * The last ball point
	 */
	private Point lastPoint;

	/**
	 * Add a new point to the point list and calculate the speed at that moment
	 * 
	 * @param p the new point
	 */
	public void addPoint(Point p) {
		if (lastPoint == null) {
			timeOnMathStart = System.currentTimeMillis();
			lastPoint = p;
			return;
		}
		Double d = calculateSpeed(calculateSpace(p, lastPoint) * Constants.CONST_FIELD);
		if (!d.isInfinite())
			speedValues.add(d); // FIXME ground size!
		lastPoint = p;
		timeOnMathStart = System.currentTimeMillis();
	}

	/**
	 * Calculate the space between two points
	 * 
	 * @param p0 the first point
	 * @param p1 the second point
	 * @return the distance between p0 and p1
	 */
	private double calculateSpace(Point p0, Point p1) {
		return Math.sqrt(Math.pow(p0.x() - p1.x(), 2) + Math.pow(p0.y() - p1.y(), 2));
	}

	/**
	 * This method should be called when the team makes a goal.
	 * 
	 * @author GhostyJade
	 */
	public void resetPoint() {
		ballPositions.clear();
		speedValues.clear();
	}

	/**
	 * Get the max speed from
	 */
	private void calculateMaxSpeed() {
		maxSpeed = Collections.max(speedValues);
	}

	/**
	 * Calculate the speed.
	 * 
	 * @param space the space between two points
	 */
	private double calculateSpeed(double space) {
		return (space / (System.currentTimeMillis() - timeOnMathStart));
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

	/**
	 * Calculate the average speed.
	 */
	private void calculateAverageSpeed() {
		double sum = 0;
		for (int i = 0; i < speedValues.size(); i++) {
			sum += speedValues.get(i);
		}
		averageSpeed = (sum / speedValues.size());
	}

	/**
	 * Perform average calculation and max speed calculation.
	 */
	public void performCalculation() {
		calculateMaxSpeed();
		calculateAverageSpeed();
	}

}
