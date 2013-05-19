package com.qinbin.ukey.ui;

import android.content.Context;

public class UIUtil {

	public static int dp2px(Context context, int dp) {
		float denstity = context.getResources().getDisplayMetrics().density;
		return (int) (denstity * dp + 0.5);
	}
}
