package com.mylexz.sandi.lite;

import android.app.Service;
import android.content.Context;
import android.app.ActivityManager;
import java.util.List;
import android.content.pm.ServiceInfo;
import android.util.Log;

public class CheckService
{
	/*
	 public class ServiceTools { private static String LOG_TAG = ServiceTools.class.getName();
	 public static boolean isServiceRunning(Context context,Class<?> serviceClass){ 
	 final ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	 final List<RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE); 
	 for (RunningServiceInfo runningServiceInfo : services) {
		 Log.d(Constants.TAG, String.format("Service:%s", runningServiceInfo.service.getClassName())); 
		 if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())){ 
		 return true; } } return false; } }
	*/
	public static boolean isServiceRunning(Context c, Class<? extends Service> serviceCls){
		final ActivityManager actMgr = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
		final List<ActivityManager.RunningServiceInfo> serv = actMgr.getRunningServices(Integer.MAX_VALUE);
		for(ActivityManager.RunningServiceInfo serviceInfo : serv){
			Log.d("CheckService", "Service Running "+ serviceInfo.service.getClassName());
			if(serviceInfo.service.getClassName().equals(serviceCls.getName())){
				Log.d("CheckService", "Equals!");
				return true;
			}
		}
		return false;
	}
}
