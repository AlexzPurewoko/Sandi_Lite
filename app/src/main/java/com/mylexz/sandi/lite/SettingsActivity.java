package com.mylexz.sandi.lite;
import android.content.*;
import android.preference.*;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class SettingsActivity extends PreferenceActivity
{
	CheckBoxPreference on_boot, active;
	boolean on_boot_serv, active_serv;
	SharedPreferences pref;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_activity);
		getPreferenceManager().setSharedPreferencesName(getPackageName());
		on_boot = (CheckBoxPreference) findPreference(SplashPrepairToNext.ON_BOOT);
		
		active = (CheckBoxPreference) findPreference(SplashPrepairToNext.START_CONV_SERV);
		pref = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		active_serv = pref.getBoolean(SplashPrepairToNext.START_CONV_SERV, false);
		on_boot_serv = pref.getBoolean(SplashPrepairToNext.ON_BOOT, false);
		if(active_serv){
			active.setChecked(true);
			on_boot.setChecked(on_boot_serv);
		}
		else {
			active.setChecked(false);
			on_boot.setChecked(on_boot_serv);
			on_boot.setEnabled(false);
		}
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		active.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){

				@Override
				public boolean onPreferenceChange(Preference p1, Object p2)
				{
					// TODO: Implement this method
					Log.i("SettingsActivity", "boolean "+((Boolean)p2).booleanValue());
					boolean result = ((Boolean)p2).booleanValue();
					active_serv = result;
					update();
					return result;
				}
				
			
		});
		on_boot.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){

				@Override
				public boolean onPreferenceChange(Preference p1, Object p2)
				{
					// TODO: Implement this method
					Log.i("SettingsActivity", "on_boot boolean "+((Boolean)p2).booleanValue());
					boolean result = ((Boolean)p2).booleanValue();
					on_boot_serv = result;
					update();
					return result;
				}
				
			
		});
	}
	private void update(){
		if(active_serv){
			on_boot.setEnabled(true);
			active.setChecked(active_serv);
			on_boot.setChecked(on_boot_serv);
		}
		else {
			active.setChecked(active_serv);
			on_boot.setChecked(on_boot_serv);
			on_boot.setEnabled(false);
		}
		pref.edit().putBoolean(SplashPrepairToNext.ON_BOOT, on_boot_serv).apply();
		pref.edit().putBoolean(SplashPrepairToNext.START_CONV_SERV, active_serv).apply();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			startActivity(new Intent(this, SplashActivity.class));
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
}
