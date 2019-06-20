package io.ghostyjade.utils;

import java.util.List;

import org.bytedeco.opencv.opencv_core.Point;

public class Debug {

	public static void log(String s) {
		System.out.println(s);
	}

	public static String buildString(float[] v) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < v.length; i++) {
			sb.append(v[i] + " ");
		}
		return sb.toString();
	}

	public static String printBallPointPen(List<Point> points) {
		String s = "";
		for (int i = 0; i < points.size(); i++) {
			s += "X: " + points.get(i).x() + ", Y: " + points.get(i).y() + "\n";
		}
		return s;
	}

}
