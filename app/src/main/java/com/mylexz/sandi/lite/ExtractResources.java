package com.mylexz.sandi.lite;
import java.io.*;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import com.mylexz.utils.NodeData;
import android.content.SharedPreferences;

public class ExtractResources extends AsyncTask<Void, Void, Integer>
{
	//NodeData npref;
	Context t;
	TextView state;
	String packageApp, activityName;
	SharedPreferences pref;
	public ExtractResources(Context t, TextView state, String activityName)
	{
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
		pref = t.getSharedPreferences(packageApp, Context.MODE_PRIVATE);
	}
	
	@Override
	protected Integer doInBackground(Void[] p1)
	{
		// TODO: Implement this method
		File fp = t.getFilesDir();
		fp.mkdir();
		/*npref = new NodeData(fp + "/preferences.setting", "Settings");
		npref.open();
		npref.addNode(null, "Conv");
		npref.addBoolData(null, "hasFirstUse", false);*/
		// Extract Asset ----------
		extractAsset();
		// -------------
		// ------------- PASSING DATA
		pref.edit().putBoolean(SplashPrepairToNext.HAS_USAGE, false).apply();
		passingIntoData();
		//npref.close();
		try
		{
			Thread.sleep(1200);
		}
		catch (InterruptedException e)
		{}
		return 0;
	}

	@Override
	protected void onPostExecute(Integer result)
	{
		// TODO: Implement this method
		super.onPostExecute(result);
		Log.i(activityName, "Splash Prepair To Next2");
		new SplashPrepairToNext(t, state, activityName).execute();
	}

	private void passingIntoData()
	{
		// TODO: Implement this method
		AssetManager asset = t.getAssets();
		String files[] = null;
		try
		{
			files = asset.list("");
		}
		catch (IOException e)
		{
			Log.e(activityName, "Can't open", e);
		}
		//npref.startAddArray("Conv", "ListConv", NodeData.TYPE_STRING);
		int len = 0, x = 0;
		for(String name: files)	if(name.contains(".mconf"))len++;
		String[] listCv = new String[len];
		for(String name : files)if(name.contains(".mconf"))listCv[x++] = name;
		x = 0;
		
		StringBuilder i = new StringBuilder();
		for(x = 0; x < listCv.length; x++){
			i.append(listCv[x]);
			i.append(",");
		}
		Log.i(activityName, "i "+i.toString());
		//npref.addStrData("Conv", "LCONV", i.toString());
		//Log.e(activityName, npref.getErrorDesc());
		
		pref.edit().putString(SplashPrepairToNext.LIST_FILES, i.toString()).apply();
		
		//String[] h = npref.getStringArray("Conv/LCONV");
		//Log.e(activityName, npref.getErrorDesc());
		//Log.i(activityName, "value h? "+((h==null)?true:false));
		StringBuilder s = new StringBuilder();
		for(x = 0; x < listCv.length; x++){
			String buff = listCv[x];
			s.append(buff.substring(0, buff.length()-6)).append(",");
		}
		Log.i(activityName, "s "+s.toString());
		//npref.addStrData("Conv", "NameConv", s.toString());
		//npref.getStringData("Conv/NameConv");
		//Log.e(activityName, npref.getErrorDesc());
		
		pref.edit().putString(SplashPrepairToNext.LIST_CONVERTER, s.toString()).apply();
		pref.edit().putInt(SplashPrepairToNext.POSITION_FROM, 5).apply();
		pref.edit().putInt(SplashPrepairToNext.POSITION_TO, 1).apply();
		pref.edit().putBoolean(SplashPrepairToNext.ON_BOOT, false).apply();
		pref.edit().putBoolean(SplashPrepairToNext.START_CONV_SERV, false).apply();
		
		//npref.addStrData("Conv", "pathConf", "/data/data/"+packageApp+"/files");
		String asSys = "";
		for(x = 0; x < 6; x++)
		    asSys+=true+",";
		pref.edit().putString(SplashPrepairToNext.AS_SYSTEM, asSys).apply();
		try
		{
			Process vv = Runtime.getRuntime().exec("toolbox touch /data/data/" + packageApp + "/files/.has");
			try
			{
				vv.waitFor();
			}
			catch (InterruptedException e)
			{}
			vv.destroy();
		}
		catch (IOException e)
		{}
	}
	private void extractAsset()
	{
		AssetManager asset = t.getAssets();
		String[] files = null;
		InputStream in = null;
		OutputStream out = null;
		File outFile = null;
		try
		{
			files = asset.list("");
		}
		catch (IOException e)
		{
			Log.e(activityName, "Can't see list files in assests.", e);
		}
		if (files != null)
			for (String name : files)
			{
				if (name.contains(".mconf"))
				{
					try
					{
						in = asset.open(name);
						outFile = new File(t.getFilesDir().getPath(), name);
						out = new FileOutputStream(outFile);
						copyTo(in, out);
					}
					catch (IOException e)
					{
						Log.e(activityName, "Can't get " + name, e);
					}
					finally
					{
						try
						{
							if (in != null)
							{
								in.close();
							}
							if (out != null)
							{
								out.close();
							}
						}
						catch (IOException e)
						{
							Log.e(activityName, "Error closing file.", e);
						}
						finally
						{in = null;out = null;}
					}
				}
			}




	}

	private void copyTo(InputStream in, OutputStream out) throws IOException
	{
		// TODO: Implement this method
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, read);
		}
	}


}
