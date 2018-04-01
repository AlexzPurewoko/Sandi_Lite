package com.mylexz.sandi.lite;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.content.Intent;

public class AboutActivity extends Activity
{
	Bundle data;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		data = getIntent().getExtras();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case android.R.id.home:
			{
				finish();
				Intent i = new Intent(this, MainActivity.class);
				i.putExtras(data);
				startActivity(i);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
