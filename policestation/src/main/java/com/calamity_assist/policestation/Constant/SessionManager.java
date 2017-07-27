package com.calamity_assist.policestation.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	static Context context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	private static final String PREF_NAME = "Authentication";

	public static final String ID = "u_id";
	public static final String name = "name";
	public static final String email = "email";
	public static final String password = "password";
	public static final String mobile = "mobile";
	public static final String Img = "img";

	
	static SessionManager sessionManager;
	public static SessionManager getInstance() {
		if(null == sessionManager){
		sessionManager = new SessionManager(context);
		}
		return sessionManager;
	}

	// Constructor
	public SessionManager(Context context) {
		SessionManager.context = context;
		pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
		editor.commit();
	}

	public String getID() {
		return pref.getString(ID, "");
	}
	
	public void setID(String uid)
	{
		editor.putString(ID, uid);
		editor.commit();
	}

	public String getNames() {
		return pref.getString(name, "");
	}

	public void setNames(String uname) {
		editor.putString(name, uname);
		editor.commit();
	}
	public String getEmail() {
		return pref.getString(email, "");
	}

	public void setEmail(String uemail)
	{
		editor.putString(email, uemail);
		editor.commit();
	}

	public String getAddress() {
		return pref.getString(password, "");
	}

	public void setAddress(String upassword)
	{
		editor.putString(password, upassword);
		editor.commit();
	}

	public String getMobile() {
		return pref.getString(mobile, "");
	}

	public void setMobile(String umobile)
	{
		editor.putString(mobile, umobile);
		editor.commit();
	}

	public String getwebsite() {
		return pref.getString(Img,"");
	}

	public void setwebsite(String img)
	{
		editor.putString(Img, img);
		editor.commit();
	}
}