package com.mylexz.sandi.lite;
import android.content.*;

import android.app.Service;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import java.util.logging.Level;
import android.graphics.Bitmap;

public class NotificationService extends Service
{
	private static final String TAG = "NotificationService";
	SharedPreferences pref;
	ArrayList<String> NameConf;
	int positionFrom;
	int positionTo;
	boolean[] asSystem;
	Bundle data;
	NotificationCompat.Builder mnotif;
	NotificationManager NotifMg;
	private final int notif_id = 86;
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		Log.i(TAG, "onCreate() service");
		pref = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		String ct = pref.getString(SplashPrepairToNext.LIST_CONVERTER, null);
		positionFrom = pref.getInt(SplashPrepairToNext.POSITION_FROM, 1);
		positionTo = pref.getInt(SplashPrepairToNext.POSITION_TO, 5);
		String asCt = pref.getString(SplashPrepairToNext.AS_SYSTEM, null);
		if(ct == null || asCt == null)stopSelf();
		String[] atmp1 = ct.split(",");
		String[] atmp2 = asCt.split(",");
		NameConf = new ArrayList<String>();
		asSystem = new boolean[atmp2.length];
		for(int x = 0; x < atmp1.length; x++){
			NameConf.add(atmp1[x]);
			asSystem[x] = Boolean.parseBoolean(atmp2[x]);
		}
		// parsing Bundle
		data = new Bundle();
		data.putStringArrayList(SplashPrepairToNext.LIST_CONVERTER, NameConf);
		data.putInt(SplashPrepairToNext.POSITION_FROM, positionFrom);
		data.putInt(SplashPrepairToNext.POSITION_TO, positionTo);
		data.putBooleanArray(SplashPrepairToNext.AS_SYSTEM, asSystem);
		// pending intent
		Intent touser = new Intent(this, MainUserActivity.class);
		touser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
		
		touser.putExtras(data);
		PendingIntent ptouser = PendingIntent.getActivity(this, 0, touser, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Intent cancelServ = new Intent(this, StopNotifServiceReceiver.class);
		PendingIntent cancel = PendingIntent.getBroadcast(this, (int)System.currentTimeMillis(), cancelServ, PendingIntent.FLAG_CANCEL_CURRENT);
		mnotif = new NotificationCompat.Builder(this);
		mnotif.setSmallIcon(R.drawable.ic_notif2);
		mnotif.setOngoing(true);
		mnotif.setTicker(getResources().getString(R.string.notif_str_convert_ticker));
		mnotif.setContentTitle(getResources().getString(R.string.notif_str_title));
		mnotif.setContentText(getResources().getString(R.string.notif_str_content));
		mnotif.addAction(R.drawable.ic_notif2, getResources().getString(R.string.notif_str_convert_action), ptouser);
		mnotif.addAction(R.drawable.ic_close_grey5, getResources().getString(R.string.notif_str_convert_close), cancel);
		NotifMg = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO: Implement this method
		;
		NotifMg.notify(notif_id, mnotif.build());
		return START_STICKY;
	}

	@Override
	public void onLowMemory()
	{
		// TODO: Implement this method
		Log.i(TAG, "LOW MEMMORY....");
		super.onLowMemory();
	}

	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		NotifMg.cancel(notif_id);
		super.onDestroy();
	}
	
}
