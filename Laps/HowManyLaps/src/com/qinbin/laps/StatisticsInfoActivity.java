package com.qinbin.laps;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class StatisticsInfoActivity extends Activity implements OnClickListener, OnItemClickListener {

	Calendar today = Calendar.getInstance();
	Calendar choose = today;
	private GridView gv_statistics;
	private Button bt_pre_month;
	private Button bt_next_month;
	private TextView tv_year_month;
	private MyBaseAdapter adapter;
	private static int chooseDayCount;
	private static int chooseFirstDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.statistics_info_activity);
		
		choose.set(Calendar.DAY_OF_MONTH, 1);
		chooseDayCount = choose.getActualMaximum(Calendar.DAY_OF_MONTH);
		chooseFirstDay = choose.get(Calendar.DAY_OF_WEEK);

		findView();
		setClick();
		initData();
		setAdapter();
	}
	private void initData() {
		chooseDayCount = choose.getActualMaximum(Calendar.DAY_OF_MONTH);
		chooseFirstDay = choose.get(Calendar.DAY_OF_WEEK);
		tv_year_month.setText(choose.get(Calendar.YEAR)+ "-" + (choose.get(Calendar.MONTH) + 1));
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void findView() {
		gv_statistics = (GridView) findViewById(R.id.gv_statistics);
		bt_pre_month = (Button) findViewById(R.id.bt_pre_month);
		bt_next_month = (Button) findViewById(R.id.bt_next_month);
		tv_year_month = (TextView) findViewById(R.id.tv_year_month);
	}

	private void setClick() {
		bt_pre_month.setOnClickListener(this);
		bt_next_month.setOnClickListener(this);
		gv_statistics.setOnItemClickListener(this);
	}

	private void setAdapter() {
		adapter = new MyBaseAdapter();
		gv_statistics.setAdapter(adapter);

	}

	private class MyBaseAdapter extends BaseAdapter {
		private int rows;
		@Override
		public int getCount() {
			rows = (chooseFirstDay  - Calendar.SUNDAY+ chooseDayCount - 1) / 7 + 1;
			return rows * 7;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = new TextView(StatisticsInfoActivity.this);
			// System.err.println("parent.getWidth()"
			// +parent.getWidth()+"parent.getHeight()" +parent.getHeight());
			// tv.setHeight(parent.getWidth() / rows);
			String content;
			if ((position - chooseFirstDay + 1) < 0 || position >= (chooseFirstDay - 1 + chooseDayCount)) {
				content = "";
				tv.setBackgroundColor(0xFFAAAAAA);
			}

			else {
				content = position - chooseFirstDay + 2 + "";

				tv.setBackgroundColor(0xFF22AA22);
			}
			tv.setText(content);
			return tv;
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_pre_month :
				choose.add(Calendar.MONTH, -1);
				initData();
				adapter.notifyDataSetChanged();
				break;
			case R.id.bt_next_month :
				choose.add(Calendar.MONTH, 1);
				initData();
				adapter.notifyDataSetChanged();
				break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.gv_statistics) {

		}
	}
}
