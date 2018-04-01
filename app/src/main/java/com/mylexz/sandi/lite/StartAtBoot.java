package com.mylexz.sandi.lite;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import android.util.Log;

public class StartAtBoot extends BroadcastReceiver
{

	@Override
	public void onReceive(Context p1, Intent p2)
	{
		// TODO: Implement this method
		Log.i("startboot", "onreceive handled");
		SharedPreferences a = p1.getSharedPreferences(getClass().getPackage().getName(), Context.MODE_PRIVATE);
		boolean start_serv = a.getBoolean(SplashPrepairToNext.START_CONV_SERV, false);
		boolean onboot = a.getBoolean(SplashPrepairToNext.ON_BOOT, false);
		Log.i("startboot", "onboot value "+onboot+", start_serv value "+start_serv);
		if(start_serv && onboot){
			// start a service
			// statement here...
			Toast.makeText(p1, "Starting service...", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(p1, NotificationService.class);
			p1.startService(intent);
			
			
			
		}
	}
	
}
