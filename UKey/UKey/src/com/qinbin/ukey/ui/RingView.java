package com.qinbin.ukey.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RingView extends View {

	Context context;
	private Paint paintboard;
	private Paint paintin;
	private static final int BOARD_COLOR = Color.argb(155, 167, 190, 206);;
	private static final int IN_COLOR = Color.argb(255, 212 ,225, 233);
	private static final int RING_WIDTH_IN_DP = 6;
	private final int RING_WIDTH_IN_PX;
	public RingView(Context context) {
		super(context);
		this.context = context;
		RING_WIDTH_IN_PX = UIUtil.dp2px(context, RING_WIDTH_IN_DP);
		initPaint();
	}

	public RingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		RING_WIDTH_IN_PX = UIUtil.dp2px(context, RING_WIDTH_IN_DP);
		initPaint();
	}
	private void initPaint() {
		paintboard = new Paint();
		paintboard.setAntiAlias(true);
		paintboard.setColor(BOARD_COLOR);
		
		paintboard.setStyle(Paint.Style.STROKE);
		
		paintin = new Paint();
		paintin.setAntiAlias(true);
		paintin.setColor(IN_COLOR);
		paintin.setStrokeWidth(RING_WIDTH_IN_PX);
		paintin.setStyle(Paint.Style.STROKE);

	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - RING_WIDTH_IN_PX /2, paintin);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paintboard);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - RING_WIDTH_IN_PX, paintboard);
	}
	


}
