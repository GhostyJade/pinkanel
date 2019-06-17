package io.ghostyjade.utils;

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

}
