package com.mylexz.sandi.lite;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.AsyncTask;
import java.util.concurrent.ExecutionException;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends Activity
{
	TextView state;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		state = (TextView) findViewById(R.id.splashTextView1);
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		Log.i(SplashActivity.this.getClass().getName(), "Checking First Use");
		new CheckFirstUse(this, state, SplashActivity.this.getClass().getName()).execute();
	}
	
	
}
