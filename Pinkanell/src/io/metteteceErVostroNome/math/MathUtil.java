package io.metteteceErVostroNome.math;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bytedeco.opencv.opencv_core.Point;

/**
 * This class is a thread that calculate the average value and the max value of
 * a match.
 * 
 * @author GhostyJade, <insert_other_authors_here>
 *
 */
public class MathUtil extends Thread {

	/**
	 * Is this thread running?
	 */
	@SuppressWarnings("unused")
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
	 * Class constructor.
	 */
	public MathUtil() {
		setName("MathUtilThread");
		running = true;
	}

	/**
	 * Calculate the average speed and the max speed
	 */
	@Override
	public void run() {
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
	 * Get the max speed
	 */
	public void calculateMaxValue() {
	}

	/**
	 * Get the average speed
	 */
	public void calculateAverage() {
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
