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

	private long time0;

	private List<Double> speedValues = null;

	/**
	 * Class constructor.
	 */
	public MathUtil() {
		setName("MathUtilThread");
		running = true;
		maxSpeed = 0;
		averageSpeed = 0; // togliere?
		time0 = System.currentTimeMillis();
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
	}

	/**
	 * il metodo calcola il tempo passato dall'ultimo calcolo
	 * 
	 * @return la differenza tra il tempo attuale e quello ottenuto in precedenza
	 */
	private double calculateTime() { // ricontrolla metodo
		long timeDifference = System.currentTimeMillis() - time0;
		time0 = System.currentTimeMillis();
		return timeDifference;
	}

	/**
	 * il metodo calcola lo spazio percorso dalla palla
	 * 
	 * @return la differenza tra lo spazio attuale e quello ottenuto in precedenza
	 *         se è possibile calcolarlo.
	 * @return -1 se non può calcolare lo spazio.
	 */
	private double calculateSpace() {
		int pointX0 = 0;
		int pointX1 = 0;
		int pointY0 = 0;
		int pointY1 = 0;
		boolean calcoloPossibile = false;

		for (int i = 1; i < ballPositions.size(); i++) {

			pointX0 = ballPositions.get(i).x();
			pointX1 = ballPositions.get(i - 1).x();

			pointY0 = ballPositions.get(i).y();
			pointY1 = ballPositions.get(i - 1).y();

			calcoloPossibile = true;
		}

		if (!calcoloPossibile)
			return -1;

		int spaceDifferenceX = pointX0 - pointX1;
		int spaceDifferenceY = pointY0 - pointY1;

		double space = Math.sqrt(Math.pow(spaceDifferenceX, 2) + Math.pow(spaceDifferenceY, 2));
		return space;
	}

	private static double calculateSpeed(double time, double space) {
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
