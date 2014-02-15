package org.incava.xumoqi;

public class Util {
	public static final boolean type = true;
	
	public static String repeat(String s, int num) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; ++i) {
			sb.append(s);
		}
		return sb.toString();
	}
}
