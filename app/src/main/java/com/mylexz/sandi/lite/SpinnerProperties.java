package com.mylexz.sandi.lite;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;

public class SpinnerProperties
{
	public static final int SPINNER_FROM = 0,
	SPINNER_TO = 1;
	public static void checkBeforeExecute(Spinner spFrom, Spinner spTo, int typeSpin, int currentpos, int last_fpos, int last_tpos, EditText t, TextView u){
		switch(typeSpin){
			case SPINNER_FROM:
			{
				if(currentpos == last_tpos){
					spFrom.setSelection(last_tpos);
					spTo.setSelection(last_fpos);
					String tmp = u.getText().toString();
					String fr = (String)spFrom.getItemAtPosition(last_tpos);
					String to = (String)spTo.getItemAtPosition(last_fpos);
					String tresult = ConverterOptions.convert(fr, to, tmp);
					u.setText(tresult);
					t.setText(tmp);
				}
				else {
					spFrom.setSelection(currentpos);
					if(currentpos != last_fpos){
						String tmp1 = ConverterOptions.convert(spFrom.getItemAtPosition(last_fpos).toString(), spFrom.getItemAtPosition(currentpos).toString(), t.getText().toString());
						u.setText("");
						t.setText(tmp1);
					}
					//u.setText("");
					//t.setText("");
				}
			}
			break;
			case SPINNER_TO:
			{
				if(currentpos == last_fpos){
					spTo.setSelection(last_fpos);
					spFrom.setSelection(last_tpos);
					
					String tmp = u.getText().toString();
					String fr = (String)spFrom.getItemAtPosition(spFrom.getSelectedItemPosition());
					String to = (String)spTo.getItemAtPosition(spTo.getSelectedItemPosition());
					String tresult = ConverterOptions.convert(fr, to, tmp);
					u.setText(tresult);
					t.setText(tmp);
				}
				else {
					spTo.setSelection(currentpos);
					if(currentpos != last_tpos){
						String fr = (String)spFrom.getSelectedItem();
						String to = (String)spTo.getSelectedItem();
						String tmp = ConverterOptions.convert(fr, to, t.getText().toString());
						u.setText(tmp);
					}
					//t.setText("");
					//u.setText("");
				}
			}
		}
	}
	public static void swapComp(Spinner from, Spinner To, EditText u, TextView t){
		int pos = from.getSelectedItemPosition();
		from.setSelection(To.getSelectedItemPosition());
		To.setSelection(pos);
		
		String tmp = t.getText().toString();
		String fr = (String)from.getItemAtPosition(from.getSelectedItemPosition());
		String to = (String)To.getItemAtPosition(To.getSelectedItemPosition());
		String tresult = ConverterOptions.convert(fr, to, tmp);
		t.setText(tresult);
		u.setText(tmp);
	}
}
