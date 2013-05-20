package com.qinbin.laps.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RunPointDao {
	DBHelper helper;
	SQLiteDatabase db;
	Integer currentShotId;
	public RunPointDao(Context context) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	public void addShot(String ymd, String time, String provience, String city) {
		ContentValues values = new ContentValues();
		values.put("ymd", ymd);
		values.put("time", time);
		values.put("provience", provience);
		values.put("city", city);
		db.insert("shot", null, values);
	}

	public void addPoint(double jing, double wei, float radius, float speed, int loctype) {
		if (currentShotId == null) {
			currentShotId = getMaxShotId();
		}
		ContentValues values = new ContentValues();
		values.put("sid", currentShotId);
		values.put("jing", jing);
		values.put("wei", wei);
		values.put("radius", radius);
		values.put("speed", speed);
		values.put("loctype", loctype);
		db.insert("point", null, values);
	}

	private Integer getMaxShotId() {
		Integer rtnValue;
		Cursor cursor = db.query("shot", new String[]{"_id"}, null, null, null, null, "_id desc ");
		if (cursor.moveToFirst()) {
			rtnValue = cursor.getInt(0);
		} else {
			rtnValue = 0;
		}
		cursor.close();
		return rtnValue;
	}
}
