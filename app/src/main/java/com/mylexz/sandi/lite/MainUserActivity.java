package com.mylexz.sandi.lite;
import android.widget.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.mylexz.sandi.lite.SplashPrepairToNext;
import java.util.ArrayList;
import android.text.TextWatcher;
import android.text.Editable;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.MotionEvent;
import android.content.SharedPreferences;
import android.view.KeyEvent;

public class MainUserActivity extends Activity
{
	Spinner listConverterFrom, listConverterTo;
	TextView result;
	EditText input;
	ImageButton compare, copy;
	// Fields
	boolean[] asSystem;
	String[] converter;
	ArrayList<String> NameConv;
	int positionFrom;
	int positionTo;
	LinearLayout base_layout;
	LinearLayout l1,l2;
	RelativeLayout rel1;
	boolean hasProcess = true;
	SharedPreferences spref;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_user);
		listConverterFrom = (Spinner) findViewById(R.id.onuser_spinnerFrom);
		listConverterTo = (Spinner) findViewById(R.id.onuser_spinnerTo);
		result = (TextView) findViewById(R.id.onuser_output);
		input = (EditText) findViewById(R.id.onuser_input);
		compare = (ImageButton) findViewById(R.id.onuser_compare);
		copy = (ImageButton) findViewById(R.id.onuser_copy);
		base_layout = (LinearLayout) findViewById(R.id.useractiv_linear_base);
		//linear_inner = (LinearLayout) findViewById(R.id.user_linear_inner);
		initializelay();
		base_layout.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					Log.i("onClicked", "at "+p1.getId());
					savepref();
					finishAffinity();
				}
				
			
		});
		copy.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(result.getText() != null){
						ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
						clip.setText(result.getText());
					}
				}
				
			
		});
		input.addTextChangedListener(new TextWatcher(){
			boolean ignore = false;
				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
					Log.i("MainUserActivity", "beforeTextChanged p1="+p1+", p2="+p2+", p3="+p3+", p4="+p4);
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
					Log.i("MainUserActivity", "onTextChanged p1="+p1+", p2="+p2+", p3="+p3+", p4="+p4);
					if(!asSystem[positionFrom] || !asSystem[positionTo]){
						ConverterOptions.loadConverter(null, 0);
					}
					else{
						String tresult = ConverterOptions.convert(NameConv.get(listConverterFrom.getSelectedItemPosition()), NameConv.get(listConverterTo.getSelectedItemPosition()), input.getText().toString());
						result.setText(tresult);
					}
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					// TODO: Implement this method
					if(ignore)return;
					ignore = true;
					
					ignore = false;
				}
				
			
		});
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
		loadExtras();
		prepareSpinner();
		
	}

	private void initializelay()
	{
		// TODO: Implement this method
		spref = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		l1 = (LinearLayout) findViewById(R.id.onuser_linear3ch);
		l2 = (LinearLayout) findViewById(R.id.onuser_linear2);
		rel1 = (RelativeLayout) findViewById(R.id.onuser_relative1);
		l1.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1){
				}});
		l2.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1){}});
		rel1.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1){}});
	}
	private void loadExtras(){
		Bundle data = getIntent().getExtras();
		
		asSystem = data.getBooleanArray(SplashPrepairToNext.AS_SYSTEM);
		NameConv = (ArrayList<String>) data.getStringArrayList(SplashPrepairToNext.LIST_CONVERTER);
		positionFrom = data.getInt(SplashPrepairToNext.POSITION_FROM, 5);
		positionTo = data.getInt(SplashPrepairToNext.POSITION_TO, 1);
	}
	private void onSpinnerFromSelected(int position){
		SpinnerProperties.checkBeforeExecute(listConverterFrom, listConverterTo, SpinnerProperties.SPINNER_FROM, position, positionFrom, positionTo, input, result);
		positionFrom = listConverterFrom.getSelectedItemPosition();
		positionTo = listConverterTo.getSelectedItemPosition();
		Log.i("[SpinnerFrom]", "Selected at "+positionFrom+" content "+NameConv.get(positionFrom));
	}
	private void onSpinnerToSelected(int position){
		SpinnerProperties.checkBeforeExecute(listConverterFrom, listConverterTo, SpinnerProperties.SPINNER_TO, position, positionFrom, positionTo, input, result);
		// check before execute 
		positionFrom = listConverterFrom.getSelectedItemPosition();
		positionTo = listConverterTo.getSelectedItemPosition();
		Log.i("[SpinnerTo]", "Selected at "+positionTo+" content "+NameConv.get(positionTo));
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
		
	}
	private void savepref(){
		spref.edit().putInt(SplashPrepairToNext.POSITION_FROM, positionFrom).apply();
		spref.edit().putInt(SplashPrepairToNext.POSITION_TO, positionTo).apply();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode == KeyEvent.KEYCODE_BACK){
			savepref();
			finishAffinity();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
