package io.zaffino.math;

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
	 * attributo in costante aggiornamento calcolato ogni volta che si ottiene un nuovo tempo
	 * constantly updated attribute, calculate every time 
	 */
	private long time0;

	/**
	 * List that contains every speed calculated 
	 */
	private List<Double> speedValues = null;

	/**
	 * Class constructor.
	 */
	public MathUtil() {
		setName("MathUtilThread");
		running = true;
		maxSpeed = 0;
		averageSpeed = 0; //togliere?
		time0 = System.currentTimeMillis(); //togliere?
	}

	/**
	 * Calculate the average speed and the max speed
	 */
	@Override
	public void run() {
		while (running) {
			double space = calculateSpace();
			double time = calculateTime();

			if (space != -1) {
				speedValues.add(calculateSpeed(time, space));
				calculateAverage();
				calculateMaxValue();
			}

		}
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
	 * calculate the time spent since the last calculation
	 * 
	 * @return the difference between the current time and the previous one
	 */
	private double calculateTime() { //ricontrolla metodo
		long timeDifference = System.currentTimeMillis() - time0;
		return timeDifference;
	}

	/**
	 * calculate the space travelled by the ball
	 * 
	 * @return the total space travelled by the ball
	 */
	private double calculateSpace() {
		int pointX0 = 0;
		int pointX1 = 0;
		int pointY0 = 0;
		int pointY1 = 0;
		double space = 0;
		
		for (int i = 1; i < ballPositions.size(); i++) {

			pointX0 = ballPositions.get(i).x();
			pointX1 = ballPositions.get(i - 1).x();

			pointY0 = ballPositions.get(i).y();
			pointY1 = ballPositions.get(i - 1).y();

			int spaceDifferenceX = pointX0 - pointX1;
			int spaceDifferenceY = pointY0 - pointY1;

			space += Math.sqrt(Math.pow(spaceDifferenceX, 2) + Math.pow(spaceDifferenceY, 2));
			
		}

		
		return space;
	}

	/**
	 * calculates the speed
	 * @param time 
	 * @param space
	 * @return speed
	 */
	public static double calculateSpeed(double time, double space) {//rivedere se si può sostituire static
		return (space / time);
	}

	/**
	 * Get the max speed
	 */
	public void calculateMaxValue() {
		double max = 0;
		for (int i = 0; i < speedValues.size(); i++) {
			max = Math.max(max, speedValues.get(i));
		}
		maxSpeed = max;
	}

	/**
	 * Get the average speed
	 */
	public void calculateAverage() {
		double average = 0;
		for (int i = 0; i < speedValues.size(); i++) {
			average += speedValues.get(i);
		}
		average /= speedValues.size();
		averageSpeed = average;
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
