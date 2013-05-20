package com.qinbin.laps.util;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
	
	private static boolean isDev = true;
	
	public static void makeText(Context context, CharSequence text , int duration){
		Toast.makeText(context, text, duration).show();
	}
	public static void makeText(Context context, int resId , int duration){
		Toast.makeText(context, resId, duration).show();
	}
	
	public static void makeTT(Context context, CharSequence text  , int duration){
		if (isDev)
			Toast.makeText(context, text, duration).show();
	}
	
	
	public static void makeTT(Context context, int resId , int duration){
		if (isDev)
			Toast.makeText(context, resId, duration).show();
	}
}
