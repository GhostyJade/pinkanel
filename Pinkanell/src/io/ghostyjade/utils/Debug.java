package io.ghostyjade.utils;

import java.io.PrintStream;
import java.util.List;

import org.bytedeco.opencv.opencv_core.Point;

/**
 * This class provides some functions used when we had debugged this program.
 * 
 * @author GhostyJade
 */
public class Debug {

	/**
	 * Log the specified string to the default {@link PrintStream}
	 * 
	 * @param s the string to log
	 */
	public static void log(String s) {
		System.out.println(s);
	}

	/**
	 * @param v an array of float
	 * @return the string representation using the values from given array
	 */
	public static String buildString(float[] v) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < v.length; i++) {
			sb.append(v[i] + " ");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param points a {@link List} of {@link Point}
	 * @return
	 *         <p>
	 *         a string representation of the given list as
	 *         <code> [X: (x), Y: (y)] </code>
	 *         </p>
	 */
	public static String printBallPointPen(List<Point> points) {
		String s = "";
		for (int i = 0; i < points.size(); i++) {
			s += "X: " + points.get(i).x() + ", Y: " + points.get(i).y() + "\n";
		}
		return s;
	}

}
