package com.qinbin.laps.util;

import android.util.Log;

public class Logger {
	private static final int LEVEL = 1;

	public static void v(String TAG, String info) {
		if (LEVEL <= 1) {
			Log.v(TAG, info);
		}
	}
	public static void d(String TAG, String info) {
		if (LEVEL <= 2) {
			Log.d(TAG, info);
		}
	}
	public static void i(String TAG, String info) {
		if (LEVEL <= 3) {
			Log.i(TAG, info);
		}
	}
	public static void w(String TAG, String info) {
		if (LEVEL <= 4) {
			Log.w(TAG, info);
		}
	}
	public static void e(String TAG, String info) {
		if (LEVEL <= 5) {
			Log.e(TAG, info);
		}
	}
	@Deprecated
	public static void a(String TAG, String info) {
		if (LEVEL <= 6) {
			Log.i(TAG, info);
		}
	}
}
