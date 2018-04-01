package com.mylexz.sandi.lite;

import java.lang.reflect.Method;
import com.mylexz.utils.Sandi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;

public class ConverterOptions
{
	public static final int TO_STR = 0,
	TO_ANY = 1;
	private static final String TAG = "ConverterProcess";
	public static String loadConverter(String nameFile, int convertTo){
		return null;
	}
	public static String convert(String preffix, String suffix, String data){
		String method = preffix+"To"+suffix;
		Method m;
		try
		{
			m = Sandi.class.getMethod(method, String.class);
			String result = (String)m.invoke(new Sandi(), data);
			return (result == null)?data:result;
		}
		catch (NoSuchMethodException e)
		{
			Log.e(TAG, "NoSuchMethodException! ", e);
		}
		catch (SecurityException e)
		{
			Log.e(TAG, "SecurityException! ", e);
		}
		catch (IllegalAccessException e){
			Log.e(TAG, "IllegalAccessException! ", e);
		}
		catch (InvocationTargetException e){
			Log.e(TAG, "InvocationTargetException! ", e);
		}
		return data;
	}
}
