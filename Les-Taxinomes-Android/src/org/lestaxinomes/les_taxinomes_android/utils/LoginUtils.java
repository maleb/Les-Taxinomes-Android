package org.lestaxinomes.les_taxinomes_android.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginUtils {


	public static final String PREFS_NAME = "LoginPrefs";

	public static boolean isLoggued(Context ctx) {
		
		
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		if (settings.getString("logged", "").toString().equals("logged")) {
			return true;
		}
		return false;
		
	}
	
	public static String getLogin(Context ctx){
		
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString("login", "").toString();
		
		
	}
	
	public static String getPassword(Context ctx){
		
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		return settings.getString("password", "").toString();
		
		
	}

	public static void setLoggued(Context ctx, boolean b, String login,
			String password) {

		if (b) {
			
			
			
			SharedPreferences settings = ctx
					.getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("logged", "logged");
			editor.putString("login", login);
			editor.putString("password", password);
			editor.commit();
		} else {
			SharedPreferences settings = ctx
					.getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("logged", "");
			editor.putString("login", "");
			editor.putString("password", "");
			editor.commit();

		}

	}

}
