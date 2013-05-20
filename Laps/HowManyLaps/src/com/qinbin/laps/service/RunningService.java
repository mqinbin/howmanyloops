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
		option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.disableCache(true);//��ֹ���û��涨λ
		option.setPoiNumber(5);	//��෵��POI����	
		option.setPoiDistance(1000); //poi��ѯ����		
		option.setPoiExtraInfo(true); //�Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ		
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
        /* ��Setting.System����ȡҲ���ԣ�ֻ�����Ǹ��ɵ��÷�
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
