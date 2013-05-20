package com.qinbin.laps.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.qinbin.laps.dao.RunPointDao;
import com.qinbin.laps.util.Logger;
import com.qinbin.laps.util.Toaster;

public class RunningService extends Service {

	public static final String TAG = "RunningService";
	private LocationClient mLocationClient;
	private MyLocationListenner myListener =  new MyLocationListenner();
	@Override
	public void onCreate() {
		super.onCreate();
		if(!isGPSEnable()){
			toggleGPS();
		}
		dao = new RunPointDao(this);
		Toaster.makeText(this, "RunningService onCreate", 0);
		mLocationClient = new LocationClient(this);
		setLocationOption();
		mLocationClient.registerLocationListener(myListener);
		mLocationClient.start();
	}

	@Override
	public void onDestroy() {
		mLocationClient.stop();
		if(isGPSEnable()){
			toggleGPS();
		}
		Toaster.makeText(this, "RunningService onDestroy", 0);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	boolean isNew =true;
	RunPointDao dao;
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;
			if (isNew){
				dao.addShot(location.getTime(), location.getTime(), location.getProvince(), location.getCity());
				isNew = false;
			}
			dao.addPoint(location.getLongitude(), location.getLatitude(), location.getRadius(), location.getSpeed(), location.getLocType());
			
		}
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}
	
	private void setLocationOption(){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");//返回的定位结果包含地址信息
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);//禁止启用缓存定位
		option.setPoiNumber(5);	//最多返回POI个数	
		option.setPoiDistance(1000); //poi查询距离		
		option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息		
		mLocationClient.setLocOption(option);
	}

	private void toggleGPS() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();
        }
        catch (CanceledException e) {
            e.printStackTrace();
        }
    }

	private boolean isGPSEnable() {
        /* 用Setting.System来读取也可以，只是这是更旧的用法
        String str = Settings.System.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        */
        String str = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        Logger.i("GPS", str);
        if (str != null) {
            return str.contains("gps");
        }
        else{
            return false;
        }
    }
}
