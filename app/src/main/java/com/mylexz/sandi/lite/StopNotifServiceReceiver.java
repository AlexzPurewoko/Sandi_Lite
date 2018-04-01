package com.mylexz.sandi.lite;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.app.NotificationManager;

public class StopNotifServiceReceiver extends BroadcastReceiver
{
	private final int notif_id = 86;
	@Override
	public void onReceive(Context p1, Intent p2)
	{
		// TODO: Implement this method
		Log.i(getClass().getName(), "stopService");
		Intent service = new Intent(p1, NotificationService.class);
		p1.stopService(service);
		NotificationManager i = (NotificationManager) p1.getSystemService(Context.NOTIFICATION_SERVICE);
		i.cancel(notif_id);
	}
	
}
