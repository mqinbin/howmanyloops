package com.qinbin.ukey.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

public class FanProgressView extends View {
	public FanProgressView(Context context) {
		super(context);
		BETWEEN_IN_PX = UIUtil.dp2px(context, BETWEEN_IN_DP);
		initPaint();
	}
	public FanProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		BETWEEN_IN_PX = UIUtil.dp2px(context, BETWEEN_IN_DP);
		initPaint();
	}

	private static final int BETWEEN_IN_DP = 12;
	private final int BETWEEN_IN_PX;

	 private static final int COLOR_IN = Color.argb(255, 143, 201, 233);
//	private static final int COLOR_IN = Color.argb(255, 100, 100, 100);
	private static final int COLOR_OUT = Color.argb(255, 166, 212, 235);
	private Paint paint;
	private void initPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);

	}
	RadialGradient rg;
	RectF rectf;
	@Override
	protected void onDraw(Canvas canvas) {
		if (rectf == null || rg == null) {
			rectf = new RectF(0 + BETWEEN_IN_PX, 0 + BETWEEN_IN_PX, getWidth() - BETWEEN_IN_PX, getHeight()
			        - BETWEEN_IN_PX);
			rg = new RadialGradient(getWidth() / 2, getHeight() / 2, getHeight() / 2, COLOR_IN, COLOR_OUT,
			        TileMode.MIRROR);
			paint.setShader(rg);
		}
//		progress = 0.1f;
		int angle = (int) (360 * progress);
		if (mode){
			canvas.drawArc(rectf, -90, angle, true, paint);
		}else{
			canvas.drawArc(rectf, 270, angle -360, true, paint);
		}
		
	}

	private boolean mode;
	

	private float lastprogress;
	private float progress;

	public void setProgress(float progress) {
		if (progress > 1 || progress < 0) {
			throw new IllegalArgumentException("progress should between 0 and 1;");
		}
		this.progress = progress;
		if (progress < lastprogress){
			mode = !mode;
		}
		lastprogress = progress;
		invalidate();
	}
}
