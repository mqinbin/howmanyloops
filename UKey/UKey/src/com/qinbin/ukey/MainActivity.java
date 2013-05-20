package com.qinbin.ukey;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.qinbin.ukey.ui.FanProgressView;

public class MainActivity extends Activity {

	private FanProgressView progress;
	private static final int MESSAGE_CHANGE_PIE  = 1;
	
	private Handler handler = new Handler(){
		@Override
        public void handleMessage(Message msg) {
			switch (msg.what) {
				case MESSAGE_CHANGE_PIE :
					changeProgress();
					break;
				default :
					break;
			}
        }
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		progress = (FanProgressView) findViewById(R.id.fpv_progress);
	}
	@Override
	protected void onResume() {
		super.onResume();

		changeProgress();
		timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, CHANGE_PROGRESS_IN_MILLIS);
	}

	protected void onPause() {
		timer.cancel();
		super.onPause();
	};

	Timer timer;
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			handler.sendEmptyMessage(MESSAGE_CHANGE_PIE);
		}
	};

	private static final int CHANGE_PROGRESS_IN_MILLIS = 100;
	private static final int CHANGE_CODE_IN_SECOND = 10;

	private void changeProgress() {
		long now = System.currentTimeMillis();
		progress.setProgress((float) (now % (1000 * CHANGE_CODE_IN_SECOND)) / (1000 * CHANGE_CODE_IN_SECOND));
	}

}
