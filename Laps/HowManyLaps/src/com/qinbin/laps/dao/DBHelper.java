package com.qinbin.laps.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, "laps.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql1 = "create table point (_id INTEGER PRIMARY KEY, sid integer, time varchar,  jing double , wei double ,radius float ,speed float , loctype int)";
		String sql2 = "create table shot(_id INTEGER PRIMARY KEY,ymd varchar , starttime varchar , endtime varchar, distance double, laps , provience varchar , city varchar);";
		db.execSQL(sql1);
		db.execSQL(sql2);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
