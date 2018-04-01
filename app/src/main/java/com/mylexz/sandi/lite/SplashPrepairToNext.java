package com.mylexz.sandi.lite;
import android.content.*;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import com.mylexz.utils.NodeData;
import android.os.Bundle;
import java.util.ArrayList;

public class SplashPrepairToNext extends AsyncTask<Void, Void, Void>
{
	public static final String LIST_CONVERTER = "Conv_LIST"
	, PATH_CONF = "PATH_CONFIGURATION"
	, HAS_USAGE = "HAS_FIRST_USAGE"
	, AS_SYSTEM = "AS_SYSTEM_Conv"
	, LIST_FILES = "LIST_FILES"
	, POSITION_FROM = "POS_FROM"
	, POSITION_TO = "CONV_TO"
	, ON_BOOT = "SETCONV_ONBOOT"
	, START_CONV_SERV = "START_CONV_SERV";
	
	NodeData npref;
	Context t;
	TextView state;
	String packageApp, activityName;
	boolean hasFirstUse;
	boolean[] asSystem;
	String pathConf;
	SharedPreferences pref;
	ArrayList<String> NameConv;
	String[] Fconf;
	int fr, to;
	public SplashPrepairToNext(Context t, TextView state, String activityName){
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
		state.setText(R.string.splash_action_starting_app);
		pref = t.getSharedPreferences(packageApp, Context.MODE_PRIVATE);
	}
	
	@Override
	protected Void doInBackground(Void[] p1)
	{
		// TODO: Implement this method
		NameConv = new ArrayList<String>();
		/*npref = new NodeData(t.getFilesDir()+"/preferences.setti
		/*if(!hasFirstUse)
			npref.setContentData("hasFirstUse", true);*/
		hasFirstUse = pref.getBoolean(HAS_USAGE, false);
		if(!hasFirstUse){
			pref.edit().remove(HAS_USAGE).apply();
			pref.edit().putBoolean(HAS_USAGE, true).apply();
		}
		String tmp = pref.getString(AS_SYSTEM, null);
		if(tmp != null){
			String[] vb = tmp.split(",");
			asSystem = new boolean[vb.length];
			int e = 0;
			for(String l: vb){
				asSystem[e++] = Boolean.parseBoolean(l);
			}
		}
		else asSystem = null;
		pathConf = "/data/data/"+packageApp+"/files";
		
		String temp = pref.getString(LIST_FILES, "");
		Log.i(activityName, "temp = "+temp);
		Fconf = temp.split(",");
		
		String temp2 = pref.getString(LIST_CONVERTER, "");
		Log.i(activityName, "temp2 : "+temp2);
		String[] buff2 = temp2.split(",");
		for(String c : buff2){
			Log.i(activityName, "adding "+c+" to NameConv");
			NameConv.add(c);
		}
		Log.i(activityName, "List Converter : "+NameConv.toString());
		//Log.i(activityName, "List files : "+Fconf.toString());
		//Log.i(activityName, "Length temp : "+((temp == null)?true:false));
		
		//npref.close();
		fr = pref.getInt(POSITION_FROM, 5);
		to = pref.getInt(POSITION_TO, 1);
		try
		{
			Thread.sleep(1100);
		}
		catch (InterruptedException e)
		{}
		return null;
	}

	@Override
	protected void onPostExecute(Void result)
	{
		// TODO: Implement this method
		super.onPostExecute(result);
		
		Intent intent = new Intent(t, MainActivity.class);
		Bundle data = new Bundle();
		data.putStringArrayList(LIST_CONVERTER, (ArrayList<String>)NameConv);
		data.putStringArray(LIST_FILES, (String[])Fconf);
		data.putString(PATH_CONF, pathConf);
		data.putBoolean(HAS_USAGE, hasFirstUse);
		data.putBooleanArray(AS_SYSTEM, asSystem);
		data.putInt(POSITION_FROM, fr);
		data.putInt(POSITION_TO, to);
		intent.putExtras(data);
		t.startActivity(intent);
	}
	
	
	
}
