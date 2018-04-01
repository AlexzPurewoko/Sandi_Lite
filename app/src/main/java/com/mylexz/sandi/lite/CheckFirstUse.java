package com.mylexz.sandi.lite;
import android.os.AsyncTask;
import android.widget.TextView;
import android.content.Context;
import java.io.IOException;
import android.util.Log;

public class CheckFirstUse extends AsyncTask<Void, Void, Integer>
{
	Context t;
	TextView state;
	String packageApp, activityName;
	public CheckFirstUse(Context t, TextView state, String activityName){
		this.t = t;
		this.state = state;
		packageApp = t.getPackageName();
		this.activityName = activityName;
	}

	@Override
	protected void onPreExecute()
	{
		// TODO: Implement this method
		super.onPreExecute();
		state.setText(R.string.splash_action_loadimg_resources);
	}
	
	@Override
	protected Integer doInBackground(Void[] p1)
	{
		// TODO: Implement this method
		try
		{
			Process p = Runtime.getRuntime().exec("toolbox ls /data/data/" + packageApp + "/files/.has");
			p.waitFor();
			int exit = p.exitValue();
			p.destroy();
			return exit;
			
		}
		catch (IOException e)
		{
			Log.e(activityName, e.toString());
		}
		catch (Exception e){
			Log.e(activityName, e.toString());
		}
		return 1;
	}

	@Override
	protected void onPostExecute(Integer result)
	{
		// TODO: Implement this method
		super.onPostExecute(result);
		if(result!=0){
			Log.i(activityName, "Starting ExtractResources");
			new ExtractResources(t, state, activityName).execute();
		}
		else {
			Log.i(activityName, "Splash Prepair To Next");
			new SplashPrepairToNext(t, state, activityName).execute();
		}
	}

	
	
}
