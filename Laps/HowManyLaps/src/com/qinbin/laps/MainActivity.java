package com.qinbin.laps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.qinbin.laps.service.RunningService;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		setClick();
	}
	private void setClick() {
		ib_start.setOnClickListener(this);
		ib_done.setOnClickListener(this);
	}
	private ImageButton ib_start;
	private ImageButton ib_done;
	private void findView() {
		ib_start = (ImageButton) findViewById(R.id.ib_start);
		ib_done = (ImageButton) findViewById(R.id.ib_done);
	}
	
	private long firstPressOnDone;
	private int clickOnDoneTimes;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ib_start :
				startService(new Intent(this, RunningService.class));
				break;
			case R.id.ib_done :
				stopService(new Intent(this, RunningService.class));
				
//				if(0 == clickOnDoneTimes){
//					firstPressOnDone = System.currentTimeMillis();
//					clickOnDoneTimes ++;
//					Toast.makeText(this, "for anti mistake, please click 3 times to done.", 0).show();
//				}else{
//					if(System.currentTimeMillis() - firstPressOnDone < 1000){
//						clickOnDoneTimes ++;
//					}else{
//						firstPressOnDone = System.currentTimeMillis();
//						clickOnDoneTimes = 1;
//					}
//					if (3 == clickOnDoneTimes){
//						//TODO done
//						
//						clickOnDoneTimes = 0;
//						firstPressOnDone = 0;
//					}
//				}
				break;
			default :
				break;
		}
	}
	
	public void goActivity(View v){
		startActivity(new Intent(this, StatisticsInfoActivity.class));
	}

}
