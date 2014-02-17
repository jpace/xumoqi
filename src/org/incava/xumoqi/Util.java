package org.incava.xumoqi;

import android.util.Log;

public class Util {
	public static final boolean type = true;
	
	public static String repeat(String s, int num) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; ++i) {
			sb.append(s);
		}
		return sb.toString();
	}
	
	public static void log(String component, String msg, Object obj) {
		Log.i(component, msg + ": " + obj);
	}
	
	public static void log(String component, String msg, String str) {
		Log.i(component, msg + ": '" + str + "'");
	}
}
