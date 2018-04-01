package com.mylexz.sandi.lite;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;

import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;

public class MainActivity extends Activity 
{

	
	// Widgets
	Spinner listConverterFrom, listConverterTo;
	TextView state_from, state_to, result;
	EditText input;
	ImageButton compare;
	Button btn_convert, btn_clear;
	// Fields
	boolean hasFirstUse, start_serv;
	boolean[] asSystem;
	String pathConf;
	String[] converter;
	ArrayList<String> NameConv;
	String[] fnmConv;
	int positionFrom;
	int positionTo;
	Bundle data;
	SharedPreferences spref;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		listConverterFrom = (Spinner) findViewById(R.id.main_spinner_from);
		listConverterTo = (Spinner) findViewById(R.id.main_spinner_to);
		state_from = (TextView) findViewById(R.id.main_txtFrom);
		state_to = (TextView) findViewById(R.id.main_txtTo);
		result = (TextView) findViewById(R.id.main_txt_result);
		input = (EditText) findViewById(R.id.main_editfrom);
		compare = (ImageButton) findViewById(R.id.main_img_btn_compare);
		btn_convert = (Button) findViewById(R.id.main_btn_convert);
		btn_clear = (Button) findViewById(R.id.main_btn_clear);
		result.setTextIsSelectable(true);
		compare.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					SpinnerProperties.swapComp(listConverterFrom, listConverterTo, input, result);
					positionTo = listConverterTo.getSelectedItemPosition();
					positionFrom = listConverterFrom.getSelectedItemPosition();
					
					
				}
				
			
		});
		btn_convert.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(!asSystem[positionFrom] || !asSystem[positionTo]){
						ConverterOptions.loadConverter(null, 0);
					}
					else{
						String tresult = ConverterOptions.convert(NameConv.get(positionFrom), NameConv.get(positionTo), input.getText().toString());
						result.setText(tresult);
					}
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
					alert.setIcon(R.drawable.ic_launcher);
					alert.setTitle(listConverterFrom.getItemAtPosition(positionFrom).toString()+" To "+listConverterTo.getItemAtPosition(positionTo).toString());
					alert.setMessage(result.getText().toString());
					alert.setCancelable(false);
					alert.setPositiveButton(R.string.COPY_BTN, new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								ClipboardManager clip = (ClipboardManager) MainActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
								clip.setText(result.getText().toString());
								p1.cancel();
							}
							
						
					});
					alert.setNegativeButton(R.string.CANCEL_BTN, new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								// TODO: Implement this method
								p1.cancel();
								
							}
							
						
					});
					alert.show();
				}
				
			
		});
		btn_clear.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					input.setText("");
					result.setText("");
				}
				
			
		});
		loadExtras();
		prepareSpinner();
		spref = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		if(!hasFirstUse){
			showExplanation();
		}
		else
			startNotificationService();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// TODO: Implement this method
		super.onSaveInstanceState(outState);
	}

	
	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		MenuInflater  m = getMenuInflater();
		m.inflate(R.layout.menu, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		
		Log.i("Menu", "Selected menu "+item.getTitle()+" position "+item.getItemId());
		switch(item.getItemId()){
			/*case R.id.start_notif:{
				Intent a = new Intent(this, MainUserActivity.class);
				a.putExtra(SplashPrepairToNext.LIST_CONVERTER, NameConv);
				a.putExtra(SplashPrepairToNext.POSITION_FROM, positionFrom);
				a.putExtra(SplashPrepairToNext.POSITION_TO, positionTo);
				a.putExtra(SplashPrepairToNext.AS_SYSTEM, asSystem);
				startActivity(a);}*/
			case R.id.menu_about:
			{
				Intent i = new Intent(this, AboutActivity.class);
				i.putExtras(data);
				startActivity(i);
			}
			break;
			case R.id.menu_settings:
			{
				startActivity(new Intent(this, SettingsActivity.class));
			}
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
	
	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		SharedPreferences.Editor ed = spref.edit();
		ed.putInt(SplashPrepairToNext.POSITION_FROM, positionFrom);
		ed.putInt(SplashPrepairToNext.POSITION_TO, positionTo);
		ed.apply();
		super.onPause();
	}
	
	private void loadExtras(){
		data = getIntent().getExtras();
		converter = data.getStringArray(SplashPrepairToNext.LIST_CONVERTER);
		asSystem = data.getBooleanArray(SplashPrepairToNext.AS_SYSTEM);
		hasFirstUse = data.getBoolean(SplashPrepairToNext.HAS_USAGE, false);
		pathConf = data.getString(SplashPrepairToNext.PATH_CONF);
		NameConv = (ArrayList<String>) data.getStringArrayList(SplashPrepairToNext.LIST_CONVERTER);
		fnmConv = (String[]) data.getStringArray(SplashPrepairToNext.LIST_FILES);
		positionFrom = data.getInt(SplashPrepairToNext.POSITION_FROM, 5);
		positionTo = data.getInt(SplashPrepairToNext.POSITION_TO, 1);
	}
	private void onSpinnerFromSelected(int position){
		SpinnerProperties.checkBeforeExecute(listConverterFrom, listConverterTo, SpinnerProperties.SPINNER_FROM, position, positionFrom, positionTo, input, result);
		positionFrom = listConverterFrom.getSelectedItemPosition();
		positionTo = listConverterTo.getSelectedItemPosition();
		Log.i("[SpinnerFrom]", "Selected at "+positionFrom+" content "+NameConv.get(positionFrom));
		state_from.setText(NameConv.get(positionFrom));
	}
	private void onSpinnerToSelected(int position){
		SpinnerProperties.checkBeforeExecute(listConverterFrom, listConverterTo, SpinnerProperties.SPINNER_TO, position, positionFrom, positionTo, input, result);
		positionFrom = listConverterFrom.getSelectedItemPosition();
		positionTo = listConverterTo.getSelectedItemPosition();
		Log.i("[SpinnerTo]", "Selected at "+positionTo+" content "+NameConv.get(positionTo));
		state_to.setText(NameConv.get(positionTo));
	}
	private void prepareSpinner()
	{
		// TODO: Implement this method
		ArrayAdapter<String> Conv = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, NameConv);
		Conv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		listConverterFrom.setAdapter(Conv);
		listConverterTo.setAdapter(Conv);
		listConverterTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
				{
					// TODO: Implement this method
					onSpinnerToSelected(p3);
				}

				@Override
				public void onNothingSelected(AdapterView<?> p1)
				{
					// TODO: Implement this method
				}
				
			
		});
		listConverterFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
				{
					// TODO: Implement this method
					onSpinnerFromSelected(p3);
				}

				@Override
				public void onNothingSelected(AdapterView<?> p1)
				{
					// TODO: Implement this method
				}
				
			
		});
		listConverterTo.setSelection(positionTo);
		listConverterFrom.setSelection(positionFrom);
		//Log.i("Spinner", "From : "+NameConv.get(p3));
		state_from.setText(NameConv.get(listConverterFrom.getSelectedItemPosition()));
		//Log.i("Spinner", "From : "+NameConv.get(p3));
		state_to.setText(NameConv.get(listConverterTo.getSelectedItemPosition()));
	}
	private void showExplanation(){
		AlertDialog.Builder t = new AlertDialog.Builder(this);
		t.setIcon(R.drawable.ic_launcher);
		t.setMessage(R.string.main_firstusage);
		t.setTitle("Explanation");
		t.setCancelable(false);
		t.setNegativeButton("Okay", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					p1.cancel();
					showPref();
				}
				
			
		});
		t.show();
	}
	private void showPref()
	{
		// TODO: Implement this method
		AlertDialog.Builder t = new AlertDialog.Builder(this);
		t.setIcon(R.drawable.ic_launcher);
		t.setMessage(R.string.main_tell_user_convertanytime);
		t.setTitle("Preferences");
		t.setCancelable(false);
		t.setPositiveButton(R.string.OK_BUTTON, new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					spref.edit().putBoolean(SplashPrepairToNext.START_CONV_SERV, true).apply();
					start_serv = true;
					startNotificationService();
					p1.cancel();
					showPref2();
				}


			});
		t.setNegativeButton(R.string.NO_BUTTON, new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					p1.cancel();
				}


			});
		t.show();
	}
	private void showPref2()
	{
		// TODO: Implement this method
		AlertDialog.Builder t = new AlertDialog.Builder(this);
		t.setIcon(R.drawable.ic_launcher);
		t.setMessage(R.string.main_tell_user_startatboot);
		t.setTitle("Preferences");
		t.setCancelable(false);
		t.setPositiveButton(R.string.OK_BUTTON, new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					spref.edit().putBoolean(SplashPrepairToNext.ON_BOOT, true).apply();
					Toast.makeText(MainActivity.this, getResources().getString(R.string.main_tell_user_moresetting), Toast.LENGTH_LONG).show();
					p1.cancel();
				}


			});
		t.setNegativeButton(R.string.NO_BUTTON, new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					Toast.makeText(MainActivity.this, getResources().getString(R.string.main_tell_user_moresetting), Toast.LENGTH_LONG).show();
					p1.cancel();
				}


			});
		t.show();
	}
	private void startNotificationService()
	{
		// TODO: Implement this method
		start_serv = spref.getBoolean(SplashPrepairToNext.START_CONV_SERV, false);
		if(start_serv){
			if(!CheckService.isServiceRunning(this, NotificationService.class)){
				// start service
				Intent serv = new Intent(this, NotificationService.class);
				startService(serv);
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			
			boolean res =moveTaskToBack(true);
			Log.i("Main", "move Task : "+res);
			return true;
		}
		return super.onKeyDown(keyCode, event);
		
	}
	
}
