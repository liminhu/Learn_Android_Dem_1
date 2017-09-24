package com.local.hook.demo;



import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;


/**
 * ��ʾ��ͼ���ţ���ת���ӽǿ���
 */
public class MainActivity extends Activity {

	private LocationManager locationManager;
	
	private TextView locationTxt, imeiTxt;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		
		locationTxt = (TextView)findViewById(R.id.location);
		imeiTxt = (TextView)findViewById(R.id.imei);

		//��ȡ����λ�ù�����  
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
		//��ȡLocation  
		Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);  
		if(location!=null){  
			//��Ϊ��,��ʾ����λ�þ�γ��  
			showLocation(location);  
		}else{
			Log.i("jw", "location:"+"is null  ---");
		}
		//���ӵ���λ�ñ仯  
		locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 3000, 1, locationListener);
		
		TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		imeiTxt.setText("imei:"+imei);
		Log.i("jw", "location:"+"test ---");
	}

	/** 
	 * ��ʾ����λ�þ��Ⱥ�γ����Ϣ 
	 * @param location 
	 */  
	private void showLocation(Location location){  
		Log.i("jw", "location:"+"test ---  1111");
		String locationStr = "γ�ȣ�" + location.getLatitude() + ",���ȣ�" + location.getLongitude();  
		locationTxt.setText(locationStr);
		Log.i("jw", "location:"+locationStr);
	}  

	/** 
	 * LocationListern������ 
	 * ����������λ���ṩ��������λ�ñ仯��ʱ������λ�ñ仯�ľ�������LocationListener������ 
	 */  

	LocationListener locationListener =  new LocationListener() {  

		@Override  
		public void onStatusChanged(String provider, int status, Bundle arg2) {  

		}  

		@Override  
		public void onProviderEnabled(String provider) {  

		}  

		@Override  
		public void onProviderDisabled(String provider) {  

		}  

		@Override  
		public void onLocationChanged(Location location) {  
			//���λ�÷����仯,������ʾ  
			showLocation(location);  

		}  
	};  

}
